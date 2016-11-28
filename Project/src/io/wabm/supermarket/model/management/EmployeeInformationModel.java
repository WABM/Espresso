package io.wabm.supermarket.model.management;

import io.wabm.supermarket.misc.config.DBConfig;
import io.wabm.supermarket.misc.pojo.Classification;
import io.wabm.supermarket.misc.pojo.Employee;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.model.Model;
import io.wabm.supermarket.model.TableViewModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.util.Assert;

import java.util.Calendar;
import java.util.List;

/**
 * Created by liu on 2016-11-19 .
 */
@Repository
@ContextConfiguration(classes = DBConfig.class)
    public class  EmployeeInformationModel<T> extends TableViewModel<T> {

    private final String kSelectAll = "SELECT f.* FROM wabm.Employee f";
    private Service<Void> backgroundThread;
        public EmployeeInformationModel(TableView<T> tableView) {

            super(tableView);
            ConsoleLog.print("EmployeeInformationModel init");
        }

    public void fetchData(Callback<Boolean, Void> callback) {
        ConsoleLog.print("fetching data…");
        Assert.notNull(jdbcOperations);

        backgroundThread = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {

                        List<Employee> templist;

                        templist = jdbcOperations.query(kSelectAll,
                                (resultSet, i) -> new Employee(
                                        resultSet.getInt("employee_id"),
                                        resultSet.getString("name"),
                                        resultSet.getString("birth_date"),     //date类型的，要转换为string类型
                                        resultSet.getString("sex_status"),
                                        resultSet.getString("phone"),
                                        resultSet.getString("position_status"),
                                        resultSet.getString("entry_date")   //数据库中取出来是date
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


    }//end of fetchData

}
