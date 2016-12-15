package io.wabm.supermarket.controller.sales;

import io.wabm.supermarket.misc.javafx.alert.SimpleErrorAlert;
import io.wabm.supermarket.misc.pojo.CashInformation;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.model.sales.CashInformationModel;
import io.wabm.supermarket.protocol.StageSetableController;
import io.wabm.supermarket.view.ViewPathHelper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/12/8 0008.
 */
public class CheckCashController implements StageSetableController {
    private CashInformationModel<CashInformation> model;
    private TableView tableView;
    @FXML Stage stage;
    @FXML TextField employ_idText;
    @FXML TextField nameText;
    @FXML TextField moneyINText;
    @FXML TextField moneyOUTText;
    @FXML Button cancel;
    @FXML Button okButton;

    @FXML public void initialize(){
        ConsoleLog.print("CheckCashController init");
//        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(ViewPathHelper.class.getResource("CashManagementView.fxml"));
//        CashManagementController controller = loader.getController();
        //model = new CashInformationModel(new TableView(FXCollections.observableArrayList()));

    }
    @FXML private void cancelPressed(){
        ConsoleLog.print("Button pressed");

        stage.close();
    }
    @FXML private void okButtonPressed()
    {
        model = new CashInformationModel<>(tableView);
        //String date = "2016-11-5";
        String date = LocalDate.now().toString();

        ConsoleLog.print("Button pressed");

        if (isDouble(employ_idText.getText()) && isDouble(moneyINText.getText())
                &&isDouble(moneyOUTText.getText())) {
            BigDecimal in = new BigDecimal(Double.valueOf(moneyINText.getText()));
            BigDecimal out = new BigDecimal(Double.valueOf(moneyOUTText.getText()));
            model.checkAllPriceData(employ_idText.getText(), in,
                    out, date, isSuccess -> {
                        return null;
                    });
            stage.close();
        }
        else {
            SimpleErrorAlert alert = new SimpleErrorAlert("输入格式错误","只能输入数字","请重新输入！");
            alert.show();
        }
    }
    public void setStage(Stage stage){
        this.stage = stage;
    }
    public void setTableView(TableView tableView){ this.tableView = tableView;}

    private boolean isDouble(String str) {
        if (null == str || "".equals(str)) {
            return false;
        }
        Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");
        return pattern.matcher(str).matches();
    }
}
