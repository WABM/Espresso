package io.wabm.supermarket.model.cashier;

import io.wabm.supermarket.application.Main;
import io.wabm.supermarket.misc.pojo.Commodity;
import io.wabm.supermarket.misc.pojo.SalesRecordDetail;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.misc.util.SingleLogin;
import io.wabm.supermarket.misc.util.WABMThread;
import io.wabm.supermarket.model.TableViewModel;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.swing.*;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


/**
 * Created by MainasuK on 2016-12-2.
 */
public class CashierModel<T> extends TableViewModel<T> {

    private DataSourceTransactionManager transactionManager = Main.getTransactionManager();
    private final String kSelectCommoditySQL = "SELECT co.* FROM commodity co WHERE co.valid = 1 AND (binary co.bar_code = ? OR binary co.commodity_id = ?) LIMIT 1;";
    private final String kInsertSalesRecordWIthAutoIncr = "INSERT INTO sales_record (employee_id, all_price_db) VALUES (?, ?)";

    private StringProperty status;

    private Commodity currentCommodity = null;
    private int commodityCount = 0;
    private BigDecimal totalPrice = null;

    public CashierModel(TableView<T> tableView) {
        super(tableView);

        setupProperty();

        // init JDBC
//        try { jdbcOperations.queryForObject("SELECT co.* FROM commodity co LIMIT 1", (resultSet, i) -> new Commodity(resultSet)); }
//        catch (Exception e) { ConsoleLog.print("init JDBC failed"); }

        // Check connection every 10s
        Timer timer = new Timer(10000, arg0 -> {
            boolean isConnected;
            try {
                jdbcOperations.execute("SELECT 1");
                isConnected = true;
            } catch (Exception e) {
                isConnected = false;
            }

            boolean finalIsConnected = isConnected;
            Platform.runLater(() -> {
            // Change property in JavaFX thread
            setStatus(finalIsConnected ? "正常收银" : "断开连接");
            });

        });
        timer.setRepeats(true);
        timer.start();
    }

    private void setupProperty() {
        status = new SimpleStringProperty("检查中…");

        totalPrice = new BigDecimal(0.00);
        totalPrice.setScale(2);
    }

//    @Override
//    protected void setupJdbcOperations() {
//        jdbcOperations = CashierMain.getJdbcOperations();
//    }

    public void addCommoditytWith(String text, Callback<DataAccessException, Void> callback) {
        ConsoleLog.print("Check bar code: " + text);

        new WABMThread().run(_void -> {

            try {
                SalesRecordDetail saleRecoredDetail = jdbcOperations.queryForObject(kSelectCommoditySQL, (resultSet, i) -> new SalesRecordDetail(new Commodity(resultSet)), text, text);

                Optional<SalesRecordDetail> optional = list.parallelStream()
                        .filter(t -> t instanceof SalesRecordDetail)
                        .map(c -> (SalesRecordDetail) c)
                        .filter(s -> s.getCommodity().getBarcode().equals(saleRecoredDetail.getCommodity().getBarcode()))
                        .findFirst();


                if (optional.isPresent()) {
                    optional.ifPresent(s -> s.addQuantity(1));
                } else {
                    list.add((T) saleRecoredDetail);
                }


                // Deal with model change
                currentCommodity = saleRecoredDetail.getCommodity();
                commodityCount += 1;

                totalPrice = totalPrice.add(saleRecoredDetail.getSalesPrice());

                callback.call(null);

            } catch (EmptyResultDataAccessException emptyException) {
                callback.call(emptyException);
                ConsoleLog.print("No result");

                // emptyException.printStackTrace();
            } catch (DataAccessException exception) {
                callback.call(exception);
                exception.printStackTrace();
            }


            return null;
        });
    }

    public DataAccessException pay() {
        ConsoleLog.print("handle pay…");
        DataAccessException dataAccessException = null;

        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(definition);

        int id = SingleLogin.getInstance().getEmployee().getEmployeeID();
        BigDecimal totalPrice = getTotalPrice();

        try {
            KeyHolder holder = new GeneratedKeyHolder();

            // Create preparedStatementCreator via lambda
            PreparedStatementCreator insertSalesRecordStatementCreator = connection -> {
                PreparedStatement preparedStatement = connection.prepareStatement(kInsertSalesRecordWIthAutoIncr, Statement.RETURN_GENERATED_KEYS);

                preparedStatement.setInt(1, id);
                preparedStatement.setBigDecimal(2, totalPrice);

                return preparedStatement;
            };

            ConsoleLog.print("insert sales record");
            jdbcOperations.update(insertSalesRecordStatementCreator, holder);

            int salesRecoredID = holder.getKey().intValue();

            BatchPreparedStatementSetter batchPreparedStatementSetter = new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                    SalesRecordDetail detail = ((SalesRecordDetail) list.get(i));
                    preparedStatement.setInt(1, salesRecoredID);
                    preparedStatement.setString(2, detail.getCommodity().getCommodityID());
                    preparedStatement.setInt(3, detail.getQuantity());
                    preparedStatement.setBigDecimal(4, detail.getSalesPrice());
                }

                @Override
                public int getBatchSize() {
                    return list.size();
                }
            };

            ConsoleLog.print("insert sales record details");
            jdbcOperations.batchUpdate("INSERT INTO sales_record_detail (sales_record_id, commodity_id, quantity, price_db) VALUES (?, ?, ?, ?)", batchPreparedStatementSetter);

            transactionManager.commit(status);
            ConsoleLog.print("done");

        } catch (DataAccessException exception) {
            exception.printStackTrace();

            transactionManager.rollback(status);
            dataAccessException = exception;
        } catch (Exception exception) {
            exception.printStackTrace();

            transactionManager.rollback(status);
            dataAccessException = new DataAccessResourceFailureException("Unknown error");
        }

        return dataAccessException;
    }

    public void clear() {
        ConsoleLog.print("Model clear…");

        list.clear();
        currentCommodity = null;
        commodityCount = 0;
        totalPrice = new BigDecimal(0.00);
        totalPrice.setScale(2);
    }

    public String getStatus() {
        return status.get();
    }

    public StringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public Commodity getCurrentCommodity() {
        return currentCommodity;
    }

    public int getCommodityCount() {
        return commodityCount;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void resetTotalPrice() {
        final BigDecimal[] price = {new BigDecimal(0.0)};
        price[0].setScale(2);
        final int[] totalQuantity = {0};

        list.parallelStream()
                .filter(t -> t instanceof SalesRecordDetail)
                .map(c -> (SalesRecordDetail) c)
                .forEach(c -> {
                    int quantity = c.getQuantity();
                    BigDecimal sum = c.getSalesPrice().multiply(new BigDecimal(quantity));

                    c.setTotalPrice(sum);

                    Platform.runLater(() -> {
                        if (c.getQuantity() == 0) {
                            list.remove(c);
                        }
                    });

                    // Side effect
                    price[0] = price[0].add(sum);
                    totalQuantity[0] += quantity;
                });

        totalPrice = price[0];
        commodityCount = totalQuantity[0];
    }
}
