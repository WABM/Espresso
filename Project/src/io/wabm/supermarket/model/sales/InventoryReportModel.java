package io.wabm.supermarket.model.sales;

import io.wabm.supermarket.misc.pojo.InventoryReport;
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
 * Created by Administrator on 2016/12/15 0015.
 */
public class InventoryReportModel<T> extends TableViewModel<T> {
    private final String KselectAll="SELECT s.commodity_id,c.name,s.quantity,s.price_db,s.type,e.name,s.create_timestamp FROM storage_change s,employee e,commodity c where s.commodity_id = c.commodity_id and s.employee_id = e.employee_id";

    public InventoryReportModel(TableView tableView){
        super(tableView);
    }

    public void fetchData(Callback<Boolean, Void> callback) {
        ConsoleLog.print("fetching ...");
        Assert.notNull(jdbcOperations);

        new WABMThread().run((_void) -> {
            ConsoleLog.print("Start background task…");

            try {
                List<InventoryReport> templist = jdbcOperations.query(KselectAll, (ResultSet resultSet, int i) -> {
                    InventoryReport inventoryReport;
                    inventoryReport = new InventoryReport(
                            resultSet.getString("s.commodity_id"),
                            resultSet.getString("c.name"),
                            resultSet.getInt("quantity"),
                            resultSet.getBigDecimal("price_db"),
                            resultSet.getInt("type"),
                            resultSet.getString("e.name"),
                            resultSet.getString("create_timestamp")   //数据库中取出来是date
                    );
                    return inventoryReport;
                });

                list.clear();
                list.addAll((T[]) templist.toArray());

                // Return callback with isfetchSuccess flag;
                // TODO:
                callback.call(true);
            } catch (DataAccessException e) {
                e.printStackTrace();
                callback.call(false);
            }

            return null;
        });
    }
}
