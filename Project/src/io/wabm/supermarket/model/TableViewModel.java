package io.wabm.supermarket.model;

import io.wabm.supermarket.misc.util.ConsoleLog;
import javafx.collections.FXCollections;
import javafx.scene.control.TableView;

/**
 * Created by MainasuK on 2016-11-20.
 */
public class TableViewModel<T> extends Model<T> {

    private TableView<T> tableView;

    public TableViewModel(TableView<T> tableView) {
        this.tableView = tableView;

        list = FXCollections.observableArrayList();
        tableView.setItems(list);
    }

    @Override
    public void add(T item) {
        list.add(item);
    }

}
