package io.wabm.supermarket.model.warehouse;

import io.wabm.supermarket.misc.enums.OrderStatusEnum;
import io.wabm.supermarket.misc.pojo.Order;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.misc.util.WABMThread;
import io.wabm.supermarket.model.TableViewModel;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import org.springframework.dao.DataAccessException;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Created by MainasuK on 2016-12-12.
 */
public class TransportingOrderModel<T> extends TableViewModel<T> {

    private String kSelectTransprtingSQL = "SELECT o.*, s.name FROM `order` o JOIN supplier s ON o.supplier_id = s.supplier_id WHERE o.status = 2;";

    public TransportingOrderModel(TableView<T> tableView) {
        super(tableView);
    }

    public void fetchData(Callback<Boolean, Void> callback) {
        ConsoleLog.print("fetching…");
        Assert.notNull(jdbcOperations);

        new WABMThread().run((_void) -> {

            try {
                List<Order> templist = jdbcOperations.query(kSelectTransprtingSQL, (resultSet, i) -> new Order(
                        resultSet.getInt("order_id"),
                        resultSet.getInt("order_id"),
                        resultSet.getString("name"),
                        resultSet.getString("create_timestamp"),
                        OrderStatusEnum.values()[resultSet.getInt("status")])
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
