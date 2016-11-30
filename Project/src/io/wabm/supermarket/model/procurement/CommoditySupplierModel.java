package io.wabm.supermarket.model.procurement;

import io.wabm.supermarket.misc.config.DBConfig;
import io.wabm.supermarket.misc.pojo.Classification;
import io.wabm.supermarket.misc.pojo.Employee;
import io.wabm.supermarket.misc.pojo.Supplier;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.misc.util.WABMThread;
import io.wabm.supermarket.model.Model;
import io.wabm.supermarket.model.TableViewModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.util.Assert;

import java.sql.ResultSet;
import java.util.Calendar;
import java.util.List;

/**
 * Created by liu on 2016-11-21 .
 */
public class  CommoditySupplierModel<T> extends TableViewModel<T> {
    private final String kSelectAll = "SELECT f.* FROM wabm.supplier f";
    private final String kInsertSQL = "INSERT INTO wabm.supplier (supplier_id, name,address,phone,representative_name) VALUES (?, ?, ?, ?, ?);";
    private final String kInsertSQLAutoIncrease = "INSERT INTO wabm.supplier (name, address,phone,representative_name) VALUES (?, ?, ?, ?);";
    private final String kDeleteSQLWithID = "DELETE FROM wabm.supplier WHERE wabm.supplier.supplier_id = ?;";


    //private Service<Void> backgroundThread;
    public CommoditySupplierModel(TableView<T> tableView) {

        super(tableView);
        ConsoleLog.print("CommoditySupplierModel init");
    }

    public void fetchData(Callback<Boolean, Void> callback) {
        ConsoleLog.print("fetching data…");
        Assert.notNull(jdbcOperations);

        new WABMThread().run((_void) -> {
            ConsoleLog.print("Start background task…");

            try{
            List<Supplier> templist = jdbcOperations.query(kSelectAll, (ResultSet resultSet, int i) -> {
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

    public void add(Supplier supplier, Callback<DataAccessException, Void> callback) {
        ConsoleLog.print("Add supplier: " + supplier.getSupplierName());
        Assert.notNull(jdbcOperations);
        try {
            if (String.valueOf(supplier.getSupplierID()) == null || String.valueOf(supplier.getSupplierID()) == "") {
                jdbcOperations.update(kInsertSQLAutoIncrease,
                        supplier.getSupplierName(),
                        supplier.getAddress(),
                        supplier.getPhone(),
                        supplier.getLinkman()

                );
            } else {
                jdbcOperations.update(kInsertSQL,
                        supplier.getSupplierID(),
                        supplier.getSupplierName(),
                        supplier.getAddress(),
                        supplier.getPhone(),
                        supplier.getLinkman()
                );
            }
            add((T) supplier);
            callback.call(null);
        } catch (QueryTimeoutException timeoutException) {
            callback.call(timeoutException);
        } catch (DataAccessException dataAccessException) {
            callback.call(dataAccessException);
        }

    }   // end add(…) { … }
    public void delete(Supplier supplier, Callback<DataAccessException, Void> callback) {
        ConsoleLog.print("Delete supplier: " + supplier.getSupplierName());
        Assert.notNull(jdbcOperations);

        try {

            jdbcOperations.update(kDeleteSQLWithID, supplier.getSupplierID());

            // No error
            delete((T) supplier);
            callback.call(null);

        } catch (QueryTimeoutException timeoutException) {
            callback.call(timeoutException);
        } catch (DataAccessException dataAccessException) {
            callback.call(dataAccessException);
        }
    }//end of delete
}


