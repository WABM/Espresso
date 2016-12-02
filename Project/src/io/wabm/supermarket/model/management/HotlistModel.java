package io.wabm.supermarket.model.management;

import io.wabm.supermarket.misc.pojo.Hotsale;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.model.TableViewModel;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import org.springframework.dao.DataAccessException;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Created by liu on 2016-12-01 .
 */
public class HotlistModel<T> extends TableViewModel<T> {
    private final String kSelectsql = "select c.*,sa.all_price_db,sum(quantity) from commodity c,sales_record sa,sales_record_detail sad where sa.timestamp>'2016-11-01' and sa.timestamp<'2016-11-06' and sa.sales_record_id=sad.sales_record_id and sad.commodity_id=c.commodity_id group by c.commodity_id LIMIT 10";
    private Service<Void> backgroundThread;

    public HotlistModel(TableView<T> tableView){
        super(tableView);
        ConsoleLog.print("HotlistModel init");
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
                        try {

                            List<Hotsale> templist;

                            templist = jdbcOperations.query(kSelectsql,
                                    (resultSet, i) -> new Hotsale(
                                            String.valueOf(i+1),
                                            resultSet.getString("commodity_id"),
                                            resultSet.getString("bar_code"),
                                            resultSet.getString("name"),
                                            resultSet.getInt("classification_id"),
                                            resultSet.getString("specification"),
                                            resultSet.getString("unit"),
                                            resultSet.getInt("sum(quantity)"),
                                            resultSet.getString("price_db"),
                                            resultSet.getString("all_price_db")
                                    )
                            );

                            list.clear();
                            list.addAll((T[]) templist.toArray());

                            // Return callback with isfetchSuccess flag;
                            // TODO:

                        }catch (DataAccessException exception){
                            callback.call(true);
                            exception.printStackTrace();
                        }
                        return null;
                    }
                };
            }
        };

        backgroundThread.start();

    };  // end of fetchData
}
