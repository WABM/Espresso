package io.wabm.supermarket.controller.procurement;

import io.wabm.supermarket.controller.SceneController;
import io.wabm.supermarket.misc.javafx.alert.SimpleErrorAlert;
import io.wabm.supermarket.misc.pojo.*;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.model.procurement.*;
import io.wabm.supermarket.protocol.CallbackAcceptableProtocol;
import io.wabm.supermarket.protocol.StageSetableController;
import io.wabm.supermarket.view.ViewPathHelper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.springframework.dao.DataAccessException;

/**
 * Created by 14580 on 2016/12/9 0009.
 */
public class SelectSupplierController implements StageSetableController,CallbackAcceptableProtocol<CommoditySupplyDemand, DataAccessException> {
    private Callback<CommoditySupplyDemand, DataAccessException> callback = null;
    private SelectSupplierModel<SelectSupplier> model;

    private CommodityOrderModel<Order> commodityOrderModel;
    private OrderDetailModel<OrderDetail> orderDetailModel;
    private CommoditySupplyDemandModel<CommoditySupplyDemand> commoditySupplyDemandModel;
    private CommoditySupplyDemand commoditySupplyDemand;
    private Supplier supplier;
    private TableView supplierTableView;


    @FXML Stage stage;
    @FXML private TableView<SelectSupplier> tableView;
    @FXML private TableColumn<SelectSupplier,String> commodityIDColumn;
    @FXML private TableColumn<SelectSupplier,Integer> supplierIDColumn;
    @FXML private TableColumn<SelectSupplier, String> supplierNameColumn;
    @FXML private TableColumn<SelectSupplier, Double> priceColumn;
    @FXML private TableColumn<SelectSupplier,String> deliveryTimeCostColumn;

    @FXML Button cancelButton;
    @FXML Button selectButton;

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void set (Callback<CommoditySupplyDemand, DataAccessException > callback){
        this.callback = callback;
    }

    @FXML
    public void initialize() {
        ConsoleLog.print("SelectSupplierController init");

        setupControl();
        setupModel();
        setupTableView();
        setupTableViewColumn();
    }

    private void setupControl() {
        selectButton.setDisable(true);
    }

    private void setupModel() {

        model = new SelectSupplierModel<>(tableView);

    }

    private void setupTableView() {
        tableView.setEditable(true);
        tableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selectButton.setDisable(newValue == null)
        );
    }

    private void setupTableViewColumn() {
        commodityIDColumn.setCellValueFactory(cellData -> cellData.getValue().commodityidProperty());
        supplierIDColumn.setCellValueFactory(cellData -> cellData.getValue().supplieridProperty().asObject());
        supplierNameColumn.setCellValueFactory(cellData -> cellData.getValue().supplierNameProperty());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        deliveryTimeCostColumn.setCellValueFactory(cellData -> cellData.getValue().deliveryTimeCostProperty());
    }

    public void fetchWith(String commodityID) {

        this.model.fetchData(commodityID,
                (isSuccess) -> {
                    ConsoleLog.print("Fetch is " + (isSuccess ? "success" : "failed"));
                    return null;
                }
        );
    }
    public void setCommoditySupplyDemand(CommoditySupplyDemand commoditySupplyDemand){
        this.commoditySupplyDemand = commoditySupplyDemand;
    }
    public void setTableView(TableView tableView){
        this.supplierTableView = tableView;
    }


    @FXML private void setSelectButtonPressed(){

        SelectSupplier selectSupplier = tableView.getSelectionModel().getSelectedItem();
        //CommoditySupplyDemand commoditySupplyDemand = tableView.getSelectionModel().getSelectedItem();
        ConsoleLog.print("Get select SUPPLIERID1 " + selectSupplier.getSupplierid());

        commodityOrderModel = new CommodityOrderModel<Order>(supplierTableView);
        commoditySupplyDemandModel = new CommoditySupplyDemandModel<CommoditySupplyDemand>(supplierTableView);

        ConsoleLog.print("Get select SUPPLIERID2 " + selectSupplier.getSupplierid());

        commodityOrderModel.CreateOrder(String.valueOf(selectSupplier.getSupplierid()),selectSupplier.getCommodityid(),commoditySupplyDemand.getQuantity(),selectSupplier.getPrice(),
                isSuccess -> {
                    ConsoleLog.print("Fetch is " + (isSuccess ? "success" : "failed"));
                    return null;
                });

        ConsoleLog.print("Get select SUPPLIERID3 " + selectSupplier.getSupplierid());
        commoditySupplyDemandModel.fetchData(isSuccess -> {
            ConsoleLog.print("Fetch is " + (isSuccess ? "success" : "failed"));
            return null;
        });

        ConsoleLog.print("Get select SUPPLIERID5 " + selectSupplier.getSupplierid());
        stage.close();
    }

    @FXML private void setCancelButtonPressed () {
        ConsoleLog.print("Button pressed");
        stage.close();
    }
}
