package io.wabm.supermarket.model.procurement;

import io.wabm.supermarket.misc.config.DBConfig;
import io.wabm.supermarket.misc.pojo.SupplyGoods;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.misc.util.WABMThread;
import io.wabm.supermarket.model.FilteredTableViewModel;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.util.Assert;

import java.sql.ResultSet;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created by 14580 on 2016/12/5 0005.
 */
@Repository
@ContextConfiguration(classes = DBConfig.class)
public class SupplyGoodsModel<T> extends FilteredTableViewModel<T> {

    private final String kSelectAll = "select commodity.commodity_id,commodity.name,supply_detail.price_db,supply_detail.delivery_time_cost\n" +
            "FROM supply_detail,commodity\n" +
            "WHERE  supply_detail.commodity_id=commodity.commodity_id and supply_detail.valid = 1 and supplier_id=?\n";

    private final String kRmoveSQLWithID = "UPDATE supply_detail SET valid=0 WHERE supply_detail.commodity_id = ?;";

    private final String kInsert = "insert into supply_detail(supplier_id,commodity_id,price_db,delivery_time_cost) values(?,?,0.00,6)";


    private int SupplierID;

    public SupplyGoodsModel(TableView tableView) {

        super(tableView);
    }

    public void fetchData(int SupplierID, Callback<Boolean, Void> callback) {
        ConsoleLog.print("fetching data with id: " + SupplierID + "…");
        this.SupplierID = SupplierID;
        Assert.notNull(jdbcOperations);

        new WABMThread().run((_void) -> {
            ConsoleLog.print("Start background task…");

            try {
                List<SupplyGoods> templist = jdbcOperations.query(kSelectAll, (ResultSet resultSet, int i) -> {
                    SupplyGoods supplyGoods;
                    supplyGoods = new SupplyGoods(
                            resultSet.getString("commodity.commodity_id"),
                            resultSet.getString("commodity.name"),
                            resultSet.getDouble("supply_detail.price_db"),
                            resultSet.getString("supply_detail.delivery_time_cost")
                    );
                    return supplyGoods;
                }, SupplierID);

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

    public void add(String SupplierID,String CommodityID, Callback<Boolean, Void> callback) {
        Assert.notNull(jdbcOperations);
        try {
            jdbcOperations.update(kInsert,
                    SupplierID, CommodityID
            );
        } catch (QueryTimeoutException timeoutException) {
            timeoutException.printStackTrace();
        } catch (DataAccessException dataAccessException) {
            dataAccessException.printStackTrace();
        }
        callback.call(true);
    }
    public void delete(SupplyGoods supplyGoods, Callback<DataAccessException, Void> callback) {
        ConsoleLog.print("remove supplyGoods: " + supplyGoods.getCommodityName());
        Assert.notNull(jdbcOperations);
        try {
            jdbcOperations.update(kRmoveSQLWithID, supplyGoods.getCommodityID());
            delete((T) supplyGoods);
            callback.call(null);
        } catch (QueryTimeoutException timeoutException) {
            callback.call(timeoutException);
        } catch (DataAccessException dataAccessException) {
            callback.call(dataAccessException);
        }
    }
    public void update(SupplyGoods supplyGoods, Callback<DataAccessException, Void> callback) {
        ConsoleLog.print("modify supplyGoods: " + supplyGoods.getCommodityName());
        Assert.notNull(jdbcOperations);

        try {
            jdbcOperations.update("UPDATE supply_detail SET price_db=?, delivery_time_cost=? WHERE commodity_id=?",
                    //supplyGoods.getCommodityName(),
                    supplyGoods.getPrice_db(),
                    supplyGoods.getDelivery_time_cost(),
                    supplyGoods.getCommodityID()
            );
            callback.call(null);
        } catch (DataAccessException exception) {
            callback.call(exception);
        }

    }
    public void setFilter(Predicate<T> predicate) {
        ConsoleLog.print("set predicate");
        setPredicate(predicate);
    }

}

