package io.wabm.supermarket.controller.procurement;

import io.wabm.supermarket.misc.javafx.alert.SimpleErrorAlert;
import io.wabm.supermarket.misc.pojo.Supplier;
import io.wabm.supermarket.protocol.CallbackAcceptableProtocol;
import io.wabm.supermarket.protocol.StageSetableController;
import io.wabm.supermarket.misc.util.ConsoleLog;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;



/**
 * Created by 14580 on 2016/11/19 0019.
 */
public class NewSupplierController implements StageSetableController,CallbackAcceptableProtocol<Supplier, DataAccessException> {
    private Callback<Supplier, DataAccessException> callback = null;
    @FXML
    Stage stage;

    @FXML
    TextField idTextField;
    @FXML
    TextField nameTextField;
    @FXML
    TextField representative_nameTextField;
    @FXML
    TextField addressTextField;
    @FXML
    TextField phoneTextField;



    @FXML
    Button comfirmButton;
    @FXML
    Button cancelButton;

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    @FXML private void comfirmButtonPressed() {

        ConsoleLog.print("Button pressed");
        if (callback == null) {
            ConsoleLog.print("Callback not set");
            return;
        }
        Supplier supplier = new Supplier(
                Integer.parseInt(idTextField.getText()),
                nameTextField.getText(),
                representative_nameTextField.getText(),
                phoneTextField.getText(),
                addressTextField.getText()
        );

        DataAccessException exception =null;

        if (null!= (exception = callback.call(supplier)))
        {
            if (exception instanceof DuplicateKeyException) {
                String message = exception.getLocalizedMessage();

                if (message.contains("UNIQUE")) {
                    new SimpleErrorAlert("数据库更新出现错误", "请检查输入条形码", "该条形码在数据库中已存在").show();
                } else if (message.contains("PRIMARY")) {
                    new SimpleErrorAlert("数据库更新出现错误", "请检查输入供应商编号", "该供应商编码在数据库中已存在").show();
                } else {
                    new SimpleErrorAlert("", "", message).show();
                }
            } else {
                new SimpleErrorAlert("数据库更新出现错误", "请检查输入字段并重试", exception.getLocalizedMessage()).show();
            }   // end if (exception instanceof …)

        } else {
            stage.close();
        }
    }

        @Override
        public void set (Callback < Supplier, DataAccessException > callback){
            this.callback = callback;
        }


        @FXML private void setCancelButtonpressed () {
            ConsoleLog.print("Button pressed");
            stage.close();
        }
    }
