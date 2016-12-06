package io.wabm.supermarket.model.procurement;

import io.wabm.supermarket.misc.pojo.Order;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.misc.util.WABMThread;
import io.wabm.supermarket.model.TableViewModel;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import org.springframework.dao.DataAccessException;
import org.springframework.util.Assert;
import java.sql.ResultSet;
import java.util.List;

/**
 * Created by 14580 on 2016/12/4 0004.
 */
public class CommodityOrderModel<T> extends TableViewModel<T> {
    private final String kSelectAll = "SELECT  order_id,suppiler_id,create_timestamp,status FROM wabm.order";

    public CommodityOrderModel(TableView<T> tableView) {
        super(tableView);
        ConsoleLog.print("CommodityOrderModel init");
    }

    public void fetchData(Callback<Boolean, Void> callback) {
        ConsoleLog.print("fetching data…");
        Assert.notNull(jdbcOperations);
        new WABMThread().run((_void) -> {
            ConsoleLog.print("Start background task…");
            try{
                List<Order> templist = jdbcOperations.query(kSelectAll, (ResultSet resultSet, int i) -> {
                    Order order= new Order(
                                    resultSet.getInt("order_id"),
                                    resultSet.getInt("supplier_id"),
                                    resultSet.getString("create_timestamp"),
                                    resultSet.getInt("status")
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
