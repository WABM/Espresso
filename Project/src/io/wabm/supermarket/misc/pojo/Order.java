package io.wabm.supermarket.misc.pojo;

import javafx.beans.property.*;

/**
 * Created by 14580 on 2016/12/4 0004.
 */
public class Order{

    private IntegerProperty orderID;
    private StringProperty supplierName;
    private StringProperty create_timestamp;
    private IntegerProperty status;

    public Order(int orderID, String supplierName, String create_timestamp,int status)
    {
        this.orderID = new SimpleIntegerProperty(orderID);
        this.supplierName = new SimpleStringProperty(supplierName);
        this.create_timestamp = new SimpleStringProperty(create_timestamp);
        this.status = new SimpleIntegerProperty(status);
    }

    public String getSupplierName() {
        return supplierName.get();
    }

    public StringProperty supplierNameProperty() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName.set(supplierName);
    }


    public int  getOrderID() {return orderID.get();}

    public IntegerProperty orderIDProperty() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID.set(orderID);
    }


    public String getCreate_timestamp() {
        return create_timestamp.get();
    }

    public StringProperty create_timestampProperty() {
        return create_timestamp;
    }

    public void setCreate_timestamp(String create_timestamp) {this.create_timestamp.set(create_timestamp);}


    public int  getStatus() {
        return status.get();
    }

    public IntegerProperty statusProperty() {
        return status;
    }

    public void setStatus(int status) {
        this.status.set(status);
    }



}
