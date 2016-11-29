package io.wabm.supermarket.controller.warehouse;

import io.wabm.supermarket.protocol.StageSetableController;
import io.wabm.supermarket.misc.util.ConsoleLog;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Created by MainasuK on 2016-10-25.
 */
public class AddCommodityClassificationController implements StageSetableController {

    @FXML Stage stage;

    @FXML Button confirmButton;
    @FXML Button cancelButton;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML private void confirmButtonPressed() {
        ConsoleLog.print("Button pressed");

        stage.close();
    }

    @FXML private void cancelButtonPressed() {
        ConsoleLog.print("Button pressed");

        stage.close();
    }
}
