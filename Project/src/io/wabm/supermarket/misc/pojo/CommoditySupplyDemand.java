package io.wabm.supermarket.misc.pojo;

import javafx.beans.property.*;

import java.math.BigDecimal;

/**
 * Created by 14580 on 2016/12/8 0008.
 */
public class CommoditySupplyDemand {
    private StringProperty commodityID;
    private StringProperty barcode;
    private StringProperty commodityname;
    private StringProperty classification;
    private StringProperty specification;
    private IntegerProperty deliveryspecification;
    private StringProperty unit;
    private IntegerProperty quantity;
    private DoubleProperty price;
    //private StringProperty supplier;

    public CommoditySupplyDemand(String commodityID,String barcode,String commodityname, String classification, String specification,int deliveryspecification,String unit,int quantity,Double price)
    {
        this.commodityID = new SimpleStringProperty(commodityID);
        this.barcode = new SimpleStringProperty(barcode);
        this.commodityname = new SimpleStringProperty(commodityname);
        this.classification = new SimpleStringProperty(classification);
        this.specification = new SimpleStringProperty(specification);
        this.deliveryspecification = new SimpleIntegerProperty(deliveryspecification);
        this.unit = new SimpleStringProperty(unit);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.price = new SimpleDoubleProperty(price);
        //this.supplier = new SimpleStringProperty(supplier);

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


    public String getBarcode() {
        return barcode.get();
    }

    public StringProperty barcodeProperty() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode.set(barcode);
    }


    public String getCommodityname() {
        return commodityname.get();
    }

    public StringProperty commoditynameProperty() {
        return commodityname;
    }

    public void setCommodityname(String commodityname) {
        this.commodityname.set(commodityname);
    }


    public String getClassification() {
        return classification.get();
    }

    public StringProperty classificationProperty() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification.set(classification);
    }


    public String getSpecification() {
        return specification.get();
    }

    public StringProperty specificationProperty() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification.set(specification);
    }

    public int getDeliveryspecification() {
        return deliveryspecification.get();
    }

    public IntegerProperty deliveryspecificationProperty() {
        return deliveryspecification;
    }

    public void setDeliveryspecification(int deliveryspecification) {
        this.deliveryspecification.set(deliveryspecification);
    }

    public Integer getQuantity() {
        return quantity.get();
    }

    public IntegerProperty quantityProperty() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }


    public String getUnit() {
        return unit.get();
    }

    public StringProperty unitProperty() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit.set(unit);
    }


    public Double getPrice() {
        return price.get();
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public void setPrice(Double price) {
        this.price.set(price);
    }



}
