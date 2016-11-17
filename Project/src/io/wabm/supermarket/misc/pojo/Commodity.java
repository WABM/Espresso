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

    private ObjectProperty<Timestamp> createTimestamp;

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
                     Timestamp createTimestamp) {
        this.commodityID = new SimpleStringProperty(commodityID);
        this.classificationID = new SimpleIntegerProperty(classificationID);
        this.barcode = new SimpleStringProperty(barcode);
        this.name = new SimpleStringProperty(name);
        this.specification = new SimpleStringProperty(specification);
        this.unit = new SimpleStringProperty(unit);
        this.deliverySpecification = new SimpleIntegerProperty(deliverySpecification);
        this.shelfLife = new SimpleIntegerProperty(shelfLife);
        this.startStorage = new SimpleIntegerProperty(startStorage);
        this.createTimestamp = new SimpleObjectProperty<>(createTimestamp);

        BigDecimal decimal = new BigDecimal(price);
        DecimalFormat formater = new DecimalFormat("#.00");
        decimal.setScale(2, BigDecimal.ROUND_HALF_EVEN);

        this.price = new SimpleStringProperty(formater.format(decimal.doubleValue()));
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
                     ObjectProperty<Timestamp> createTimestamp) {
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
        this.createTimestamp = createTimestamp;
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

    public Timestamp getCreateTimestamp() {
        return createTimestamp.get();
    }

    public ObjectProperty<Timestamp> createTimestampProperty() {
        return createTimestamp;
    }

    public void setCreateTimestamp(Timestamp createTimestamp) {
        this.createTimestamp.set(createTimestamp);
    }
}
