package io.wabm.supermarket.misc.pojo;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by MainasuK on 2016-12-11.
 */
public class PurchaseCommodity extends Commodity {
    public BooleanProperty isChoose;
    public IntegerProperty minStorage;
    public IntegerProperty purchaseNum;

    public PurchaseCommodity(int minStorage, ResultSet resultSet) throws SQLException {
        super(resultSet);

        this.isChoose = new SimpleBooleanProperty(true);   // default chosen
        this.minStorage = new SimpleIntegerProperty(minStorage);
        this.purchaseNum = new SimpleIntegerProperty(minStorage);
    }

    public boolean isIsChoose() {
        return isChoose.get();
    }

    public BooleanProperty isChooseProperty() {
        return isChoose;
    }

    public void setIsChoose(boolean isChoose) {
        this.isChoose.set(isChoose);
    }

    public IntegerProperty minStorageProperty() {
        return minStorage;
    }

    public void setMinStorage(int minStorage) {
        this.minStorage.set(minStorage);
    }

    public int getPurchaseNum() {
        return purchaseNum.get();
    }

    public IntegerProperty purchaseNumProperty() {
        return purchaseNum;
    }

    public void setPurchaseNum(int purchaseNum) {
        this.purchaseNum.set(purchaseNum);
    }
}
