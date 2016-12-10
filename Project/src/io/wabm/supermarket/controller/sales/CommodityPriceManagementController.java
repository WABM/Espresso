package io.wabm.supermarket.controller.sales;

import io.wabm.supermarket.controller.SceneController;
import io.wabm.supermarket.misc.pojo.Classification;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.model.sales.ClassComboBoxModel;
import io.wabm.supermarket.model.sales.SaleClassificationInformationModel;
import io.wabm.supermarket.model.warehouse.CommodityClassificationInformationModel;
import io.wabm.supermarket.view.ViewPathHelper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by MR-lulu on 2016/10/27 0027.
 */
public class CommodityPriceManagementController extends SceneController {

    private SaleClassificationInformationModel<Classification> Classmodel;
    private ClassComboBoxModel<String> boxModel;

    @FXML private TableView<Classification> tableView;
    @FXML private TableColumn<Classification, String> nameColumn;
    @FXML private TableColumn<Classification,Double> profitRate;
    @FXML private TableColumn<Classification,Double> taxRate;

    @FXML Button queryButton;
    @FXML ComboBox classButton;
    @FXML Button modify;

    @FXML public void initialize() {
        ConsoleLog.print("CommodityPriceManagementController init");

        setupControl();
        setupModel();
        setupTableView();
        setupTableViewColumn();
    }
    @FXML private void queryButtonPressed(){
        ConsoleLog.print("Button Pressed");

        String name = classButton.getValue().toString();

        if (name.equals("全部")) {
            Classmodel.fetchData(isSuccess -> {
                ConsoleLog.print("Fetch is " + (isSuccess ? "success" : "failed"));
                return null;
            });
        }else {
            Classmodel.Choose(name, isSuccess -> {
                ConsoleLog.print("Fetch is " + (isSuccess ? "success" : "failed"));
                return null;
            });
        }

    }
    @FXML private void modifyButtonPressed(){
        ConsoleLog.print("Button Pressed");

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ViewPathHelper.class.getResource("sales/modifyPriceDialogByType.fxml"));
            AnchorPane pane = loader.load();

            Stage stage = new Stage();
            stage.setTitle("修改价格");
            stage.initModality(Modality.APPLICATION_MODAL);

            Scene scene = new Scene(pane);
            stage.setScene(scene);

            ModifyPriceByTypeController controller = loader.getController();
            controller.setStage(stage);
            controller.setDate(getClassification());

            stage.showAndWait();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    private void setupControl(){
        modify.setDisable(true);
        boxModel = new ClassComboBoxModel<>(classButton);
        classButton.setItems(boxModel.getList());
    }
    private void setupModel(){
        Classmodel = new SaleClassificationInformationModel<>(tableView);

        // TODO: loading info need add to view (Spinner or just Loading… (e.g. 加载中……)
        Classmodel.fetchData(isSuccess -> {
            ConsoleLog.print("Fetch is " + (isSuccess ? "success" : "failed"));
            return null;
        });
    }
    private void setupTableView(){
        tableView.setEditable(true);

        // Set tableView select event listener
        tableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> modify.setDisable(newValue == null)
        );
    }
    private void setupTableViewColumn(){
        nameColumn.setCellValueFactory(new PropertyValueFactory<Classification, String>("name"));
        profitRate.setCellValueFactory(new PropertyValueFactory<Classification, Double>("profitRate"));
        taxRate.setCellValueFactory(new PropertyValueFactory<Classification, Double>("taxRate"));
    }

    private Classification getClassification()
    {
        Classification classification = tableView.getSelectionModel().getSelectedItem();
        return classification;
    }
}
