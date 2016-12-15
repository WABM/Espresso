package io.wabm.supermarket.controller.procurement;

import io.wabm.supermarket.misc.javafx.alert.SimpleErrorAlert;
import io.wabm.supermarket.misc.pojo.OrderDetail;
import io.wabm.supermarket.misc.pojo.Supplier;
import io.wabm.supermarket.misc.pojo.SupplyGoods;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.misc.util.ValidCheckHelper;
import io.wabm.supermarket.model.procurement.CommoditySupplierModel;
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

    @FXML Stage stage;

    private OrderDetailModel model;
    private OrderDetail orderDetail;
    private Callback<OrderDetail, DataAccessException> callback = null;

    @FXML TextField quantityTextField;

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
    private void setupControl(){}

    public void setSupplier(OrderDetail orderDetail) {
        ConsoleLog.print("Set orderDetail: " + orderDetail.getOrderID());

        this.orderDetail = orderDetail;
        resetControl(orderDetail);
    }

    private void resetControl(OrderDetail orderDetail) {

        quantityTextField.setText(String.valueOf(orderDetail.getQuantity()));

    }
    private boolean isInputValid() {
        ValidCheckHelper helper = new ValidCheckHelper();
        String errorMessage = "";

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
//        ConsoleLog.print("Button pressed");
//        // stage.close();
//        if (!isInputValid()) {
//            return ;
//        }
//
//        if (callback == null) {
//            ConsoleLog.print("Callback not set");
//            return;
//        }
//        OrderDetail newOrderDetail = new OrderDetail(
//                Integer.parseInt(quantityTextField.getText())
//        );
//
//
//        DataAccessException exception = null;
//        if (null != (exception = callback.call(newOrderDetail))) {
//            if (exception instanceof DuplicateKeyException) {
//                String message = exception.getLocalizedMessage();
//                ConsoleLog.print(message);
//
//                if (message.contains("quantity_UNIQUE")) {
//                    new SimpleErrorAlert("数据库更新出现错误", "请检查输入商品编号", "该商品编号在数据库中已存在").show();
//                }
//            } else {
//                new SimpleErrorAlert("数据库更新出现错误", "请检查输入字段并重试", "").show();
//            }   // end if (exception instanceof …)
//
//        } else {
//
//
//            orderDetail.setQuantity(newOrderDetail.getQuantity());
//
//            stage.close();
//        }
//        stage.close();
    }
    @FXML
    private void setCancelButtonPressed() {
        ConsoleLog.print("Button pressed");
        stage.close();
    }

}
