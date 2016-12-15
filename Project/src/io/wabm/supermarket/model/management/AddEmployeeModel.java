package io.wabm.supermarket.model.management;

import io.wabm.supermarket.misc.pojo.Employee;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.model.ComboBoxModel;
import javafx.scene.control.ComboBox;
import javafx.util.Callback;
import org.springframework.dao.DataAccessException;

import java.util.List;

/*
 Created by liu on 2016-11-29 .*/

public class AddEmployeeModel<T> extends ComboBoxModel<T>{
    private ComboBox<T> comboBox;
    private final String kSelectAll = "SELECT f.* FROM wabm.Employee f";

    public AddEmployeeModel(ComboBox<T> comboBox) {
        super(comboBox);

        this.comboBox = comboBox;

        ConsoleLog.print("AddEmployeeModel init");
    }

    public void fetchEmployee(Callback<Boolean, Void> callback) {
        ConsoleLog.print("fetching…");

        try {
            List<Employee> templist = jdbcOperations.query(
                    kSelectAll,
                    (resultSet, i) -> {
                        Employee employee;
                        employee = new Employee(
                                resultSet.getInt("employee_id"),
                                resultSet.getString("name"),
                                resultSet.getString("birth_date"),
                                resultSet.getInt("sex_status"),
                                resultSet.getString("phone"),
                                resultSet.getInt("position_status"),
                                resultSet.getString("entry_date"),
                                resultSet.getString("username"),
                                resultSet.getString("password"),
                                resultSet.getBoolean("valid")
                        );

                        return employee;
                    }
            );

            list.clear();
            list.addAll((T[]) templist.toArray());

            ConsoleLog.print("… Get " + list.size() + " item(s)");

            callback.call(true);

        } catch (DataAccessException e) {
            callback.call(false);
            e.printStackTrace();
        }

    }
}

