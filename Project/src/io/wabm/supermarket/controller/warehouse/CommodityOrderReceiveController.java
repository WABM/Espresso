package io.wabm.supermarket.controller.warehouse;

import io.wabm.supermarket.controller.procurement.OrderDetailController;
import io.wabm.supermarket.misc.javafx.tablecell.HyperlinkTableCell;
import io.wabm.supermarket.misc.pojo.Order;
import io.wabm.supermarket.model.warehouse.TransportingOrderModel;
import io.wabm.supermarket.protocol.CellFactorySetupCallbackProtocol;
import io.wabm.supermarket.protocol.StageSetableController;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.view.ViewPathHelper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

/**
 * Created by MainasuK on 2016-10-25.
 */
public class CommodityOrderReceiveController implements StageSetableController {

    private TransportingOrderModel<Order> model;

    @FXML TableView<Order> tableView;
    @FXML TableColumn<Order, Integer> idColumn;
    @FXML TableColumn<Order, String> supplierColumn;
    @FXML TableColumn<Order, String> createTimeColumn;
    @FXML TableColumn<Order, Hyperlink> actionColumn;

    @FXML Stage stage;

    @FXML Button closeButton;

    @FXML private void closeButtonPressed() {
        ConsoleLog.print("Button pressed");

        stage.close();
    }

    @FXML private void initialize() {
        ConsoleLog.print("CommodityOrderReceiveController init");

        setupModel();
        setupTableView();
        setupTableColumn();
    }

    private void setupModel() {
        model = new TransportingOrderModel<>(tableView);

        model.fetchData(isSuccess -> {

            return null;
        });
    }

    private void setupTableView() {

    }

    private void setupTableColumn() {
        actionColumn.setCellFactory(actionColumnSetupCallback);

        idColumn.setCellValueFactory(cellData -> cellData.getValue().orderIDProperty().asObject());
        supplierColumn.setCellValueFactory(cellData -> cellData.getValue().supplierNameProperty());
        createTimeColumn.setCellValueFactory(cellData -> cellData.getValue().create_timestampProperty());
        actionColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(new Hyperlink("查看")));
    }


    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private CellFactorySetupCallbackProtocol<Order,Hyperlink> actionColumnSetupCallback = (column) -> new HyperlinkTableCell() {
        @Override
        protected void updateItem(Hyperlink item, boolean empty) {
            super.updateItem(item, empty);

            setAlignment(Pos.CENTER);

            if(!empty){
                item.setOnAction(event -> {
                    Order order = (Order) getTableRow().getItem();
                    ConsoleLog.print("" + order.getOrderID());

//                    FXMLLoader loader = new FXMLLoader();
//                    loader.setLocation(ViewPathHelper.class.getResource("procurement/OrderDetailView.fxml"));
//
//                    navigationTo(loader,controller -> {
//
//                        if (orderDetailController == null){
//                            ConsoleLog.print("Bind controller success");
//                            orderDetailController = ((OrderDetailController)controller);
//                        }
//                        return null;
//                    });
//
//                    orderDetailController.fetchWith(order.getOrderID());

                });
            }
        }
    };

}
