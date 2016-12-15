package io.wabm.supermarket.controller.procurement;

import io.wabm.supermarket.controller.SceneController;
import io.wabm.supermarket.misc.javafx.alert.SimpleErrorAlert;
import io.wabm.supermarket.misc.javafx.alert.SimpleSuccessAlert;
import io.wabm.supermarket.misc.pojo.OrderDetail;
import io.wabm.supermarket.misc.pojo.Supplier;
import io.wabm.supermarket.misc.pojo.SupplyGoods;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.model.procurement.OrderDetailModel;
import io.wabm.supermarket.model.procurement.SupplyGoodsModel;
import io.wabm.supermarket.protocol.CallbackAcceptableProtocol;
import io.wabm.supermarket.view.ViewPathHelper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.dao.DataAccessException;

import java.io.IOException;
import java.util.Optional;

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
    @FXML
    private void setDeleteButtonPressed() {
        ConsoleLog.print("Button pressed");
        OrderDetail orderDetail = tableView.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("删除订单");
        alert.setHeaderText("确认删除");
        alert.setContentText("删除 " + orderDetail.getCommodityName());
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            ConsoleLog.print("Delete " + orderDetail.getCommodityName() + " …");

            model.delete(orderDetail, exception -> {
                if (null == exception) {
                    new SimpleSuccessAlert("删除成功", "", orderDetail.getOrderID() + " 删除成功").show();
                } else {
                    new SimpleErrorAlert("删除失败", "删除数据遇到了错误", "请重试").show();
                }
                return null;
            });
        } else {
            ConsoleLog.print("Delete process cancel");
        }

    }
    @FXML private void setModifyButtonPressed(){
//        ConsoleLog.print("Button pressed");
//        try{
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(ViewPathHelper.class.getResource("procurement/ModifyOrderDetail.fxml"));
//            AnchorPane pane=loader.load();
//
//            Stage stage = new Stage();
//            stage.setTitle("修改数量");
//            stage.initModality(Modality.APPLICATION_MODAL);
//
//            Scene scene=new Scene(pane);
//            stage.setScene(scene);
//
//            ModifySupplierController  controller = loader.getController();
//            controller.setStage(stage);
//
//            ((ModifyOrderDetailController) controller).setOer(tableView.getSelectionModel().getSelectedItem());
//            ((CallbackAcceptableProtocol<Supplier, DataAccessException>) controller).set((supplier) -> {
//                ConsoleLog.print("modify supplier callback called");
//                final DataAccessException[] e = {null};
//
//                model.update(orderDetail, (exception) -> {
//                    e[0] = exception;
//                    if (null != exception) {
//                        exception.printStackTrace();
//                    } else {
//                        tableView.refresh();
//                        ConsoleLog.print("update supplier success");
//                    }
//
//                    return null;
//                });
//
//                return e[0];
//            });
//            stage.showAndWait();
//        }catch(IOException e){
//            e.printStackTrace();
//        }
//
//
//
    }



}
