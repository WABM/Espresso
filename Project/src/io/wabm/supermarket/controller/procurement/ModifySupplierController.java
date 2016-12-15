package io.wabm.supermarket.controller.procurement;

import io.wabm.supermarket.misc.javafx.alert.SimpleErrorAlert;
import io.wabm.supermarket.misc.pojo.Employee;
import io.wabm.supermarket.misc.pojo.Supplier;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.misc.util.ValidCheckHelper;
import io.wabm.supermarket.model.management.EmployeeInformationModel;
import io.wabm.supermarket.model.procurement.CommoditySupplierModel;
import io.wabm.supermarket.protocol.CallbackAcceptableProtocol;
import io.wabm.supermarket.protocol.StageSetableController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;

import java.util.Optional;

/**
 * Created by 14580 on 2016/11/19 0019.
 */
public class ModifySupplierController implements StageSetableController, CallbackAcceptableProtocol<Supplier, DataAccessException> {

    @FXML Stage stage;
    private CommoditySupplierModel model;
    private Supplier supplier;
    private Callback<Supplier, DataAccessException> callback = null;

    @FXML TextField supplierIDText;
    @FXML TextField nameText;
    @FXML TextField representative_nameText;
    @FXML TextField phoneText;
    @FXML TextField addressText;

    @FXML Button comfirmButton;
    @FXML Button cancelButton;

    @Override public  void setStage(Stage stage){
        this.stage=stage;
    }

    @Override
    public void set(Callback<Supplier, DataAccessException> callback) {
        this.callback = callback;
    }

    @FXML
    public void initialize() {
        ConsoleLog.print("ModifySupplierController init");

        setupModel();
        setupControl();
    }
    private void setupModel() {}
    private void setupControl(){supplierIDText.setDisable(true);}

    public void setSupplier(Supplier supplier) {
        ConsoleLog.print("Set supplier: " + supplier.getSupplierName());

        this.supplier = supplier;
        resetControl(supplier);
    }

    private void resetControl(Supplier supplier) {
        supplierIDText.setText(String.valueOf(supplier.getSupplierID()));
        nameText.setText(supplier.getSupplierName());
        representative_nameText.setText(supplier.getLinkman());
        phoneText.setText(supplier.getPhone());
        addressText.setText(supplier.getAddress());



    }



    @FXML private void comfirmButtonPressed(){
        ConsoleLog.print("Button pressed");
        if (!isInputValid()) {
            return ;
        }

        if (callback == null) {
            ConsoleLog.print("Callback not set");
            return;
        }

        Supplier newsupplier = new Supplier(
                Integer.parseInt(supplierIDText.getText()),
                nameText.getText(),
                representative_nameText.getText(),
                phoneText.getText(),
                addressText.getText()
        );
        DataAccessException exception = null;
        if (null != (exception = callback.call(newsupplier))) {
            if (exception instanceof DuplicateKeyException) {
                String message = exception.getLocalizedMessage();
                ConsoleLog.print(message);

                if (message.contains("supplier_id_UNIQUE")) {
                    new SimpleErrorAlert("数据库更新出现错误", "请检查输入供应商编号", "该供应商编号在数据库中已存在").show();
                }
            }
            else {
                new SimpleErrorAlert("数据库更新出现错误", "请检查输入字段并重试", "").show();
            }   // end if (exception instanceof …)

        } else {

            supplier.setSupplierID(newsupplier.getSupplierID());
            supplier.setSupplierName(newsupplier.getSupplierName());
            supplier.setLinkman(newsupplier.getLinkman());
            supplier.setPhone(newsupplier.getPhone());
            supplier.setAddress(newsupplier.getAddress());
            stage.close();
        }
        stage.close();

    }

    private boolean isInputValid() {
        ValidCheckHelper helper = new ValidCheckHelper();
        String errorMessage = "";

        errorMessage += helper.checkTypeAndLength(supplierIDText, "供应商编号", 20);
        errorMessage += helper.checkTypeAndLength(nameText, "供应商名称", 20);
        errorMessage += helper.checkTypeAndLength(representative_nameText, "联系人", 11);
        errorMessage += helper.checkTypeAndLength(phoneText, "电话号码", 14);
        errorMessage += helper.checkTypeAndLength(addressText, "详细地址", 14);


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


    @FXML private  void setCancelButtonpressed(){
        ConsoleLog.print("Button pressed");
        stage.close();
    }

//    public void setDate(Supplier supplier)
//    {
//        this.supplier = supplier;
//
//        nameText.setText(supplier.getSupplierName());
//        representative_nameText.setText(supplier.getLinkman());
//        phoneText.setText(supplier.getPhone());
//        addressText.setText(supplier.getAddress());
//    }

//    private Boolean check() {
//        if (nameText.getText().isEmpty() ||representative_nameText.getText().isEmpty()||phoneText.getText().isEmpty() ||addressText.getText().isEmpty() ) {
//            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//            alert.setTitle("修改");
//            alert.setHeaderText("");
//            alert.setContentText("填写内容不能为空");
//            Optional<ButtonType> result = alert.showAndWait();
//            return false;
//        }
//        return true;
//    }
}
