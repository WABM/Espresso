package io.wabm.supermarket.controller.warehouse;

import io.wabm.supermarket.misc.pojo.Classification;
import io.wabm.supermarket.misc.pojo.Commodity;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.protocol.CallbackAcceptableProtocol;
import io.wabm.supermarket.protocol.StageSetableController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.Arrays;

/**
 * Created by MainasuK on 2016-12-10.
 */
public class FilterCommodityController implements StageSetableController, CallbackAcceptableProtocol<String[], Void> {

    private Stage stage;
    private Callback<String[], Void> callback = null;

    private String[] filter = new String[3];

    @FXML TextField idTextField;
    @FXML TextField barCodeTextField;
    @FXML TextField nameTextField;

    @FXML Button confirmButton;
    @FXML Button cancelButton;

    @FXML public void initialize() {
        ConsoleLog.print("FilterCommodityController init");
    }

    @FXML private void confirmButtonPressed() {
        filter[0] = idTextField.getText().trim();
        filter[1] = barCodeTextField.getText().trim();
        filter[2] = nameTextField.getText().trim();

        for (String s : filter) {
            ConsoleLog.print(s);
        }

        callback.call(filter);
        stage.close();
    }

    @FXML private void cancelButtonPressed() {
        ConsoleLog.print("Button pressed");

        stage.close();
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void set(Callback<String[], Void> callback) {
        this.callback = callback;
    }
}
