package io.wabm.supermarket.model.procurement;

import io.wabm.supermarket.misc.pojo.CommoditySupplyDemand;
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
 * Created by 14580 on 2016/12/8 0008.
 */
public class CommoditySupplyDemandModel<T> extends TableViewModel<T> {

    private final String kSelectSQL ="select\n" +
            " commodity.commodity_id,\n" +
            " bar_code,\n" +
            " commodity.name,\n" +
            " classification.name,\n" +
            " specification,\n" +
            " delivery_specification,\n" +
            " unit,\n" +
            " quantity,\n" +
            " commodity.price_db\n" +
            "from procurement_requirement ,commodity ,classification\n" +
            "\n" +
            "where procurement_requirement.commodity_id=commodity.commodity_id\n" +
            "      and classification.classification_id=commodity.classification_id\n" +
            "      and procurement_requirement.status = 0";

    public CommoditySupplyDemandModel(TableView<T> tableView) {
        super(tableView);

        ConsoleLog.print("CommoditySupplyDemandModel init");
    }

    public void fetchData(Callback<Boolean, Void> callback) {     // FIXME: USE Exception not Boolean
        ConsoleLog.print("fetching data ");
        Assert.notNull(jdbcOperations);

        new WABMThread().run((_void) -> {
            ConsoleLog.print("Start background task…");

            try {
                List<CommoditySupplyDemand> templist = jdbcOperations.query(kSelectSQL, (ResultSet resultSet, int i) -> {
                    CommoditySupplyDemand commoditySupplyDemand = new CommoditySupplyDemand(
                            resultSet.getString("commodity.commodity_id"),
                            resultSet.getString("bar_code"),
                            resultSet.getString("commodity.name"),
                            resultSet.getString("classification.name"),
                            resultSet.getString("specification"),
                            resultSet.getInt("delivery_specification"),
                            resultSet.getString("unit"),
                            resultSet.getInt("quantity"),
                            resultSet.getDouble("commodity.price_db")
                    );

                    return commoditySupplyDemand;
                });

                list.clear();
                list.addAll((T[]) templist.toArray());
                callback.call(null);
            } catch (DataAccessException exception) {
                callback.call(false);
                exception.printStackTrace();
            }



            return null;
        });

    }
}
