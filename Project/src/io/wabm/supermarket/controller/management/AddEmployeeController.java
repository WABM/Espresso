package io.wabm.supermarket.controller.management;

import io.wabm.supermarket.protocol.StageSetableContoller;
import io.wabm.supermarket.misc.util.ConsoleLog;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Created by liu on 2016-10-25 .
 */
public class AddEmployeeController implements StageSetableContoller {
    @FXML Stage stage;
    @FXML Button comfirmButton;
    @FXML Button cancelButton;
    @Override
    public  void setStage(Stage stage){
        this.stage=stage;
    }
    @FXML private void comfirmButtonPressed(){
        ConsoleLog.print("Button pressed");
        stage.close();
    }
    @FXML private  void setCancelButtonpressed(){
        ConsoleLog.print("Button pressed");
        stage.close();
    }
}
