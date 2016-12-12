package io.wabm.supermarket.controller.warehouse;

import io.wabm.supermarket.misc.javafx.alert.SimpleErrorAlert;
import io.wabm.supermarket.misc.javafx.tablecell.DatePickerCell;
import io.wabm.supermarket.misc.pojo.CMKOrderDetail;
import io.wabm.supermarket.misc.pojo.OrderDetail;
import io.wabm.supermarket.misc.pojo.PurchaseCommodity;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.model.warehouse.CommodityOrderReceiveDetailModel;
import io.wabm.supermarket.protocol.StageSetableController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
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
public class CommodityOrderReceiveDetailController implements StageSetableController {

    private CommodityOrderReceiveDetailModel<CMKOrderDetail> model;
    private int orderID;

    @FXML TableView<CMKOrderDetail> tableView;
    @FXML TableColumn<CMKOrderDetail, String> commodityIDColumn;
    @FXML TableColumn<CMKOrderDetail, String> commodityBarCodeColumn;
    @FXML TableColumn<CMKOrderDetail, String> commodityNameColumn;
    @FXML TableColumn<CMKOrderDetail, Integer> quantityColumn;
    @FXML TableColumn<CMKOrderDetail, Integer> actualQuntityColumn;
    @FXML TableColumn<CMKOrderDetail, BigDecimal> purchasePriceColumn;
    @FXML TableColumn<CMKOrderDetail, LocalDate> productionDateColumn;

    @FXML Stage stage;

    @FXML Button confirmButton;
    @FXML Button cancelButton;

    @FXML private void cancelButtonPressed() {
        ConsoleLog.print("button pressed");

        stage.close();
    }

    @FXML private void confirmButtonPressed() {
        ConsoleLog.print("button pressed");

        model.confirmOrderWith(orderID, exception -> {
            if (exception != null) {
                new SimpleErrorAlert("更新失败", "无法更新订单数据", "请检查网络状况并重试").show();
                return null;
            }

            Platform.runLater(() -> {
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
        model = new CommodityOrderReceiveDetailModel<>(tableView);
    }

    private void setupTableView() {
        tableView.setEditable(true);
    }

    private void setupTableColumn() {
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
        productionDateColumn.setCellFactory(param -> new DatePickerCell<CMKOrderDetail>(param));

        commodityIDColumn.setCellValueFactory(param -> param.getValue().commodityIDProperty());
        commodityBarCodeColumn.setCellValueFactory(param -> param.getValue().barcodeProperty());
        commodityNameColumn.setCellValueFactory(param -> param.getValue().nameProperty());
        quantityColumn.setCellValueFactory(param -> param.getValue().quantityProperty().asObject());
        purchasePriceColumn.setCellValueFactory(param -> param.getValue().purchasePriceProperty());
        actualQuntityColumn.setCellValueFactory(param -> param.getValue().actualQuantityProperty().asObject());
        productionDateColumn.setCellValueFactory(param -> param.getValue().productionDateProperty());

    }

    private void fetch() {
        model.fetchOrderDetailWith(orderID, exception -> {

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

    public void setOrderID(int orderID) {
        this.orderID = orderID;

        fetch();
    }
}
