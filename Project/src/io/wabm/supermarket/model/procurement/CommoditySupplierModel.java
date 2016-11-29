package io.wabm.supermarket.model.procurement;

import io.wabm.supermarket.misc.config.DBConfig;
import io.wabm.supermarket.misc.pojo.Classification;
import io.wabm.supermarket.misc.pojo.Supplier;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.misc.util.WABMThread;
import io.wabm.supermarket.model.TableViewModel;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.util.Assert;

import java.sql.ResultSet;
import java.util.List;

/**
 * Created by liu on 2016-11-21 .
 */
public class  CommoditySupplierModel<T> extends TableViewModel<T> {
    private final String kSelectAll = "SELECT f.* FROM wabm.supplier f";
    private Service<Void> backgroundThread;
    public CommoditySupplierModel(TableView<T> tableView) {

        super(tableView);
        ConsoleLog.print("CommoditySupplierModel init");
    }

    public void fetchData(Callback<Boolean, Void> callback) {
        ConsoleLog.print("fetching data…");
        Assert.notNull(jdbcOperations);

        new WABMThread().run((_void) -> {

            try{
            List<Supplier> templist = jdbcOperations.query(kSelectAll,
                    (ResultSet resultSet, int i) -> {
                        Supplier supplier = new Supplier(
                                resultSet.getInt("supplier_id"),
                                resultSet.getString("name"),
                                resultSet.getString("representative_name"),//date类型的，要转换为string类
                                resultSet.getString("phone"),
                                resultSet.getString("address")
                        );

                        return supplier;
                    }
            );

            list.clear();
            list.addAll((T[]) templist.toArray());

            // Return callback with isfetchSuccess flag;
            // TODO:
            callback.call(true);

            } catch (DataAccessException exception) {
                callback.call(false);
                exception.printStackTrace();
            }
            return null;
        });

    }  // end of fetchData

}


