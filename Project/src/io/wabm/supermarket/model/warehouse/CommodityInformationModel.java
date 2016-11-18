package io.wabm.supermarket.model.warehouse;

import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.model.Model;
import javafx.collections.FXCollections;
import javafx.scene.control.TableView;

/**
 * Created by MainasuK on 2016-11-17.
 */
public class CommodityInformationModel<T> extends Model<T> {

    private TableView<T> tableView;

    public CommodityInformationModel(TableView tableView) {
        ConsoleLog.print("CommodityInformationModel init");

        this.tableView = tableView;

        list = FXCollections.observableArrayList();
        tableView.setItems(list);
    }

    @Override
    public void add(T item) {
        list.add(item);
    }
}
