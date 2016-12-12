package io.wabm.supermarket.controller.sales;

import io.wabm.supermarket.controller.SceneController;
import io.wabm.supermarket.misc.pojo.Classification;
import io.wabm.supermarket.misc.pojo.CommodityPriceInformation;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.model.sales.ClassComboBoxModel;
import io.wabm.supermarket.model.sales.CommodityPriceInformationModel;
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
import java.math.BigDecimal;

/**
 * Created by MR-lulu on 2016/10/27 0027.
 */
public class CommodityPriceManagementController extends SceneController {

    private SaleClassificationInformationModel<Classification> Classmodel;
    private CommodityPriceInformationModel<CommodityPriceInformation> Commoditymodel;
    private ClassComboBoxModel<String> boxModel;

    /*设置分类价格开始*/
    @FXML private TableView<Classification> tableView;
    @FXML private TableColumn<Classification, String> nameColumn;
    @FXML private TableColumn<Classification,Double> profitRate;
    @FXML private TableColumn<Classification,Double> taxRate;

    @FXML Button queryButton;
    @FXML ComboBox classButton;
    @FXML Button modify;
    /*设置分类价格结束*/

    /*设置单个商品价格开始*/
    @FXML private TableView<CommodityPriceInformation> tableViewC;
    @FXML private TableColumn<CommodityPriceInformation,String> commodityID;
    @FXML private TableColumn<CommodityPriceInformation,String> barcode;
    @FXML private TableColumn<CommodityPriceInformation,String> commodityName;
    @FXML private TableColumn<CommodityPriceInformation,String> className;
    @FXML private TableColumn<CommodityPriceInformation,String> specification;
    @FXML private TableColumn<CommodityPriceInformation,String> unit;
    @FXML private TableColumn<CommodityPriceInformation,BigDecimal> price;

    @FXML Button queryButtonC;
    @FXML ComboBox classButtonC;
    @FXML Button modifyC;
    /*设置单个商品价格结束*/
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
        boxModel = new ClassComboBoxModel<>(classButton);
        modify.setDisable(true);

        modifyC.setDisable(true);
        classButtonC.setItems(boxModel.getList());
    }
    private void setupModel(){
        Classmodel = new SaleClassificationInformationModel<>(tableView);
        Classmodel.fetchData(isSuccess -> {
            ConsoleLog.print("Fetch is " + (isSuccess ? "success" : "failed"));
            return null;
        });

        Commoditymodel = new CommodityPriceInformationModel<>(tableViewC);
        Commoditymodel.fetchData(isSuccess -> {
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

        tableViewC.setEditable(true);
        tableViewC.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> modifyC.setDisable(newValue == null)
        );
    }
    private void setupTableViewColumn(){
        nameColumn.setCellValueFactory(new PropertyValueFactory<Classification, String>("name"));
        profitRate.setCellValueFactory(new PropertyValueFactory<Classification, Double>("profitRate"));
        taxRate.setCellValueFactory(new PropertyValueFactory<Classification, Double>("taxRate"));

        commodityID.setCellValueFactory(new PropertyValueFactory<CommodityPriceInformation, String>("commodityID"));
        barcode.setCellValueFactory(new PropertyValueFactory<CommodityPriceInformation, String>("barcode"));
        commodityName.setCellValueFactory(new PropertyValueFactory<CommodityPriceInformation, String>("commodityName"));
        className.setCellValueFactory(new PropertyValueFactory<CommodityPriceInformation, String>("className"));
        specification.setCellValueFactory(new PropertyValueFactory<CommodityPriceInformation, String>("specification"));
        unit.setCellValueFactory(new PropertyValueFactory<CommodityPriceInformation, String>("unit"));
        price.setCellValueFactory(new PropertyValueFactory<CommodityPriceInformation, BigDecimal>("price"));
    }
    @FXML private void queryButtonPressedC(){
        ConsoleLog.print("Button Pressed");

        String name = classButtonC.getValue().toString();

        if (name.equals("全部")) {
            Commoditymodel.fetchData(isSuccess -> {
                ConsoleLog.print("Fetch is " + (isSuccess ? "success" : "failed"));
                return null;
            });
        }else {
            Commoditymodel.choose(name, isSuccess -> {
                ConsoleLog.print("Fetch is " + (isSuccess ? "success" : "failed"));
                return null;
            });
        }
    }
    @FXML private void modifyButtonPressedC(){
        ConsoleLog.print("Button Pressed");

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ViewPathHelper.class.getResource("sales/modifyPriceDialog.fxml"));
            AnchorPane pane = loader.load();

            Stage stage = new Stage();
            stage.setTitle("修改价格");
            stage.initModality(Modality.APPLICATION_MODAL);

            Scene scene = new Scene(pane);
            stage.setScene(scene);

            ModifyPriceController controller = loader.getController();
            controller.setStage(stage);
            controller.setData(getCommodityPriceInformation());

            stage.showAndWait();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    private Classification getClassification()
    {
        Classification classification = tableView.getSelectionModel().getSelectedItem();
        return classification;
    }
    private CommodityPriceInformation getCommodityPriceInformation(){
        CommodityPriceInformation commodityPriceInformation= tableViewC.getSelectionModel().getSelectedItem();
        return commodityPriceInformation;
    }
}
