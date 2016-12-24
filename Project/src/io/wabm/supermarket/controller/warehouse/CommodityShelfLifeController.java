package io.wabm.supermarket.controller.warehouse;

import io.wabm.supermarket.misc.javafx.tablecell.HyperlinkTableCell;
import io.wabm.supermarket.misc.pojo.ShelfLifeCommodity;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.model.warehouse.CommodityShelfLifeModel;
import io.wabm.supermarket.protocol.CellFactorySetupCallbackProtocol;
import io.wabm.supermarket.protocol.StageSetableController;
import io.wabm.supermarket.view.ViewPathHelper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * Created by MainasuK on 2016-11-20.
 */
public class CommodityShelfLifeController {

    private CommodityShelfLifeModel<ShelfLifeCommodity> model;

    @FXML TableView<ShelfLifeCommodity> tableView;
    @FXML TableColumn<ShelfLifeCommodity, String> idColumn;
    @FXML TableColumn<ShelfLifeCommodity, String> barcodeColumn;
    @FXML TableColumn<ShelfLifeCommodity, String> nameColumn;
    @FXML TableColumn<ShelfLifeCommodity, String> classificationColumn;
    @FXML TableColumn<ShelfLifeCommodity, String> specificationColumn;
    @FXML TableColumn<ShelfLifeCommodity, String> unitColumn;
    @FXML TableColumn<ShelfLifeCommodity, LocalDate> productionDateColumn;
    @FXML TableColumn<ShelfLifeCommodity, LocalDate> expirationDateColumn;
    @FXML TableColumn<ShelfLifeCommodity, String> shelfLifeColumn;
    @FXML TableColumn<ShelfLifeCommodity, Hyperlink> actionColumn;


    @FXML Button printListButton;

    @FXML private void printListButtonPressed() {
        ConsoleLog.print("button pressed");

        print(tableView);
    }

    @FXML public void initialize() {
        ConsoleLog.print("CommodityShelfLifeController init");

        setupModel();
        setupTableViewColumn();
    }

    private void setupModel() {
        model = new CommodityShelfLifeModel<>(tableView);
        refetch();
    }

    private void refetch() {
        model.fetch(exception -> {
            ConsoleLog.print("fetch with exception: " + exception);

            return null;
        });
    }

    private  void setupTableViewColumn() {
        final PseudoClass pseudoClass = PseudoClass.getPseudoClass("yellow-highlight-row");
        tableView.setRowFactory(param -> new TableRow<ShelfLifeCommodity>() {
            @Override
            protected void updateItem(ShelfLifeCommodity item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty) {
                    pseudoClassStateChanged(pseudoClass, LocalDate.now().isAfter(item.getExpirationDate().minus(4, DAYS)));
                }
            }
        });

        actionColumn.setCellFactory(actionColumnSetupCallback);

        // Setup cell value factory
        idColumn.setCellValueFactory(cellData -> cellData.getValue().commodityIDProperty());
        barcodeColumn.setCellValueFactory(cellData -> cellData.getValue().barcodeProperty());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        classificationColumn.setCellValueFactory(cellData -> cellData.getValue().classificationNameProperty());
        specificationColumn.setCellValueFactory(cellData -> cellData.getValue().specificationProperty());
        unitColumn.setCellValueFactory(cellData -> cellData.getValue().unitProperty());
        productionDateColumn.setCellValueFactory(cellData -> cellData.getValue().productionDateProperty());
        expirationDateColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getExpirationDate()));
        shelfLifeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().shelfLifeProperty().get() + "天"));

        actionColumn.setCellValueFactory(cellData -> {

            return new SimpleObjectProperty<>(new Hyperlink("报废"));
        });

    }

    public void print(final Node node) {

        PrinterJob job = PrinterJob.createPrinterJob();
        ConsoleLog.print(Printer.getDefaultPrinter().getName());
        if (job != null && job.showPrintDialog(node.getScene().getWindow())) {
            boolean success = job.printPage(node);
            if (success) {
                job.endJob();
            }
        }
    }


    private CellFactorySetupCallbackProtocol<ShelfLifeCommodity, Hyperlink> actionColumnSetupCallback = (column) -> new HyperlinkTableCell() {
        @Override
        protected void updateItem(Hyperlink item, boolean empty) {
            super.updateItem(item, empty);

            setAlignment(Pos.CENTER);

            // Check empty first
            if (!empty) {
                item.setOnAction(event -> {
                    ShelfLifeCommodity commodity = (ShelfLifeCommodity) getTableRow().getItem();
                    ConsoleLog.print("" + commodity.getName());

                    try {
                        // Load view
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(ViewPathHelper.class.getResource("warehouse/RejectCommodityView.fxml"));
                        AnchorPane pane = loader.load();

                        // Create the popup Stage.
                        Stage stage = new Stage();
                        stage.setTitle("报废商品");
                        stage.initModality(Modality.APPLICATION_MODAL);

                        Scene scene = new Scene(pane);
                        stage.setScene(scene);

                        // Pass the info into the controller.
                        StageSetableController controller = loader.getController();
                        ((RejectCommodityController) controller).set(commodity);
                        controller.setStage(stage);


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
