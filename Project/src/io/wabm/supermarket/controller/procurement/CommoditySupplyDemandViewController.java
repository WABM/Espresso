package io.wabm.supermarket.controller.procurement;

import io.wabm.supermarket.controller.SceneController;
import io.wabm.supermarket.misc.javafx.tablecell.HyperlinkTableCell;
import io.wabm.supermarket.misc.pojo.CommoditySupplyDemand;
import io.wabm.supermarket.misc.pojo.Supplier;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.model.procurement.CommoditySupplyDemandModel;
import io.wabm.supermarket.protocol.CellFactorySetupCallbackProtocol;
import io.wabm.supermarket.view.ViewPathHelper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by 14580 on 2016/11/20 0020.
 */
public class CommoditySupplyDemandViewController extends SceneController {

    private CommoditySupplyDemandModel<CommoditySupplyDemand> model;
    private Supplier supplier;

    @FXML
    TableView<CommoditySupplyDemand> tableView;

    @FXML
    TableColumn<CommoditySupplyDemand, String> commodityidColumn;
    @FXML
    TableColumn<CommoditySupplyDemand, String> barcodeColumn;
    @FXML
    TableColumn<CommoditySupplyDemand, String> commoditynameColumn;
    @FXML
    TableColumn<CommoditySupplyDemand, String> classificationColumn;
    @FXML
    TableColumn<CommoditySupplyDemand, String> specificationColumn;
    @FXML
    TableColumn<CommoditySupplyDemand, Integer> deliveryspecificationColumn;
    @FXML
    TableColumn<CommoditySupplyDemand, String> unitColumn;
    @FXML
    TableColumn<CommoditySupplyDemand, Integer> quantityColumn;
    @FXML
    TableColumn<CommoditySupplyDemand, Double> priceColumn;
    @FXML
    private TableColumn<CommoditySupplyDemand, Hyperlink> actionColumn;



    @FXML
    public void initialize() {
        ConsoleLog.print("CommoditySupplyDemandViewController init");

        setupControl();
        setupModel();
        setupTableView();
        setupTableViewColumn();

    }
    private void refetch() {
        model.fetchData(isSuccess -> {
            ConsoleLog.print("fetching…");
//            if (!isSuccess) {
//                new SimpleErrorAlert("数据获取失败", "未能成功订单数据", "请关闭并重新打开该窗口").show();
//            }
            return null;
        });
    }
    private void setupModel() {
        model = new CommoditySupplyDemandModel<>(tableView);
        model.fetchData(isSuccess -> {
            ConsoleLog.print("Fetch is " /*+ (isSuccess ? "success" : "failed")*/);
            return null;
        });
        refetch();
    }

    private void setupTableView() {
    }

    private void setupControl() {
    }

    private void setupTableViewColumn() {

        actionColumn.setCellFactory(actionColumnSetupCallback);

        commodityidColumn.setCellValueFactory(cellData -> cellData.getValue().commodityIDProperty());
        barcodeColumn.setCellValueFactory(cellData -> cellData.getValue().barcodeProperty());
        commoditynameColumn.setCellValueFactory(cellData -> cellData.getValue().commoditynameProperty());
        classificationColumn.setCellValueFactory(cellData -> cellData.getValue().classificationProperty());
        specificationColumn.setCellValueFactory(cellData -> cellData.getValue().specificationProperty());
        deliveryspecificationColumn.setCellValueFactory(cellData -> cellData.getValue().deliveryspecificationProperty().asObject());
        unitColumn.setCellValueFactory(cellData -> cellData.getValue().unitProperty());
        quantityColumn.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        actionColumn.setCellValueFactory(cellData -> {
            return new SimpleObjectProperty<>(new Hyperlink("选择供应商"));
        });
    }

    private CellFactorySetupCallbackProtocol<CommoditySupplyDemand, Hyperlink> actionColumnSetupCallback = (column) -> new HyperlinkTableCell() {
        @Override
        protected void updateItem(Hyperlink item, boolean empty) {
            super.updateItem(item, empty);

            setAlignment(Pos.CENTER);
            if (!empty){
                item.setOnAction(event -> {
                    CommoditySupplyDemand commoditySupplyDemand = (CommoditySupplyDemand) getTableRow().getItem();
                    ConsoleLog.print("pass value: " + commoditySupplyDemand.getCommodityID());

                    try {
                        // Load view
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(ViewPathHelper.class.getResource("procurement/SelectSupplierView.fxml"));
                        AnchorPane pane = loader.load();

                        // Create the popup Stage.
                        Stage stage = new Stage();
                        stage.setTitle("选择供应商");
                        stage.initModality(Modality.APPLICATION_MODAL);

                        Scene scene = new Scene(pane);
                        stage.setScene(scene);

                        // Pass the info into the controller.
                        SelectSupplierController controller = loader.getController();
                        controller.setStage(stage);
                        controller.setCommoditySupplyDemand(commoditySupplyDemand);
                        controller.setTableView(tableView);
                        controller.fetchWith(commoditySupplyDemand.getCommodityID());

                        // Show the dialog and wait until the user closes it.
                        // (This event thread is blocked until close)
                        stage.showAndWait();

                        refetch();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }); // end item.setOnAction(…)
            }   // end if (!empty) …
        }
    };



}
