package io.wabm.supermarket.controller.procurement;

import io.wabm.supermarket.protocol.StageSetableController;
import io.wabm.supermarket.misc.util.ConsoleLog;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Created by liu on 2016-10-26 .
 */
public class NeworderController implements StageSetableController {
    @FXML Stage stage;
    @FXML Button comfirmButton;
    @FXML Button cancelButton;
    @Override
    public  void setStage(Stage stage){
        this.stage=stage;
    }
    @FXML private void setComfirmButtonPressed(){
        ConsoleLog.print("Button pressed");
        stage.close();
    }
    @FXML private  void setCancelButtonpressed(){
        ConsoleLog.print("Button pressed");
        stage.close();
    }
}
