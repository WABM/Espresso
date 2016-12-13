package io.wabm.supermarket.misc.pojo;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.*;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * Created by MainasuK on 2016-12-12.
 */
public class CMKInventoryDetail extends Commodity {

    private IntegerProperty actualQuantity;
    private IntegerProperty result;


    public CMKInventoryDetail(ResultSet resultSet) throws SQLException {
        super(resultSet);

        this.actualQuantity = new SimpleIntegerProperty(getStorage());
        this.result = new SimpleIntegerProperty(0);

        result.bind(
                Bindings.createIntegerBinding(
                        () -> getActualQuantity() - getStorage(),  // The result
                        actualQuantityProperty(),
                        storageProperty()
                )   // bind actualProperty - storageProperty
        );
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

    public int getResult() {
        return result.get();
    }

    public IntegerProperty resultProperty() {
        return result;
    }

    public void setResult(int result) {
        this.result.set(result);
    }
}
