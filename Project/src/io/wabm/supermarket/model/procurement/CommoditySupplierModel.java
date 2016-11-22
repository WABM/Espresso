package io.wabm.supermarket.model.procurement;

import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.model.Model;
import javafx.collections.FXCollections;
import javafx.scene.control.TableView;

/**
 * Created by liu on 2016-11-21 .
 */
public class  CommoditySupplierModel<T> extends Model<T> {

            private TableView<T> tableView;

            public CommoditySupplierModel(TableView tableView) {
                ConsoleLog.print("CommoditySupplierModel init");

                this.tableView = tableView;

                list = FXCollections.observableArrayList();
                tableView.setItems(list);
            }

            // MARK: DataSource protocol
            public void add(T item) {
                list.add(item);
            }
}


