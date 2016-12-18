package io.wabm.supermarket.controller.warehouse;

import io.wabm.supermarket.misc.javafx.alert.SimpleErrorAlert;
import io.wabm.supermarket.misc.javafx.tablecell.DatePickerCell;
import io.wabm.supermarket.misc.pojo.CMKInventoryDetail;
import io.wabm.supermarket.misc.pojo.CMKOrderDetail;
import io.wabm.supermarket.misc.pojo.PurchaseCommodity;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.model.warehouse.CommodityInventoryDetailModel;
import io.wabm.supermarket.model.warehouse.CommodityOrderReceiveDetailModel;
import io.wabm.supermarket.protocol.StageSetableController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Created by MainasuK on 2016-12-12.
 */
public class CommodityInventoryDetailController implements StageSetableController {

    private CommodityInventoryDetailModel<CMKInventoryDetail> model;
    private int classificationID;
    private int inventoryID;
    private boolean stocktakingflag;

    @FXML TableView<CMKInventoryDetail> tableView;
    @FXML TableColumn<CMKInventoryDetail, String> idColumn;
    @FXML TableColumn<CMKInventoryDetail, String> barCodeColumn;
    @FXML TableColumn<CMKInventoryDetail, String> nameColumn;
    @FXML TableColumn<CMKInventoryDetail, String> specificationColumn;
    @FXML TableColumn<CMKInventoryDetail, Integer> quantityColumn;
    @FXML TableColumn<CMKInventoryDetail, Integer> actualQuntityColumn;
    @FXML TableColumn<CMKInventoryDetail, Integer> resultColumn;

    @FXML Stage stage;

    @FXML Button confirmButton;
    @FXML Button cancelButton;

    @FXML private void cancelButtonPressed() {
        ConsoleLog.print("button pressed");

        stage.close();
    }

    @FXML private void confirmButtonPressed() {
        ConsoleLog.print("button pressed");

        if (!stocktakingflag) {
            stage.close();
            return ;
        }

        confirmButton.setDisable(true);
        model.stocktakingOf(inventoryID, classificationID, exception -> {
            confirmButton.setDisable(false);
            if (exception != null) {
                new SimpleErrorAlert("更新失败", "无法更新订单数据", "请检查网络状况并重试").show();
                return null;
            }

            Platform.runLater(() -> {
                ConsoleLog.print("close stage");
                stage.close();
            });

            return null;
        });
    }

    @FXML private void initialize() {
        ConsoleLog.print("CommodityOrderReceiveDetailController init");

        setupModel();
        setupTableView();
        setupTableColumn();
    }

    private void setupModel() {
        model = new CommodityInventoryDetailModel<>(tableView);
    }

    private void setupTableView() {
        tableView.setEditable(true);
    }

    private void setupTableColumn() {

        idColumn.setCellValueFactory(param -> param.getValue().commodityIDProperty());
        barCodeColumn.setCellValueFactory(param -> param.getValue().barcodeProperty());
        nameColumn.setCellValueFactory(param -> param.getValue().nameProperty());
        specificationColumn.setCellValueFactory(param -> param.getValue().specificationProperty());
        quantityColumn.setCellValueFactory(param -> param.getValue().storageProperty().asObject());
        actualQuntityColumn.setCellValueFactory(param -> param.getValue().actualQuantityProperty().asObject());
        resultColumn.setCellValueFactory(param -> param.getValue().resultProperty().asObject());

    }

    private void fetch() {
        model.fetchWith(classificationID, exception -> {

            if (exception != null) {
                exception.printStackTrace();
                return null;
            }

            return null;
        });
    }

    private void fetchInventory(int inventoryID) {
        model.fetchInventoryDetailWith(inventoryID, exception -> {
            if (exception != null) {
                exception.printStackTrace();
                return null;
            }

            return null;
        });
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setIDs(int classificationID, int inventoryID, boolean stocktakingflag) {
        this.classificationID = classificationID;
        this.inventoryID = inventoryID;
        this.stocktakingflag = stocktakingflag;

        if (!stocktakingflag) {
            ConsoleLog.print("change button visible…");

            cancelButton.setVisible(false);
            confirmButton.setText("关闭");

            fetchInventory(inventoryID);
        } else {
            actualQuntityColumn.setCellFactory(column -> {
                StringConverter<Integer> converter = new StringConverter<Integer>() {
                    @Override
                    public String toString(Integer object) {
                        return object.toString();
                    }

                    @Override
                    public Integer fromString(String string) {
                        try {
                            int quantity = Integer.parseInt(string);
                            return (quantity < 0) ? 0 : quantity;
                        } catch (NumberFormatException exception) {
                            return 0;
                        }
                    }
                };
                TextFieldTableCell cell = new TextFieldTableCell<PurchaseCommodity, Integer>(converter) {
                    @Override
                    public void updateItem(Integer item, boolean empty) {
                        super.updateItem(item, empty);
                    }
                };

                return cell;
            });

            fetch();
        }
        
    }
}
