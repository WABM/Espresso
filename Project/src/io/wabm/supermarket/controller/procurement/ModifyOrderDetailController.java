package io.wabm.supermarket.controller.procurement;

import io.wabm.supermarket.misc.javafx.alert.SimpleErrorAlert;
import io.wabm.supermarket.misc.pojo.OrderDetail;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.misc.util.ValidCheckHelper;
import io.wabm.supermarket.model.procurement.OrderDetailModel;
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
 * Created by 14580 on 2016/12/14 0014.
 */
public class ModifyOrderDetailController implements StageSetableController, CallbackAcceptableProtocol<OrderDetail, DataAccessException> {

    private OrderDetailModel model;
    private OrderDetail orderDetail;
    private Callback<OrderDetail, DataAccessException> callback = null;

    @FXML Stage stage;
    @FXML TextField orderIDTextField;
    @FXML TextField commodityNameTextField;
    @FXML TextField quantityTextField;
    @FXML TextField priceTextField;
    @FXML TextField productDateTextField;

    @FXML Button comfirmButton;
    @FXML Button cancelButton;

    @Override public  void setStage(Stage stage){
        this.stage=stage;
    }

    @Override
    public void set(Callback<OrderDetail, DataAccessException> callback) {
        this.callback = callback;
    }

    @FXML
    public void initialize() {
        ConsoleLog.print("ModifySupplierController init");

        setupModel();
        setupControl();
    }

    private void setupModel() {}
    private void setupControl(){
        orderIDTextField.setDisable(true);
        commodityNameTextField.setDisable(true);
        priceTextField.setDisable(true);
        productDateTextField.setDisable(true);
    }

    public void setOrderDetail(OrderDetail orderDetail) {
        ConsoleLog.print("Set orderDetail: " + orderDetail.getOrderID());

        this.orderDetail = orderDetail;
        resetControl(orderDetail);
    }

    private void resetControl(OrderDetail orderDetail) {
        orderIDTextField.setText(String.valueOf(orderDetail.getOrderID()));
        commodityNameTextField.setText(orderDetail.getCommodityName());
        priceTextField.setText(String.valueOf(orderDetail.getPrice_db()));
        productDateTextField.setText(orderDetail.getProduction_date());
        quantityTextField.setText(String.valueOf(orderDetail.getQuantity()));

    }
    private boolean isInputValid() {
        ValidCheckHelper helper = new ValidCheckHelper();
        String errorMessage = "";
        errorMessage += helper.checkTypeAndLength(orderIDTextField, "订单编号", 20);
        errorMessage += helper.checkTypeAndLength(commodityNameTextField, "商品名称", 20);
        errorMessage += helper.checkTypeAndLength(priceTextField, "进价", 11);
        errorMessage += helper.checkTypeAndLength(productDateTextField, "生产日期", 14);
        errorMessage += helper.checkTypeAndLength(quantityTextField, "数量", 11);

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
        OrderDetail newOrderDetail = new OrderDetail(
                Integer.parseInt(orderIDTextField.getText()),
                commodityNameTextField.getText(),
                Integer.parseInt(quantityTextField.getText()),
                Double.parseDouble(priceTextField.getText()),
                productDateTextField.getText()

        );


        DataAccessException exception = null;
        if (null != (exception = callback.call(newOrderDetail))) {
            if (exception instanceof DuplicateKeyException) {
                String message = exception.getLocalizedMessage();
                ConsoleLog.print(message);

                if (message.contains("quantity_UNIQUE")) {
                    new SimpleErrorAlert("数据库更新出现错误", "请检查输入商品编号", "该商品编号在数据库中已存在").show();
                }
            } else {
                new SimpleErrorAlert("数据库更新出现错误", "请检查输入字段并重试", "").show();
            }   // end if (exception instanceof …)

        } else {

            orderDetail.setOrderID(newOrderDetail.getOrderID());
            orderDetail.setCommodityName(newOrderDetail.getCommodityName());
            orderDetail.setQuantity(newOrderDetail.getQuantity());
            orderDetail.setPrice_db(newOrderDetail.getPrice_db());
            orderDetail.setProduction_date(newOrderDetail.getProduction_date());
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
