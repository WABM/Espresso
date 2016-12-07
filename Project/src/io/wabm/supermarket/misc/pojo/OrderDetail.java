package io.wabm.supermarket.misc.pojo;

import javafx.beans.property.*;

/**
 * Created by 14580 on 2016/12/7 0007.
 */
public class OrderDetail {
    private IntegerProperty orderID;
    private IntegerProperty commodityID;
    private DoubleProperty price_db;
    private IntegerProperty quantity;
    private StringProperty production_date;

    public OrderDetail(int orderID,int commodityID,Double  price_db,int quantity, String production_date){
        this.orderID = new SimpleIntegerProperty(orderID);
        this.commodityID = new SimpleIntegerProperty(commodityID);
        this.price_db = new SimpleDoubleProperty(price_db);
        this.quantity= new SimpleIntegerProperty(quantity);
        this.production_date = new SimpleStringProperty(production_date);
    }

    public int getOrderID() {
        return orderID.get();
    }

    public IntegerProperty orderIDProperty() {return orderID;}

    public void setOrderID(int orderID) {
        this.orderID.set(orderID);
    }


    public int getCommodityID() {
        return commodityID.get();
    }

    public IntegerProperty commodityIDProperty() {
        return commodityID;
    }

    public void setCommodityID(int commodityID) {
        this.commodityID.set(commodityID);
    }


    public double getPrice_db() {
        return price_db.get();
    }

    public DoubleProperty price_dbProperty() {
        return price_db;
    }

    public void setPrice_db(double price_db) {
        this.price_db.set(price_db);
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


    public String getProduction_date() {
        return production_date.get();
    }

    public StringProperty production_dateProperty() {
        return production_date;
    }

    public void setProduction_date(String production_date) {
        this.production_date.set(production_date);
    }


}
