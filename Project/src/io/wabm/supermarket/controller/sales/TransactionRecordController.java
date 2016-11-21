package io.wabm.supermarket.controller.sales;

import io.wabm.supermarket.controller.SceneController;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.model.sales.TransationRecordModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import io.wabm.supermarket.misc.pojo.TransactionRecord;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Created by Administrator on 2016/11/19 0019.
 */
public class TransactionRecordController extends SceneController{

    private TransationRecordModel<TransactionRecord> model;

    @FXML private TableView<TransactionRecord> tableView;
    @FXML private TableColumn<TransactionRecord,String> recordID;
    @FXML private TableColumn<TransactionRecord,String> commodityID;
    @FXML private TableColumn<TransactionRecord,String> barcode;
    @FXML private TableColumn<TransactionRecord,String> name;
    @FXML private TableColumn<TransactionRecord,Integer> classificationID;
    @FXML private TableColumn<TransactionRecord,String> specification;
    @FXML private TableColumn<TransactionRecord,String> unit;
    @FXML private TableColumn<TransactionRecord,Integer> quantity;
    @FXML private TableColumn<TransactionRecord,String> price;
    @FXML private TableColumn<TransactionRecord,String> totalPrice;

    @FXML private Button queryButton;
    @FXML private DatePicker datePicker;

    @FXML public void initialize(){
        ConsoleLog.print("TransactionRecordController init");
        setupControl();
        setupModel();
        setupTableView();
        setupTableViewColumn();

        for (int i= 0;i<20;i+=3) {
            model.add(new TransactionRecord(0+i+"","0","0000","可口可乐",0,"500ml","瓶",50,"2.00","100.00"));
            model.add(new TransactionRecord(1+i+"","1","0001","百事可乐",0,"500ml","瓶",30,"2.00","60.00"));
            model.add(new TransactionRecord(2+i+"","2","0002","舒化奶",0,"250ml","瓶",20,"4.50","90.00"));
        }
    }
    @FXML public void queryButtonPressed(){
        ConsoleLog.print("queryButton pressed");
    }

    private void setupControl() {}
    private void setupModel() {
        model = new TransationRecordModel<>(tableView);
    }
    private void setupTableView(){
        tableView.setEditable(true);
    }
    private void setupTableViewColumn() {
        recordID.setCellValueFactory(new PropertyValueFactory<TransactionRecord, String>("recordID"));
        commodityID.setCellValueFactory(new PropertyValueFactory<TransactionRecord, String>("commodityID"));
        barcode.setCellValueFactory(new PropertyValueFactory<TransactionRecord, String>("barcode"));
        name.setCellValueFactory(new PropertyValueFactory<TransactionRecord, String>("name"));
        classificationID.setCellValueFactory(new PropertyValueFactory<TransactionRecord, Integer>("classificationID"));
        specification.setCellValueFactory(new PropertyValueFactory<TransactionRecord, String>("specification"));
        unit.setCellValueFactory(new PropertyValueFactory<TransactionRecord, String>("unit"));
        quantity.setCellValueFactory(new PropertyValueFactory<TransactionRecord, Integer>("quantity"));
        price.setCellValueFactory(new PropertyValueFactory<TransactionRecord, String>("price"));
        totalPrice.setCellValueFactory(new PropertyValueFactory<TransactionRecord, String>("totalPrice"));
    }
}
