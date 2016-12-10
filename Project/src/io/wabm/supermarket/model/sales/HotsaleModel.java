package io.wabm.supermarket.model.sales;

import io.wabm.supermarket.misc.config.DBConfig;
import io.wabm.supermarket.misc.pojo.Classification;
import io.wabm.supermarket.misc.pojo.Hotsale;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.model.Model;
import io.wabm.supermarket.model.TableViewModel;
import javafx.collections.FXCollections;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.util.Callback;
import javafx.scene.control.TableView;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Created by Administrator on 2016/11/20 0020.
 */
public class HotsaleModel<T> extends TableViewModel<T>{

    private final String kSelectAll = "select c.*,sum(sa.all_price_db),sum(quantity) from commodity c,sales_record sa,sales_record_detail sad where sa.sales_record_id=sad.sales_record_id and sad.commodity_id=c.commodity_id and EXTRACT(YEAR FROM timestamp) =? and EXTRACT(MONTH FROM timestamp)=?  group by c.commodity_id ORDER BY sum(sa.all_price_db) DESC LIMIT 10";
    private Service<Void> backgroundThread;

    public HotsaleModel(TableView<T> tableView){
        super(tableView);
        ConsoleLog.print("HotsaleModel init");
    }

    public void fetchData(String year,String month,Callback<Boolean, Void> callback){
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

                        templist = jdbcOperations.query(kSelectAll,
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
                                        resultSet.getString("sum(sa.all_price_db)")
                                )
                        ,year,month);

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
