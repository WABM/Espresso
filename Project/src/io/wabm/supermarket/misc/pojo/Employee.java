package io.wabm.supermarket.misc.pojo;

import io.wabm.supermarket.controller.management.SalesAchievementviewController;
import javafx.beans.property.*;

import java.sql.Date;
import java.util.Calendar;

/**
 * Created by liu on 2016-11-20 .
 */
public class Employee {
    private IntegerProperty employeeID;
    private StringProperty name;



    private StringProperty birthdate;
    private StringProperty sex;
    private StringProperty phone;
    private StringProperty department;
    private StringProperty entrydate;

    public Employee(int employeeID, String name, String birthdate, String sex, String phone, String department, String entrydate)
    {
        this.employeeID = new SimpleIntegerProperty(employeeID);
        this.name = new SimpleStringProperty(name);
        this.birthdate = new SimpleStringProperty(birthdate);
        this.sex = new SimpleStringProperty(sex);
        this.phone = new SimpleStringProperty(phone);
        this.department = new SimpleStringProperty(department);
        this.entrydate = new SimpleStringProperty(entrydate);
    }

    public Employee(IntegerProperty employeeID, StringProperty name, IntegerProperty age, StringProperty sex, StringProperty phone, StringProperty department) {
        this.employeeID = employeeID;
        this.name = name;
        this.birthdate = birthdate;
        this.sex = sex;
        this.phone = phone;
        this.department = department;
        this.entrydate=entrydate;
    }

    public int getEmployeeID() {
        return employeeID.get();
    }

    public IntegerProperty employeeIDProperty() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID.set(employeeID);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }



    public String getSex() {
        return sex.get();
    }

    public StringProperty sexProperty() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex.set(sex);
    }

    public String getPhone() {
        return phone.get();
    }

    public StringProperty phoneProperty() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    public String getDepartment() {
        return department.get();
    }

    public StringProperty departmentProperty() {
        return department;
    }

    public void setDepartment(String department) {
        this.department.set(department);
    }

    public String getBirthdate() {
        return birthdate.get();
    }

    public StringProperty birthdateProperty() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {this.birthdate.set(birthdate);}

    public String getEntrydate() {return entrydate.get();}

    public StringProperty entrydateProperty() {return entrydate;}

    public void setEntrydate(String entrydate) {this.entrydate.set(entrydate);}

}
