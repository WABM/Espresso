package io.wabm.supermarket.misc.pojo;

import javafx.beans.property.*;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.DecimalFormat;

/**
 * Created by MainasuK on 2016-11-14.
 */
public class Commodity {

    private StringProperty commodityID;
    private IntegerProperty classificationID;
    private StringProperty barcode;
    private StringProperty name;
    private StringProperty specification;
    private StringProperty unit;
    private StringProperty price;
    private IntegerProperty deliverySpecification;

    private IntegerProperty shelfLife;
    private IntegerProperty startStorage;
    private IntegerProperty storage;

    // Not a database stored value
    private StringProperty classificationName = new SimpleStringProperty();

    public Commodity(String commodityID,
                     int classificationID,
                     String barcode,
                     String name,
                     String specification,
                     String unit,
                     double price,
                     Integer deliverySpecification,
                     Integer shelfLife,
                     Integer startStorage,
                     Integer storage) {
        this.commodityID = new SimpleStringProperty(commodityID);
        this.classificationID = new SimpleIntegerProperty(classificationID);
        this.barcode = new SimpleStringProperty(barcode);
        this.name = new SimpleStringProperty(name);
        this.specification = new SimpleStringProperty(specification);
        this.unit = new SimpleStringProperty(unit);
        this.deliverySpecification = new SimpleIntegerProperty(deliverySpecification);
        this.shelfLife = new SimpleIntegerProperty(shelfLife);
        this.startStorage = new SimpleIntegerProperty(startStorage);
        this.storage = new SimpleIntegerProperty(storage);

        BigDecimal decimal = new BigDecimal(price);
        DecimalFormat formater = new DecimalFormat("#0.00");
        decimal.setScale(2, BigDecimal.ROUND_HALF_EVEN);

        this.price = new SimpleStringProperty(formater.format(decimal.doubleValue()));
    }

    public Commodity(String commodityID,
                     int classificationID,
                     String barcode,
                     String name,
                     String specification,
                     String unit,
                     double price,
                     Integer deliverySpecification,
                     Integer shelfLife,
                     Integer startStorage) {
        this(commodityID, classificationID, barcode, name, specification, unit, price, deliverySpecification, shelfLife, startStorage, 0);
    }

    public Commodity(StringProperty commodityID,
                     IntegerProperty classificationID,
                     StringProperty barcode,
                     StringProperty name,
                     StringProperty specification,
                     StringProperty unit,
                     StringProperty price,
                     IntegerProperty deliverySpecification,
                     IntegerProperty shelfLife,
                     IntegerProperty startStorage,
                     IntegerProperty storage) {
        this.commodityID = commodityID;
        this.classificationID = classificationID;
        this.barcode = barcode;
        this.name = name;
        this.specification = specification;
        this.unit = unit;
        this.price = price;
        this.deliverySpecification = deliverySpecification;
        this.shelfLife = shelfLife;
        this.startStorage = startStorage;
        this.storage = storage;
    }

    public Commodity(StringProperty commodityID,
                     IntegerProperty classificationID,
                     StringProperty barcode,
                     StringProperty name,
                     StringProperty specification,
                     StringProperty unit,
                     StringProperty price,
                     IntegerProperty deliverySpecification,
                     IntegerProperty shelfLife,
                     IntegerProperty startStorage) {
        this(commodityID, classificationID, barcode, name, specification, unit, price, deliverySpecification, shelfLife, startStorage, new SimpleIntegerProperty(0));
    }


    // MARK: Setter & Getter

    public String getCommodityID() {
        return commodityID.get();
    }

    public StringProperty commodityIDProperty() {
        return commodityID;
    }

    public void setCommodityID(String commodityID) {
        this.commodityID.set(commodityID);
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

    public String getPrice() {
        return price.get();
    }

    public StringProperty priceProperty() {
        return price;
    }

    public void setPrice(String price) {
        this.price.set(price);
    }

    public int getDeliverySpecification() {
        return deliverySpecification.get();
    }

    public IntegerProperty deliverySpecificationProperty() {
        return deliverySpecification;
    }

    public void setDeliverySpecification(int deliverySpecification) {
        this.deliverySpecification.set(deliverySpecification);
    }

    public int getShelfLife() {
        return shelfLife.get();
    }

    public IntegerProperty shelfLifeProperty() {
        return shelfLife;
    }

    public void setShelfLife(int shelfLife) {
        this.shelfLife.set(shelfLife);
    }

    public int getStartStorage() {
        return startStorage.get();
    }

    public IntegerProperty startStorageProperty() {
        return startStorage;
    }

    public void setStartStorage(int startStorage) {
        this.startStorage.set(startStorage);
    }

    public String getClassificationName() {
        return classificationName.get();
    }

    public StringProperty classificationNameProperty() {
        return classificationName;
    }

    public void setClassificationName(String classificationName) {
        this.classificationName.set(classificationName);
    }

    public int getStorage() {
        return storage.get();
    }

    public IntegerProperty storageProperty() {
        return storage;
    }

    public void setStorage(int storage) {
        this.storage.set(storage);
    }
}
