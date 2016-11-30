package io.wabm.supermarket.misc.pojo;

import io.wabm.supermarket.controller.management.SalesAchievementviewController;
import javafx.beans.property.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

/**
 * Created by liu on 2016-11-20 .
 */
public class Employee {
    final static public ArrayList<String> sexArray = new ArrayList<>(Arrays.asList("男","女"));
    static private ArrayList<String> positonArray = new ArrayList<>(Arrays.asList("总管理员","仓库管理员","收银员","采购管理员","销售管理员"));

    private IntegerProperty employeeID;
    private StringProperty name;
    private StringProperty birthdate;
    private IntegerProperty sex;
    private StringProperty phone;
    private StringProperty department;
    private StringProperty entrydate;
    private StringProperty username;
    private StringProperty password;


    public Employee(int employeeID, String name, String birthdate, int sex, String phone, String department, String entrydate,String username,String password)
    {
        this.employeeID = new SimpleIntegerProperty(employeeID);
        this.name = new SimpleStringProperty(name);
        this.birthdate = new SimpleStringProperty(birthdate);
        this.sex = new SimpleIntegerProperty(sex);
        this.phone = new SimpleStringProperty(phone);
        this.department = new SimpleStringProperty(department);

        this.entrydate = new SimpleStringProperty(entrydate);

        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
    }

    public Employee(IntegerProperty employeeID, StringProperty name, StringProperty birthdate, IntegerProperty sex, StringProperty phone, StringProperty department, StringProperty entrydate,StringProperty username,StringProperty password) {
        this.employeeID = employeeID;
        this.name = name;
        this.birthdate = birthdate;
        this.sex = sex;
        this.phone = phone;
        this.department = department;
        this.entrydate=entrydate;
        this.username=username;
        this.password=password;
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


    public int getSex() {
        return sex.get();
    }

    public IntegerProperty sexProperty() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex.set(sex);
    }

    public String getSexString() {
        return sexArray.get(getSex());
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

    public int getDepartmentStatus() {
        return positonArray.indexOf(getDepartment());
    }

    public String getDepartmentString() {
        try {
            return positonArray.get(Integer.parseInt(getDepartment()));
        } catch (NumberFormatException e) {
            return getDepartment();
        }
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


   public String getEntrydate() { return entrydate.get(); }

    public StringProperty entrydateProperty() { return entrydate;}

    public void setEntrydate(String entrydate) { this.entrydate.set(entrydate);}
    public String getUsername() {
        return username.get();
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public String getPassword() {
        return password.get();
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

}
