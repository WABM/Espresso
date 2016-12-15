package io.wabm.supermarket.model.warehouse;

import io.wabm.supermarket.misc.pojo.PurchaseCommodity;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.misc.util.SingleLogin;
import io.wabm.supermarket.misc.util.WABMThread;
import io.wabm.supermarket.model.TableViewModel;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by MainasuK on 2016-12-11.
 */
public class CommodityPurchaseFormModel<T> extends TableViewModel<T> {
    final private String kSelectNeedsProcurementCommoditySQL = "SELECT *, cl.name classification_name FROM needs_procurement_commodity npc JOIN classification cl ON cl.classification_id = npc.classification_id\n" +
            "WHERE npc.commodity_id NOT IN (\n" +
            "  SELECT p.commodity_id\n" +
            "  FROM procurement_requirement p\n" +
            "  WHERE p.status = 0\n" +
            ");";
    final private String kInsertProcurementSQL = "INSERT INTO procurement_requirement (employee_id, commodity_id, quantity, status) VALUES (?, ?, ?, ?);";

    public CommodityPurchaseFormModel(TableView<T> tableView) {
        super(tableView);
    }

    public void fetchNeedsProcuremetnCommodity(Callback<DataAccessException, Void> callback) {
        ConsoleLog.print("fetch needs procurement commodity…");

        new WABMThread().run(_void -> {

            try {
                List<PurchaseCommodity> tempList = jdbcOperations.query(kSelectNeedsProcurementCommoditySQL,
                        (resultSet, i) -> {
                            PurchaseCommodity commodity = new PurchaseCommodity(
                                    resultSet.getInt("min_commodity_storage"),
                                    resultSet
                            );
                            commodity.setClassificationName(resultSet.getString("classification_name"));
                            return commodity;
                        }
                );

                list.clear();
                list.addAll((T[]) tempList.toArray());

                callback.call(null);

            } catch (DataAccessException exception) {
                callback.call(exception);
                exception.printStackTrace();
            }

            return null;
        });
    }

    public DataAccessException addProcurements() {
        ConsoleLog.print("add procurement…");

        DataAccessException exception = null;
        int id = SingleLogin.getInstance().getEmployee().getEmployeeID();

        try {

            List<PurchaseCommodity> tempList = list.parallelStream()
                    .filter(c -> c instanceof PurchaseCommodity)
                    .map(c -> (PurchaseCommodity) c)
                    .filter(c -> c.isIsChoose())
                    .collect(Collectors.toList());

            ConsoleLog.print(tempList.size() + " length");
            for (PurchaseCommodity c : tempList) {
                ConsoleLog.print(c.getName());
            }


            BatchPreparedStatementSetter batchPreparedStatementSetter = new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                    PurchaseCommodity commodity = tempList.get(i);
                    preparedStatement.setInt(1, id);
                    preparedStatement.setString(2, commodity.getCommodityID());
                    preparedStatement.setInt(3, commodity.getPurchaseNum());
                    preparedStatement.setInt(4, 0); // pending status
                }

                @Override
                public int getBatchSize() {
                    return tempList.size();
                }
            };
            jdbcOperations.batchUpdate(kInsertProcurementSQL, batchPreparedStatementSetter);

        } catch (DataAccessException e) {
            exception = e;
            e.printStackTrace();
        } catch (Exception e) {
            exception = new DataAccessResourceFailureException("Unknown error");
            e.printStackTrace();
        }

        return exception;
    }

}
