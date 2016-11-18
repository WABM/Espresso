package io.wabm.supermarket.controller.warehouse;

import io.wabm.supermarket.controller.SceneController;
import io.wabm.supermarket.misc.javafx.tablecell.HyperlinkTableCell;
import io.wabm.supermarket.misc.pojo.Classification;
import io.wabm.supermarket.model.warehouse.CommodityClassificationInformationModel;
import io.wabm.supermarket.protocol.CellFactorySetupCallbackProtocol;
import io.wabm.supermarket.protocol.StageSetableContoller;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.view.ViewPathHelper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.Optional;
import java.util.function.Function;

/**
 * Created by MainasuK on 2016-10-25.
 */
public class CommodityClassificationInformationManagementController extends SceneController {

    private CommodityClassificationInformationModel<Classification> model;

    @FXML private TableView<Classification> tableView;
    @FXML private TableColumn<Classification, String> nameColumn;
    @FXML private TableColumn<Classification, Hyperlink> actionColumn;


    @FXML Button addButton;
    @FXML Button deleteButton;

    @FXML public void initialize() {
        ConsoleLog.print("CommodityClassificationInformationManagementController init");

        setupControl();
        setupModel();
        setupTableView();
        setupTableViewColumn();

        // Stub for develop
        model.add(new Classification(0, "水果", 0.10, 0.17));
        model.add(new Classification(1, "副食", 0.10, 0.17));
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
        final String name = tableView.getSelectionModel().getSelectedItem().getName();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("删除分类");
        alert.setHeaderText("确认删除");
        alert.setContentText("删除 " + name);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {

        } else {

        }
    }

    // MARK: Setup method

    private void setupControl() {
        deleteButton.setDisable(true);
    }

    private void setupModel() {
        model = new CommodityClassificationInformationModel<>(tableView);
    }

    private void setupTableView() {
        // Set editable on
        tableView.setEditable(true);

        // Set tableView select event listener
        tableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> deleteButton.setDisable(newValue == null)
        );
    }

    private void setupTableViewColumn() {

        // Setup cell factory
        actionColumn.setCellFactory(actionColumnSetupCallback);

        // Setup cell value factory
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        actionColumn.setCellValueFactory(cellData -> {

            return new SimpleObjectProperty<>(new Hyperlink("查看"));
        });

        // Just for test
//        actionColumn.setCellFactory(column -> new CheckBoxTableCell<>());
//        actionColumn.setCellValueFactory(cellData -> {
//            Classification classification = cellData.getValue();
//            BooleanProperty property = classification.choosedProperty();
//
//            // Add listener for handler change
//            property.addListener((observable, oldValue, newValue) -> classification.setChoosed(newValue));
//
//            return property;
//        });
    }

    private CellFactorySetupCallbackProtocol<Classification, Hyperlink> actionColumnSetupCallback = (column) -> new HyperlinkTableCell() {
        @Override
        protected void updateItem(Hyperlink item, boolean empty) {
            super.updateItem(item, empty);

            setAlignment(Pos.CENTER);

            // Check empty first
            if (!empty) {
                item.setOnAction(event -> {
                    Classification classification = (Classification) getTableRow().getItem();
                    ConsoleLog.print("" + classification.getName());

                    FXMLLoader loder = new FXMLLoader();
                    loder.setLocation(ViewPathHelper.class.getResource("warehouse/CommodityInformationManagementView.fxml"));

                    navigationTo(loder);
                });
            }
        }
    };
}
