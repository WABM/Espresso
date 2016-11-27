package io.wabm.supermarket.model.warehouse;

import io.wabm.supermarket.misc.pojo.Classification;
import io.wabm.supermarket.misc.pojo.Commodity;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.misc.util.WABMThread;
import io.wabm.supermarket.model.Model;
import io.wabm.supermarket.model.TableViewModel;
import javafx.collections.FXCollections;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import org.springframework.util.Assert;

import java.sql.ResultSet;
import java.util.List;

/**
 * Created by MainasuK on 2016-11-17.
 */
public class CommodityInformationModel<T> extends TableViewModel<T> {

    private String kSelectSQL = "SELECT co.*, cl.name classification_name FROM commodity co JOIN classification cl ON co.classification_id=cl.classification_id WHERE co.classification_id = ?";

    public CommodityInformationModel(TableView<T> tableView) {
        super(tableView);

        ConsoleLog.print("CommodityInformationModel init");
    }

    public void fetchData(int classificationID, Callback<Boolean, Void> callback) {
        ConsoleLog.print("fetching data with id: " + classificationID +"…");
        Assert.notNull(jdbcOperations);

        new WABMThread().run((_void) -> {
            ConsoleLog.print("Start background task…");

            List<Commodity> templist = jdbcOperations.query(kSelectSQL, (ResultSet resultSet, int i) -> {
                Commodity commodity;
                commodity = new Commodity(
                        resultSet.getString("commodity_id"),
                        resultSet.getInt("classification_id"),
                        resultSet.getString("bar_code"),
                        resultSet.getString("name"),
                        resultSet.getString("specification"),
                        resultSet.getString("unit"),
                        resultSet.getDouble("price_db"),
                        resultSet.getInt("delivery_specification"),
                        resultSet.getInt("shelf_life"),
                        resultSet.getInt("start_storage")
                );
                commodity.setClassificationName(resultSet.getString("classification_name"));

                return commodity;
            }, classificationID);

            list.clear();
            list.addAll((T[]) templist.toArray());

            // Return callback with isfetchSuccess flag;
            // TODO:
            callback.call(true);
            return null;
        });

    }  // end of fetchData
}
