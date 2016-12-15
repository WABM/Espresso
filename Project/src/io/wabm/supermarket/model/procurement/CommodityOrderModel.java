package io.wabm.supermarket.model.procurement;

import io.wabm.supermarket.misc.enums.OrderStatusEnum;
import io.wabm.supermarket.misc.pojo.Classification;
import io.wabm.supermarket.misc.pojo.Order;
import io.wabm.supermarket.misc.pojo.Supplier;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.misc.util.WABMThread;
import io.wabm.supermarket.model.TableViewModel;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.util.Assert;
import java.sql.ResultSet;
import java.util.List;

/**
 * Created by 14580 on 2016/12/4 0004.
 */
public class CommodityOrderModel<T> extends TableViewModel<T> {
    private String kSelectAll = "SELECT\n" +
            "  order.order_id,\n" +
            "  supplier.name,\n" +
            "  order.create_timestamp,\n" +
            "  order.status\n" +
            "from    wabm.order , wabm.supplier\n" +
            "where   order.supplier_id = supplier.supplier_id and supplier.valid=1";
    private String KSelectone = "SELECT\n" +
            "  order.order_id,\n" +
            "  supplier.name,\n" +
            "  order.create_timestamp,\n" +
            "  order.status\n" +
            "from    wabm.order , wabm.supplier\n" +
            "where   order.supplier_id = supplier.supplier_id and supplier.valid=1 and order.status=?";

    private String kPassSQLWithID = "UPDATE wabm.order  SET status=2 WHERE wabm.order.order_id = ?; ";
    public CommodityOrderModel(TableView<T> tableView) {
        super(tableView);
        ConsoleLog.print("CommodityOrderModel init");
    }

    public void pass(Order order, Callback<DataAccessException, Void> callback) {
        ConsoleLog.print("pass order: " + order.getOrderID());
        Assert.notNull(jdbcOperations);
        try {
            jdbcOperations.update(kPassSQLWithID, order.getOrderID());
            delete((T) order);
            callback.call(null);
        } catch (QueryTimeoutException timeoutException) {
            callback.call(timeoutException);
        } catch (DataAccessException dataAccessException) {
            callback.call(dataAccessException);
        }
    }
    public void Choose(Integer status,Callback<Boolean, Void> callback) {
        ConsoleLog.print("fetching data…");

        new WABMThread().run((_void) -> {
            try {
                List<Order> templist = jdbcOperations.query(KSelectone, (ResultSet resultSet, int i) -> {
                            Order order= new Order(
                                    resultSet.getInt("order.order_id"),
                                    resultSet.getString("supplier.name"),
                                    resultSet.getString("order.create_timestamp"),
                                    OrderStatusEnum.values()[resultSet.getInt("order.status")]
                            );
                            return order;
                        }
                ,status);

                list.clear();
                list.addAll((T[]) templist.toArray());
            }catch (DataAccessException e){
                e.printStackTrace();
            }


            // TODO:
            callback.call(true);
            return null;
        });

    }
    public void fetchData(Callback<Boolean, Void> callback) {
        ConsoleLog.print("fetching data…");
        Assert.notNull(jdbcOperations);
        new WABMThread().run((_void) -> {
            ConsoleLog.print("Start background task…");
            try{
                List<Order> templist = jdbcOperations.query(kSelectAll, (ResultSet resultSet, int i) -> {
                    Order order= new Order(
                                    resultSet.getInt("order.order_id"),
                                    resultSet.getString("supplier.name"),
                                    resultSet.getString("order.create_timestamp"),
                                    OrderStatusEnum.values()[resultSet.getInt("order.status")]
                            );
                            return order;
                        }
                );
                list.clear();
                list.addAll((T[]) templist.toArray());
                callback.call(true);
            } catch (DataAccessException exception) {
                callback.call(false);
                exception.printStackTrace();
            }
            return null;
        });

    }
}
