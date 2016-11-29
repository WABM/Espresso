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
import org.springframework.dao.DataAccessException;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.util.Assert;

import java.sql.ResultSet;
import java.util.List;

/**
 * Created by MainasuK on 2016-11-17.
 */
public class CommodityInformationModel<T> extends TableViewModel<T> {

    private final String kSelectSQL = "SELECT co.*, cl.name classification_name FROM commodity co JOIN classification cl ON co.classification_id=cl.classification_id WHERE co.classification_id = ?";
    private final String kInsertSQL = "INSERT INTO wabm.commodity (commodity_id, classification_id, bar_code, name, shelf_life, specification, unit, price_db, start_storage, delivery_specification, sales_avg) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private final String kInsertSQLAutoIncrease = "INSERT INTO wabm.commodity (classification_id, bar_code, name, shelf_life, specification, unit, price_db, start_storage, delivery_specification, sales_avg) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private final String kDeleteSQLWithID = "DELETE FROM wabm.commodity WHERE wabm.commodity.commodity_id = ?;";

    private int classificationID;


    public CommodityInformationModel(TableView<T> tableView) {
        super(tableView);

        ConsoleLog.print("CommodityInformationModel init");
    }

    public int getClassificationID() {
        return classificationID;
    }

    public void fetchData(int classificationID, Callback<Boolean, Void> callback) {     // FIXME: USE Exception not Boolean
        ConsoleLog.print("fetching data with id: " + classificationID +"…");

        this.classificationID = classificationID;
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

    public void add(Commodity commodity, Callback<DataAccessException, Void> callback) {
        ConsoleLog.print("Add commodity: " + commodity.getName());
        Assert.notNull(jdbcOperations);

        try {

            if (commodity.getCommodityID() == null || commodity.getCommodityID() == "") {
                jdbcOperations.update(kInsertSQLAutoIncrease,
                        commodity.getClassificationID(),
                        commodity.getBarcode(),
                        commodity.getName(),
                        commodity.getShelfLife(),
                        commodity.getSpecification(),
                        commodity.getUnit(),
                        commodity.getPrice(),
                        commodity.getStartStorage(),
                        commodity.getDeliverySpecification(),
                        0); // New commodity has 0 sales avg
            } else {
                jdbcOperations.update(kInsertSQL,
                        commodity.getCommodityID(),
                        commodity.getClassificationID(),
                        commodity.getBarcode(),
                        commodity.getName(),
                        commodity.getShelfLife(),
                        commodity.getSpecification(),
                        commodity.getUnit(),
                        commodity.getPrice(),
                        commodity.getStartStorage(),
                        commodity.getDeliverySpecification(),
                        0); // New commodity has 0 sales avg
            }

            // No error
            add((T) commodity);
            callback.call(null);

        } catch (QueryTimeoutException timeoutException) {
            callback.call(timeoutException);
        } catch (DataAccessException dataAccessException) {
            callback.call(dataAccessException);
        }

    }   // end add(…) { … }

    public void delete(Commodity commodity, Callback<DataAccessException, Void> callback) {
        ConsoleLog.print("Delete commodity: " + commodity.getName());
        Assert.notNull(jdbcOperations);

        try {

            jdbcOperations.update(kDeleteSQLWithID, commodity.getCommodityID());

            // No error
            delete((T) commodity);
            callback.call(null);

        } catch (QueryTimeoutException timeoutException) {
            callback.call(timeoutException);
        } catch (DataAccessException dataAccessException) {
            callback.call(dataAccessException);
        }

    }   // end delete(…) { … }

}
