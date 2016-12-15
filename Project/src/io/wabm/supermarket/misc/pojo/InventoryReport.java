package io.wabm.supermarket.misc.pojo;

import javafx.beans.property.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Administrator on 2016/12/15 0015.
 */
public class InventoryReport {
    final static public ArrayList<String> typeArray = new ArrayList<>(Arrays.asList("未知","盘盈","盘亏","报废"));

    private StringProperty commodityID;
    private StringProperty commodityName;
    private IntegerProperty quantity;
    private ObjectProperty<BigDecimal> price;
    private IntegerProperty type;
    private StringProperty employeeName;
    private StringProperty date;

    public InventoryReport(String commodityID,String commodityName,
                           int quantity,BigDecimal price,
                           int type,String employeeName,
                           String date){
        this.commodityID = new SimpleStringProperty(commodityID);
        this.commodityName = new SimpleStringProperty(commodityName);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.price = new SimpleObjectProperty<>(price);
        this.type = new SimpleIntegerProperty(type);
        this.employeeName = new SimpleStringProperty(employeeName);
        this.date = new SimpleStringProperty(date);
    }

    public String getCommodityID() {
        return commodityID.get();
    }

    public StringProperty commodityIDProperty() {
        return commodityID;
    }

    public void setCommodityID(String commodityID) {
        this.commodityID.set(commodityID);
    }

    public String getCommodityName() {
        return commodityName.get();
    }

    public StringProperty commodityNameProperty() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName.set(commodityName);
    }

    public int getQuantity() {
        return quantity.get();
    }

    public IntegerProperty quantityProperty() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }

    public BigDecimal getPrice() {
        return price.get();
    }

    public ObjectProperty<BigDecimal> priceProperty() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price.set(price);
    }

    public int getType() {
        return type.get();
    }

    public IntegerProperty typeProperty() {
        return type;
    }

    public void setType(int type) {
        this.type.set(type);
    }

    public String getTypeString(){
        return typeArray.get(getType());
    }

    public String getEmployeeName() {
        return employeeName.get();
    }

    public StringProperty employeeNameProperty() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName.set(employeeName);
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
