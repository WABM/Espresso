package io.wabm.supermarket.controller.cashier;

import io.wabm.supermarket.application.PrimaryStage;
import io.wabm.supermarket.misc.pojo.SalesRecordDetail;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.misc.util.SingleLogin;
import io.wabm.supermarket.model.cashier.CashierModel;
import io.wabm.supermarket.protocol.StageSetableController;
import io.wabm.supermarket.view.ViewPathHelper;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.StringConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Optional;

/**
 * Created by MainasuK on 2016-12-2.
 */
public class CashierController implements StageSetableController {
    private Stage primaryStage;

    private DecimalFormat decimalFormat = new DecimalFormat("#0.00");
    private CashierModel<SalesRecordDetail> model;
    @FXML Pane pane;

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

    @FXML Label nameLabel;
    @FXML Label dateLabel;

    @FXML Label commodityNameLabel;
    @FXML Label commodityPriceLabel;

    @FXML Label actualPayLabel;
    @FXML Label changeLabel;

    @FXML Label commodityCountLabel;
    @FXML Label totalPriceLabel;


    @FXML TextField barCodeTextField;


    @FXML private void initialize() {
        ConsoleLog.print("CashierController init");
        setupModel();
        setupTableColumn();
        setupControl();
        setupShortcut();
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
                } else {
                    setText("");
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
                        ConsoleLog.print("Reset order info label…");
                        resetControl();
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
                    try {
                        int quantity = Integer.parseInt(string);

                        if (quantity < 0) {
                            return 0;
                        }

                        return quantity;
                    } catch (NumberFormatException exception) {
                        return 0;
                    }
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

        if (SingleLogin.getInstance().getEmployee() != null) {
            nameLabel.setText(SingleLogin.getInstance().getEmployee().getName());
        } else {
            nameLabel.setText("未登录用户");
        }

        dateLabel.setText(LocalDate.now().toString());

        statusLabel.textProperty().bind(model.statusProperty());

        // Set bar code input control
        barCodeTextField.setPromptText("");
        barCodeTextField.setText("");

        // Release focus of tableView
        tableView.setFocusTraversable(false);
    }

    private void resetControl() {
        ConsoleLog.print("Resetting…");

        if (model.getTotalPrice().doubleValue() == 0.0) {
            totalPriceLabel.setText("¥" + "0.00");
        } else {
            totalPriceLabel.setText("¥" + model.getTotalPrice());
        }

        commodityCountLabel.setText(model.getCommodityCount() + "");

        if (model.getCurrentCommodity() == null) {
            commodityNameLabel.setText("—");
            commodityPriceLabel.setText("¥0.00");
        } else {
            commodityNameLabel.setText(model.getCurrentCommodity().getName());
            commodityPriceLabel.setText("¥" + model.getCurrentCommodity().getPrice());
        }
    }

    private void setupShortcut() {
        barCodeTextField.setOnKeyPressed(event -> {
            ConsoleLog.print("Key " + event.getCode() + " pressed in barCodeTextFiled");

            triggerActionWithKey(event.getCode());
            if (event.getCode() == KeyCode.ENTER) {
                triggerActionWithEnter();
            }
        });

        pane.setOnKeyPressed(event -> {
            ConsoleLog.print("Key " + event.getCode() + " pressed in scene");

            triggerActionWithKey(event.getCode());
        });
    }

    private void triggerActionWithEnter() {
        fetchCommodityWith(barCodeTextField.getText().trim());
    }

    private void triggerActionWithKey(KeyCode keyCode) {
        switch (keyCode) {
            case F1:
                barCodeTextField.requestFocus();
                break;
            case F2:
                barCodeTextField.setText("");
                break;
            case F5:
                if (model.getTotalPrice().doubleValue() > 0.0) {
                    presentPayView();
                }
                break;
            case F9:
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("确认删单");
                alert.setHeaderText("是否删单");
                alert.setContentText("按 回车键 删除\n按 ESC 取消");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    ConsoleLog.print("Delete order");

                    model.clear();

                    ConsoleLog.print("Reset bar code label");
                    barCodeTextField.setPromptText("");
                    barCodeTextField.setText("");

                    // resetControl(…) will be called by quantityColumn listener

                } else {
                    ConsoleLog.print("Delete process cancel");
                }
                break;
        }
    }

    /**
     * Fetch commodity with commodity_id or bar_code
     * @param text
     */
    private void fetchCommodityWith(String text) {
        if (text == null || text.equals("")) {
            return ;
        }

        // Press ENTER for length < 13 case


//        if (text.length() < 8) {
//            return ;
//        }


        // Try to fetch & add commodity in POS model
        String finalBarCode = text;
        model.addCommoditytWith(text, exception -> {
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
    }

    private void presentPayView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ViewPathHelper.class.getResource("cashier/PayView.fxml"));
            AnchorPane pane = loader.load();

            // Create the popup Stage.
            Stage stage = new Stage();
            stage.setTitle("结算商品");
            stage.initModality(Modality.APPLICATION_MODAL);

            Scene scene = new Scene(pane);
            stage.setScene(scene);


            ((StageSetableController) loader.getController()).setStage(stage);
            ((PayController) loader.getController()).setShouldPay(model.getTotalPrice().doubleValue());
            ((PayController) loader.getController()).setCallback(pay -> {

                DataAccessException result = model.pay();

                if (result == null) {
                    Platform.runLater(() -> {
                        actualPayLabel.setText("¥" + decimalFormat.format(pay));
                        changeLabel.setText("¥" + decimalFormat.format(pay - model.getTotalPrice().doubleValue()));
                        model.clear();
                        resetControl();
                    });
                }

                return result;
            });

            stage.show();

            stage.setMinWidth(stage.getWidth());
            stage.setMinHeight(stage.getHeight());
            stage.setMaxHeight(stage.getHeight());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setStage(Stage stage) {
        ConsoleLog.print("get stage");
        primaryStage = stage;

        Platform.runLater(() -> {
            setupShortcut();
        });
    }
}
