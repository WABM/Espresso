package io.wabm.supermarket.misc.pojo;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2016/11/20 0020.
 */
public class CashInformation {
    private StringProperty employeeID;
    private StringProperty name;
    private ObjectProperty<BigDecimal> moneyIN;
    private ObjectProperty<BigDecimal> moneyOUT;
    private ObjectProperty<BigDecimal> moneyShould;
    private StringProperty date;

    public CashInformation(String employeeID,String name,
                           BigDecimal moneyIN,BigDecimal moneyOUT,
                           BigDecimal moneyShould,
                           String date){
        this.employeeID = new SimpleStringProperty(employeeID);
        this.name = new SimpleStringProperty(name);
        this.moneyIN = new SimpleObjectProperty<>(moneyIN);
        this.moneyOUT = new SimpleObjectProperty<>(moneyOUT);
        this.moneyShould = new SimpleObjectProperty<>(moneyShould);
        this.date = new SimpleStringProperty(date);

        moneyIN.setScale(2);
        moneyOUT.setScale(2);
        moneyShould.setScale(2);
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

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public BigDecimal getMoneyIN() {
        return moneyIN.get();
    }

    public ObjectProperty<BigDecimal> moneyINProperty() {
        return moneyIN;
    }

    public void setMoneyIN(BigDecimal moneyIN) {
        this.moneyIN.set(moneyIN);
    }

    public BigDecimal getMoneyOUT() {
        return moneyOUT.get();
    }

    public ObjectProperty<BigDecimal> moneyOUTProperty() {
        return moneyOUT;
    }

    public void setMoneyOUT(BigDecimal moneyOUT) {
        this.moneyOUT.set(moneyOUT);
    }

    public BigDecimal getMoneyShould() {
        return moneyShould.get();
    }

    public ObjectProperty<BigDecimal> moneyShouldProperty() {
        return moneyShould;
    }

    public void setMoneyShould(BigDecimal moneyShould) {
        this.moneyShould.set(moneyShould);
    }

    public String getDate() {
        return date.get();
    }

    public StringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }
}
