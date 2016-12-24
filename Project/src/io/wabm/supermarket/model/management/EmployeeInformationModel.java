package io.wabm.supermarket.model.management;

import io.wabm.supermarket.misc.pojo.Employee;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.misc.util.WABMThread;
import io.wabm.supermarket.model.FilteredTableViewModel;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.util.Assert;

import java.sql.ResultSet;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created by liu on 2016-11-19 .
 */
    public class  EmployeeInformationModel<T> extends FilteredTableViewModel<T> {

    private final String kSelectAll = "SELECT f.* FROM employee f";
    private final String kInsertSQL = "INSERT INTO employee (employee_id, name,sex_status,phone,position_status,entry_date,username,password) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private final String kInsertSQLAutoIncrease = "INSERT INTO employee (name, birth_date,sex_status,phone,position_status,entry_date,username,password) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
    private final String kDeleteSQLWithID = "DELETE FROM employee WHERE employee.employee_id = ?;";
    private final String kUpdateValidWithID = "UPDATE employee SET valid = ? WHERE employee.employee_id = ?";

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
                            resultSet.getInt("position_status"),
                            resultSet.getString("entry_date"),   //数据库中取出来是date
                            resultSet.getString("username"),
                            resultSet.getString("password"),
                            resultSet.getBoolean("valid")

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
            if (employee.getEmployeeID()<0) {
                jdbcOperations.update(kInsertSQLAutoIncrease,
                        employee.getName(),
                        employee.getBirthdate(),
                        employee.getSex(),
                        employee.getPhone(),
                        employee.getDepartment(),
                        employee.getEntrydate(),
                        employee.getUsername(),
                        employee.getPassword()
                        );
                SqlRowSet rowSet = jdbcOperations.queryForRowSet("select LAST_INSERT_ID() AS id;");
                if (rowSet.next()) {
                    employee.setEmployeeID(rowSet.getInt("id"));
                }
            } else {
                jdbcOperations.update(kInsertSQLAutoIncrease,
                      //  employee.getEmployeeID(),
                        employee.getName(),
                        employee.getBirthdate(),
                        employee.getSex(),
                        employee.getPhone(),
                        employee.getDepartment(),
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

    public void remove(Employee employee, Callback<DataAccessException, Void> callback) {
        ConsoleLog.print("remove employee: " + employee.getName());
        Assert.notNull(jdbcOperations);

        try {

            jdbcOperations.update(kUpdateValidWithID , 0 , employee.getEmployeeID());

            employee.setValid(false);
            callback.call(null);

        } catch (QueryTimeoutException timeoutException) {
            callback.call(timeoutException);
        } catch (DataAccessException dataAccessException) {
            callback.call(dataAccessException);
        }

    }
    public void update(Employee employee, Callback<DataAccessException, Void> callback) {
        ConsoleLog.print("modify employee: " + employee.getName());
        Assert.notNull(jdbcOperations);

        try {
            jdbcOperations.update("UPDATE employee SET name=?, birth_date=?, sex_status=?, phone=?, position_status=?, entry_date=? ,username=?,password=? WHERE employee_id=?",
                   // employee.getEmployeeID(),
                    employee.getName(),
                    employee.getBirthdate(),
                    employee.getSex(),
                    employee.getPhone(),
                    employee.getDepartment(),
                    employee.getEntrydate(),
                    employee.getUsername(),
                    employee.getPassword(),
                    employee.getEmployeeID()
            );
            callback.call(null);
        } catch (DataAccessException exception) {
            callback.call(exception);
        }

    }

    public void setFilter(Predicate<T> predicate) {
        ConsoleLog.print("set predicate");
        setPredicate(predicate);
    }

}


