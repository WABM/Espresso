package io.wabm.supermarket.misc.pojo;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2016/12/11 0011.
 */
public class CommodityPriceInformation {
    private StringProperty commodityID;
    private StringProperty barcode;
    private StringProperty commodityName;
    private StringProperty className;
    private StringProperty specification;
    private StringProperty unit;
    private ObjectProperty<BigDecimal> price;

    public CommodityPriceInformation(String commodityID,String barcode,
                                     String commodityName,String className,
                                     String specification, String unit,BigDecimal price){
        this.commodityID = new SimpleStringProperty(commodityID);
        this.barcode = new SimpleStringProperty(barcode);
        this.commodityName = new SimpleStringProperty(commodityName);
        this.className = new SimpleStringProperty(className);
        this.specification = new SimpleStringProperty(specification);
        this.unit = new SimpleStringProperty(unit);
        this.price = new SimpleObjectProperty<>(price);
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

    public String getCommodityName() {
        return commodityName.get();
    }

    public StringProperty commodityNameProperty() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName.set(commodityName);
    }

    public String getClassName() {
        return className.get();
    }

    public StringProperty classNameProperty() {
        return className;
    }

    public void setClassName(String className) {
        this.className.set(className);
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

    public BigDecimal getPrice() {
        return price.get();
    }

    public ObjectProperty<BigDecimal> priceProperty() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price.set(price);
    }
}
