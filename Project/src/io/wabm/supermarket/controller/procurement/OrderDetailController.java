package io.wabm.supermarket.controller.procurement;

import io.wabm.supermarket.controller.SceneController;
import io.wabm.supermarket.misc.pojo.OrderDetail;
import io.wabm.supermarket.misc.pojo.SupplyGoods;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.model.procurement.OrderDetailModel;
import io.wabm.supermarket.model.procurement.SupplyGoodsModel;
import io.wabm.supermarket.view.ViewPathHelper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Created by 14580 on 2016/12/7 0007.
 */
public class OrderDetailController extends SceneController {
    private OrderDetailModel<OrderDetail> model;
    @FXML private TableView<OrderDetail> tableView;
    @FXML private TableColumn<OrderDetail, Integer> order_idColumn;
    @FXML private TableColumn<OrderDetail, String> commodityNameColumn;
    @FXML private TableColumn<OrderDetail, Integer> quantityColumn;
    @FXML private TableColumn<OrderDetail, Double> price_dbColumn;
    @FXML private TableColumn<OrderDetail, String> production_dateColumn;

    @FXML Button backButton;
    @FXML Button deleteButton;
    @FXML Button modifyButton;

    @FXML public void initialize() {
        ConsoleLog.print("OrderDetailController init");

        setupControl();
        setupModel();
        setupTableView();
        setupTableViewColumn();
    }

    private void setupControl() {
    }

    private void setupModel() {
        model = new OrderDetailModel<>(tableView);
    }

    private void setupTableView() {
    }

    private void setupTableViewColumn() {
        order_idColumn.setCellValueFactory(cellData -> cellData.getValue().orderIDProperty().asObject());
        commodityNameColumn.setCellValueFactory(cellData -> cellData.getValue().commodityNameProperty());
        quantityColumn.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());
        price_dbColumn.setCellValueFactory(cellData -> cellData.getValue().price_dbProperty().asObject());
        production_dateColumn.setCellValueFactory(cellData -> cellData.getValue().production_dateProperty());

    }
    public void fetchWith(int orderID) {

        this.model.fetchData(orderID,
                (isSuccess) -> {
                    ConsoleLog.print("Fetch is " + (isSuccess ? "success" : "failed"));
                    return null;
                }
        );
    }

    @FXML
    private void backButtonPressed() {
        ConsoleLog.print("button pressed");

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ViewPathHelper.class.getResource("procurement/CommodityOrderManagementView.fxml"));

        navigationTo(loader);
    }


}
