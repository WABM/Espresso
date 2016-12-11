package io.wabm.supermarket.model.warehouse;

import io.wabm.supermarket.misc.pojo.Commodity;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.misc.util.WABMThread;
import io.wabm.supermarket.model.FilteredTableViewModel;
import io.wabm.supermarket.model.Model;
import io.wabm.supermarket.model.TableViewModel;
import javafx.collections.FXCollections;
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

}
