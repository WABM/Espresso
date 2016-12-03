package io.wabm.supermarket.controller.procurement;

import io.wabm.supermarket.controller.SceneController;
import io.wabm.supermarket.controller.procurement.CommoditySupplierManagementViewController;
import io.wabm.supermarket.misc.javafx.alert.SimpleErrorAlert;
import io.wabm.supermarket.misc.javafx.alert.SimpleSuccessAlert;
import io.wabm.supermarket.misc.javafx.tablecell.HyperlinkTableCell;
import io.wabm.supermarket.misc.pojo.Supplier;
import io.wabm.supermarket.misc.pojo.Supplier;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.model.procurement.CommoditySupplierModel;
import io.wabm.supermarket.protocol.CallbackAcceptableProtocol;
import io.wabm.supermarket.protocol.CellFactorySetupCallbackProtocol;
import io.wabm.supermarket.protocol.StageSetableController;
import io.wabm.supermarket.view.ViewPathHelper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.dao.DataAccessException;

import java.io.IOException;
import java.util.Optional;

/**
 * Created by 14580 on 2016/11/20 0020.
 */
public class CommoditySupplierManagementViewController extends SceneController {

    private CommoditySupplierModel<Supplier> model;

    @FXML TableView<Supplier> tableView;
    @FXML TableColumn<Supplier, Integer> idColumn;
    @FXML TableColumn<Supplier, String> nameColumn;
    @FXML TableColumn<Supplier, String> representativeColumn;
    @FXML TableColumn<Supplier, String> phoneColumn;
    @FXML TableColumn<Supplier, String> addressColumn;
    @FXML private TableColumn<Supplier, Hyperlink> actionColumn;


    @FXML Button newButton;
    @FXML Button deleteButton;
    @FXML Button modifyButton;

    @FXML public void initialize() {
        ConsoleLog.print("CommoditySupplierManagementViewController init");

        setupControl();
        setupModel();
        setupTableView();
        setupTableViewColumn();

    }
    private void setupTableViewColumn() {
        idColumn.setCellValueFactory(cellData -> cellData.getValue().supplierIDProperty().asObject());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().supplierNameProperty());
        representativeColumn.setCellValueFactory(cellData -> cellData.getValue().linkmanProperty());

        phoneColumn.setCellValueFactory(cellData -> cellData.getValue().phoneProperty());
        addressColumn.setCellValueFactory(cellData -> cellData.getValue().addressProperty());
        actionColumn.setCellValueFactory(cellData -> {

            return new SimpleObjectProperty<>(new Hyperlink("查看"));
        });
    }
    private CellFactorySetupCallbackProtocol<Supplier, Hyperlink> actionColumnSetupCallback = (column) -> new HyperlinkTableCell() {
        @Override
        protected void updateItem(Hyperlink item, boolean empty) {
            super.updateItem(item, empty);

            setAlignment(Pos.CENTER);

            // Check empty first
            if (!empty) {
                item.setOnAction(event -> {
                    Supplier supplier = (Supplier) getTableRow().getItem();
                    ConsoleLog.print("" + supplier.getName());

                    FXMLLoader loder = new FXMLLoader();
                    loder.setLocation(ViewPathHelper.class.getResource("procurement/SupplyGoodsView.fxml"));

                    // Bind controller when you first call it.
                    navigationTo(loder, controller -> {

                       /* if (Supplier == null) {
                            ConsoleLog.print("Bind controller success");
                            Supplier = ((SupplyGoodsController) controller);
                        }*/

                        return null;
                    });

                    // Then dispatch the task to controller you hold.
                    //SupplyGoodsController.fetchWith(classification.getClassificationID());
                });
            }
        }
    };
    private void setupModel() {
        model = new CommoditySupplierModel<>(tableView);
        model.fetchData(isSuccess -> {
            ConsoleLog.print("Fetch is " + (isSuccess ? "success" : "failed"));
            return null;
        });
    }

    private void setupTableView() {
    }

    private void setupControl() {
    }


    @FXML private void newButtonPressed(){
        ConsoleLog.print("Button pressed");
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ViewPathHelper.class.getResource("procurement/NewSupplier.fxml"));
            AnchorPane pane=loader.load();

            Stage stage = new Stage();
            stage.setTitle("添加供应商");
            stage.initModality(Modality.APPLICATION_MODAL);

            Scene scene=new Scene(pane);
            stage.setScene(scene);

            StageSetableController controller=loader.getController();
            controller.setStage(stage);

            ((CallbackAcceptableProtocol<Supplier, DataAccessException>) controller).set((supplier) -> {
                ConsoleLog.print("Add supplier callback called");
                final DataAccessException[] e = {null};

                model.add(supplier, (exception) -> {
                    e[0] = exception;
                    if (null != exception) {
                        exception.printStackTrace();
                    } else {
                        ConsoleLog.print("Insert supplier success");
                    }

                    return null;
                });
                return e[0];
            });
            stage.showAndWait();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    @FXML private void deleteButtonPressed(){
        ConsoleLog.print("Button pressed");
        Supplier supplier = tableView.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("删除供应商");
        alert.setHeaderText("确认删除");
        alert.setContentText("删除 " + supplier.getName());
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            ConsoleLog.print("Delete " + supplier.getName() + " …");

            model.delete(supplier, exception -> {
                if (null == exception) {
                    new SimpleSuccessAlert("删除成功", "", supplier.getName() + " 删除成功").show();
                } else {
                    new SimpleErrorAlert("删除失败", "删除数据遇到了错误", "请重试").show();
                }
                return null;
            });
        } else {
            ConsoleLog.print("Delete process cancel");
        }
    }
    @FXML private void modifyButtonPressed(){
        ConsoleLog.print("Button pressed");
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ViewPathHelper.class.getResource("procurement/ModifySupplier.fxml"));
            AnchorPane pane=loader.load();

            Stage stage = new Stage();
            stage.setTitle("我不知道写什么好");
            stage.initModality(Modality.APPLICATION_MODAL);

            Scene scene=new Scene(pane);
            stage.setScene(scene);

            StageSetableController contoller=loader.getController();
            contoller.setStage(stage);

            stage.showAndWait();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
}
