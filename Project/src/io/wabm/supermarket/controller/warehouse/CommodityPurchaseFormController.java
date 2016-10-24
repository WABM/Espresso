package io.wabm.supermarket.controller.warehouse;

import io.wabm.supermarket.util.ConsoleLog;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Created by MainasuK on 2016-10-24.
 */
public class CommodityPurchaseFormController {

    @FXML Stage stage;

    @FXML Button comfirmButton;
    @FXML Button cancelButton;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML private void comfirmButtonPressed() {
        ConsoleLog.print("Button Pressed");

        stage.close();
    }

    @FXML private void cancelButtonPressed() {
        ConsoleLog.print("Button Pressed");

        stage.close();
    }
}
