package io.wabm.supermarket.controller.warehouse;

import io.wabm.supermarket.misc.javafx.alert.SimpleErrorAlert;
import io.wabm.supermarket.misc.pojo.Classification;
import io.wabm.supermarket.misc.pojo.Commodity;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.misc.util.ValidCheckHelper;
import io.wabm.supermarket.model.warehouse.CommodityModel;
import io.wabm.supermarket.protocol.CallbackAcceptableProtocol;
import io.wabm.supermarket.protocol.StageSetableController;
import javafx.application.Platform;
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

import java.math.BigDecimal;

/**
 * Created by MainasuK on 2016-11-28.
 */
public class AddCommodityController implements StageSetableController, CallbackAcceptableProtocol<Commodity, DataAccessException> {

    private CommodityModel<Classification> model;
    private Callback<Commodity, DataAccessException> callback = null;
    private int classificationID;

    @FXML Stage stage;

    @FXML TextField idTextField;
    @FXML TextField barCodeTextField;
    @FXML TextField nameTextField;
    @FXML ComboBox<Classification> classificationComboBox;
    @FXML TextField specificationTextField;
    @FXML TextField unitTextField;
    @FXML TextField deliverySpecificationTextField;
    @FXML TextField shelfLifeTextField;

    @FXML Button confirmButton;
    @FXML Button cancelButton;

    @FXML private void confirmButtonPressed() {
        ConsoleLog.print("Button pressed");

        if (!isInputValid()) {
            return ;
        }

        if (callback == null) {
            ConsoleLog.print("Callback not set");
            return ;
        }

        String shelfLifeStr = shelfLifeTextField.getText();
        if (shelfLifeStr == null || shelfLifeStr.equals("")) {
            shelfLifeStr = "0";
        }

        BigDecimal price = new BigDecimal("0.00");
        price.setScale(2);

        Commodity commodity = new Commodity(
                idTextField.getText(),
                classificationComboBox.getValue().getClassificationID(),
                barCodeTextField.getText(),
                nameTextField.getText(),
                specificationTextField.getText(),
                unitTextField.getText(),
                price,
                Integer.parseInt(deliverySpecificationTextField.getText()),
                Integer.parseInt(shelfLifeStr),
                true
        );
        commodity.setClassificationName(classificationComboBox.getValue().getName());

        DataAccessException exception = null;
        if (null != (exception = callback.call(commodity))) {
            if (exception instanceof DuplicateKeyException) {
                String message = exception.getLocalizedMessage();
                ConsoleLog.print(message);

                if (message.contains("bar_code_UNIQUE")) {
                    new SimpleErrorAlert("数据库更新出现错误", "请检查输入条形码", "该条形码在数据库中已存在").show();
                } else if (message.contains("commodity_id_UNIQUE")) {
                    new SimpleErrorAlert("数据库更新出现错误", "请检查输入商品编码", "该商品编码在数据库中已存在").show();
                }   // end if message.contains(…)
            } else {
                new SimpleErrorAlert("数据库更新出现错误", "请检查输入字段并重试","").show();
            }   // end if (exception instanceof …)

        } else {
            stage.close();
        }   // end if (null != callback…)

    }

    @FXML private void cancelButtonPressed() {
        ConsoleLog.print("Button pressed");

        stage.close();
    }

    /**
     * Use JavaFX initialize. This method will be called after control init.
     */
    @FXML public void initialize() {
        ConsoleLog.print("AddCommodityController init");

        setupModel();
    }

    // MARK: Public config method

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void set(Callback<Commodity, DataAccessException> callback) {
        this.callback = callback;
    }

    public void fetchClassificationWithDefault(int classificationID) {
        ConsoleLog.print("Set default classification id: " + classificationID);
        this.classificationID = classificationID;

        model.fetchClassification(isSuccess -> {
            ConsoleLog.print("Fetch classification is: " + isSuccess);

            Platform.runLater(() -> {
                if (isSuccess) {
                    classificationComboBox.getItems().stream().filter(classification ->
                            classification.getClassificationID() == classificationID
                    ).findFirst().ifPresent(classification -> {
                        int index = classificationComboBox.getItems().indexOf(classification);
                        classificationComboBox.getSelectionModel().select(index);
                    });
                }
            });

            return null;
        });
    }


    // MARK: Setup method

    private void setupModel() {
        Assert.notNull(classificationComboBox);
        model = new CommodityModel<>(classificationComboBox);
    }

    /**
     * Validates the user input in the text fields
     *
     * @return
     */
    private boolean isInputValid() {
        ValidCheckHelper helper = new ValidCheckHelper();
        String errorMessage = "";

        if (idTextField.getText().length() != 8) {
            errorMessage += "需要 8 位商品编码\n";
        }

        errorMessage += helper.checkTypeAndLength(idTextField, "编码", 8);
        errorMessage += helper.checkTypeAndLength(barCodeTextField, "条形码", 16);
        errorMessage += helper.checkTypeAndLength(nameTextField, "商品名称", 45);
        errorMessage += helper.checkTypeAndLength(specificationTextField, "商品规格", 45);
        errorMessage += helper.checkTypeAndLength(unitTextField, "商品单位", 45);
        errorMessage += helper.checkTypeAndLengthForIntegerNotZero(deliverySpecificationTextField, "配送规格", 11);
//        errorMessage += helper.checkTypeAndLengthForInteger(shelfLifeTextField, "保质期", 11);

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
