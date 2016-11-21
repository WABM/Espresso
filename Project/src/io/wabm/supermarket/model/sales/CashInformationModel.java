package io.wabm.supermarket.model.sales;

import io.wabm.supermarket.misc.pojo.CashInformation;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.model.Model;
import javafx.collections.FXCollections;
import javafx.scene.control.TableView;

/**
 * Created by Administrator on 2016/11/20 0020.
 */
public class CashInformationModel<T> extends Model<T> {

    private TableView tableView;
    public CashInformationModel(TableView tableView){
        ConsoleLog.print("CashInformationModel init");
        this.tableView = tableView;

        list = FXCollections.observableArrayList();
        tableView.setItems(list);
    }
    public void add(T item){
        list.add(item);
    }
}
