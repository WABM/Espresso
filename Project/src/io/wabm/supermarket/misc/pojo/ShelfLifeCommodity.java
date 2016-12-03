package io.wabm.supermarket.misc.pojo;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.time.LocalDate;

/**
 * Created by MainasuK on 2016-11-20.
 */
public class ShelfLifeCommodity {

    private Commodity commodity;

    private ObjectProperty<LocalDate> productionDate;


    public ShelfLifeCommodity(Commodity commodity, LocalDate productionDate) {
        this(commodity, new SimpleObjectProperty<>(productionDate));
    }

    public ShelfLifeCommodity(Commodity commodity, ObjectProperty<LocalDate> productionDate) {
        this.commodity = commodity;
        this.productionDate = productionDate;
    }

    public Commodity getCommodity() {
        return commodity;
    }

    public void setCommodity(Commodity commodity) {
        this.commodity = commodity;
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
        return productionDate.get().plusDays(getCommodity().getShelfLife());
    }
}
