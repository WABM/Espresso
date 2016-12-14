package io.wabm.supermarket.controller.procurement;

import io.wabm.supermarket.controller.SceneController;
import io.wabm.supermarket.misc.javafx.tablecell.HyperlinkTableCell;
import io.wabm.supermarket.misc.pojo.Order;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.model.procurement.CommodityOrderModel;
import io.wabm.supermarket.protocol.CallbackAcceptableProtocol;
import io.wabm.supermarket.protocol.CellFactorySetupCallbackProtocol;
import io.wabm.supermarket.view.ViewPathHelper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;


/**
 * Created by liu on 2016-10-25 .
 */
public class CommodityOrderManagementController extends SceneController {

    private CommodityOrderModel<Order> model;
    Integer status;

    @FXML TableView<Order> tableView;
    @FXML TableColumn<Order, Integer> idColumn;
    @FXML TableColumn<Order, String> supplierNameColumn;
    @FXML TableColumn<Order, String> create_timestampColumn;
    @FXML TableColumn<Order, String> statusColumn;
    @FXML TableColumn<Order, Hyperlink> actionColumn;

    @FXML Button noncheckedButton;
    @FXML Button waitButton;
    @FXML Button completeButton;

    @FXML public void initialize() {
        ConsoleLog.print("CommodityOrderManagementViewController init");

        setupControl();
        setupModel();
        setupTableView();
        setupTableViewColumn();

    }

    private void setupTableView() {

    }

    private void setupModel() {
        model = new CommodityOrderModel<>(tableView);
        model.fetchData(isSuccess -> {
            ConsoleLog.print("Fetch is " + (isSuccess ? "success" : "failed"));
            return null;
        });
    }

    private void setupControl() {

    }


    private void setupTableViewColumn() {

        actionColumn.setCellFactory(actionColumnSetupCallback);

        idColumn.setCellValueFactory(cellData -> cellData.getValue().orderIDProperty().asObject());
        supplierNameColumn.setCellValueFactory(cellData -> cellData.getValue().supplierNameProperty());
        create_timestampColumn.setCellValueFactory(cellData -> cellData.getValue().create_timestampProperty());
        statusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty().asString());
        actionColumn.setCellValueFactory(cellData -> {

            return new SimpleObjectProperty<>(new Hyperlink("查看"));
        });
    }
    private OrderDetailController orderDetailController;

    private CellFactorySetupCallbackProtocol<Order,Hyperlink> actionColumnSetupCallback = (column) -> new HyperlinkTableCell() {
        @Override
        protected void updateItem(Hyperlink item, boolean empty) {
            super.updateItem(item, empty);

            setAlignment(Pos.CENTER);

            if(!empty){
                item.setOnAction(event -> {
                    Order order = (Order) getTableRow().getItem();
                    ConsoleLog.print("" + order.getOrderID());

                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(ViewPathHelper.class.getResource("procurement/OrderDetailView.fxml"));

                    navigationTo(loader,controller -> {

                        if (orderDetailController == null){
                            ConsoleLog.print("Bind controller success");
                            orderDetailController = ((OrderDetailController)controller);
                        }
                        return null;
                    });
                    ConsoleLog.print("" + order.getOrderID());

                    orderDetailController.fetchWith(order.getOrderID());

                });
            }
        }
    };

    @FXML private void noncheckedButtonPressed(){
        ConsoleLog.print("Button pressed");

        status = 1;
        model.Choose(status, isSuccess -> {
            ConsoleLog.print("Fetch is " + (isSuccess ? "success" : "failed"));
            return null;
        });

    }

   @FXML private void waitButtonPressed(){
        ConsoleLog.print("Button pressed");

        status = 2;
        model.Choose(status, isSuccess -> {
           ConsoleLog.print("Fetch is " + (isSuccess ? "success" : "failed"));
           return null;
       });
   }
    @FXML private void completeButtonPressed(){
        ConsoleLog.print("Button pressed");

        status = 3;
        model.Choose(status, isSuccess -> {
            ConsoleLog.print("Fetch is " + (isSuccess ? "success" : "failed"));
            return null;
        });
    }
}
