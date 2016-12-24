package io.wabm.supermarket.model.warehouse;

import io.wabm.supermarket.application.Main;
import io.wabm.supermarket.misc.enums.StorageChangeEnum;
import io.wabm.supermarket.misc.pojo.CMKInventoryDetail;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.misc.util.SingleLogin;
import io.wabm.supermarket.misc.util.WABMThread;
import io.wabm.supermarket.model.TableViewModel;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by MainasuK on 2016-12-13.
 */
public class CommodityInventoryDetailModel<T> extends TableViewModel<T> {

    private DataSourceTransactionManager transactionManager = Main.getTransactionManager();
    private final String kSelectSQL = "SELECT co.* FROM commodity co WHERE co.classification_id = ?";
    private final String kSelectWithInventoryIDSQL = "SELECT co.*, ind.quantity '_quantity', ind.should_have_quantity '_should_have_quantity' FROM commodity co JOIN inventory_detail ind ON co.commodity_id = ind.commodity_id WHERE ind.inventory_id = ?";

    public CommodityInventoryDetailModel(TableView<T> tableView) {
        super(tableView);
    }

    public void fetchWith(int classificationID, Callback<DataAccessException, Void> callback) {
        ConsoleLog.print("fetching…");

        new WABMThread().run(_void -> {

            try {

                List<CMKInventoryDetail> tempList = jdbcOperations.query(kSelectSQL, (resultSet, i) -> new CMKInventoryDetail(resultSet), classificationID);

                list.clear();
                list.addAll((T[]) tempList.toArray());

                callback.call(null);

            } catch (DataAccessException exception) {
                callback.call(exception);
                exception.printStackTrace();
            } catch (Exception exception) {
                callback.call(new DataAccessResourceFailureException("Unknown error"));
                exception.printStackTrace();
            }

            return null;
        });

    }

    public void fetchInventoryDetailWith(int inventoryID, Callback<DataAccessException, Void> callback) {
        ConsoleLog.print("fetching…");

        new WABMThread().run(_void -> {

            try {

                List<CMKInventoryDetail> tempList = jdbcOperations.query(kSelectWithInventoryIDSQL, (resultSet, i) -> {
                    CMKInventoryDetail detail = new CMKInventoryDetail(resultSet);
                    detail.setActualQuantity(resultSet.getInt("_quantity"));
                    detail.setStorage(resultSet.getInt("_should_have_quantity"));
                    return detail;
                }, inventoryID);

                list.clear();
                list.addAll((T[]) tempList.toArray());

                callback.call(null);

            } catch (DataAccessException exception) {
                callback.call(exception);
                exception.printStackTrace();
            } catch (Exception exception) {
                callback.call(new DataAccessResourceFailureException("Unknown error"));
                exception.printStackTrace();
            }

            return null;
        });

    }

    public void stocktakingOf(int inventoryID, int classificationID, Callback<DataAccessException, Void> callback) {
        ConsoleLog.print("stocktaking…");

        int employeeID = SingleLogin.getInstance().getEmployee().getEmployeeID();

        new WABMThread().run(_void -> {

            DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
            TransactionStatus status = transactionManager.getTransaction(definition);

            try {
                // update inventory status
                jdbcOperations.update("UPDATE inventory SET employee_id = ?, fill_timestamp = ? WHERE inventory_id = ?",
                        employeeID, new Timestamp(System.currentTimeMillis()), inventoryID
                );

                BatchPreparedStatementSetter inventoryDetailSetter = new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                        CMKInventoryDetail detail = ((CMKInventoryDetail) list.get(i));
                        preparedStatement.setInt(1, inventoryID);
                        preparedStatement.setString(2, detail.getCommodityID());
                        preparedStatement.setInt(3, detail.getActualQuantity());
                        preparedStatement.setInt(4, detail.getStorage());
                    }

                    @Override
                    public int getBatchSize() {
                        return list.size();
                    }
                };

                ConsoleLog.print("insert inventory details");
                // insert inventory detail
                jdbcOperations.batchUpdate("INSERT INTO inventory_detail (inventory_id, commodity_id, quantity, should_have_quantity) VALUES (?, ?, ?, ?)", inventoryDetailSetter);

                List<CMKInventoryDetail> resultList = list.parallelStream()
                        .filter(d -> d instanceof CMKInventoryDetail)
                        .map(d -> (CMKInventoryDetail)d)
                        .filter(d -> d.getResult() != 0)
                        .collect(Collectors.toList());

                BatchPreparedStatementSetter storageChangeSetter = new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                        CMKInventoryDetail detail = ((CMKInventoryDetail) resultList.get(i));

                        int type = (detail.getResult() > 0) ? StorageChangeEnum.prifit.ordinal() : StorageChangeEnum.lose.ordinal();

                        preparedStatement.setString(1, detail.getCommodityID());
                        preparedStatement.setInt(2, employeeID);
                        preparedStatement.setInt(3, detail.getResult());
                        preparedStatement.setBigDecimal(4, detail.getPrice());
                        preparedStatement.setInt(5, type);
                    }

                    @Override
                    public int getBatchSize() {
                        return resultList.size();
                    }
                };

                ConsoleLog.print("insert storage change records");
                // insert inventory detail
                jdbcOperations.batchUpdate("INSERT INTO storage_change (commodity_id, employee_id, quantity, price_db, `type`) VALUES (?, ?, ?, ?, ?)", storageChangeSetter);

                transactionManager.commit(status);
                ConsoleLog.print("done");
                callback.call(null);

            } catch (DataAccessException exception) {
                transactionManager.rollback(status);
                callback.call(exception);
                exception.printStackTrace();
            } catch (Exception exception) {
                transactionManager.rollback(status);
                callback.call(new DataAccessResourceFailureException("Unknown error"));
                exception.printStackTrace();
            }

            return null;
        });


    }

}
