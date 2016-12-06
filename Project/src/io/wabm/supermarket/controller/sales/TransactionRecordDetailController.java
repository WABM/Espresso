package io.wabm.supermarket.controller.sales;

import io.wabm.supermarket.controller.SceneController;
import io.wabm.supermarket.misc.pojo.TransactionRecordDetail;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.model.sales.TransationRecordDetailModel;
import io.wabm.supermarket.view.ViewPathHelper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Created by Administrator on 2016/11/28 0028.
 */
public class TransactionRecordDetailController extends SceneController {

    private TransationRecordDetailModel<TransactionRecordDetail> model;
    @FXML Button backButton;

    @FXML private TableView<TransactionRecordDetail> tableView;
    @FXML private TableColumn<TransactionRecordDetail,Integer> recordID;
    @FXML private TableColumn<TransactionRecordDetail,String> commodityID;
    @FXML private TableColumn<TransactionRecordDetail,String> barcode;
    @FXML private TableColumn<TransactionRecordDetail,String> name;
    @FXML private TableColumn<TransactionRecordDetail,Integer> classificationID;
    @FXML private TableColumn<TransactionRecordDetail,String> specification;
    @FXML private TableColumn<TransactionRecordDetail,String> unit;
    @FXML private TableColumn<TransactionRecordDetail,Integer> quantity;
    @FXML private TableColumn<TransactionRecordDetail,Double> price;


    @FXML public void initialize() {
        ConsoleLog.print("TransactionRecordController init");

        setupControl();
        setupModel();
        setupTableView();
        setupTableViewColumn();
    }
    @FXML private void backButtonPressed() {
        ConsoleLog.print("button pressed");

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ViewPathHelper.class.getResource("sales/TransactionRecordsView.fxml"));

        navigationTo(loader);
    }
    private void setupControl(){
    }
    private void setupModel(){

        model = new TransationRecordDetailModel<>(tableView);
    }
    private void setupTableView(){}
    private void setupTableViewColumn(){
        recordID.setCellValueFactory(new PropertyValueFactory<TransactionRecordDetail, Integer>("recordID"));
        commodityID.setCellValueFactory(new PropertyValueFactory<TransactionRecordDetail, String>("commodityID"));
        barcode.setCellValueFactory(new PropertyValueFactory<TransactionRecordDetail, String>("barcode"));
        name.setCellValueFactory(new PropertyValueFactory<TransactionRecordDetail, String>("name"));
        classificationID.setCellValueFactory(new PropertyValueFactory<TransactionRecordDetail, Integer>("classificationID"));
        specification.setCellValueFactory(new PropertyValueFactory<TransactionRecordDetail, String>("specification"));
        unit.setCellValueFactory(new PropertyValueFactory<TransactionRecordDetail, String>("unit"));
        quantity.setCellValueFactory(new PropertyValueFactory<TransactionRecordDetail, Integer>("quantity"));
        price.setCellValueFactory(new PropertyValueFactory<TransactionRecordDetail, Double>("price"));
    }

    public void fetchWith(int recordID) {

        this.model.fetchData(recordID,
                (isSuccess) -> {
                    ConsoleLog.print("Fetch is " + (isSuccess ? "success" : "failed"));
                    return null;
                }
        );

    }
}
