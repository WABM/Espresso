package io.wabm.supermarket.model.sales;

import io.wabm.supermarket.misc.config.DBConfig;
import io.wabm.supermarket.misc.pojo.TransactionRecord;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.model.TableViewModel;
import javafx.collections.FXCollections;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Created by Administrator on 2016/11/19 0019.
 */
@Repository
@ContextConfiguration(classes = DBConfig.class)
public class TransationRecordModel<T> extends TableViewModel<T> {
    private final String kSelectAll = "SELECT * FROM sales_record where timestamp >= ? and timestamp <= ?";
    private Service<Void> backgroundThread;

    public TransationRecordModel(TableView tableView){
        super(tableView);
        ConsoleLog.print("TransationRecordModel init");
    }
    public void fetchData(String front,String rear,Callback<Boolean, Void> callback){
        ConsoleLog.print("fetching data…");
        Assert.notNull(jdbcOperations);

        backgroundThread = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {

                        List<TransactionRecord> templist;

                        templist = jdbcOperations.query(kSelectAll,
                                (resultSet, i) -> new TransactionRecord(
                                        resultSet.getString("sales_record_id"),
                                        resultSet.getString("employee_id"),
                                        resultSet.getDouble("all_price_db"),
                                        resultSet.getString("timestamp")
                                )
                        ,front,rear);

                        list.clear();
                        list.addAll((T[]) templist.toArray());

                        // Return callback with isfetchSuccess flag;
                        // TODO:
                        callback.call(true);
                        return null;
                    }
                };
            }
        };

        backgroundThread.start();

    };  // end of fetchData
}
