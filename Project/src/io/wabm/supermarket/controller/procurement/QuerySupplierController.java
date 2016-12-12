package io.wabm.supermarket.controller.procurement;

import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.protocol.CallbackAcceptableProtocol;
import io.wabm.supermarket.protocol.StageSetableController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * Created by 14580 on 2016/12/13 0013.
 */
public class QuerySupplierController implements StageSetableController, CallbackAcceptableProtocol<String[], Void> {

    @FXML Stage stage;

    private Callback<String[], Void> callback = null;

    private String[] filter = new String[4];

    @FXML TextField idTextField ;
    @FXML TextField nameTextField ;
    @FXML TextField linkmanTextField ;
    @FXML TextField phoneTextField ;

    @FXML Button comfirmButton;
    @FXML Button cancelButton;

    @FXML public void initialize() {
        ConsoleLog.print("QuerySupplierController init");
    }
    @Override
    public  void setStage(Stage stage){
        this.stage=stage;
    }

    @Override
    public void set(Callback<String[], Void> callback) {
        this.callback = callback;
    }

    @FXML private void setComfirmButtonPressed(){
        ConsoleLog.print("Button pressed");

        filter[0] = idTextField.getText().trim();

        filter[1] = nameTextField.getText().trim();
        filter[2] = linkmanTextField.getText().trim();
        filter[3] = phoneTextField.getText().trim();

        for (String s : filter) {
            ConsoleLog.print(s);
        }

        callback.call(filter);

        stage.close();
    }
    @FXML private  void setCancelButtonPressed(){
        ConsoleLog.print("Button pressed");
        stage.close();
    }

}
