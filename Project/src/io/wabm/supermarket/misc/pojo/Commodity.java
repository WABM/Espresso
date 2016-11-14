package io.wabm.supermarket.misc.pojo;

import java.math.BigDecimal;

/**
 * Created by MainasuK on 2016-11-14.
 */
public class Commodity {

    private int commodityID;
    private int classificationID;
    private String barcode;
    private int shelfLife;
    private String unit;
    private BigDecimal price;
    private int startStorage;
    private int deliverySpecification;

    // MARK: Setter
    public void setCommodityID(int commodityID) {
        this.commodityID = commodityID;
    }

    public void setClassificationID(int classificationID) {
        this.classificationID = classificationID;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public void setShelfLife(int shelfLife) {
        this.shelfLife = shelfLife;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setStartStorage(int startStorage) {
        this.startStorage = startStorage;
    }

    public void setDeliverySpecification(int deliverySpecification) {
        this.deliverySpecification = deliverySpecification;
    }

    // MARK: Getter
    public int getCommodityID() {

        return commodityID;
    }

    public int getClassificationID() {
        return classificationID;
    }

    public String getBarcode() {
        return barcode;
    }

    public int getShelfLife() {
        return shelfLife;
    }

    public String getUnit() {
        return unit;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getStartStorage() {
        return startStorage;
    }

    public int getDeliverySpecification() {
        return deliverySpecification;
    }

}
