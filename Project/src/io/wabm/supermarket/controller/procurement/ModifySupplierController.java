package io.wabm.supermarket.controller.procurement;

import io.wabm.supermarket.misc.pojo.Supplier;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.protocol.StageSetableController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Optional;

/**
 * Created by 14580 on 2016/11/19 0019.
 */
public class ModifySupplierController implements StageSetableController {
    @FXML Stage stage;

    private Supplier supplier;

    @FXML TextField nameText;
    @FXML TextField representative_nameText;
    @FXML TextField phoneText;
    @FXML TextField addressText;

    @FXML Button comfirmButton;
    @FXML Button cancelButton;

    @Override public  void setStage(Stage stage){
        this.stage=stage;
    }

    @FXML private void comfirmButtonPressed(){
        ConsoleLog.print("Button pressed");
        if (check()) {
            supplier.setSupplierName(nameText.getText());
            supplier.setLinkman(representative_nameText.getText());
            supplier.setPhone(phoneText.getText());
            supplier.setAddress(addressText.getText());
            stage.close();
        }
    }
    @FXML private  void setCancelButtonpressed(){
        ConsoleLog.print("Button pressed");
        stage.close();
    }
    public void setDate(Supplier supplier)
    {
        this.supplier = supplier;

        nameText.setText(supplier.getSupplierName());
        representative_nameText.setText(supplier.getLinkman());
        phoneText.setText(supplier.getPhone());
        addressText.setText(supplier.getAddress());
    }
    private Boolean check() {
        if (nameText.getText().isEmpty() ||representative_nameText.getText().isEmpty()||phoneText.getText().isEmpty() ||addressText.getText().isEmpty() ) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("修改");
            alert.setHeaderText("");
            alert.setContentText("填写内容不能为空");
            Optional<ButtonType> result = alert.showAndWait();
            return false;
        }
        return true;
    }
}
