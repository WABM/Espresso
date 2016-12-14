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
public class RjectCommodityModel<T> {

    private JdbcOperations jdbcOperations = Main.getJdbcOperations();
    private DataSourceTransactionManager transactionManager = Main.getTransactionManager();

    public void reject(Commodity commodity, int rejectQuantity, Callback<DataAccessException, Void> callback) {
        ConsoleLog.print("reject commodity with quantity: " + rejectQuantity);

        int employeeID = SingleLogin.getInstance().getEmployee().getEmployeeID();


        new WABMThread().run(_void -> {

            DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
            TransactionStatus status = transactionManager.getTransaction(definition);

            try {
                ConsoleLog.print("insert storage change records");
                // insert inventory detail
                jdbcOperations.update("INSERT INTO storage_change (commodity_id, employee_id, quantity, price_db, `type`) VALUES (?, ?, ?, ?, ?)",
                        commodity.getCommodityID(),
                        employeeID,
                        -1 * rejectQuantity,
                        commodity.getPrice(),
                        3
                );

                if (commodity instanceof ShelfLifeCommodity) {
                    jdbcOperations.update("UPDATE order_detail od SET reject = ? WHERE od.order_detail_id = ?",
                            true,
                            ((ShelfLifeCommodity) commodity).getOrderDetailID()
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
