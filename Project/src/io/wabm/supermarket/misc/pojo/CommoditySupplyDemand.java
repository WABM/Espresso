package io.wabm.supermarket.misc.pojo;

import javafx.beans.property.*;

import java.math.BigDecimal;

/**
 * Created by 14580 on 2016/12/8 0008.
 */
public class CommoditySupplyDemand {
    private StringProperty commodityid;
    private StringProperty barcode;
    private StringProperty commodityname;
    private StringProperty classification;
    private IntegerProperty specification;
    private IntegerProperty deliveryspecification;
    private StringProperty unit;
    private IntegerProperty quantity;
    private ObjectProperty<BigDecimal> price;
    //private StringProperty supplier;

    public CommoditySupplyDemand(String commodityid,String barcode,String commodityname, String classification, int specification,int deliveryspecification,String unit,int quantity,BigDecimal price)
    {
        this.commodityid = new SimpleStringProperty(commodityid);
        this.barcode = new SimpleStringProperty(barcode);
        this.commodityname = new SimpleStringProperty(commodityname);
        this.classification = new SimpleStringProperty(classification);
        this.specification = new SimpleIntegerProperty(specification);
        this.deliveryspecification = new SimpleIntegerProperty(deliveryspecification);
        this.unit = new SimpleStringProperty(unit);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.price = new SimpleObjectProperty<>(price);
        //this.supplier = new SimpleStringProperty(supplier);

    }
    public String getCommodityid() {
        return commodityid.get();
    }

    public StringProperty commodityidProperty() {
        return commodityid;
    }

    public void setCommodityid(String commodityid) {
        this.commodityid.set(commodityid);
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


    public int getSpecification() {
        return specification.get();
    }

    public IntegerProperty specificationProperty() {
        return specification;
    }

    public void setSpecification(int specification) {
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

    public int getQuantity() {
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


    public BigDecimal getPrice() {
        return price.get();
    }

    public ObjectProperty<BigDecimal> priceProperty() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price.set(price);
    }


//    public String getsupplier() {
//        return supplier.get();
//    }
//
//    public StringProperty supplierProperty() {
//        return supplier;
//    }
//
//    public void setSupplier(String supplier) {
//        this.supplier.set(supplier);
//    }

}
