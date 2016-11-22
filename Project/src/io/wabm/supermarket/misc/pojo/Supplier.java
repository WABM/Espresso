package io.wabm.supermarket.misc.pojo;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by liu on 2016-11-21 .
 */
public class Supplier {
    private StringProperty supplierID;
    private StringProperty suppliername;
    private StringProperty linkman;
    private StringProperty phone;
    private StringProperty address;

    private BooleanProperty choosed;
        public Supplier(String supplierID, String suppliername, String linkman,String phone,String address)
    {
        this.supplierID = new SimpleStringProperty(supplierID);
        this.suppliername = new SimpleStringProperty(suppliername);
        this.linkman = new SimpleStringProperty(linkman);
        this.phone = new SimpleStringProperty(phone);
        this.address = new SimpleStringProperty(address);
    }
    public String getSupplierID() { return  supplierID.get(); }

    public StringProperty supplierIDProperty() {return supplierID;}

    public void setSupplierID(String supplierID) {
        this.supplierID.set(supplierID);
    }


    public String getSupplierName() {return suppliername.get();}

    public StringProperty suppliernameProperty() {return suppliername;}

    public void setSupplierName(String suppliername) {this.suppliername.set(suppliername);}


    public  String getLinkman() {return linkman.get();}

    public StringProperty linkmanProperty() {return linkman;}

    public void setLinkman(String linkman) {this.linkman.set(linkman);}


    public String getPhone() {return phone.get();}

    public StringProperty phoneProperty() {return phone;}

    public void setPhone(String phone) {this.phone.set(phone);}


    public String getAddress() {return address.get();}

    public StringProperty addressProperty() {return address;}

    public void setAddress(String address) {this.address.set(address);}

    public boolean isChoosed() {
        return choosed.get();
    }

    public BooleanProperty choosedProperty() {
        return choosed;
    }

    public void setChoosed(boolean choosed) {
        this.choosed.set(choosed);
    }


}
