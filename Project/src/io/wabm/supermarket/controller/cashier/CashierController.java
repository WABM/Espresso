package io.wabm.supermarket.controller.cashier;

import io.wabm.supermarket.misc.pojo.SalesRecordDetail;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.model.cashier.CashierModel;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.text.Font;
import javafx.util.StringConverter;

import java.math.BigDecimal;
import java.util.EventListener;
import java.util.Objects;

/**
 * Created by MainasuK on 2016-12-2.
 */
public class CashierController {

    private CashierModel<SalesRecordDetail> model;

    @FXML Label statusLabel;

    @FXML TableView<SalesRecordDetail> tableView;
    @FXML TableColumn<SalesRecordDetail, Integer> noColumn;
    @FXML TableColumn<SalesRecordDetail, String> barCodeColumn;
    @FXML TableColumn<SalesRecordDetail, Integer> quantityColumn;
    @FXML TableColumn<SalesRecordDetail, String> nameColumn;
    @FXML TableColumn<SalesRecordDetail, String> specificationColumn;
    @FXML TableColumn<SalesRecordDetail, String> unitColumn;
    @FXML TableColumn<SalesRecordDetail, BigDecimal> salesPriceColumn;
    @FXML TableColumn<SalesRecordDetail, BigDecimal> priceSumColumn;

    @FXML Label commodityNameLabel;
    @FXML Label commodityPriceLabel;

    @FXML Label commodityCountLabel;
    @FXML Label totalPriceLabel;


    @FXML TextField barCodeTextField;


    @FXML private void initialize() {
        ConsoleLog.print("CashierController init");

        setupModel();
        setupTableColumn();
        setupControl();
    }


    private void setupModel() {
        model = new CashierModel<>(tableView);
    }

    private void setupTableColumn() {
        noColumn.setCellFactory(column -> new TableCell<SalesRecordDetail, Integer>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                if (!empty) {
                    setText("" + (getIndex() + 1));
                }

            }
        });
        quantityColumn.setCellFactory(column -> {
            TextFieldTableCell cell = new TextFieldTableCell<SalesRecordDetail, Integer>() {

                @Override
                public void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);

                    column.getTableView().getSelectionModel().select(getIndex());

                    // Handle total price label
                    model.resetTotalPrice();
                    Platform.runLater(() -> {
                        totalPriceLabel.setText("¥" + model.getTotalPrice());
                    });
                }
            };

            cell.setConverter(new StringConverter<Integer>() {
                @Override
                public String toString(Integer object) {
                    return object.toString();
                }

                @Override
                public Integer fromString(String string) {
                    return Integer.parseInt(string);
                }
            });

            return cell;
        });


        barCodeColumn.setCellValueFactory(cellData -> cellData.getValue().getCommodity().barcodeProperty());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().getCommodity().nameProperty());
        specificationColumn.setCellValueFactory(cellData -> cellData.getValue().getCommodity().specificationProperty());
        unitColumn.setCellValueFactory(cellData -> cellData.getValue().getCommodity().unitProperty());
        quantityColumn.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());
        salesPriceColumn.setCellValueFactory(cellData -> cellData.getValue().salesPriceProperty());
        priceSumColumn.setCellValueFactory(cellData -> cellData.getValue().totalPriceProperty());
    }

    private void setupControl() {

        statusLabel.textProperty().bind(model.statusProperty());

        // Set bar code input control
        barCodeTextField.setText("");
        // Handle text changes.
        setupBarCodeListener();

        // Release focus of tableView
        tableView.setFocusTraversable(false);
    }

    private void setupBarCodeListener() {
        barCodeTextField.textProperty().addListener((observable, oldValue, newValue) -> {

            newValue = newValue.trim();

            if (newValue == null || newValue.equals("")) {
                return ;
            }

            if (newValue.length() < 8) {
                return ;
            }

            // Try to fetch & add commodity in POS model
            String finalBarCode = newValue;
            model.addCommoditytWith(newValue, exception -> {
                ConsoleLog.print("finish add '" + finalBarCode + "' with exception:" + exception);

                if (exception == null) {
                    ConsoleLog.print("add success…");

                    String barCode = barCodeTextField.getText();
                    Platform.runLater(() -> {
                        // Handle barCode input control
                        barCodeTextField.setPromptText(barCode.trim());
                        barCodeTextField.setText("");

                        // Handle commodity name label
                        commodityNameLabel.setText(model.getCurrentCommodity().getName());

                        // Handle commodity price label
                        commodityPriceLabel.setText("¥" + model.getCurrentCommodity().getPrice().toPlainString());

                        // Handle commodity count label
                        commodityCountLabel.setText(model.getCommodityCount() + "");

                        // Handle total price label
                        totalPriceLabel.setText("¥" + model.getTotalPrice());
                    });

                }

                return null;
            });
        });
    }   // end setupBarCodeListener() { … }

}
