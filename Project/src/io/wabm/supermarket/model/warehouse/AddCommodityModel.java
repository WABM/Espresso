package io.wabm.supermarket.model.warehouse;

import io.wabm.supermarket.misc.pojo.Classification;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.model.ComboBoxModel;
import javafx.scene.control.ComboBox;
import javafx.util.Callback;
import org.springframework.dao.DataAccessException;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Created by MainasuK on 2016-11-28.
 */
public class AddCommodityModel<T> extends ComboBoxModel<T> {

    private ComboBox<T> comboBox;
    private final String kSelectSQL = "SELECT cl.classification_id, cl.name FROM wabm.classification cl;";

    public AddCommodityModel(ComboBox<T> comboBox) {
        super(comboBox);

        this.comboBox = comboBox;

        ConsoleLog.print("AddCommodityModel init");
    }


    public void fetchClassification(Callback<Boolean, Void> callback) {
        ConsoleLog.print("fetching…");

        try {
            List<Classification> templist = jdbcOperations.query(
                    kSelectSQL,
                    (resultSet, i) -> {
                        Classification classification;
                        classification = new Classification(
                            resultSet.getInt("classification_id"),
                            resultSet.getString("name"),
                            0.0,
                            0.0
                        );

                        return classification;
                    }
            );

            list.clear();
            list.addAll((T[]) templist.toArray());

            ConsoleLog.print("… Get " + list.size() + " item(s)");

            callback.call(true);

        } catch (DataAccessException e) {
            callback.call(false);
            e.printStackTrace();
        }

    }

}
