package io.wabm.supermarket.controller.management;

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
 * Created by liu on 2016-10-25 .
 */
public class SalesAchievementviewController {
    @FXML Button hotgoodsButton;
    @FXML
    private void hotgoodsButtonPressed() {
        ConsoleLog.print("Button pressed");
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ViewPathHelper.class.getResource("management/Hot list.fxml"));
            AnchorPane pane = loader.load();

            Stage stage = new Stage();
            stage.setTitle("我不知道写什么好");
            stage.initModality(Modality.APPLICATION_MODAL);

            Scene scene = new Scene(pane);
            stage.setScene(scene);

            StageSetableContoller contoller = loader.getController();
            contoller.setStage(stage);

            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}