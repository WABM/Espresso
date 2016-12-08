package io.wabm.supermarket.controller.procurement;

import io.wabm.supermarket.controller.SceneController;
import io.wabm.supermarket.misc.javafx.tablecell.HyperlinkTableCell;
import io.wabm.supermarket.misc.pojo.Order;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.model.procurement.CommodityOrderModel;
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


/**
 * Created by liu on 2016-10-25 .
 */
public class CommodityOrderManagementController extends SceneController {

    private CommodityOrderModel<Order> model;

    @FXML TableView<Order> tableView;
    @FXML TableColumn<Order, Integer> idColumn;
    @FXML TableColumn<Order, String> supplierNameColumn;
    @FXML TableColumn<Order, String> create_timestampColumn;
    @FXML TableColumn<Order, Integer> statusColumn;
    @FXML TableColumn<Order, Hyperlink> actionColumn;

    @FXML Button unpaymentButton;
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
        statusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty().asObject());
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

                    orderDetailController.fetchWith(order.getOrderID());

                });
            }
        }
    };

//    @FXML private void unpaymentPressed(){
//        ConsoleLog.print("Button pressed");
//        try{
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(ViewPathHelper.class.getResource("management/Add employee.fxml"));
//            AnchorPane pane=loader.load();
//
//            Stage stage = new Stage();
//            stage.setTitle("待审核订单");
//            stage.initModality(Modality.APPLICATION_MODAL);
//
//            Scene scene=new Scene(pane);
//            stage.setScene(scene);
//
//            StageSetableController contoller=loader.getController();
//            contoller.setStage(stage);
//
//            stage.showAndWait();
//        }catch(IOException e){
//            e.printStackTrace();
//        }
//    }
//    @FXML private void waitButtonPressed(){
//        ConsoleLog.print("Button pressed");
//        try{
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(ViewPathHelper.class.getResource("management/Modify employee.fxml"));
//            AnchorPane pane=loader.load();
//
//            Stage stage = new Stage();
//            stage.setTitle("待收货订单");
//            stage.initModality(Modality.APPLICATION_MODAL);
//
//            Scene scene=new Scene(pane);
//            stage.setScene(scene);
//
//            StageSetableController contoller=loader.getController();
//            contoller.setStage(stage);
//
//            stage.showAndWait();
//        }catch(IOException e){
//            e.printStackTrace();
//        }
//    }
//    @FXML private void completeButtonPressed(){
//        ConsoleLog.print("Button pressed");
//        try{
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(ViewPathHelper.class.getResource("management/Query employee information.fxml"));
//            AnchorPane pane=loader.load();
//
//            Stage stage = new Stage();
//            stage.setTitle("已完成订单");
//            stage.initModality(Modality.APPLICATION_MODAL);
//
//            Scene scene=new Scene(pane);
//            stage.setScene(scene);
//
//            StageSetableController contoller=loader.getController();
//            contoller.setStage(stage);
//
//            stage.showAndWait();
//        }catch(IOException e){
//            e.printStackTrace();
//        }
//    }
}
