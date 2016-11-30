package io.wabm.supermarket.model.sales;

import io.wabm.supermarket.misc.config.DBConfig;
import io.wabm.supermarket.misc.pojo.Classification;
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
public class HotsaleModel<T> extends TableViewModel<T>{

    private final String kSelectAll = "SELECT * FROM wabm. ";
    private Service<Void> backgroundThread;

    public HotsaleModel(TableView<T> tableView){
        super(tableView);
        ConsoleLog.print("HotsaleModel init");
    }

    /*public void fetchData(Callback<Boolean, Void> callback){
        ConsoleLog.print("fetching data…");
        Assert.notNull(jdbcOperations);

        backgroundThread = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {

                        List<Classification> templist;

                        templist = jdbcOperations.query(kSelectAll,
                                (resultSet, i) -> new Hotsale(
                                        resultSet.getString()
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
    */
}
