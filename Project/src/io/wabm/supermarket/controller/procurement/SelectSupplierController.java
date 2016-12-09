package io.wabm.supermarket.controller.procurement;

import io.wabm.supermarket.controller.SceneController;
import io.wabm.supermarket.misc.pojo.CommoditySupplyDemand;
import io.wabm.supermarket.misc.pojo.SelectSupplier;
import io.wabm.supermarket.misc.pojo.Supplier;
import io.wabm.supermarket.misc.pojo.SupplyGoods;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.model.procurement.CommoditySupplyDemandModel;
import io.wabm.supermarket.model.procurement.SelectSupplierModel;
import io.wabm.supermarket.model.procurement.SupplyGoodsModel;
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
    @FXML Stage stage;

    private SelectSupplierModel<SelectSupplier> model;
    @FXML private TableView<SelectSupplier> tableView;

    @FXML private TableColumn<SelectSupplier,Integer> supplieridColumn;
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

    private void setupControl() {}
    private void setupModel() {
        model = new SelectSupplierModel<>(tableView);
    }
    private void setupTableView() {}
    private void setupTableViewColumn() {
        supplieridColumn.setCellValueFactory(cellData -> cellData.getValue().supplieridProperty().asObject());
        supplierNameColumn.setCellValueFactory(cellData -> cellData.getValue().supplierNameProperty());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        deliveryTimeCostColumn.setCellValueFactory(cellData -> cellData.getValue().deliveryTimeCostProperty());
    }
    public void fetchWith(String commodityid) {

        this.model.fetchData(commodityid,
                (isSuccess) -> {
                    ConsoleLog.print("Fetch is " + (isSuccess ? "success" : "failed"));
                    return null;
                }
        );
    }
    @FXML private void selectButtonPressed(){}
    @FXML private void cancelButtonPressed () {
        ConsoleLog.print("Button pressed");
        stage.close();
    }
}
