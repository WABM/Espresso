package io.wabm.supermarket.misc.pojo;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by Administrator on 2016/11/19 0019.
 */
public class TransactionRecord {
    private StringProperty recordID;
    private StringProperty commodityID;
    private StringProperty barcode;
    private StringProperty name;
    private IntegerProperty classificationID;
    private StringProperty specification;
    private StringProperty unit;
    private IntegerProperty quantity;
    private StringProperty price;
    private StringProperty totalPrice;

    public TransactionRecord(String recordID,String commodityID,
                             String barcode,String name,
                             int classificationID,String specification,
                             String unit,int quantity,
                             String price,String totalPrice){
        this.recordID = new SimpleStringProperty(recordID);
        this.commodityID = new SimpleStringProperty(commodityID);
        this.barcode = new SimpleStringProperty(barcode);
        this.name = new SimpleStringProperty(name);
        this.classificationID = new SimpleIntegerProperty(classificationID);
        this.specification = new SimpleStringProperty(specification);
        this.unit = new SimpleStringProperty(unit);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.price = new SimpleStringProperty(price);
        this.totalPrice = new SimpleStringProperty(totalPrice);
    }
    public String getRecordID() {
        return recordID.get();
    }

    public StringProperty recordIDProperty() {
        return recordID;
    }

    public void setRecordID(String recordID) {
        this.recordID.set(recordID);
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

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public int getClassificationID() {
        return classificationID.get();
    }

    public IntegerProperty classificationIDProperty() {
        return classificationID;
    }

    public void setClassificationID(int classificationID) {
        this.classificationID.set(classificationID);
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

    public String getUnit() {
        return unit.get();
    }

    public StringProperty unitProperty() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit.set(unit);
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

    public String getPrice() {
        return price.get();
    }

    public StringProperty priceProperty() {
        return price;
    }

    public void setPrice(String price) {
        this.price.set(price);
    }

    public String getTotalPrice() {
        return totalPrice.get();
    }

    public StringProperty totalPriceProperty() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice.set(totalPrice);
    }
 }
