package io.wabm.supermarket.controller.warehouse;

import io.wabm.supermarket.misc.pojo.Classification;
import io.wabm.supermarket.misc.pojo.Commodity;
import io.wabm.supermarket.misc.pojo.ShelfLifeCommodity;
import io.wabm.supermarket.misc.util.CalendarFormater;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.model.warehouse.CommodityShelfLifeModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.Timestamp;
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
    @FXML TableColumn<ShelfLifeCommodity, Integer> classificationColumn;        // FIXME: should be String type
    @FXML TableColumn<ShelfLifeCommodity, String> specificationColumn;
    @FXML TableColumn<ShelfLifeCommodity, String> unitColumn;
    @FXML TableColumn<ShelfLifeCommodity, String> productionDateColumn;
    @FXML TableColumn<ShelfLifeCommodity, String> expirationDateColumn;
    @FXML TableColumn<ShelfLifeCommodity, Integer> shelfLifeColumn;
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

        // Stub for develop
        int shelfLife = 10 * 30;
        Calendar today = new GregorianCalendar(TimeZone.getTimeZone("CST"));

        Calendar experationDate = ((Calendar) today.clone());
        experationDate.add(Calendar.DATE, 10 * 30);

        model.add(new ShelfLifeCommodity(100 + "", 0, "6902538006261", "脉动 青柠味", "1L", "瓶", 8.00, 12, shelfLife, 10, today, experationDate));
    }

    private void setupModel() {
        model = new CommodityShelfLifeModel<>(tableView);
    }

    private  void setupTableViewColumn() {
        // Setup cell value factory
        idColumn.setCellValueFactory(cellData -> cellData.getValue().commodityIDProperty());
        barcodeColumn.setCellValueFactory(cellData -> cellData.getValue().barcodeProperty());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        classificationColumn.setCellValueFactory(cellData -> cellData.getValue().classificationIDProperty().asObject());
        specificationColumn.setCellValueFactory(cellData -> cellData.getValue().specificationProperty());
        unitColumn.setCellValueFactory(cellData -> cellData.getValue().unitProperty());
        productionDateColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(CalendarFormater.toString(cellData.getValue().getProductionData()));

        });
        expirationDateColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(CalendarFormater.toString(cellData.getValue().getExpirationDate()));
        });
        explanationColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty("");
        });

        // TODO: actionColumn

    }
}
