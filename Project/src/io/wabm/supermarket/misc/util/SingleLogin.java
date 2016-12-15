package io.wabm.supermarket.misc.util;

import io.wabm.supermarket.misc.pojo.Employee;

/**
 * Created by Administrator on 2016/12/5 0005.
 */
public class SingleLogin {

    private Employee employee;
    private SingleLogin() {}

    private static class SingletonInstance {
        private static final SingleLogin INSTANCE = new SingleLogin();
    }

    public static SingleLogin getInstance() {
        return SingletonInstance.INSTANCE;
    }

    public void initEmployee(Employee employee1){
        employee = new Employee(employee1.getEmployeeID(),
                                employee1.getName(),
                                employee1.getBirthdate(),
                                employee1.getSex(),
                                employee1.getPhone(),
                                employee1.getDepartment(),
                                employee1.getEntrydate(),
                                employee1.getUsername(),
                                employee1.getPassword(),
                                employee1.isValid());
    }
    public Employee getEmployee(){
        return employee;
    }
}
