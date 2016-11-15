package io.wabm.supermarket.controller.sales;

import io.wabm.supermarket.protocol.StageSetableContoller;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.view.ViewPathHelper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by MR-lulu on 2016/10/27 0027.
 */
public class CommodityPriceManagementController{
    @FXML Button modify;

    @FXML private void modifyButtonPressed(){
        ConsoleLog.print("Button Pressed");

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ViewPathHelper.class.getResource("sales/modifyPriceDialogByType.fxml"));
            AnchorPane pane = loader.load();

            Stage stage = new Stage();
            stage.setTitle("修改价格");
            stage.initModality(Modality.APPLICATION_MODAL);

            Scene scene = new Scene(pane);
            stage.setScene(scene);

            StageSetableContoller controller = loader.getController();
            controller.setStage(stage);

            stage.showAndWait();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
