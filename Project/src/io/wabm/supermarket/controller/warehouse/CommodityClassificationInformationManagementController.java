package io.wabm.supermarket.controller.warehouse;

import io.wabm.supermarket.misc.pojo.Classification;
import io.wabm.supermarket.model.warehouse.CommodityClassificationInformationModel;
import io.wabm.supermarket.protocol.StageSetableContoller;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.view.ViewPathHelper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

/**
 * Created by MainasuK on 2016-10-25.
 */
public class CommodityClassificationInformationManagementController {

    private CommodityClassificationInformationModel model;

    @FXML private TableView<Classification> tableView;
    @FXML private TableColumn<Classification, String> nameColumn;


    @FXML Button addButton;
    @FXML Button deleteButton;

    @FXML public void initialize() {
        ConsoleLog.print("CommodityClassificationInformationManagementController init");

        model = new CommodityClassificationInformationModel(tableView);
        setupTableViewColumn();

        // Stub for develop
        model.add(new Classification(0, "水果", 0.10, 0.17));
    }


    @FXML private void addButtonPressed() {
        ConsoleLog.print("Button pressed");

        try {
            // Load view
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ViewPathHelper.class.getResource("warehouse/AddCommodityClassificationView.fxml"));
            AnchorPane pane = loader.load();

            // Create the popup Stage.
            Stage stage = new Stage();
            stage.setTitle("添加商品分类");
            stage.initModality(Modality.APPLICATION_MODAL);

            Scene scene = new Scene(pane);
            stage.setScene(scene);

            // Pass the info into the controller.
            StageSetableContoller controller = loader.getController();
            controller.setStage(stage);

            // Show the dialog and wait until the user closes it.
            // (This event thread is blocked until close)
            stage.showAndWait();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML private void deleteButtonPressed() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("删除分类");
        alert.setHeaderText("确认删除");
        alert.setContentText("删除 " + "ABC");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {

        } else {

        }
    }

    private void setupModel() {
        model = new CommodityClassificationInformationModel(tableView);
    }

    private void setupTableViewColumn() {
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
    }
}
