package io.wabm.supermarket.model.sales;

import io.wabm.supermarket.misc.config.DBConfig;
import io.wabm.supermarket.misc.pojo.CashInformation;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.model.Model;
import io.wabm.supermarket.model.TableViewModel;
import javafx.collections.FXCollections;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.util.Callback;
import javafx.scene.control.TableView;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.util.Assert;
import java.util.List;
/**
 * Created by Administrator on 2016/11/20 0020.
 */
@Repository
@ContextConfiguration(classes = DBConfig.class)
public class CashInformationModel<T> extends TableViewModel<T> {

    private final String kSelectAll = "SELECT * FROM change_record";
    private Service<Void> backgroundThread;

    public CashInformationModel(TableView tableView){
        super(tableView);
        ConsoleLog.print("CashInformationModel init");
    }

    public void fetchData(Callback<Boolean, Void> callback){
        ConsoleLog.print("fetching data…");
        Assert.notNull(jdbcOperations);

        backgroundThread = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {

                        List<CashInformation> templist;

                        templist = jdbcOperations.query(kSelectAll,
                                (resultSet, i) -> new CashInformation(
                                        resultSet.getString("cash_register_id"),
                                        resultSet.getString("employee_employee_id"),
                                        resultSet.getDouble("money_in_db"),
                                        resultSet.getDouble("money_out_db"),
                                        resultSet.getDouble("money_should_out_db")
                                )
                        );
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
