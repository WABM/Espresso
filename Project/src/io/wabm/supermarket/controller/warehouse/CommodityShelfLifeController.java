package io.wabm.supermarket.controller.warehouse;

import io.wabm.supermarket.misc.pojo.ShelfLifeCommodity;
import io.wabm.supermarket.misc.util.CalendarFormater;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.model.warehouse.CommodityShelfLifeModel;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

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
    @FXML TableColumn<ShelfLifeCommodity, String> explanationColumn;
    @FXML TableColumn<ShelfLifeCommodity, Hyperlink> actionColumn;


    @FXML Button printListButton;

    @FXML private void printListButtonPressed() {
        ConsoleLog.print("button pressed");
    }

    @FXML public void initialize() {
        ConsoleLog.print("CommodityShelfLifeController init");

        setupModel();
        setupTableViewColumn();
    }

    private void setupModel() {
        model = new CommodityShelfLifeModel<>(tableView);
        model.fetch(exception -> {
            ConsoleLog.print("fetch with exception: " + exception);

            return null;
        });
    }

    private  void setupTableViewColumn() {
        // Setup cell value factory
        idColumn.setCellValueFactory(cellData -> cellData.getValue().getCommodity().commodityIDProperty());
        barcodeColumn.setCellValueFactory(cellData -> cellData.getValue().getCommodity().barcodeProperty());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().getCommodity().nameProperty());
        classificationColumn.setCellValueFactory(cellData -> cellData.getValue().getCommodity().classificationNameProperty());
        specificationColumn.setCellValueFactory(cellData -> cellData.getValue().getCommodity().specificationProperty());
        unitColumn.setCellValueFactory(cellData -> cellData.getValue().getCommodity().unitProperty());
        productionDateColumn.setCellValueFactory(cellData -> cellData.getValue().productionDateProperty());
        expirationDateColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getExpirationDate()));

        explanationColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty("");
        });
        shelfLifeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCommodity().shelfLifeProperty().get() + "天"));

        // TODO: actionColumn

    }
}
