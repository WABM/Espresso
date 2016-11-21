package io.wabm.supermarket.controller.sales;

import io.wabm.supermarket.controller.SceneController;
import io.wabm.supermarket.misc.pojo.CashInformation;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.model.sales.CashInformationModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Created by Administrator on 2016/11/20 0020.
 */
public class CashManagementController extends SceneController {

    private CashInformationModel<CashInformation> model;

    @FXML Button accountingButton;
    @FXML TableView<CashInformation> tableView;
    @FXML TableColumn<CashInformation,String> CashRegisterID;
    @FXML TableColumn<CashInformation,String> EmployeeID;
    @FXML TableColumn<CashInformation,Double> MoneyIN;
    @FXML TableColumn<CashInformation,Double> MoneyOUT;
    @FXML TableColumn<CashInformation,Double> MoneyShould;

    @FXML public void initialize(){
        ConsoleLog.print("CashManagementController init");

        setupControl();
        setupModel();
        setupTableView();
        setupTableViewColumn();

        for (int i=1;i<6;i++)
            model.add(new CashInformation(i+"",i+"",1000.0,2600.0,1600.0));
    }
    @FXML public void accountingPressed(){
        ConsoleLog.print("accountingButton pressed");
    }
    private void setupControl() {
    }

    private void setupModel() {
        model = new CashInformationModel<>(tableView);
    }

    private void setupTableView() {
        tableView.setEditable(true);
    }

    private void setupTableViewColumn() {
        CashRegisterID.setCellValueFactory(new PropertyValueFactory<CashInformation, String>("cashRegisterID"));
        EmployeeID.setCellValueFactory(new PropertyValueFactory<CashInformation, String>("employeeID"));
        MoneyIN.setCellValueFactory(new PropertyValueFactory<CashInformation, Double>("moneyIN"));
        MoneyOUT.setCellValueFactory(new PropertyValueFactory<CashInformation, Double>("moneyOUT"));
        MoneyShould.setCellValueFactory(new PropertyValueFactory<CashInformation, Double>("moneyShould"));
    }
}
