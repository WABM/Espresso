package io.wabm.supermarket.controller.cashier;

import io.wabm.supermarket.misc.pojo.Commodity;
import io.wabm.supermarket.misc.pojo.SalesRecordDetail;
import io.wabm.supermarket.misc.pojo.TransactionRecordDetail;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.model.cashier.CashierModel;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.awt.event.KeyEvent;
import java.math.BigDecimal;

/**
 * Created by MainasuK on 2016-12-2.
 */
public class CashierController {

    private CashierModel<SalesRecordDetail> model;

    @FXML TableView<SalesRecordDetail> tableView;
    @FXML TableColumn<SalesRecordDetail, Integer> noColumn;
    @FXML TableColumn<SalesRecordDetail, String> barCodeColumn;
    @FXML TableColumn<SalesRecordDetail, Integer> quantityColumn;
    @FXML TableColumn<SalesRecordDetail, String> nameColumn;
    @FXML TableColumn<SalesRecordDetail, String> specificationColumn;
    @FXML TableColumn<SalesRecordDetail, String> unitColumn;
    @FXML TableColumn<SalesRecordDetail, BigDecimal> salesPriceColumn;
    @FXML TableColumn<SalesRecordDetail, String> priceSumColumn;


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

        barCodeColumn.setCellValueFactory(cellData -> cellData.getValue().getCommodity().barcodeProperty());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().getCommodity().nameProperty());
        specificationColumn.setCellValueFactory(cellData -> cellData.getValue().getCommodity().specificationProperty());
        unitColumn.setCellValueFactory(cellData -> cellData.getValue().getCommodity().unitProperty());
        quantityColumn.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());
        salesPriceColumn.setCellValueFactory(cellData -> cellData.getValue().salesPriceProperty());
        priceSumColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTotalPrice()));
    }

    private void setupControl() {
        // Handle text changes.
        barCodeTextField.textProperty().addListener((observable, oldValue, newValue) -> {

            newValue = newValue.trim();
            if (newValue == null || newValue.equals("")) {
                return ;
            }

            // Try to fetch & add commodity in POS model
            model.addCommoditytWith(newValue, exception -> {
                ConsoleLog.print("finish add with exception:" + exception);

                if (exception == null) {
                    String barCode = barCodeTextField.getText();
                    Platform.runLater(() -> {
                        barCodeTextField.setPromptText(barCode.trim());
                        barCodeTextField.setText("");
                    });

                }

                return null;
            });
        });
    }

}
