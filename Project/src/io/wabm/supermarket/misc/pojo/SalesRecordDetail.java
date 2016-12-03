package io.wabm.supermarket.misc.pojo;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

/**
 * Created by MainasuK on 2016-12-2.
 */
public class SalesRecordDetail {

    private Commodity commodity;

    private IntegerProperty salesRecordDetailID;
    private IntegerProperty salesRecordID;
    private IntegerProperty quantity;
    private ObjectProperty<BigDecimal> salesPrice;

    public SalesRecordDetail(Commodity commodity) {
        this.commodity = commodity;

        this.salesPrice = new SimpleObjectProperty<>(commodity.getPrice());
        this.quantity = new SimpleIntegerProperty(1);
    }

    public SalesRecordDetail(Commodity commodity, int salesRecordDetailID, int salesRecordID, int quantity) {
        this(commodity);

        this.salesRecordDetailID = new SimpleIntegerProperty(salesRecordDetailID);
        this.salesRecordID = new SimpleIntegerProperty(salesRecordID);
        this.quantity = new SimpleIntegerProperty(quantity);
    }

    public Commodity getCommodity() {
        return commodity;
    }

    public void setCommodity(Commodity commodity) {
        this.commodity = commodity;
    }

    public int getSalesRecordDetailID() {
        return salesRecordDetailID.get();
    }

    public IntegerProperty salesRecordDetailIDProperty() {
        return salesRecordDetailID;
    }

    public void setSalesRecordDetailID(int salesRecordDetailID) {
        this.salesRecordDetailID.set(salesRecordDetailID);
    }

    public int getSalesRecordID() {
        return salesRecordID.get();
    }

    public IntegerProperty salesRecordIDProperty() {
        return salesRecordID;
    }

    public void setSalesRecordID(int salesRecordID) {
        this.salesRecordID.set(salesRecordID);
    }

    public int getQuantity() {
        return quantity.get();
    }

    public IntegerProperty quantityProperty() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }

    public BigDecimal getSalesPrice() {
        return salesPrice.get();
    }

    public ObjectProperty<BigDecimal> salesPriceProperty() {
        return salesPrice;
    }

    public void setSalesPrice(BigDecimal salesPrice) {
        this.salesPrice.set(salesPrice);
    }

    public String getTotalPrice() {
        DecimalFormat formater = new DecimalFormat("#0.00");
        BigDecimal price = getSalesPrice();
        BigDecimal total = new BigDecimal(getQuantity()).multiply(price);

        return formater.format(total.doubleValue());
    }

    public void addQuantity(int n) {
        quantity.set(quantity.get() + n);
    }
}
