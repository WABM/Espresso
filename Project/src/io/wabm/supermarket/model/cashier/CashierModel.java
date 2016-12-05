package io.wabm.supermarket.model.cashier;

import io.wabm.supermarket.application.CashierMain;
import io.wabm.supermarket.misc.pojo.Commodity;
import io.wabm.supermarket.misc.pojo.SalesRecordDetail;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.misc.util.WABMThread;
import io.wabm.supermarket.model.TableViewModel;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.swing.*;
import java.math.BigDecimal;
import java.util.Optional;


/**
 * Created by MainasuK on 2016-12-2.
 */
public class CashierModel<T> extends TableViewModel<T> {

    private final String kSelectCommoditySQL = "SELECT co.* FROM commodity co WHERE co.valid = 1 AND (co.bar_code = ? OR co.commodity_id = ?) LIMIT 1;";

    private StringProperty status;

    private Commodity currentCommodity = null;
    private int commodityCount = 0;
    private BigDecimal totalPrice = null;

    public CashierModel(TableView<T> tableView) {
        super(tableView);

        setupControl();

        // init JDBC
        try { jdbcOperations.execute("SELECT  1"); }
        catch (Exception e) { ConsoleLog.print("init JDBC failed"); }

        // Check connection every 10s
        Timer timer = new Timer(10000, arg0 -> {
            Platform.runLater(() -> {
            boolean isConnected;
            try {
                jdbcOperations.execute("SELECT 1");
                isConnected = true;
            } catch (Exception e) {
                isConnected = false;
            }

            // Change property in JavaFX thread
            setStatus(isConnected ? "正常收银" : "断开连接");
            });

        });
        timer.setRepeats(true);
        timer.start();
    }

    private void setupControl() {
        status = new SimpleStringProperty("检查中…");

        totalPrice = new BigDecimal(0.00);
        totalPrice.setScale(2);

    }

    @Override
    protected void setupJdbcOperations() {
        jdbcOperations = CashierMain.getJdbcOperations();
    }

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
}
