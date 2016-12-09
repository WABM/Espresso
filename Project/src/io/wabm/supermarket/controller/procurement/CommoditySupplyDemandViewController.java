package io.wabm.supermarket.controller.procurement;

import io.wabm.supermarket.controller.SceneController;
import io.wabm.supermarket.misc.javafx.tablecell.HyperlinkTableCell;
import io.wabm.supermarket.misc.pojo.CommoditySupplyDemand;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.model.procurement.CommoditySupplyDemandModel;
import io.wabm.supermarket.protocol.CellFactorySetupCallbackProtocol;
import io.wabm.supermarket.protocol.StageSetableController;
import io.wabm.supermarket.view.ViewPathHelper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * Created by 14580 on 2016/11/20 0020.
 */
public class CommoditySupplyDemandViewController extends SceneController {

    private CommoditySupplyDemandModel<CommoditySupplyDemand> model;

    @FXML TableView<CommoditySupplyDemand> tableView;

    @FXML TableColumn<CommoditySupplyDemand, String> commodityidColumn;
    @FXML TableColumn<CommoditySupplyDemand, String> barcodeColumn;
    @FXML TableColumn<CommoditySupplyDemand, String> commoditynameColumn;
    @FXML TableColumn<CommoditySupplyDemand, String> classificationColumn;
    @FXML TableColumn<CommoditySupplyDemand, String> specificationColumn;
    @FXML TableColumn<CommoditySupplyDemand, Integer> deliveryspecificationColumn;
    @FXML TableColumn<CommoditySupplyDemand, String> unitColumn;
    @FXML TableColumn<CommoditySupplyDemand, Integer> quantityColumn;
    @FXML TableColumn<CommoditySupplyDemand, BigDecimal> priceColumn;
    @FXML TableColumn<CommoditySupplyDemand, String> supplierColumn;
    @FXML private TableColumn<CommoditySupplyDemand, Hyperlink> actionColumn;

    @FXML Button createButton;


    @FXML public void initialize() {
        ConsoleLog.print("CommoditySupplyDemandViewController init");

        setupControl();
        setupModel();
        setupTableView();
        setupTableViewColumn();

    }
    private void setupModel() {
        model = new CommoditySupplyDemandModel<>(tableView);
        model.fetchData(isSuccess -> {
            ConsoleLog.print("Fetch is " /*+ (isSuccess ? "success" : "failed")*/);
            return null;
        });
    }

    private void setupTableView() {
    }

    private void setupControl() {
    }

    private void setupTableViewColumn() {

        actionColumn.setCellFactory(actionColumnSetupCallback);

        commodityidColumn.setCellValueFactory(cellData -> cellData.getValue().commodityidProperty());
        barcodeColumn.setCellValueFactory(cellData -> cellData.getValue().barcodeProperty());
        commoditynameColumn.setCellValueFactory(cellData -> cellData.getValue().commoditynameProperty());
        classificationColumn.setCellValueFactory(cellData -> cellData.getValue().classificationProperty());
        specificationColumn.setCellValueFactory(cellData -> cellData.getValue().specificationProperty());
        deliveryspecificationColumn.setCellValueFactory(cellData -> cellData.getValue().deliveryspecificationProperty().asObject());
        unitColumn.setCellValueFactory(cellData -> cellData.getValue().unitProperty());
        quantityColumn.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty());
        supplierColumn.setCellValueFactory(cellData -> cellData.getValue().supplierProperty());
        actionColumn.setCellValueFactory(cellData -> {
            return new SimpleObjectProperty<>(new Hyperlink("选择供应商"));
        });
    }
    private SelectSupplierController selectSupplierController;
    private CellFactorySetupCallbackProtocol<CommoditySupplyDemand, Hyperlink> actionColumnSetupCallback = (column) -> new HyperlinkTableCell() {
        @Override
        protected void updateItem(Hyperlink item, boolean empty) {
            super.updateItem(item, empty);

            setAlignment(Pos.CENTER);

            // Check empty first
            if (!empty) {
                item.setOnAction(event -> {
                    CommoditySupplyDemand commoditySupplyDemand = (CommoditySupplyDemand) getTableRow().getItem();
                    ConsoleLog.print("ok");

                    FXMLLoader loder = new FXMLLoader();
                    loder.setLocation(ViewPathHelper.class.getResource("procurement/SelectSupplier.fxml"));

                    // Bind controller when you first call it.
                    navigationTo(loder, controller -> {

                        if (selectSupplierController == null) {
                            ConsoleLog.print("Bind controller success");
                            selectSupplierController = ((SelectSupplierController) controller);
                        }

                        return null;
                    });

                    // Then dispatch the task to controller you hold.
                    selectSupplierController.fetchWith(commoditySupplyDemand.getCommodityid());
                });
            }
        }
    };

    @FXML private void createButtonPressed(){
        ConsoleLog.print("Button pressed");
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ViewPathHelper.class.getResource("procurement/CreateOrder.fxml"));
            AnchorPane pane=loader.load();

            Stage stage = new Stage();
            stage.setTitle("生成订单");
            stage.initModality(Modality.APPLICATION_MODAL);

            Scene scene=new Scene(pane);
            stage.setScene(scene);

            StageSetableController contoller=loader.getController();
            contoller.setStage(stage);

            stage.showAndWait();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
