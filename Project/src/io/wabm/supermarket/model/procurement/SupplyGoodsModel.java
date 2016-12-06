package io.wabm.supermarket.model.procurement;

import io.wabm.supermarket.misc.config.DBConfig;
import io.wabm.supermarket.misc.pojo.SupplyGoods;
import io.wabm.supermarket.misc.pojo.TransactionRecordDetail;
import io.wabm.supermarket.misc.util.WABMThread;
import io.wabm.supermarket.model.TableViewModel;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.TableView;
import io.wabm.supermarket.misc.util.ConsoleLog;
import javafx.util.Callback;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.util.Assert;

import java.sql.ResultSet;
import java.util.List;

/**
 * Created by 14580 on 2016/12/5 0005.
 */
@Repository
@ContextConfiguration(classes = DBConfig.class)
public class SupplyGoodsModel<T> extends TableViewModel<T> {

    private final String kSelectAll ="select commodity_id,price_db,delivery_time_cost FROM wabm.supply_detail  WHERE valid=1";
    private int SupplierID;

    public SupplyGoodsModel(TableView tableView){

        super(tableView);
    }

    public  void fetchData(int SupplierID, Callback<Boolean, Void> callback) {
        ConsoleLog.print("fetching data with id: " + SupplierID+"…");
        this.SupplierID = SupplierID;
        Assert.notNull(jdbcOperations);

        new WABMThread().run((_void) -> {
            ConsoleLog.print("Start background task…");

            try {
                List<SupplyGoods> templist = jdbcOperations.query(kSelectAll, (ResultSet resultSet, int i) -> {
                    SupplyGoods supplyGoods;
                    supplyGoods = new SupplyGoods(
                            resultSet.getInt("commodity_idColumn"),
                            resultSet.getDouble("price_dbColumn"),
                            resultSet.getString("delivery_time_costColumn")
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

}
