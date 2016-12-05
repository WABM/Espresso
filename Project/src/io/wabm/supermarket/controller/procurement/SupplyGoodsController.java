package io.wabm.supermarket.controller.procurement;

import io.wabm.supermarket.controller.SceneController;
import io.wabm.supermarket.controller.warehouse.AddCommodityController;
import io.wabm.supermarket.misc.javafx.alert.SimpleErrorAlert;
import io.wabm.supermarket.misc.javafx.alert.SimpleSuccessAlert;
import io.wabm.supermarket.misc.pojo.Classification;
import io.wabm.supermarket.misc.pojo.Commodity;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.model.warehouse.CommodityInformationModel;
import io.wabm.supermarket.protocol.CallbackAcceptableProtocol;
import io.wabm.supermarket.protocol.StageSetableController;
import io.wabm.supermarket.view.ViewPathHelper;
import javafx.beans.property.IntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.dao.DataAccessException;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Optional;
/**
 * Created by 14580 on 2016/12/3 0003.
 */
public class SupplyGoodsController  extends SceneController {


    @FXML Button backButton;
    @FXML Button addButton;
    @FXML Button deleteButton;
    @FXML Button modifyButton;
    @FXML Button searchButton;

    @FXML private void backButtonPressed() {
        ConsoleLog.print("button pressed");

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ViewPathHelper.class.getResource("procurement/CommoditySupplierManagementView.fxml"));

        navigationTo(loader);
    }

   /* @FXML private void addButtonPressed() {
        ConsoleLog.print("button pressed");

        try {
            // Load view
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ViewPathHelper.class.getResource("procurement/AddCommodityView.fxml"));
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
            ((AddCommodityController) controller).fetchClassificationWithDefault(model.getClassificationID());
            ((CallbackAcceptableProtocol<Commodity, DataAccessException>) controller).set((commodity) -> {
                ConsoleLog.print("Add commodity callback called");
                final DataAccessException[] e = {null};

                model.add(commodity, (exception) -> {
                    e[0] = exception;
                    if (null != exception) {
                        exception.printStackTrace();
                    } else {
                        ConsoleLog.print("Insert commodity success");
                    }

                    return null;
                });

                return e[0];
            });

            // Show the dialog and wait until the user closes it.
            // (This event thread is blocked until close)
            stage.showAndWait();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
    /*@FXML private void deleteButtonPressed() {
        ConsoleLog.print("button pressed");

        Commodity commodity = tableView.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("删除分类");
        alert.setHeaderText("确认删除");
        alert.setContentText("删除 " + commodity.getName() + " " + (commodity.getSpecification()));

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            ConsoleLog.print("Delete " + commodity.getName() + " …");

            model.delete(commodity, exception -> {
                if (null == exception) {
                    new SimpleSuccessAlert("删除成功", "", commodity.getName() + " 删除成功").show();
                } else {
                    new SimpleErrorAlert("删除失败", "删除数据遇到了错误", "请重试").show();
                }
                return null;
            });
        } else {
            ConsoleLog.print("Delete process cancel");
        }
    }*/

   /* @FXML public void initialize() {
        ConsoleLog.print("SupplierController init");

        setupControl();
        setupModel();
        setupTableView();
        setupTableViewColumn();
    }*/



    /*public void fetchWith(int classificationID) {

        this.model.fetchData(classificationID,
                (exception) -> {

                    // TODO: handle exception
                    return null;
                }
        );

    }*/


}