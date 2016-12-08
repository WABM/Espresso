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

    private final String kSelectSQL ="select commodity.commodity_id,\n" +
            "  bar_code,commodity.name,\n" +
            "  classification.name,\n" +
            "  specification,delivery_specification,\n" +
            "  unit,price_db,quantity\n" +
            "\n" +
            "from procurement_requirement ,commodity ,classification\n" +
            "\n" +
            "where procurement_requirement.commodity_id=commodity.commodity_id\n" +
            "      and classification.classification_id=commodity.classification_id";

    private String commodityid;

    public CommoditySupplyDemandModel(TableView<T> tableView) {
        super(tableView);

        ConsoleLog.print("CommoditySupplyDemandModel init");
    }

    public String getCommodityid() {
        return commodityid;
    }

    public void fetchData(String commodityid, Callback<DataAccessException, Void> callback) {     // FIXME: USE Exception not Boolean
        ConsoleLog.print("fetching data with id: " + commodityid +"…");

        this.commodityid = commodityid;
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
                            resultSet.getInt("specification"),
                            resultSet.getInt("delivery_specification"),
                            resultSet.getString("unit"),
                            resultSet.getInt("quantity"),
                            resultSet.getBigDecimal("price_db")
                    );
                    //commoditySupplyDemand.setClassificationName(resultSet.getString("classification_name"));

                    return commoditySupplyDemand;
                }, commodityid);

                list.clear();
                list.addAll((T[]) templist.toArray());

                // Return callback with isfetchSuccess flag;
                // TODO:
                callback.call(null);
            } catch (DataAccessException exception) {
                callback.call(exception);
                exception.printStackTrace();
            }

            return null;
        });

    }
}
