package io.wabm.supermarket.misc.pojo;

import javafx.beans.property.*;

/**
 * Created by 14580 on 2016/12/5 0005.
 */
public class SupplyGoods {
    private StringProperty commodityName;
    private DoubleProperty price_db;
    private StringProperty delivery_time_cost;

    public SupplyGoods(String commodityName,Double  price_db,String delivery_time_cost){
        this.commodityName = new SimpleStringProperty(commodityName);
        this.price_db = new SimpleDoubleProperty(price_db);
        this.delivery_time_cost = new SimpleStringProperty(delivery_time_cost);
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


    public String getCommodityName() {
        return commodityName.get();
    }

    public StringProperty commodityNameProperty() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName.set(commodityName);
    }


    public String getDelivery_time_cost() {
        return delivery_time_cost.get();
    }

    public StringProperty delivery_time_costProperty() {
        return delivery_time_cost;
    }

    public void setDelivery_time_cost(String delivery_time_cost) {
        this.delivery_time_cost.set(delivery_time_cost);
    }

}
