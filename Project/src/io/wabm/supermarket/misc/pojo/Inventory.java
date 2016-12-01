package io.wabm.supermarket.misc.pojo;

import javafx.beans.property.*;

import java.sql.Timestamp;

/**
 * Created by MainasuK on 2016-12-1.
 */
public class Inventory {

    private IntegerProperty inventoryID;
    private IntegerProperty classificationID;
    private IntegerProperty employeeID;
    private SimpleObjectProperty<Timestamp> createTimestamp;
    private SimpleObjectProperty<Timestamp> fillTimestamp;      // mark inventory finish time

    // Not database stored property
    private StringProperty classificationName = new SimpleStringProperty();
    private IntegerProperty hasNum = new SimpleIntegerProperty();

    public Inventory(int inventoryID, int classificationID, int employeeID, Timestamp createTimestamp, Timestamp fillTimestamp) {
        this.inventoryID = new SimpleIntegerProperty(inventoryID);
        this.classificationID = new SimpleIntegerProperty(classificationID);
        this.employeeID = new SimpleIntegerProperty(employeeID);
        this.createTimestamp = new SimpleObjectProperty<>(createTimestamp);
        this.fillTimestamp = new SimpleObjectProperty<>(fillTimestamp);
    }

    public int getInventoryID() {
        return inventoryID.get();
    }

    public IntegerProperty inventoryIDProperty() {
        return inventoryID;
    }

    public void setInventoryID(int inventoryID) {
        this.inventoryID.set(inventoryID);
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

    public int getEmployeeID() {
        return employeeID.get();
    }

    public IntegerProperty employeeIDProperty() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID.set(employeeID);
    }

    public Timestamp getCreateTimestamp() {
        return createTimestamp.get();
    }

    public SimpleObjectProperty<Timestamp> createTimestampProperty() {
        return createTimestamp;
    }

    public void setCreateTimestamp(Timestamp createTimestamp) {
        this.createTimestamp.set(createTimestamp);
    }

    public Timestamp getFillTimestamp() {
        return fillTimestamp.get();
    }

    public SimpleObjectProperty<Timestamp> fillTimestampProperty() {
        return fillTimestamp;
    }

    public void setFillTimestamp(Timestamp fillTimestamp) {
        this.fillTimestamp.set(fillTimestamp);
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

    public int getHasNum() {
        return hasNum.get();
    }

    public IntegerProperty hasNumProperty() {
        return hasNum;
    }

    public void setHasNum(int hasNum) {
        this.hasNum.set(hasNum);
    }
}
