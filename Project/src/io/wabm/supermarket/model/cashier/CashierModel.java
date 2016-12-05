package io.wabm.supermarket.model.cashier;

import io.wabm.supermarket.misc.pojo.Commodity;
import io.wabm.supermarket.misc.pojo.SalesRecordDetail;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.misc.util.WABMThread;
import io.wabm.supermarket.model.TableViewModel;
import javafx.application.Platform;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;

/**
 * Created by MainasuK on 2016-12-2.
 */
public class CashierModel<T> extends TableViewModel<T> {

    private final String kSelectCommoditySQL = "SELECT co.* FROM commodity co WHERE co.valid = 1 AND (co.bar_code = ? OR co.commodity_id = ?) LIMIT 1;";

    private Commodity currentCommodity = null;
    private int commodityCount = 0;
    private BigDecimal totalPrice = null;

    public CashierModel(TableView<T> tableView) {
        super(tableView);

        totalPrice = new BigDecimal(0.00);
        totalPrice.setScale(2);

        // Init JDBC
        jdbcOperations.queryForObject("SELECT co.* FROM commodity co LIMIT 1", (resultSet, i) -> new Commodity(resultSet));
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
