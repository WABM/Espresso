package io.wabm.supermarket.controller.warehouse;

import io.wabm.supermarket.protocol.StageSetableContoller;
import io.wabm.supermarket.misc.util.ConsoleLog;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Created by MainasuK on 2016-10-25.
 */
public class CommodityOrderReceiveController implements StageSetableContoller {

    @FXML Stage stage;

    @FXML Button closeButton;


    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML private void closeButtonPressed() {
        ConsoleLog.print("Button pressed");

        stage.close();
    }

}
