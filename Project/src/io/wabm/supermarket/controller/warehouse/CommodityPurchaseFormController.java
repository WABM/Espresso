package io.wabm.supermarket.controller.warehouse;

import io.wabm.supermarket.misc.javafx.alert.SimpleErrorAlert;
import io.wabm.supermarket.misc.javafx.alert.SimpleSuccessAlert;
import io.wabm.supermarket.misc.pojo.CMKOrderDetail;
import io.wabm.supermarket.misc.pojo.PurchaseCommodity;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.misc.util.WABMThread;
import io.wabm.supermarket.model.warehouse.CommodityPurchaseFormModel;
import io.wabm.supermarket.protocol.StageSetableController;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.springframework.dao.DataAccessException;

/**
 * Created by MainasuK on 2016-10-24.
 */
public class CommodityPurchaseFormController implements StageSetableController {

    private CommodityPurchaseFormModel<PurchaseCommodity> model;

    @FXML Stage stage;

    @FXML TableView<PurchaseCommodity> tableView;
    @FXML TableColumn<PurchaseCommodity, Boolean> chooseColumn;
    @FXML TableColumn<PurchaseCommodity, String> idColumn;
    @FXML TableColumn<PurchaseCommodity, String> barCodeColumn;
    @FXML TableColumn<PurchaseCommodity, String> nameColumn;
    @FXML TableColumn<PurchaseCommodity, String> classificationColumn;
    @FXML TableColumn<PurchaseCommodity, String> specificationColumn;
    @FXML TableColumn<PurchaseCommodity, String> unitColumn;
    @FXML TableColumn<PurchaseCommodity, Integer> deliverySpecificationColumn;
    @FXML TableColumn<PurchaseCommodity, Integer> minStorageColumn;
    @FXML TableColumn<PurchaseCommodity, Integer> storageColumn;
    @FXML TableColumn<PurchaseCommodity, Integer> purchaseColumn;

    @FXML Button confirmButton;
    @FXML Button cancelButton;

    @FXML private void initialize() {
        ConsoleLog.print("CommodityPurchaseFormController init");

        setupModel();
        setupTableView();
        setupTableColumn();
    }

    private void setupModel() {
        model = new CommodityPurchaseFormModel<>(tableView);
        model.fetchNeedsProcuremetnCommodity(exception -> {
            if (exception != null) {
                ConsoleLog.print("fetching get error");
                return null;
            }

            return null;
        });
    }

    private void setupTableView() {
        tableView.setEditable(true);
    }

    private void setupTableColumn() {
        chooseColumn.setEditable(true);
        chooseColumn.setCellFactory(param -> new CheckBoxTableCell<>());
        purchaseColumn.setCellFactory(column -> {
            StringConverter<Integer> converter = new StringConverter<Integer>() {
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
            };
            TextFieldTableCell cell = new TextFieldTableCell<PurchaseCommodity, Integer>(converter) {

                @Override
                public void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
//                    setStyle("-fx-background-color: -fx-control-inner-background-alt");
                }
            };

            return cell;
        });

        // Setup cell value factory
        chooseColumn.setCellValueFactory(cellData -> {
            PurchaseCommodity commodity = cellData.getValue();
            BooleanProperty property = commodity.isChooseProperty();

            // Add listener for handler change
            property.addListener((observable, oldValue, newValue) -> commodity.setIsChoose(newValue));

            return property;
        });

        idColumn.setCellValueFactory(cellData -> cellData.getValue().commodityIDProperty());
        classificationColumn.setCellValueFactory(cellData -> cellData.getValue().classificationNameProperty());
        barCodeColumn.setCellValueFactory(cellData -> cellData.getValue().barcodeProperty());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        specificationColumn.setCellValueFactory(cellData -> cellData.getValue().specificationProperty());
        unitColumn.setCellValueFactory(cellData -> cellData.getValue().unitProperty());
        deliverySpecificationColumn.setCellValueFactory(cellData -> cellData.getValue().deliverySpecificationProperty().asObject());
        minStorageColumn.setCellValueFactory(cellData -> cellData.getValue().minStorage.asObject());
        storageColumn.setCellValueFactory(cellData -> cellData.getValue().storageProperty().asObject());
        purchaseColumn.setCellValueFactory(cellData -> cellData.getValue().purchaseNumProperty().asObject());


    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML private void confirmButtonPressed() {
        ConsoleLog.print("Button Pressed");

        confirmButton.setDisable(true);
        new WABMThread().run(_void -> {

            DataAccessException exception = model.addProcurements();

            Platform.runLater(() -> {
                confirmButton.setDisable(false);
                if (exception != null) {
                    ConsoleLog.print("get error: " + exception.getLocalizedMessage());
                    new SimpleErrorAlert("采购需求提交失败", "更新数据库出现了错误", "请稍后重试");
                } else {
                    ConsoleLog.print("add success");

                    new SimpleSuccessAlert("采购需求", "","成功").show();
                    stage.close();
                }
            }); // end runlater…

            return null;
        });
    }

    @FXML private void cancelButtonPressed() {
        ConsoleLog.print("Button Pressed");

        stage.close();
    }

}
