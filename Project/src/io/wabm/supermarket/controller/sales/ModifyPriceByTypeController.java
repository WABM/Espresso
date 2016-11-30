package io.wabm.supermarket.controller.sales;

import io.wabm.supermarket.misc.pojo.Classification;
import io.wabm.supermarket.protocol.StageSetableController;
import io.wabm.supermarket.misc.util.ConsoleLog;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Optional;

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
        if (profitRateText.getText().isEmpty() ||taxRateText.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("修改");
            alert.setHeaderText("");
            alert.setContentText("填写内容不能为空");
            Optional<ButtonType> result = alert.showAndWait();
            return false;
        }
        return true;
    }

}
