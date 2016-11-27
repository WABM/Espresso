package io.wabm.supermarket.misc.pojo;

import io.wabm.supermarket.controller.management.SalesAchievementviewController;
import javafx.beans.property.*;

/**
 * Created by liu on 2016-11-20 .
 */
public class Employee {
    private StringProperty employeeID;
    private StringProperty name;
    private StringProperty age;
    private StringProperty sex;
    private StringProperty phone;
    private StringProperty entrydate;
    private StringProperty department;
    private BooleanProperty choosed;
    public Employee(String employeeID, String name, String age, String sex,String phone,String entrydate,String department)
    {
        this.employeeID = new SimpleStringProperty(employeeID);
        this.name = new SimpleStringProperty(name);
        this.age = new SimpleStringProperty(age);
        this.sex = new SimpleStringProperty(sex);
        this.phone = new SimpleStringProperty(phone);
        this.entrydate = new SimpleStringProperty(entrydate);
        this.department = new SimpleStringProperty(department);
    }
    public String getEmployeeID() { return  employeeID.get(); }

    public StringProperty employeeIDProperty() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID.set(employeeID);
    }


    public String getName() {return name.get();}

    public StringProperty nameProperty() {return name;}

    public void setName(String name) {this.name.set(name);}


    public  String getAge() {return age.get();}

    public StringProperty ageProperty() {return age;}

    public void setAge(String age) {this.age.set(age);}


    public String getSex() {return sex.get();}

    public StringProperty sexProperty() {return sex;}

    public void setSex(String sex) {this.sex.set(sex);}


    public String getPhone() {return phone.get();}

    public StringProperty phoneProperty() {return phone;}

    public void setPhone(String phone) {this.phone.set(phone);}


    public String getEntrydate() {return entrydate.get();}

    public StringProperty entrydateProperty() {return entrydate;}

    public void setEntrydate(String entrydate) {this.entrydate.set(entrydate);}


    public String getDepartment() {return department.get();}

    public StringProperty departmentProperty() {return department;}

    public void setDepartment(String department) {this.department.set(department);}


    public boolean isChoosed() {return choosed.get();}

    public BooleanProperty choosedProperty() {
        return choosed;
    }

    public void setChoosed(boolean choosed) {
        this.choosed.set(choosed);
    }


}
