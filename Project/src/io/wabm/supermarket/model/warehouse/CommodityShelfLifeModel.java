package io.wabm.supermarket.model.warehouse;

import io.wabm.supermarket.misc.pojo.ShelfLifeCommodity;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.misc.util.WABMThread;
import io.wabm.supermarket.model.TableViewModel;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * Created by MainasuK on 2016-11-20.
 */
public class CommodityShelfLifeModel<T> extends TableViewModel<T> {

    private final String kSelectSQL = "SELECT co.*, cl.name classificationName, od.order_detail_id, od.production_date FROM order_detail od JOIN commodity co ON od.commodity_id = co.commodity_id JOIN classification cl ON co.classification_id = cl.classification_id WHERE od.production_date IS NOT NULL AND od.reject <> 1;";

    public CommodityShelfLifeModel(TableView<T> tableView) {
        super(tableView);

        ConsoleLog.print("CommodityShelfLifeModel init");
    }

    public void fetch(Callback<DataAccessException, Void> callback) {
        ConsoleLog.print("fetching…");

        new WABMThread().run(_void -> {

            try {
                List<ShelfLifeCommodity> tempList = jdbcOperations.query(kSelectSQL, (resultSet, i) -> {
                    ShelfLifeCommodity c = new ShelfLifeCommodity(
                            resultSet.getDate("production_date").toLocalDate(),
                            resultSet.getInt("order_detail_id"),
                            resultSet
                    );
                    c.setClassificationName(resultSet.getString("classificationName"));

                    return c;
                });

                list.clear();
                list.addAll((T[]) tempList.toArray());

                callback.call(null);

            } catch (DataAccessException exception) {
                exception.printStackTrace();
                callback.call(exception);
            }

            return null;
        });

    }

}
