package io.wabm.supermarket.misc.pojo;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * Created by MainasuK on 2016-12-12.
 */
public class CMKOrderDetail extends Commodity {

    private IntegerProperty orderID;
    private IntegerProperty orderDetailID;
    private IntegerProperty quantity;
    private ObjectProperty<BigDecimal> purchasePrice;

    private IntegerProperty actualQuantity;
    private ObjectProperty<LocalDate> productionDate;


    public CMKOrderDetail(int orderID, int orderDetailID, int quantity, BigDecimal purchasePrice, LocalDate productionDate, ResultSet resultSet) throws SQLException {
        super(resultSet);

        purchasePrice.setScale(2);

        this.orderID = new SimpleIntegerProperty(orderID);
        this.orderDetailID = new SimpleIntegerProperty(orderDetailID);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.purchasePrice = new SimpleObjectProperty<>(purchasePrice);

        this.actualQuantity = new SimpleIntegerProperty(quantity);
        this.productionDate = new SimpleObjectProperty<>(productionDate);
    }

    public int getOrderID() {
        return orderID.get();
    }

    public IntegerProperty orderIDProperty() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID.set(orderID);
    }

    public int getOrderDetailID() {
        return orderDetailID.get();
    }

    public IntegerProperty orderDetailIDProperty() {
        return orderDetailID;
    }

    public void setOrderDetailID(int orderDetailID) {
        this.orderDetailID.set(orderDetailID);
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

    public BigDecimal getPurchasePrice() {
        return purchasePrice.get();
    }

    public ObjectProperty<BigDecimal> purchasePriceProperty() {
        return purchasePrice;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice.set(purchasePrice);
    }

    public LocalDate getProductionDate() {
        return productionDate.get();
    }

    public ObjectProperty<LocalDate> productionDateProperty() {
        return productionDate;
    }

    public void setProductionDate(LocalDate productionDate) {
        this.productionDate.set(productionDate);
    }

    public int getActualQuantity() {
        return actualQuantity.get();
    }

    public IntegerProperty actualQuantityProperty() {
        return actualQuantity;
    }

    public void setActualQuantity(int actualQuantity) {
        this.actualQuantity.set(actualQuantity);
    }

}
