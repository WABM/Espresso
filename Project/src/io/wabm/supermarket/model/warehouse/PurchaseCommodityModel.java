package io.wabm.supermarket.model.warehouse;

import io.wabm.supermarket.application.Main;
import io.wabm.supermarket.misc.pojo.Commodity;
import io.wabm.supermarket.misc.pojo.ShelfLifeCommodity;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.misc.util.SingleLogin;
import io.wabm.supermarket.misc.util.WABMThread;
import javafx.util.Callback;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * Created by MainasuK on 2016-12-14.
 */
public class PurchaseCommodityModel<T> {

    private JdbcOperations jdbcOperations = Main.getJdbcOperations();
    private DataSourceTransactionManager transactionManager = Main.getTransactionManager();

    public void purchase(Commodity commodity, int purchaseQuantity, Callback<DataAccessException, Void> callback) {
        ConsoleLog.print("purchase commodity with quantity: " + purchaseQuantity);

        int employeeID = SingleLogin.getInstance().getEmployee().getEmployeeID();


        new WABMThread().run(_void -> {

            DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
            TransactionStatus status = transactionManager.getTransaction(definition);

            try {

                int num = jdbcOperations.queryForObject("SELECT count(*) num FROM procurement_requirement pr WHERE pr.commodity_id = ? AND pr.status = ? LIMIT 1;", Integer.class, commodity.getCommodityID(), 0);

                if (num != 0) {
                    ConsoleLog.print("update procurement record");
                    jdbcOperations.update("UPDATE procurement_requirement pr SET pr.quantity = pr.quantity + ? WHERE pr.commodity_id = ?", purchaseQuantity, commodity.getCommodityID());
                } else {
                    ConsoleLog.print("insert procurement record");
                    // insert inventory detail
                    jdbcOperations.update(
                            "INSERT INTO procurement_requirement (commodity_id, employee_id, quantity) VALUES (?, ?, ?)",
                            commodity.getCommodityID(),
                            employeeID,
                            purchaseQuantity
                    );
                }

                transactionManager.commit(status);
                callback.call(null);

            } catch (DataAccessException exception) {
                exception.printStackTrace();

                transactionManager.rollback(status);
                callback.call(exception);
            } catch (Exception exception) {
                exception.printStackTrace();
                transactionManager.rollback(status);
                callback.call(new DataAccessResourceFailureException("Unknown error"));
            }

            return null;
        });
    }
}
