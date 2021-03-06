package io.wabm.supermarket.misc.pojo;

import javafx.beans.property.*;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

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
    private ObjectProperty<BigDecimal> price;
    private IntegerProperty deliverySpecification;
    private BooleanProperty valid;

    private IntegerProperty shelfLife;
    private IntegerProperty storage;

    // Not a database stored value
    private StringProperty classificationName = new SimpleStringProperty();

    public Commodity(String commodityID,
                     int classificationID,
                     String barcode,
                     String name,
                     String specification,
                     String unit,
                     BigDecimal price,
                     Integer deliverySpecification,
                     Integer shelfLife,
                     Integer storage,
                     boolean valid) {
        this.commodityID = new SimpleStringProperty(commodityID);
        this.classificationID = new SimpleIntegerProperty(classificationID);
        this.barcode = new SimpleStringProperty(barcode);
        this.name = new SimpleStringProperty(name);
        this.specification = new SimpleStringProperty(specification);
        this.unit = new SimpleStringProperty(unit);
        this.price = new SimpleObjectProperty<>(price);
        this.deliverySpecification = new SimpleIntegerProperty(deliverySpecification);
        this.shelfLife = new SimpleIntegerProperty(shelfLife);
        this.storage = new SimpleIntegerProperty(storage);
        this.valid = new SimpleBooleanProperty(valid);

        price.setScale(2);

//        BigDecimal decimal = new BigDecimal(price);
//        DecimalFormat formater = new DecimalFormat("#0.00");
//        decimal.setScale(2, BigDecimal.ROUND_HALF_EVEN);

//        this.price = new SimpleStringProperty(formater.format(decimal.doubleValue()));
    }

    // storage = 0 init method
    public Commodity(String commodityID,
                     int classificationID,
                     String barcode,
                     String name,
                     String specification,
                     String unit,
                     BigDecimal price,
                     Integer deliverySpecification,
                     Integer shelfLife,
                     boolean valid) {
        this(commodityID, classificationID, barcode, name, specification, unit, price, deliverySpecification, shelfLife, 0, valid);
    }

    public Commodity(StringProperty commodityID,
                     IntegerProperty classificationID,
                     StringProperty barcode,
                     StringProperty name,
                     StringProperty specification,
                     StringProperty unit,
                     ObjectProperty<BigDecimal> price,
                     IntegerProperty deliverySpecification,
                     IntegerProperty shelfLife,
                     IntegerProperty storage,
                     BooleanProperty valid) {
        this.commodityID = commodityID;
        this.classificationID = classificationID;
        this.barcode = barcode;
        this.name = name;
        this.specification = specification;
        this.unit = unit;
        this.price = price;
        this.deliverySpecification = deliverySpecification;
        this.shelfLife = shelfLife;
        this.storage = storage;
        this.valid = valid;
    }

    public Commodity(ResultSet resultSet) throws SQLException {
        this(
                resultSet.getString("commodity_id"),
                resultSet.getInt("classification_id"),
                resultSet.getString("bar_code"),
                resultSet.getString("name"),
                resultSet.getString("specification"),
                resultSet.getString("unit"),
                resultSet.getBigDecimal("price_db"),
                resultSet.getInt("delivery_specification"),
                resultSet.getInt("shelf_life"),
                resultSet.getInt("storage"),
                resultSet.getBoolean("valid")
        );
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

    public BigDecimal getPrice() {
        return price.get();
    }

    public ObjectProperty<BigDecimal> priceProperty() {
        return price;
    }

    public void setPrice(BigDecimal price) {
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

    public boolean isValid() {
        return valid.get();
    }

    public BooleanProperty validProperty() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid.set(valid);
    }
}
