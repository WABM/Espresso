package io.wabm.supermarket.controller.warehouse;

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

    private CommodityStorageModel<StorageCommodity> model;


    @FXML TableView<StorageCommodity> tableView;
    @FXML TableColumn<StorageCommodity, String> idColumn;
    @FXML TableColumn<StorageCommodity, String> barcodeColumn;
    @FXML TableColumn<StorageCommodity, String> nameColumn;
    @FXML TableColumn<StorageCommodity, Integer> classificationColumn;      // FIXME: classification should be String
    @FXML TableColumn<StorageCommodity, String> specificationColumn;
    @FXML TableColumn<StorageCommodity, String> unitColumn;
    @FXML TableColumn<StorageCommodity, Integer> storageColumn;
    @FXML TableColumn<StorageCommodity, Hyperlink> actionColumn;

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

        model.add(new StorageCommodity("TD1324", 1, "6570234145436", "来一桶老坛酸菜牛肉面", "300g", "桶", 20, 73));
        model.add(new StorageCommodity("TD1322", 1, "6570234145435", "来两桶老坛酸菜牛肉面", "300g*2", "桶", 20, 113));
        model.add(new StorageCommodity("TD1323", 1, "6570234145431", "来三桶老坛酸菜牛肉面", "300g*3", "桶", 20, 273));
        model.add(new StorageCommodity("TD1321", 1, "6570234145433", "来四桶老坛酸菜牛肉面", "300g*4", "桶", 20, 373));
    }


    // MARK: Setup method

    private void setupModel() {
        model = new CommodityStorageModel<>(tableView);
    }

    private void setupTableViewColumn() {
        // Setup cell value factory
        idColumn.setCellValueFactory(cellData -> cellData.getValue().storageCommodityIDProperty());
        barcodeColumn.setCellValueFactory(cellData -> cellData.getValue().barcodeProperty());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        classificationColumn.setCellValueFactory(cellData -> cellData.getValue().classificationIDProperty().asObject());
        specificationColumn.setCellValueFactory(cellData -> cellData.getValue().specificationProperty());
        unitColumn.setCellValueFactory(cellData -> cellData.getValue().unitProperty());
        storageColumn.setCellValueFactory(cellData -> cellData.getValue().storageProperty().asObject());

        // TODO: actionColumn
    }
}
