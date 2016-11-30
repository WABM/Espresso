package io.wabm.supermarket.misc.pojo;

import javafx.beans.property.*;
import java.time.LocalDate;

/**
 * Created by Administrator on 2016/11/20 0020.
 */
public class CashInformation {

    private StringProperty cashRegisterID;
    private StringProperty employeeID;
    private DoubleProperty moneyIN;
    private DoubleProperty moneyOUT;
    private DoubleProperty moneyShould;
    private ObjectProperty<LocalDate> date;

    public CashInformation(String CashRegisterID,String EmployeeID,
                           Double MoneyIN,Double MoneyOUT,
                           Double MoneyShould){
        this.cashRegisterID = new SimpleStringProperty(CashRegisterID);
        this.employeeID = new SimpleStringProperty(EmployeeID);
        this.moneyIN = new SimpleDoubleProperty(MoneyIN);
        this.moneyOUT = new SimpleDoubleProperty(MoneyOUT);
        this.moneyShould = new SimpleDoubleProperty(MoneyShould);
        //this.date = new SimpleObjectProperty<LocalDate>(date);
    }

    public String getCashRegisterID() {
        return cashRegisterID.get();
    }

    public StringProperty cashRegisterIDProperty() {
        return cashRegisterID;
    }

    public void setCashRegisterID(String cashRegisterID) {
        this.cashRegisterID.set(cashRegisterID);
    }

    public String getEmployeeID() {
        return employeeID.get();
    }

    public StringProperty employeeIDProperty() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID.set(employeeID);
    }

    public double getMoneyIN() {
        return moneyIN.get();
    }

    public DoubleProperty moneyINProperty() {
        return moneyIN;
    }

    public void setMoneyIN(double moneyIN) {
        this.moneyIN.set(moneyIN);
    }

    public double getMoneyOUT() {
        return moneyOUT.get();
    }

    public DoubleProperty moneyOUTProperty() {
        return moneyOUT;
    }

    public void setMoneyOUT(double moneyOUT) {
        this.moneyOUT.set(moneyOUT);
    }

    public double getMoneyShould() {
        return moneyShould.get();
    }

    public DoubleProperty moneyShouldProperty() {
        return moneyShould;
    }

    public void setMoneyShould(double moneyShould) {
        this.moneyShould.set(moneyShould);
    }

    public LocalDate getDate() {
        return date.get();
    }

    public ObjectProperty<LocalDate> dateProperty() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date.set(date);
    }
}
