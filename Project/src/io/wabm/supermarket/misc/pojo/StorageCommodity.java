package io.wabm.supermarket.misc.pojo;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by MainasuK on 2016-11-20.
 */
public class StorageCommodity {

    private StringProperty storageCommodityID;
    private IntegerProperty classificationID;
    private StringProperty barcode;
    private StringProperty name;
    private StringProperty specification;
    private StringProperty unit;
    private StringProperty price;
    private IntegerProperty deliverySpecification;

    private IntegerProperty storage;

    public StorageCommodity(String storageCommodityID, int classificationID, String barcode, String name, String specification, String unit, String price, int deliverySpecification, int storage) {
        this(new SimpleStringProperty(storageCommodityID), new SimpleIntegerProperty(classificationID), new SimpleStringProperty(barcode), new SimpleStringProperty(name), new SimpleStringProperty( specification), new SimpleStringProperty(unit), new SimpleStringProperty(price), new SimpleIntegerProperty(deliverySpecification), new SimpleIntegerProperty(storage));
    }

    public StorageCommodity(StringProperty storageCommodityID, IntegerProperty classificationID, StringProperty barcode, StringProperty name, StringProperty specification, StringProperty unit, StringProperty price, IntegerProperty deliverySpecification, IntegerProperty storage) {
        this.storageCommodityID = storageCommodityID;
        this.classificationID = classificationID;
        this.barcode = barcode;
        this.name = name;
        this.specification = specification;
        this.unit = unit;
        this.price = price;
        this.deliverySpecification = deliverySpecification;
        this.storage = storage;
    }

    // MARK: Getter and Setter
    
    public String getStorageCommodityID() {
        return storageCommodityID.get();
    }

    public StringProperty storageCommodityIDProperty() {
        return storageCommodityID;
    }

    public void setStorageCommodityID(String storageCommodityID) {
        this.storageCommodityID.set(storageCommodityID);
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
