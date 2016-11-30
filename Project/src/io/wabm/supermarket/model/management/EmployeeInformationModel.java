package io.wabm.supermarket.model.management;

import io.wabm.supermarket.misc.config.DBConfig;
import io.wabm.supermarket.misc.pojo.Classification;
import io.wabm.supermarket.misc.pojo.Employee;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.misc.util.WABMThread;
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
import org.springframework.dao.DataAccessException;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.util.Assert;

import java.sql.ResultSet;
import java.util.Calendar;
import java.util.List;

/**
 * Created by liu on 2016-11-19 .
 */
    public class  EmployeeInformationModel<T> extends TableViewModel<T> {

    private final String kSelectAll = "SELECT f.* FROM wabm.Employee f";
    private final String kInsertSQL = "INSERT INTO wabm.employee (employee_id, name, birth_date,sex_status,phone,position_status,entry_date,username,password) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private final String kInsertSQLAutoIncrease = "INSERT INTO wabm.employee (name, birth_date,sex_status,phone,position_status,entry_date,username,password) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
    private final String kDeleteSQLWithID = "DELETE FROM wabm.employee WHERE wabm.employee.employee_id = ?;";

   // private int employeeID;

        public EmployeeInformationModel(TableView<T> tableView) {

            super(tableView);
            ConsoleLog.print("EmployeeInformationModel init");
        }

   /* public int getEmployeeID() {
        return employeeID;
    }*/

    public void fetchData(Callback<Boolean, Void> callback) {
        ConsoleLog.print("fetching ...");
        Assert.notNull(jdbcOperations);

        new WABMThread().run((_void) -> {
            ConsoleLog.print("Start background task…");

            try {
                List<Employee> templist = jdbcOperations.query(kSelectAll, (ResultSet resultSet, int i) -> {
                    Employee employee;
                    employee = new Employee(
                            resultSet.getInt("employee_id"),
                            resultSet.getString("name"),
                            resultSet.getString("birth_date"),     //date类型的，要转换为string类型
                            resultSet.getInt("sex_status"),
                            resultSet.getString("phone"),
                            resultSet.getString("position_status"),
                            resultSet.getString("entry_date"),   //数据库中取出来是date
                            resultSet.getString("username"),
                            resultSet.getString("password")
                    );
                    return employee;
                });

                list.clear();
                list.addAll((T[]) templist.toArray());

                // Return callback with isfetchSuccess flag;
                // TODO:
                callback.call(true);
            } catch (DataAccessException e) {
                e.printStackTrace();
                callback.call(false);
            }

            return null;
            });
        }
    public void add(Employee employee, Callback<DataAccessException, Void> callback) {
        ConsoleLog.print("Add employee: " + employee.getName());
        Assert.notNull(jdbcOperations);
        try {
            if (String.valueOf(employee.getEmployeeID()) == null || String.valueOf(employee.getEmployeeID()) == "") {
                jdbcOperations.update(kInsertSQLAutoIncrease,
                        employee.getName(),
                        employee.getBirthdate(),
                        employee.getSex(),
                        employee.getPhone(),
                        employee.getDepartmentStatus(),
                        employee.getEntrydate(),
                        employee.getUsername(),
                        employee.getPassword()
                        );
            } else {
                jdbcOperations.update(kInsertSQL,
                        employee.getEmployeeID(),
                        employee.getName(),
                        employee.getBirthdate(),
                        employee.getSex(),
                        employee.getPhone(),
                        employee.getDepartmentStatus(),
                        employee.getEntrydate(),
                        employee.getUsername(),
                        employee.getPassword()
                        );
            }
            add((T) employee);
            callback.call(null);
        } catch (QueryTimeoutException timeoutException) {
            callback.call(timeoutException);
        } catch (DataAccessException dataAccessException) {
            callback.call(dataAccessException);
        }

    }   // end add(…) { … }

    public void delete(Employee employee, Callback<DataAccessException, Void> callback) {
        ConsoleLog.print("Delete employee: " + employee.getName());
        Assert.notNull(jdbcOperations);

        try {

            jdbcOperations.update(kDeleteSQLWithID, employee.getEmployeeID());

            // No error
            delete((T) employee);
            callback.call(null);

        } catch (QueryTimeoutException timeoutException) {
            callback.call(timeoutException);
        } catch (DataAccessException dataAccessException) {
            callback.call(dataAccessException);
        }
    }//end of delete

}


