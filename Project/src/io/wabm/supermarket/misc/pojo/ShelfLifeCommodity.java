package io.wabm.supermarket.misc.pojo;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * Created by MainasuK on 2016-11-20.
 */
public class ShelfLifeCommodity extends Commodity {

    private ObjectProperty<LocalDate> productionDate;
    private IntegerProperty orderDetailID;

    public ShelfLifeCommodity(LocalDate productionDate, int orderDetailID, ResultSet resultSet) throws SQLException {
        super(resultSet);

        this.productionDate = new SimpleObjectProperty<>(productionDate);
        this.orderDetailID = new SimpleIntegerProperty(orderDetailID);
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

    public LocalDate getExpirationDate() {
        return productionDate.get().plusDays(getShelfLife());
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
}
