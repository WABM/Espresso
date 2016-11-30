package io.wabm.supermarket.misc.pojo;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by MainasuK on 2016-11-20.
 */
public class StorageCommodity extends Commodity {

    private IntegerProperty storageCommodityID;
    private IntegerProperty storage;


    public StorageCommodity(int storageCommodityID,
                     int storage,
                     String commodityID,
                     int classificationID,
                     String barcode,
                     String name,
                     String specification,
                     String unit,
                     double price,
                     Integer deliverySpecification,
                     Integer shelfLife,
                     Integer startStorage) {
        super(commodityID, classificationID, barcode, name, specification, unit, price, deliverySpecification, shelfLife, startStorage);

        this.storageCommodityID = new SimpleIntegerProperty(storageCommodityID);
        this.storage = new SimpleIntegerProperty(storage);
    }

    // MARK: Getter and Setter

    public int getStorageCommodityID() {
        return storageCommodityID.get();
    }

    public IntegerProperty storageCommodityIDProperty() {
        return storageCommodityID;
    }

    public void setStorageCommodityID(int storageCommodityID) {
        this.storageCommodityID.set(storageCommodityID);
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
