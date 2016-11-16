package io.wabm.supermarket.model.warehouse;

import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.model.Model;
import javafx.collections.FXCollections;
import javafx.scene.control.TableView;

/**
 * Created by MainasuK on 2016-11-14.
 */
public class CommodityClassificationInformationModel<T> extends Model<T> {

    private TableView<T> tableView;

    public CommodityClassificationInformationModel(TableView tableView) {
        ConsoleLog.print("CommodityClassificationInformationModel init");

        this.tableView = tableView;

        list = FXCollections.observableArrayList();
        tableView.setItems(list);
    }

    // MARK: DataSource protocol
    public void add(T item) {
        list.add(item);
    }

}