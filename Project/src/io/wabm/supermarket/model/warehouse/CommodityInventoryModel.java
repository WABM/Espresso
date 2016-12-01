package io.wabm.supermarket.model.warehouse;

import io.wabm.supermarket.misc.pojo.Commodity;
import io.wabm.supermarket.misc.pojo.Inventory;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.model.TableViewModel;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * Created by MainasuK on 2016-12-1.
 */
public class CommodityInventoryModel<T> extends TableViewModel<T> {

    private String kSelectSQL = "SELECT inv.*, cl.name classificationName, COUNT(co.commodity_id) hasNum FROM inventory inv JOIN classification cl ON inv.classification_id = cl.classification_id JOIN commodity co ON cl.classification_id = co.classification_id GROUP BY inv.inventory_id, inv.classification_id, inv.employee_id;";

    public CommodityInventoryModel(TableView<T> tableView) {
        super(tableView);

        ConsoleLog.print("CommodityInventoryModel init");
    }

    public void fetch(Callback<DataAccessException, Void> callback) {
        ConsoleLog.print("fetching data…");

        try {
            List<Inventory> templist = jdbcOperations.query(kSelectSQL, (resultSet, i) -> {
                Inventory inventory;

                inventory = new Inventory(
                        resultSet.getInt("inventory_id"),
                        resultSet.getInt("classification_id"),
                        resultSet.getInt("employee_id"),
                        resultSet.getTimestamp("create_timestamp"),
                        resultSet.getTimestamp("fill_timestamp")
                );
                inventory.setClassificationName(resultSet.getString("classificationName"));
                inventory.setHasNum(resultSet.getInt("hasNum"));

                return inventory;
            });

            list.clear();
            list.addAll((T[]) templist.toArray());

            callback.call(null);

        } catch (DataAccessException exception) {
            callback.call(exception);
            exception.printStackTrace();
        }
    }

}
