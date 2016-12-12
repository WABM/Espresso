package io.wabm.supermarket.controller.sales;

import io.wabm.supermarket.misc.javafx.alert.SimpleErrorAlert;
import io.wabm.supermarket.misc.pojo.CommodityPriceInformation;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.protocol.StageSetableController;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/12/12 0012.
 */
public class ModifyPriceController implements StageSetableController {
    private CommodityPriceInformation commodityPriceInformation;
    @FXML Stage stage;
    @FXML TextField nameText;
    @FXML TextField oldPriceText;
    @FXML TextField newPriceText;

    @FXML private void cancelButtonPressed(){
        ConsoleLog.print("Button pressed");

        stage.close();
    }
    @FXML private void comfirmButtonPressed(){
        if (check())
        {
            commodityPriceInformation.setPrice(BigDecimal.valueOf(Double.valueOf(newPriceText.getText())));
            stage.close();
        }
    }
    public void setStage(Stage stage){
        this.stage = stage;
    }
    public void setData(CommodityPriceInformation commodityPriceInformation){
        this.commodityPriceInformation = commodityPriceInformation;

        nameText.setText(commodityPriceInformation.getCommodityName());
        oldPriceText.setText(String.valueOf(commodityPriceInformation.getPrice()));
    }
    private boolean isDouble(String str) {
        if (null == str || "".equals(str)) {
            return false;
        }
        Pattern pattern = Pattern.compile("^[0-9]+(.[0-9]*)?$");
        return pattern.matcher(str).matches();
    }
    private Boolean check() {
        if (!isDouble(newPriceText.getText())) {
            SimpleErrorAlert alert = new SimpleErrorAlert("输入格式错误","只能输入数字","请重新输入！");
            alert.show();
            return false;
        }
        return true;
    }
}
