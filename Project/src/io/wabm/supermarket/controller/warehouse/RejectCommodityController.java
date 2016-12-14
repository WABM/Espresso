package io.wabm.supermarket.controller.warehouse;

import io.wabm.supermarket.misc.javafx.alert.SimpleErrorAlert;
import io.wabm.supermarket.misc.pojo.Classification;
import io.wabm.supermarket.misc.pojo.Commodity;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.misc.util.ValidCheckHelper;
import io.wabm.supermarket.model.warehouse.CommodityModel;
import io.wabm.supermarket.model.warehouse.RjectCommodityModel;
import io.wabm.supermarket.protocol.CallbackAcceptableProtocol;
import io.wabm.supermarket.protocol.StageSetableController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.util.Assert;

import java.math.BigDecimal;

/**
 * Created by MainasuK on 2016-11-28.
 */
public class RejectCommodityController implements StageSetableController {

    private RjectCommodityModel<Commodity> model;
    private Commodity commodity;
    private int orderDetailID = -1;


    @FXML Stage stage;

    @FXML Label idLabel;
    @FXML Label  barCodeLabel;
    @FXML Label nameLabel;
    @FXML Label storageQuantityLabel;
    @FXML TextField rejectQuantityTextField;

    @FXML Button confirmButton;
    @FXML Button cancelButton;

    @FXML private void confirmButtonPressed() {
        ConsoleLog.print("Button pressed");

        if (!isInputValid()) {
            return ;
        }

        int rejectQuantity = Integer.parseInt(rejectQuantityTextField.getText());
        int remainsQuantity = commodity.getStorage() - rejectQuantity;

        if (remainsQuantity < 0) {
            new SimpleErrorAlert("输入数据无效", "报废商品数量不得大于库存数量", "请检查报废数量").show();
            return ;
        }

        model.reject(commodity, rejectQuantity, exception -> {

            if (null != exception) {
                new SimpleErrorAlert("数据库更新出现错误", "请检查输入字段并重试","").show();
            } else {
                commodity.setStorage(remainsQuantity);

                Platform.runLater(() -> {
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

        model = new RjectCommodityModel<>();
    }

    private void resetControl(Commodity commodity) {
        idLabel.setText(commodity.getCommodityID());
        barCodeLabel.setText(commodity.getBarcode());
        nameLabel.setText(commodity.getName());
        storageQuantityLabel.setText(commodity.getStorage()+"");
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
