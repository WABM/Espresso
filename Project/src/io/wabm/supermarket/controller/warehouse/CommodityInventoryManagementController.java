package io.wabm.supermarket.controller.warehouse;

import io.wabm.supermarket.misc.javafx.tablecell.HyperlinkTableCell;
import io.wabm.supermarket.misc.pojo.Inventory;
import io.wabm.supermarket.misc.util.CalendarFormater;
import io.wabm.supermarket.model.warehouse.CommodityInventoryModel;
import io.wabm.supermarket.protocol.CellFactorySetupCallbackProtocol;
import io.wabm.supermarket.protocol.StageSetableController;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.view.ViewPathHelper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Optional;
import java.util.TimeZone;

/**
 * Created by MainasuK on 2016-10-25.
 */
public class CommodityInventoryManagementController {

    private CommodityInventoryModel model;

    @FXML TableView<Inventory> tableView;
    @FXML TableColumn<Inventory, String> nameColumn;
    @FXML TableColumn<Inventory, String> classificationColumn;
    @FXML TableColumn<Inventory, Integer> commodityNumColumn;
    @FXML TableColumn<Inventory, String> createDateColumn;
    @FXML TableColumn<Inventory, String> statusColumn;
    @FXML TableColumn<Inventory, Hyperlink> actionColumn;

    @FXML Button addButton;
    @FXML Button deleteButton;

    @FXML private void addButtonPressed() {
        ConsoleLog.print("Button pressed");

        try {
            // Load view
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ViewPathHelper.class.getResource("warehouse/AddInventoryListView.fxml"));
            AnchorPane pane = loader.load();

            // Create the popup Stage.
            Stage stage = new Stage();
            stage.setTitle("新建盘点清单");
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

    @FXML private void deleteButtonPressed() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete");
        alert.setHeaderText("确认删除");
        alert.setContentText("删除 " + "ABC");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
//            personTableView.getItems().remove(index);
        } else {
            // Cancel
        }
    }

    @FXML private void initialize() {
        ConsoleLog.print("CommodityInventoryManagementController init");

        setupModel();
        setupTableColumn();
    }

    private void setupModel() {
        model = new CommodityInventoryModel(tableView);
        refetch();
    }

    private void refetch() {
        model.fetch(exception -> {
            ConsoleLog.print("fetch finish");

            return null;
        });
    }

    private void setupTableColumn() {
        actionColumn.setCellFactory(actionColumnSetupCallback);

        nameColumn.setCellValueFactory(cellData -> {
            Calendar calendar = new GregorianCalendar(TimeZone.getTimeZone("CST"));
            calendar.setTimeInMillis(cellData.getValue().getCreateTimestamp().getTime());

            return new SimpleStringProperty(CalendarFormater.toString(calendar, "MM月 月结清单"));
        });
        classificationColumn.setCellValueFactory(cellData -> cellData.getValue().classificationNameProperty());
        createDateColumn.setCellValueFactory(cellData -> {
            Calendar calendar = new GregorianCalendar(TimeZone.getTimeZone("CST"));
            calendar.setTimeInMillis(cellData.getValue().getCreateTimestamp().getTime());

            return new SimpleStringProperty(CalendarFormater.toString(calendar, "yyyy年MM月dd日"));
        });
        commodityNumColumn.setCellValueFactory(cellData -> cellData.getValue().hasNumProperty().asObject());
        statusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
        actionColumn.setCellValueFactory(cellData -> {
            String text = (cellData.getValue().getFillTimestamp() == null) ? "盘点" : "查看";
            return new SimpleObjectProperty<>(new Hyperlink(text));
        });

    }

    private CellFactorySetupCallbackProtocol<Inventory, Hyperlink> actionColumnSetupCallback = (column) -> new HyperlinkTableCell() {
        @Override
        protected void updateItem(Hyperlink item, boolean empty) {
            super.updateItem(item, empty);

            setAlignment(Pos.CENTER);

            // Check empty first
            if (!empty) {
                item.setOnAction(event -> {
                    Inventory inventory = (Inventory) getTableRow().getItem();
                    ConsoleLog.print("" + inventory.getClassificationName());

                    try {
                        // Load view
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(ViewPathHelper.class.getResource("warehouse/CommodityInventoryDetailView.fxml"));
                        AnchorPane pane = loader.load();

                        // Create the popup Stage.
                        Stage stage = new Stage();
                        stage.setTitle("盘点详单 — " + inventory.getClassificationName());
                        stage.initModality(Modality.APPLICATION_MODAL);

                        Scene scene = new Scene(pane);
                        stage.setScene(scene);

                        boolean stocktakingflag = (inventory.getFillTimestamp() == null) ? true : false;

                        // Pass the info into the controller.
                        CommodityInventoryDetailController controller = loader.getController();
                        controller.setStage(stage);
                        controller.setIDs(inventory.getClassificationID(), inventory.getInventoryID(), stocktakingflag);

                        // Show the dialog and wait until the user closes it.
                        // (This event thread is blocked until close)
                        stage.showAndWait();

                        refetch();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                });
            }
        }
    };
}
