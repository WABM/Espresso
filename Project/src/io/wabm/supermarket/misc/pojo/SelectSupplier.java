package io.wabm.supermarket.misc.pojo;

import javafx.beans.property.*;

/**
 * Created by 14580 on 2016/12/9 0009.
 */
public class SelectSupplier {
    private IntegerProperty supplierid;
    private StringProperty supplierName;
    private DoubleProperty price;
    private StringProperty deliveryTimeCost;

    public SelectSupplier(int supplierid ,String supplierName,Double  price,String deliveryTimeCost){
        this.supplierid = new SimpleIntegerProperty(supplierid);
        this.supplierName = new SimpleStringProperty(supplierName);
        this.price = new SimpleDoubleProperty(price);
        this.deliveryTimeCost = new SimpleStringProperty(deliveryTimeCost);
    }

    public double getPrice() {
        return price.get();
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public void setPrice(double price) {
        this.price.set(price);
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


    public String getDeliveryTimeCost() {
        return deliveryTimeCost.get();
    }

    public StringProperty deliveryTimeCostProperty() {
        return deliveryTimeCost;
    }

    public void setDeliveryTimeCost(String deliveryTimeCost) {
        this.deliveryTimeCost.set(deliveryTimeCost);
    }

    public Integer getSupplierid() {
        return supplierid.get();
    }

    public IntegerProperty supplieridProperty() {
        return supplierid;
    }

    public void setSupplierid(Integer supplierid) {
        this.supplierid.set(supplierid);
    }


}
