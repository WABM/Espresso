package io.wabm.supermarket.model;

import io.wabm.supermarket.application.Main;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.util.Assert;

/**
 * Created by MainasuK on 2016-11-28.
 */
public class ComboBoxModel<T> extends Model<T> {

    protected JdbcOperations jdbcOperations;

    public ComboBoxModel(ComboBox<T> comboBox) {
        list = FXCollections.observableArrayList();
        Assert.notNull(list);

        comboBox.setItems(list);

        jdbcOperations = Main.getJdbcOperations();
    }

    @Override
    public void add(T item) {
        list.add(item);
    }

}
