package io.wabm.supermarket.controller.procurement;

import io.wabm.supermarket.misc.javafx.alert.SimpleErrorAlert;
import io.wabm.supermarket.misc.pojo.Employee;
import io.wabm.supermarket.misc.pojo.SupplyGoods;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.misc.util.ValidCheckHelper;
import io.wabm.supermarket.model.management.EmployeeInformationModel;
import io.wabm.supermarket.model.procurement.SupplyGoodsModel;
import io.wabm.supermarket.protocol.CallbackAcceptableProtocol;
import io.wabm.supermarket.protocol.StageSetableController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;

/**
 * Created by 14580 on 2016/12/13 0013.
 */
public class ModifySupplyDetailController implements StageSetableController, CallbackAcceptableProtocol<SupplyGoods, DataAccessException> {

    private SupplyGoodsModel model;
    private Callback<SupplyGoods, DataAccessException> callback = null;
    private SupplyGoods supplyGoods;

    @FXML Stage stage;
    @FXML TextField commodityIDTextField;
    @FXML TextField commodityNameTextField;
    @FXML TextField priceTextField;
    @FXML TextField deliveryTimeTextField;

    @FXML Button comfirmButton;
    @FXML Button cancelButton;

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    @Override
    public void set(Callback<SupplyGoods, DataAccessException> callback) {
        this.callback = callback;
    }

    @FXML
    public void initialize() {
        ConsoleLog.print("ModifySupplyDetailController init");

        setupModel();
        setupControl();
    }

    private void setupModel() {}
    private void setupControl() {
        commodityIDTextField.setDisable(true);
        commodityNameTextField.setDisable(true);
    }

    public void setSupplyGoods(SupplyGoods supplyGoods) {
        ConsoleLog.print("Set supplyGoods: " + supplyGoods.getCommodityName());

        this.supplyGoods = supplyGoods;
        resetControl(supplyGoods);
    }

    private void resetControl(SupplyGoods supplyGoods) {
        commodityIDTextField.setText(String.valueOf(supplyGoods.getCommodityID()));
        commodityNameTextField.setText(supplyGoods.getCommodityName());
        priceTextField.setText(String.valueOf(supplyGoods.getPrice_db()));
        deliveryTimeTextField.setText(supplyGoods.getDelivery_time_cost());
    }

    private boolean isInputValid() {
        ValidCheckHelper helper = new ValidCheckHelper();
        String errorMessage = "";

        errorMessage += helper.checkTypeAndLength(commodityIDTextField, "商品编号", 20);
        errorMessage += helper.checkTypeAndLength(commodityNameTextField, "商品名称", 20);
        errorMessage += helper.checkTypeAndLength(priceTextField, "进价", 11);
        errorMessage += helper.checkTypeAndLength(deliveryTimeTextField, "提前期", 14);


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
    @FXML
    private void setComfirmButtonPressed() {
        ConsoleLog.print("Button pressed");
        // stage.close();
        if (!isInputValid()) {
            return ;
        }

        if (callback == null) {
            ConsoleLog.print("Callback not set");
            return;
        }
        SupplyGoods newSupplyGoods = new SupplyGoods(
                commodityIDTextField.getText(),
                commodityNameTextField.getText(),
                Double.parseDouble(priceTextField.getText()),
                deliveryTimeTextField.getText()
        );


        DataAccessException exception = null;
        if (null != (exception = callback.call(newSupplyGoods))) {
            if (exception instanceof DuplicateKeyException) {
                String message = exception.getLocalizedMessage();
                ConsoleLog.print(message);

                if (message.contains("commodity_id_UNIQUE")) {
                    new SimpleErrorAlert("数据库更新出现错误", "请检查输入商品编号", "该商品编号在数据库中已存在").show();
                }
            } else {
                new SimpleErrorAlert("数据库更新出现错误", "请检查输入字段并重试", "").show();
            }   // end if (exception instanceof …)

        } else {

            supplyGoods.setCommodityID(newSupplyGoods.getCommodityID());
            supplyGoods.setCommodityName(newSupplyGoods.getCommodityName());
            supplyGoods.setPrice_db(newSupplyGoods.getPrice_db());
            supplyGoods.setDelivery_time_cost(newSupplyGoods.getDelivery_time_cost());
            stage.close();
        }
        stage.close();
    }


    @FXML
    private void setCancelButtonPressed() {
        ConsoleLog.print("Button pressed");
        stage.close();
    }



}
