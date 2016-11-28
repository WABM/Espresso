package io.wabm.supermarket.model.sales;

import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.model.Model;
import javafx.collections.FXCollections;

import javafx.scene.control.TableView;

/**
 * Created by Administrator on 2016/11/19 0019.
 */
public class TransationRecordModel<T> extends Model<T> {
    private TableView tableView;

    public TransationRecordModel(TableView tableView){
        ConsoleLog.print("TransationRecordModel init");
        this.tableView = tableView;

        list = FXCollections.observableArrayList();
        tableView.setItems(list);
    }
    public void add(T item){
        list.add(item);
    }
}
