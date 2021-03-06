package io.wabm.supermarket.model.warehouse;

import io.wabm.supermarket.misc.pojo.Commodity;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.misc.util.WABMThread;
import io.wabm.supermarket.model.FilteredTableViewModel;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import org.springframework.dao.DataAccessException;
import org.springframework.util.Assert;

import java.sql.ResultSet;
import java.util.List;

/**
 * Created by MainasuK on 2016-11-20.
 */
public class CommodityStorageModel<T> extends FilteredTableViewModel<T> {

    private final String kSelectAllSQL = "SELECT co.*, cl.name classification_name FROM commodity co JOIN classification cl ON co.classification_id=cl.classification_id;";
    private final String kSelectNeedsProcurementSQL = "SELECT count(*) \n" +
            "FROM needs_procurement_commodity npc JOIN classification cl ON cl.classification_id = npc.classification_id\n" +
            "WHERE npc.commodity_id NOT IN (\n" +
            "SELECT p.commodity_id\n" +
            "FROM procurement_requirement p\n" +
            "WHERE p.status = 0\n" +
            "UNION\n" +
            "SELECT od.commodity_id\n" +
            "FROM order_detail od, `order` o\n" +
            "WHERE od.order_id = o.order_id AND o.`status` <> 3\n" +
            ");";
    private final String kSelectTransportingOrderCountSQL = "SELECT count(*) num FROM `order` o WHERE o.status = 2 LIMIT 1;";

    public CommodityStorageModel(TableView<T> tableView) {
        super(tableView);
    }

    public void fetchAllData(Callback<DataAccessException, Void> callback) {
        ConsoleLog.print("fetching all data…");

        Assert.notNull(jdbcOperations);

        new WABMThread().run((_void) -> {
            ConsoleLog.print("Start background task…");

            try {
                List<Commodity> templist = jdbcOperations.query(kSelectAllSQL, (ResultSet resultSet, int i) -> {
                    Commodity commodity;
                    commodity = new Commodity(
                            resultSet.getString("commodity_id"),
                            resultSet.getInt("classification_id"),
                            resultSet.getString("bar_code"),
                            resultSet.getString("name"),
                            resultSet.getString("specification"),
                            resultSet.getString("unit"),
                            resultSet.getBigDecimal("price_db"),
                            resultSet.getInt("delivery_specification"),
                            resultSet.getInt("shelf_life"),
                            resultSet.getInt("storage"),
                            resultSet.getBoolean("valid")
                    );
                    commodity.setClassificationName(resultSet.getString("classification_name"));
                    return commodity;
                });

                list.clear();
                list.addAll((T[]) templist.toArray());

                callback.call(null);

            } catch (DataAccessException exception) {
                callback.call(exception);
                exception.printStackTrace();
            }

            return null;
        });

    }  // end of fetchData

    private void fetchCountWithSQL(String sql, Callback<Integer, Void> callback) {
        ConsoleLog.print("fetching count…");

        Assert.notNull(jdbcOperations);

        new WABMThread().run(_void -> {

            try {
                int num = jdbcOperations.queryForObject(sql, Integer.class);
                callback.call(num);

            } catch (DataAccessException exception) {
                callback.call(null);
                exception.printStackTrace();
            }

            return null;
        });
    }

    public void fetchNeedsProcurementCount(Callback<Integer, Void> callback) {
        fetchCountWithSQL(kSelectNeedsProcurementSQL, callback);
    }

    public void fetchTransportingOrderCount(Callback<Integer, Void> callback) {
        fetchCountWithSQL(kSelectTransportingOrderCountSQL, callback);
    }

}
