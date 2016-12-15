package io.wabm.supermarket.model.procurement;

import io.wabm.supermarket.misc.config.DBConfig;
import io.wabm.supermarket.misc.pojo.SelectSupplier;
import io.wabm.supermarket.misc.pojo.SupplyGoods;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.misc.util.WABMThread;
import io.wabm.supermarket.model.TableViewModel;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.util.Assert;

import java.sql.ResultSet;
import java.util.List;

/**
 * Created by 14580 on 2016/12/9 0009.
 */
@Repository
@ContextConfiguration(classes = DBConfig.class)
public class SelectSupplierModel<T> extends TableViewModel<T> {

    private final String kSelectAll ="select\n" +
            "  commodity.commodity_id,\n" +
            "  supplier.supplier_id, \n" +
            "  supplier.name,\n" +
            "  supply_detail.price_db,\n" +
            "  supply_detail.delivery_time_cost\n" +
            "from wabm.supply_detail,wabm.supplier,wabm.commodity\n" +
            "WHERE supply_detail.commodity_id=commodity.commodity_id\n" +
            "and supplier.supplier_id=supply_detail.supplier_id and commodity.commodity_id=?";
    private final String kInsert = "insert into supply_detail(supplier_id,commodity_id,price_db,delivery_time_cost) values(?,?,0.00,6)";

    private String commodityID;

    public SelectSupplierModel(TableView tableView){

        super(tableView);
    }

    public  void fetchData(String commodityID, Callback<Boolean, Void> callback) {
        ConsoleLog.print("fetching data with id: " + commodityID+"…");
        this.commodityID = commodityID;
        Assert.notNull(jdbcOperations);

        new WABMThread().run((_void) -> {
            ConsoleLog.print("Start background task…");

            try {
                List<SelectSupplier> templist = jdbcOperations.query(kSelectAll, (ResultSet resultSet, int i) -> {
                    SelectSupplier selectSupplier;
                    selectSupplier = new SelectSupplier(
                            resultSet.getString("commodity.commodity_id"),
                            resultSet.getInt("supplier.supplier_id"),
                            resultSet.getString("supplier.name"),
                            resultSet.getDouble("supply_detail.price_db"),
                            resultSet.getString("supply_detail.delivery_time_cost")
                    );
                    return selectSupplier;
                }, commodityID);

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

