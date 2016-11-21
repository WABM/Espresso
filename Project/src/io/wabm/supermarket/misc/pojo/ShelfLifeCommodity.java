package io.wabm.supermarket.misc.pojo;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import java.sql.Timestamp;
import java.util.Calendar;

/**
 * Created by MainasuK on 2016-11-20.
 */
public class ShelfLifeCommodity extends Commodity {

    private ObjectProperty<Calendar> productionData;
    private ObjectProperty<Calendar> expirationDate;

    public ShelfLifeCommodity(String commodityID, int classificationID, String barcode, String name, String specification, String unit, double price, Integer deliverySpecification, Integer shelfLife, Integer startStorage, Timestamp createTimestamp, Calendar productionData, Calendar expirationDate) {
        super(commodityID, classificationID, barcode, name, specification, unit, price, deliverySpecification, shelfLife, startStorage, createTimestamp);

        this.productionData = new SimpleObjectProperty<>(productionData);
        this.expirationDate = new SimpleObjectProperty<>(expirationDate);
    }

    public ShelfLifeCommodity(StringProperty commodityID, IntegerProperty classificationID, StringProperty barcode, StringProperty name, StringProperty specification, StringProperty unit, StringProperty price, IntegerProperty deliverySpecification, IntegerProperty shelfLife, IntegerProperty startStorage, ObjectProperty<Timestamp> createTimestamp, ObjectProperty<Calendar> productionData, ObjectProperty<Calendar> expirationDate) {
        super(commodityID, classificationID, barcode, name, specification, unit, price, deliverySpecification, shelfLife, startStorage, createTimestamp);

        this.productionData = productionData;
        this.expirationDate = expirationDate;
    }

    public Calendar getProductionData() {
        return productionData.get();
    }

    // MARK: Getter and Setter

    public ObjectProperty<Calendar> productionDataProperty() {
        return productionData;
    }

    public void setProductionData(Calendar productionData) {
        this.productionData.set(productionData);
    }

    public Calendar getExpirationDate() {
        return expirationDate.get();
    }

    public ObjectProperty<Calendar> expirationDateProperty() {
        return expirationDate;
    }

    public void setExpirationDate(Calendar expirationDate) {
        this.expirationDate.set(expirationDate);
    }
}
