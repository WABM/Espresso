package io.wabm.supermarket.controller.sales;

import io.wabm.supermarket.controller.AbstractMasterDetailController;
import io.wabm.supermarket.misc.pojo.Employee;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.misc.util.SingleLogin;
import io.wabm.supermarket.view.ViewPathHelper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;

/**
 * Created by MainasuK on 2016-10-17.
 */
public class SalesManagementController extends AbstractMasterDetailController {

    @FXML Button transactionRecordsButton;
    @FXML Button HotSalesButton;
    @FXML Button SalesReportsButton;
    @FXML Button commodityPriceManagementButton;
    @FXML Button cashManagementButton;

    @FXML
    public void initialize() {
        ConsoleLog.print("ProcureManagementController init");

        FXMLLoader loder = new FXMLLoader();
        loder.setLocation(ViewPathHelper.class.getResource("sales/TransactionRecordsView.fxml"));
        setDetailViewFrom(loder);
    }

    @FXML private void transactionRecordsButtonPressed() {
        ConsoleLog.print("Button pressed");

        FXMLLoader loder = new FXMLLoader();
        loder.setLocation(ViewPathHelper.class.getResource("sales/TransactionRecordsView.fxml"));
        setDetailViewFrom(loder);
    }

    @FXML private void HotSalesButtonPressed() {
        ConsoleLog.print("Button pressed");

        FXMLLoader loder = new FXMLLoader();
        loder.setLocation(ViewPathHelper.class.getResource("sales/HotSalesView.fxml"));
        setDetailViewFrom(loder);
    }

    @FXML private void SalesReportsButtonPressed() {
        ConsoleLog.print("Button pressed");

        FXMLLoader loder = new FXMLLoader();
        loder.setLocation(ViewPathHelper.class.getResource("sales/SalesReportsView.fxml"));
        setDetailViewFrom(loder);
    }

    @FXML private void commodityPriceManagementButtonPressed() {
        ConsoleLog.print("Button pressed");

        FXMLLoader loder = new FXMLLoader();
        loder.setLocation(ViewPathHelper.class.getResource("sales/CommodityPriceManagementView.fxml"));
        setDetailViewFrom(loder);
    }

    @FXML private void cashManagementButtonPressed() {
        ConsoleLog.print("Button pressed");

        FXMLLoader loder = new FXMLLoader();
        loder.setLocation(ViewPathHelper.class.getResource("sales/CashManagementView.fxml"));
        setDetailViewFrom(loder);
    }
}
