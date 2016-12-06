package io.wabm.supermarket.misc.pojo;

import javafx.beans.property.*;

import javax.swing.*;

/**
 * Created by liu on 2016-11-21 .
 */
public class Supplier {
    private IntegerProperty supplierID;
    private StringProperty supplierName;
    private StringProperty linkman;
    private StringProperty phone;
    private StringProperty address;

        public Supplier(int supplierID, String supplierName, String linkman,String phone,String address)
    {
        this.supplierID = new SimpleIntegerProperty(supplierID);
        this.supplierName = new SimpleStringProperty(supplierName);
        this.linkman = new SimpleStringProperty(linkman);
        this.phone = new SimpleStringProperty(phone);
        this.address = new SimpleStringProperty(address);
    }

    public int getSupplierID() {
        return supplierID.get();
    }

    public IntegerProperty supplierIDProperty() {
        return supplierID;
    }

    public void setSupplierID(int supplierID) {
        this.supplierID.set(supplierID);
    }



    public String getSupplierName() {
        return supplierName.get();
    }

    public StringProperty supplierNameProperty() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName.set(supplierName);
    }


    public String getLinkman() {
        return linkman.get();
    }

    public StringProperty linkmanProperty() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman.set(linkman);
    }


    public String getPhone() {
        return phone.get();
    }

    public StringProperty phoneProperty() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }


    public String getAddress() {
        return address.get();
    }

    public StringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
    }


    public String getName() {return supplierName.get();
    }


}
