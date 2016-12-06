package io.wabm.supermarket.misc.pojo;

import javafx.beans.property.*;

/**
 * Created by 14580 on 2016/12/4 0004.
 */
public class Order{

    private IntegerProperty orderID;
    private IntegerProperty supplierID;
    private StringProperty create_timestamp;
    private IntegerProperty status;

    public Order(int orderID, int supplierID, String create_timestamp,int status)
    {
        this.orderID = new SimpleIntegerProperty(orderID);
        this.supplierID = new SimpleIntegerProperty(supplierID);
        this.create_timestamp = new SimpleStringProperty(create_timestamp);
        this.status = new SimpleIntegerProperty(status);
    }

    public int getSupplierID() {
        return supplierID.get();
    }

    public IntegerProperty supplierIDProperty() {
        return supplierID;
    }

    public void setSupplierID(int supplierID) {
        this.supplierID.set(supplierID);
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
