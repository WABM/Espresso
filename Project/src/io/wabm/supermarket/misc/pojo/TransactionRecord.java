package io.wabm.supermarket.misc.pojo;

import javafx.beans.property.*;

import java.time.LocalDate;

/**
 * Created by Administrator on 2016/11/28 0028.
 */
public class TransactionRecord {
    private StringProperty recordID;
    private StringProperty emploee_ID;
    private DoubleProperty totalprice;
    //private ObjectProperty<LocalDate> date;
    private StringProperty date;

    private BooleanProperty choose;

    public TransactionRecord(String recordID, String emploee_ID, Double totalprice,String date) {
        this.recordID = new SimpleStringProperty(recordID);
        this.emploee_ID = new SimpleStringProperty(emploee_ID);
        this.totalprice = new SimpleDoubleProperty(totalprice);
        this.date = new SimpleStringProperty(date);
    }

    public String getRecordID() {
        return recordID.get();
    }

    public StringProperty recordIDProperty() {
        return recordID;
    }

    public void setRecordID(String recordID) {
        this.recordID.set(recordID);
    }

    public String getEmploee_ID() {
        return emploee_ID.get();
    }

    public StringProperty emploee_IDProperty() {
        return emploee_ID;
    }

    public void setEmploee_ID(String emploee_ID) {
        this.emploee_ID.set(emploee_ID);
    }

    public double getTotalprice() {
        return totalprice.get();
    }

    public DoubleProperty totalpriceProperty() {
        return totalprice;
    }

    public void setTotalprice(double totalprice) {
        this.totalprice.set(totalprice);
    }

    public String getDate() {
        return date.get();
    }

    public StringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public boolean isChoose() {
        return choose.get();
    }

    public BooleanProperty chooseProperty() {
        return choose;
    }

    public void setChoose(boolean choose) {
        this.choose.set(choose);
    }
}
