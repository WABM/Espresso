package io.wabm.supermarket.model.management;

import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.model.Model;
import javafx.collections.FXCollections;
import javafx.scene.control.TableView;

/**
 * Created by liu on 2016-11-19 .
 */
    public class  EmployeeInformationModel<T> extends Model<T> {

        private TableView<T> tableView;

        public EmployeeInformationModel(TableView tableView) {
            ConsoleLog.print("EmployeeInformationModel init");

            this.tableView = tableView;

            list = FXCollections.observableArrayList();
            tableView.setItems(list);
        }

        // MARK: DataSource protocol
        public void add(T item) {
            list.add(item);
        }
}
