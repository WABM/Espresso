package io.wabm.supermarket.controller.sales;

import io.wabm.supermarket.controller.SceneController;
import io.wabm.supermarket.misc.pojo.CashInformation;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.model.sales.CashInformationModel;
import io.wabm.supermarket.view.ViewPathHelper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.stereotype.Controller;

import java.io.IOException;

/**
 * Created by Administrator on 2016/11/20 0020.
 */
public class CashManagementController extends SceneController {

    private CashInformationModel<CashInformation> model;

    @FXML Button accountingButton;

    @FXML TableView<CashInformation> tableView;
    @FXML TableColumn<CashInformation,String> employeeID;
    @FXML TableColumn<CashInformation,String> name;
    @FXML TableColumn<CashInformation,Double> moneyIN;
    @FXML TableColumn<CashInformation,Double> moneyOUT;
    @FXML TableColumn<CashInformation,Double> moneyShould;
    @FXML TableColumn<CashInformation,String> date;

    @FXML public void initialize(){
        ConsoleLog.print("CashManagementController init");

        setupControl();
        setupModel();
        setupTableView();
        setupTableViewColumn();
    }
    @FXML public void accountingPressed(){
        ConsoleLog.print("accountingButton pressed");

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ViewPathHelper.class.getResource("sales/CashDialog.fxml"));
            AnchorPane pane = loader.load();

            Stage stage = new Stage();
            stage.setTitle("核算现金");
            stage.initModality(Modality.APPLICATION_MODAL);

            Scene scene = new Scene(pane);
            stage.setScene(scene);

            CheckCashController controller = loader.getController();
            controller.setStage(stage);
            controller.setTableView(tableView);

            stage.showAndWait();


        }catch (IOException e){
            e.printStackTrace();
        }
    }
    private void setupControl() {
    }

    private void setupModel() {
        model = new CashInformationModel<>(tableView);

        model.fetchData(isSuccess -> {
            ConsoleLog.print("Fetch is " + (isSuccess ? "success" : "failed"));
            return null;
        });
    }

    private void setupTableView() {
        tableView.setEditable(true);
    }

    private void setupTableViewColumn() {
        employeeID.setCellValueFactory(new PropertyValueFactory<CashInformation, String>("employeeID"));
        name.setCellValueFactory(new PropertyValueFactory<CashInformation, String>("name"));
        moneyIN.setCellValueFactory(new PropertyValueFactory<CashInformation, Double>("moneyIN"));
        moneyShould.setCellValueFactory(new PropertyValueFactory<CashInformation, Double>("moneyOUT"));
        moneyOUT.setCellValueFactory(new PropertyValueFactory<CashInformation, Double>("moneyShould"));
        date.setCellValueFactory(new PropertyValueFactory<CashInformation, String>("date"));
    }
}
