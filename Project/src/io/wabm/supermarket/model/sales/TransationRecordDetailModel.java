package io.wabm.supermarket.model.sales;

import io.wabm.supermarket.misc.config.DBConfig;
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
 * Created by Administrator on 2016/11/28 0028.
 */
public class TransationRecordDetailModel<T> extends TableViewModel<T> {

    private final String kSelectAll = "select sales_record_detail.sales_record_id,sales_record_detail.quantity,commodity.* from commodity,sales_record,sales_record_detail where sales_record_detail.sales_record_id = sales_record.sales_record_id and commodity.commodity_id = sales_record_detail.commodity_id and sales_record.sales_record_id = ?";
    private Service<Void> backgroundThread;

    public TransationRecordDetailModel(TableView tableView){
        super(tableView);

    }
    /*public void fetchData(int record_ID,Callback<Boolean, Void> callback){
        System.out.println(record_ID);
        ConsoleLog.print("fetching data…");
        Assert.notNull(jdbcOperations);

        backgroundThread = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {

                        List<TransactionRecordDetail> templist = jdbcOperations.query(kSelectAll,
                                (resultSet, i) ->
                                        new TransactionRecordDetail(
                                        resultSet.getString("sales_record_id"),
                                        resultSet.getString("commodity_id"),
                                        resultSet.getString("bar_code"),
                                        resultSet.getString("name"),
                                        resultSet.getInt("classification_id"),
                                        resultSet.getString("specification"),
                                        resultSet.getString("unit"),
                                        resultSet.getInt("quantity"),
                                        resultSet.getDouble("price_db")
                                )
                        ,record_ID);

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
    */
    public void fetchData(int recordID, Callback<Boolean, Void> callback) {
        ConsoleLog.print("fetching data with id: " + recordID +"…");
        Assert.notNull(jdbcOperations);

        new WABMThread().run((_void) -> {
            ConsoleLog.print("Start background task…");

            try {
            List<TransactionRecordDetail> templist = jdbcOperations.query(kSelectAll, (ResultSet resultSet, int i) -> {
                TransactionRecordDetail transactionRecordDetail;
                transactionRecordDetail = new TransactionRecordDetail(
                        resultSet.getInt("sales_record_id"),
                        resultSet.getString("commodity_id"),
                        resultSet.getString("bar_code"),
                        resultSet.getString("name"),
                        resultSet.getInt("classification_id"),
                        resultSet.getString("specification"),
                        resultSet.getString("unit"),
                        resultSet.getInt("quantity"),
                        resultSet.getDouble("price_db")
                );
                return transactionRecordDetail;
            }, recordID);

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
