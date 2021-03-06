package io.wabm.supermarket.model.procurement;

import io.wabm.supermarket.misc.pojo.Supplier;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.misc.util.WABMThread;
import io.wabm.supermarket.model.FilteredTableViewModel;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.util.Assert;

import java.sql.ResultSet;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created by liu on 2016-11-21 .
 */

public class  CommoditySupplierModel<T> extends FilteredTableViewModel<T> {
    private final String kSelectAll = "SELECT * FROM supplier WHERE supplier.valid=1";
    private final String kInsertSQL = "INSERT INTO supplier (supplier_id, name,address,phone,representative_name) VALUES (?, ?, ?, ?, ?);";
    private final String kInsertSQLAutoIncrease = "INSERT INTO supplier (name, address,phone,representative_name,valid) VALUES (?, ?, ?, ?,1);";
    private final String kDeleteSQLWithID = "UPDATE supplier SET name=?, representative_name=?,  phone=?, address=? WHERE suplier_id=?";
    private final String kRmoveSQLWithID = "UPDATE supplier SET valid=0 WHERE supplier.supplier_id = ?;";


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
                                resultSet.getString("representative_name"),
                                resultSet.getString("phone"),
                                resultSet.getString("address")
                        );
                        return supplier;
                    }
            );
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

    public void add(Supplier supplier, Callback<DataAccessException, Void> callback) {
        ConsoleLog.print("Add supplier: " + supplier.getSupplierName());
        Assert.notNull(jdbcOperations);
        try {
            if (supplier.getSupplierID() < 0) {
                jdbcOperations.update(kInsertSQLAutoIncrease,
                        supplier.getSupplierName(),
                        supplier.getAddress(),
                        supplier.getPhone(),
                        supplier.getLinkman()
                );
                SqlRowSet rowSet = jdbcOperations.queryForRowSet("select LAST_INSERT_ID() AS id;");
                if (rowSet.next()) {
                    supplier.setSupplierID(rowSet.getInt("id"));
                }
            } else {
                jdbcOperations.update(kInsertSQLAutoIncrease,
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

    }

    public void delete(Supplier supplier, Callback<DataAccessException, Void> callback) {
        ConsoleLog.print("remove supplier: " + supplier.getSupplierName());
        Assert.notNull(jdbcOperations);
        try {
            jdbcOperations.update(kRmoveSQLWithID, supplier.getSupplierID());
            delete((T) supplier);
            callback.call(null);
        } catch (QueryTimeoutException timeoutException) {
            callback.call(timeoutException);
        } catch (DataAccessException dataAccessException) {
            callback.call(dataAccessException);
        }
    }

    public void update(Supplier supplier, Callback<DataAccessException, Void> callback) {
        ConsoleLog.print("modify supplier: " + supplier.getSupplierName());
        Assert.notNull(jdbcOperations);

        try {
            jdbcOperations.update("UPDATE supplier SET name=?, representative_name=?,  phone=?, address=? WHERE supplier_id=?",
                    // supplier.getSupplierID(),
                    supplier.getSupplierName(),
                    supplier.getLinkman(),
                    supplier.getPhone(),
                    supplier.getAddress(),
                    supplier.getSupplierID()
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


