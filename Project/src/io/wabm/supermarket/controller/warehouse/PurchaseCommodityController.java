package io.wabm.supermarket.controller.warehouse;

import io.wabm.supermarket.misc.javafx.alert.SimpleErrorAlert;
import io.wabm.supermarket.misc.javafx.alert.SimpleSuccessAlert;
import io.wabm.supermarket.misc.pojo.Commodity;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.misc.util.ValidCheckHelper;
import io.wabm.supermarket.model.warehouse.PurchaseCommodityModel;
import io.wabm.supermarket.model.warehouse.RejectCommodityModel;
import io.wabm.supermarket.protocol.StageSetableController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Created by MainasuK on 2016-11-28.
 */
public class PurchaseCommodityController implements StageSetableController {

    private PurchaseCommodityModel<Commodity> model;
    private Commodity commodity;
    private int orderDetailID = -1;


    @FXML Stage stage;

    @FXML Label idLabel;
    @FXML Label  barCodeLabel;
    @FXML Label nameLabel;
    @FXML Label deliverySpecificationLabel;
    @FXML TextField rejectQuantityTextField;

    @FXML Button confirmButton;
    @FXML Button cancelButton;

    @FXML private void confirmButtonPressed() {
        ConsoleLog.print("Button pressed");

        if (!isInputValid()) {
            return ;
        }

        int purchaseQuantity = Integer.parseInt(rejectQuantityTextField.getText()) * commodity.getDeliverySpecification();

        model.purchase(commodity, purchaseQuantity, exception -> {

            if (null != exception) {
                new SimpleErrorAlert("数据库更新出现错误", "请检查输入字段并重试","").show();
            } else {
                Platform.runLater(() -> {
                    new SimpleSuccessAlert("采购成功", "已生成采购需求", "采购 " + purchaseQuantity + commodity.getUnit()).show();
                    stage.close();
                });
            }   // end if

            return null;
        });


    }

    @FXML private void cancelButtonPressed() {
        ConsoleLog.print("Button pressed");

        stage.close();
    }

    /**
     * Use JavaFX initialize. This method will be called after control init.
     */
    @FXML public void initialize() {
        ConsoleLog.print("ModifyCommodityController init");

        model = new PurchaseCommodityModel<>();
    }

    private void resetControl(Commodity commodity) {
        idLabel.setText(commodity.getCommodityID());
        barCodeLabel.setText(commodity.getBarcode());
        nameLabel.setText(commodity.getName());
        deliverySpecificationLabel.setText(commodity.getDeliverySpecification()+" "+commodity.getUnit());
    }

    // MARK: Public config method

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void set(Commodity commodity) {
        ConsoleLog.print("Set commodity: " + commodity.getName());

        this.commodity = commodity;

        resetControl(commodity);
    }

    /**
     * Validates the user input in the text fields
     *
     * @return
     */
    private boolean isInputValid() {
        ValidCheckHelper helper = new ValidCheckHelper();
        String errorMessage = "";

        errorMessage += helper.checkTypeAndLengthForIntegerNotZero(rejectQuantityTextField, "报废数量", 11);

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
