package io.wabm.supermarket.controller.sales;

import io.wabm.supermarket.misc.javafx.alert.SimpleErrorAlert;
import io.wabm.supermarket.misc.pojo.Classification;
import io.wabm.supermarket.protocol.StageSetableController;
import io.wabm.supermarket.misc.util.ConsoleLog;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/10/27 0027.
 */
public class ModifyPriceByTypeController implements StageSetableController {

    @FXML Stage stage;
    private Classification classification;

    @FXML TextField classificationText;
    @FXML TextField profitRateText;
    @FXML TextField taxRateText;

    @FXML Button comfirmButton;
    @FXML Button cancelButton;

    public void setStage(Stage stage){
        this.stage = stage;
    }

    @FXML private void comfirmButtonPressed() {
        ConsoleLog.print("Button pressed");
        if (check()) {
            classification.setName(classificationText.getText());
            classification.setProfitRate(Double.valueOf(profitRateText.getText()));
            classification.setTaxRate(Double.valueOf(taxRateText.getText()));
            stage.close();
        }
    }

    @FXML private void cancelButtonPressed() {
        ConsoleLog.print("Button pressed");

        stage.close();
    }
    public void setDate(Classification classification)
    {
        this.classification = classification;

        classificationText.setText(classification.getName());
        profitRateText.setText(String.valueOf(classification.getProfitRate()));
        taxRateText.setText(String.valueOf(classification.getTaxRate()));
    }
    private Boolean check() {
        if (!isDouble(profitRateText.getText()) &&isDouble(taxRateText.getText())) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("输入格式错误");
            alert.setHeaderText("只能输入数字");
            alert.setContentText("请重新输入！");
            Optional<ButtonType> result = alert.showAndWait();
            return false;
        }
        if (Double.valueOf(profitRateText.getText())>1.0
                ||Double.valueOf(taxRateText.getText())>1.0)
        {
            SimpleErrorAlert alert = new SimpleErrorAlert("输入错误","税率和利润率不能大于100%","请重新输入！");
            alert.show();
            return false;
        }
        return true;
    }
    private boolean isDouble(String str) {
        if (null == str || "".equals(str)) {
            return false;
        }
        Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");
        return pattern.matcher(str).matches();
    }
}
