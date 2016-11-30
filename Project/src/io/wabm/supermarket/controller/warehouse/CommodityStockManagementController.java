package io.wabm.supermarket.controller.warehouse;

import io.wabm.supermarket.misc.pojo.Commodity;
import io.wabm.supermarket.misc.pojo.StorageCommodity;
import io.wabm.supermarket.model.warehouse.CommodityStorageModel;
import io.wabm.supermarket.protocol.StageSetableController;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.view.ViewPathHelper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by MainasuK on 2016-10-24.
 */
public class CommodityStockManagementController {

    private CommodityStorageModel<Commodity> model;

    @FXML TableView<Commodity> tableView;
    @FXML TableColumn<Commodity, String> idColumn;
    @FXML TableColumn<Commodity, String> barcodeColumn;
    @FXML TableColumn<Commodity, String> nameColumn;
    @FXML TableColumn<Commodity, String> classificationColumn;
    @FXML TableColumn<Commodity, String> specificationColumn;
    @FXML TableColumn<Commodity, String> unitColumn;
    @FXML TableColumn<Commodity, Integer> storageColumn;
    @FXML TableColumn<Commodity, Hyperlink> actionColumn;

    @FXML Button purchaseFormButton;
    @FXML Button orderReceiveButton;

    @FXML private void purchaseFormButtonPressed() {
        ConsoleLog.print("Button pressed");

        try {
            // Load view
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ViewPathHelper.class.getResource("warehouse/CommodityPurchaseFormView.fxml"));
            AnchorPane pane = loader.load();

            // Create the popup Stage.
            Stage stage = new Stage();
            stage.setTitle("需补货商品");
            stage.initModality(Modality.APPLICATION_MODAL);

            Scene scene = new Scene(pane);
            stage.setScene(scene);

            // Pass the info into the controller.
            StageSetableController controller = loader.getController();
            controller.setStage(stage);

            // Show the dialog and wait until the user closes it.
            // (This event thread is blocked until close)
            stage.showAndWait();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML private void orderReceiveButtonPressed() {
        ConsoleLog.print("Button pressed");

        try {
            // Load view
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ViewPathHelper.class.getResource("warehouse/CommodityOrderReceiveView.fxml"));
            AnchorPane pane = loader.load();

            // Create the popup Stage.
            Stage stage = new Stage();
            stage.setTitle("待收货订单");
            stage.initModality(Modality.APPLICATION_MODAL);

            Scene scene = new Scene(pane);
            stage.setScene(scene);

            // Pass the info into the controller.
            CommodityOrderReceiveController controller = loader.getController();
            controller.setStage(stage);

            // Show the dialog and wait until the user closes it.
            // (This event thread is blocked until close)
            stage.showAndWait();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML public void initialize() {
        ConsoleLog.print("CommodityStockManagementController init");

        setupModel();
        setupTableViewColumn();
    }


    // MARK: Setup method

    private void setupModel() {
        model = new CommodityStorageModel<>(tableView);
        model.fetchAllData(exception -> {
            ConsoleLog.print("Fetch has: " + exception);
            return null;
        });
    }

    private void setupTableViewColumn() {
        // Setup cell value factory
        idColumn.setCellValueFactory(cellData -> cellData.getValue().commodityIDProperty());
        barcodeColumn.setCellValueFactory(cellData -> cellData.getValue().barcodeProperty());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        classificationColumn.setCellValueFactory(cellData -> cellData.getValue().classificationNameProperty());
        specificationColumn.setCellValueFactory(cellData -> cellData.getValue().specificationProperty());
        unitColumn.setCellValueFactory(cellData -> cellData.getValue().unitProperty());
        storageColumn.setCellValueFactory(cellData -> cellData.getValue().storageProperty().asObject());

        // TODO: actionColumn
    }
}
