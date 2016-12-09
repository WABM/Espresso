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
            "  commodity.commodity_id,\n" +
            "  commodity.bar_code,\n" +
            "  commodity.name,\n" +
            "  classification.name,\n" +
            "  commodity.specification,\n" +
            "  commodity.delivery_specification,\n" +
            "  commodity.unit,\n" +
            "  procurement_requirement.quantity,\n" +
            "  commodity.price_db ,\n" +
            "  supplier.name\n" +
            "  from wabm.procurement_requirement ,wabm.commodity ,wabm.classification,wabm.supplier, wabm.order,wabm.order_detail\n" +
            "  where procurement_requirement.commodity_id = commodity.commodity_id\n" +
            "        and classification.classification_id = commodity.classification_id\n" +
            "        and commodity.commodity_id = order_detail.commodity_id\n" +
            "        and order.supplier_id = supplier.supplier_id";



    public CommoditySupplyDemandModel(TableView<T> tableView) {
        super(tableView);

        ConsoleLog.print("CommoditySupplyDemandModel init");
    }


    public void fetchData(Callback<DataAccessException, Void> callback) {     // FIXME: USE Exception not Boolean
        ConsoleLog.print("fetching data ");
        Assert.notNull(jdbcOperations);

        new WABMThread().run((_void) -> {
            ConsoleLog.print("Start background task…");

            try {
                List<CommoditySupplyDemand> templist = jdbcOperations.query(kSelectSQL, (ResultSet resultSet, int i) -> {
                    CommoditySupplyDemand commoditySupplyDemand = new CommoditySupplyDemand(
                            resultSet.getString("commodity.commodity_id"),
                            resultSet.getString("commodity.bar_code"),
                            resultSet.getString("commodity.name"),
                            resultSet.getString("classification.name"),
                            resultSet.getString("commodity.specification"),
                            resultSet.getInt("commodity.delivery_specification"),
                            resultSet.getString("commodity.unit"),
                            resultSet.getInt("procurement_requirement.quantity"),
                            resultSet.getBigDecimal("commodity.price_db"),
                            resultSet.getString("supplier.name")
                    );

                    return commoditySupplyDemand;
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

    }
}
