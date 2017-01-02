package io.wabm.supermarket.controller.management;

import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.protocol.CallbackAcceptableProtocol;
import io.wabm.supermarket.protocol.StageSetableController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * Created by liu on 2016-10-25 .
 */
public class QueryEmployeeController implements StageSetableController, CallbackAcceptableProtocol<String[], Void> {
    @FXML Stage stage;

    private Callback<String[], Void> callback = null;

    private String[] filter = new String[3];

    @FXML TextField idTextField ;
    @FXML TextField nameTextField ;
    @FXML TextField phoneTextField ;

    @FXML Button comfirmButton;
    @FXML Button cancelButton;
    @FXML public void initialize() {
        ConsoleLog.print("QueryEmployeeController init");
    }


    @FXML private void comfirmButtonPressed(){
        ConsoleLog.print("Button pressed");

        filter[0] = idTextField.getText().trim();

        filter[1] = nameTextField.getText().trim();
       // filter[2] = (String) departmentCombBox.getValue();
        filter[2] = phoneTextField.getText().trim();

        for (String s : filter) {
            ConsoleLog.print(s);
        }

        callback.call(filter);

        stage.close();
    }
    @FXML private  void setCancelButtonpressed(){
        ConsoleLog.print("Button pressed");
        stage.close();
    }
    @Override
    public  void setStage(Stage stage){
        this.stage=stage;
    }

    @Override
    public void set(Callback<String[], Void> callback) {
        this.callback = callback;
    }
}
