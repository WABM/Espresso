package io.wabm.supermarket.controller.procurement;


import io.wabm.supermarket.controller.SceneController;
import io.wabm.supermarket.misc.javafx.alert.SimpleErrorAlert;
import io.wabm.supermarket.misc.javafx.alert.SimpleSuccessAlert;
import io.wabm.supermarket.misc.pojo.Commodity;
import io.wabm.supermarket.misc.pojo.Supplier;
import io.wabm.supermarket.misc.pojo.SupplyGoods;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.model.procurement.SupplyGoodsModel;
import io.wabm.supermarket.protocol.CallbackAcceptableProtocol;
import io.wabm.supermarket.protocol.StageSetableController;
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

/* Created by 14580 on 2016/12/3 0003.
 */

public class SupplyGoodsController  extends SceneController {

    private SupplyGoodsModel<SupplyGoods> model;
    @FXML
    private TableView<SupplyGoods> tableView;

    @FXML
    private TableColumn<SupplyGoods, String> commodityIDColumn;

    @FXML
    private TableColumn<SupplyGoods, String> commodityNameColumn;
    @FXML
    private TableColumn<SupplyGoods, String> delivery_time_costColumn;
    @FXML
    private TableColumn<SupplyGoods, Double> price_dbColumn;

    @FXML
    Button backButton;
    @FXML
    Button addButton;
    @FXML
    Button deleteButton;
    @FXML
    Button modifyButton;
    @FXML
    Button searchButton;

    @FXML
    public void initialize() {
        ConsoleLog.print("SupplyGoodsController init");

        setupControl();
        setupModel();
        setupTableView();
        setupTableViewColumn();
    }

    @FXML
    private void backButtonPressed() {
        ConsoleLog.print("button pressed");

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ViewPathHelper.class.getResource("procurement/CommoditySupplierManagementView.fxml"));

        navigationTo(loader);
    }

    private void setupControl() {
    }

    private void setupModel() {
        model = new SupplyGoodsModel<>(tableView);
    }

    private void setupTableView() {
    }

    private void setupTableViewColumn() {
        commodityIDColumn.setCellValueFactory(cellData -> cellData.getValue().commodityIDProperty());
        commodityNameColumn.setCellValueFactory(cellData -> cellData.getValue().commodityNameProperty());
        price_dbColumn.setCellValueFactory(cellData -> cellData.getValue().price_dbProperty().asObject());
        delivery_time_costColumn.setCellValueFactory(cellData -> cellData.getValue().delivery_time_costProperty());

    }

    public void fetchWith(int SupplierID) {

        this.model.fetchData(SupplierID,
                (isSuccess) -> {
                    ConsoleLog.print("Fetch is " + (isSuccess ? "success" : "failed"));
                    return null;
                }
        );
    }

   @FXML private void setAddButtonPressed() {
        ConsoleLog.print("button pressed");

        try {
            // Load view
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ViewPathHelper.class.getResource("procurement/NewSupplierDetail.fxml"));
            AnchorPane pane = loader.load();

            // Create the popup Stage.
            Stage stage = new Stage();
            stage.setTitle("添加商品");
            stage.initModality(Modality.APPLICATION_MODAL);

            Scene scene = new Scene(pane);
            stage.setScene(scene);

            // Pass the info into the controller.
            StageSetableController controller = loader.getController();
            controller.setStage(stage);

            ((CallbackAcceptableProtocol<SupplyGoods, DataAccessException>) controller).set((supplyGoods) -> {
                ConsoleLog.print("Add supplyGoods callback called");
                final DataAccessException[] e = {null};

                model.add(supplyGoods, (exception) -> {
                    e[0] = exception;
                    if (null != exception) {
                        exception.printStackTrace();
                    } else {
                        ConsoleLog.print("Insert supplyGoods success");
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
    @FXML private void setRemoveButtonPressed() {
        ConsoleLog.print("button pressed");

        SupplyGoods supplyGoods = tableView.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("删除供应商品");
        alert.setHeaderText("确认删除");
        alert.setContentText("删除 " + supplyGoods.getCommodityName());

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            ConsoleLog.print("Delete " + supplyGoods.getCommodityName() + " …");

            model.delete(supplyGoods, exception -> {
                if (null == exception) {
                    new SimpleSuccessAlert("移除成功", "", supplyGoods.getCommodityName() + " 移除成功").show();
                } else {
                    new SimpleErrorAlert("移除失败", "删除数据遇到了错误", "请重试").show();
                }
                return null;
            });
        } else {
            ConsoleLog.print("Delete process cancel");
        }
    }


}
