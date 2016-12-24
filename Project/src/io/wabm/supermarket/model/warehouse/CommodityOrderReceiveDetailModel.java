package io.wabm.supermarket.model.warehouse;

import io.wabm.supermarket.application.Main;
import io.wabm.supermarket.misc.pojo.CMKOrderDetail;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.misc.util.WABMThread;
import io.wabm.supermarket.model.TableViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by MainasuK on 2016-12-12.
 */
public class CommodityOrderReceiveDetailModel<T> extends TableViewModel<T> {

    private DataSourceTransactionManager transactionManager = Main.getTransactionManager();
    private StringProperty totalPrice = new SimpleStringProperty();

    private final String kSelectOrderDetailAndCommodityWithOrderID = "SELECT co.*, od.order_id, od.order_detail_id, od.quantity, od.price_db purchase_price, od.production_date FROM order_detail od JOIN `order` o ON od.order_id = o.order_id JOIN commodity co ON od.commodity_id = co.commodity_id WHERE o.order_id = ?;";

    public CommodityOrderReceiveDetailModel(TableView<T> tableView) {
        super(tableView);
    }

    public void fetchOrderDetailWith(int orderID, Callback<DataAccessException, Void> callback) {
        ConsoleLog.print("fetching order detail…");

        new WABMThread().run(_void -> {

            try {
                List<CMKOrderDetail> tempList = jdbcOperations.query(kSelectOrderDetailAndCommodityWithOrderID, (resultSet, i) -> {
                    LocalDate date = null;

                    if (resultSet.getDate("production_date") != null) {
                        date = resultSet.getDate("production_date").toLocalDate();
                    }

                    CMKOrderDetail orderDetail = new CMKOrderDetail(
                            resultSet.getInt("order_id"),
                            resultSet.getInt("order_detail_id"),
                            resultSet.getInt("quantity"),
                            resultSet.getBigDecimal("purchase_price"),
                            date,
                            resultSet
                    );
                    return orderDetail;
                }, orderID);

                list.clear();
                list.addAll((T[]) tempList.toArray());

                callback.call(null);

            } catch (DataAccessException exception) {
                exception.printStackTrace();
                callback.call(exception);
            } catch (Exception exception) {
                exception.printStackTrace();
                callback.call(new DataAccessResourceFailureException("Unknown error"));
            }

            return null;
        });
    }

    public void confirmOrderWith(int orderID, Callback<DataAccessException, Void> callback) {
        ConsoleLog.print("confirm order: " + orderID);

        new WABMThread().run(_void -> {

            DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
            TransactionStatus status = transactionManager.getTransaction(definition);

            try {

                BatchPreparedStatementSetter batchPreparedStatementSetter = new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                        CMKOrderDetail detail = ((CMKOrderDetail) list.get(i));
                        Date date = null;
                        if (detail.getProductionDate() != null) {
                            date = Date.valueOf(detail.getProductionDate());
                        }
                        preparedStatement.setInt(1, detail.getActualQuantity());
                        preparedStatement.setDate(2, date);
                        preparedStatement.setInt(3, detail.getOrderDetailID());
                    }

                    @Override
                    public int getBatchSize() {
                        return list.size();
                    }
                };

                jdbcOperations.batchUpdate("UPDATE order_detail od SET od.actual_quantity = ?, od.production_date = ? WHERE od.order_detail_id = ?", batchPreparedStatementSetter);

                jdbcOperations.update("UPDATE `order` o SET o.status = 3 WHERE o.order_id = ?", orderID);

                transactionManager.commit(status);
                callback.call(null);
                ConsoleLog.print("done");

            } catch (DataAccessException exception) {
                exception.printStackTrace();

                transactionManager.rollback(status);
                callback.call(exception);
            } catch (Exception exception) {
                exception.printStackTrace();

                transactionManager.rollback(status);
                callback.call(new DataAccessResourceFailureException("Unknown error"));
            }

            return null;
        });
    }

    public String getTotalPrice() {
        return totalPrice.get();
    }

    public StringProperty totalPriceProperty() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice.set(totalPrice);
    }

    public void calculate() {
        double totoal = list.parallelStream()
                .filter(od -> od instanceof CMKOrderDetail)
                .map(od -> (CMKOrderDetail) od)
                .mapToDouble(od -> od.getPurchasePrice().doubleValue() * od.getActualQuantity())
                .sum();

        setTotalPrice(String.format("%.2f", totoal));
    }
}
