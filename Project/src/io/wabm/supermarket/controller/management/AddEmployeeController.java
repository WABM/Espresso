package io.wabm.supermarket.controller.management;

import io.wabm.supermarket.misc.javafx.alert.SimpleErrorAlert;
import io.wabm.supermarket.misc.pojo.Employee;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.misc.util.GenderWrapper;
import io.wabm.supermarket.misc.util.GenderWrapper1;
import io.wabm.supermarket.misc.util.ValidCheckHelper;
import io.wabm.supermarket.model.management.AddEmployeeModel;
import io.wabm.supermarket.protocol.CallbackAcceptableProtocol;
import io.wabm.supermarket.protocol.StageSetableController;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.util.Assert;

/**
 * Created by liu on 2016-10-25 .
 */
public class AddEmployeeController implements StageSetableController,CallbackAcceptableProtocol<Employee, DataAccessException> {

    private AddEmployeeModel<Employee> model;
    private Callback<Employee, DataAccessException> callback = null;
    @FXML
    Stage stage;

    @FXML
    TextField idTextField;
    @FXML
    TextField nameTextField;
    @FXML
    ComboBox<GenderWrapper1> departmentComboBox;
    @FXML
    ComboBox<GenderWrapper> sexComboBox;
    @FXML
    TextField birthTextField;
    @FXML
    TextField entryTextField;
    @FXML
    TextField phoneTextField;

    @FXML TextField userTextField;
    @FXML TextField passwordTextField;

    @FXML
    Button comfirmButton;
    @FXML
    Button cancelButton;

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void comfirmButtonPressed() {
        ConsoleLog.print("Button pressed");
        if (!isInputValid()) {
            return;
        }

        // stage.close();

        if (callback == null) {
            ConsoleLog.print("Callback not set");
            return;
        }


        Employee employee = new Employee(
                //Integer.parseInt(idTextField.getText()),
                -1,
                nameTextField.getText(),
                birthTextField.getText(),
                sexComboBox.getValue().getSex(),
                phoneTextField.getText(),
                departmentComboBox.getValue().getDepartment(),
                entryTextField.getText(),
                userTextField.getText(),
                passwordTextField.getText(),
                true
        );

    DataAccessException exception = null;
    if (null != (exception = callback.call(employee))) {
        if (exception instanceof DuplicateKeyException) {
            String message = exception.getLocalizedMessage();

            /*if (message.contains("UNIQUE")) {
                new SimpleErrorAlert("数据库更新出现错误", "请检查输入条形码", "该条形码在数据库中已存在").show();
            } else*/ if (message.contains("PRIMARY")) {
                new SimpleErrorAlert("数据库更新出现错误", "请检查输入员工编号", "该商品编码在数据库中已存在").show();
            } else {
                new SimpleErrorAlert("","", message).show();
            }
        } else {
            new SimpleErrorAlert("数据库更新出现错误", "请检查输入字段并重试",exception.getLocalizedMessage()).show();
        }   // end if (exception instanceof …)

    } else {
        stage.close();
    }   // end if (null != callback…)

    }


    @FXML
    private void setCancelButtonpressed() {
        ConsoleLog.print("Button pressed");
        stage.close();
    }

   /* public boolean isInputValid() {
        return inputValid;
    }*/

    @FXML
public void initialize() {
    ConsoleLog.print("AddEmployeeController init");

    setupModel();

   // departmentComboBox.setItems(FXCollections.observableArrayList("总管理员","仓库管理员","收银员","采购管理员","销售管理员"));

    sexComboBox.setItems(FXCollections.observableArrayList(new GenderWrapper(0),new GenderWrapper(1)));
        departmentComboBox.setItems(FXCollections.observableArrayList(new GenderWrapper1(0),new GenderWrapper1(1),new GenderWrapper1(2),new GenderWrapper1(3),new GenderWrapper1(4)));
}

    private void setupModel() {
        Assert.notNull(departmentComboBox);

        //model = new AddEmployeeModel<>(departmentComboBox);
    }


    @Override
    public void set(Callback<Employee, DataAccessException> callback) {
        this.callback = callback;
    }


    private boolean isInputValid() {
        ValidCheckHelper helper = new ValidCheckHelper();
        String errorMessage = "";

        if (phoneTextField.getText().length() != 7 && phoneTextField.getText().length() !=11 ) {
            errorMessage += "电话号码为：11 位或者7位\n";
        }
        if (nameTextField.getText().length() == 0)
        {
            errorMessage += "姓名不能为空\n";
        }
        if (entryTextField.getText().length() == 0)
        {
            errorMessage += "入职日期不能为空\n";
        }
        if (birthTextField.getText().length() == 0)
        {
            errorMessage += "出生日期不能为空\n";
        }
        if (userTextField.getText().length() == 0)
        {
            errorMessage += "帐户名不能为空\n";
        }
        if (passwordTextField.getText().length() == 0)
        {
            errorMessage += "密码不能为空\n";
        }




        //errorMessage += helper.checkTypeAndLength(idTextField, "员工号", 20);
        errorMessage += helper.checkTypeAndLength(nameTextField, "姓名", 20);
        errorMessage += helper.checkTypeAndLength(phoneTextField, "电话号码", 11);
        errorMessage += helper.checkTypeAndLength(birthTextField, "出生日期", 14);
        errorMessage += helper.checkTypeAndLength(entryTextField, "入职日期", 14);
        errorMessage += helper.checkTypeAndLength(userTextField, "帐户名", 14);
        errorMessage += helper.checkTypeAndLength(passwordTextField, "密码", 14);


        ConsoleLog.print(errorMessage);
        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("无效数据");
            alert.setHeaderText("请检查无效数据");
            alert.setContentText(errorMessage);
            alert.show();

            return false;
        }
    }
}