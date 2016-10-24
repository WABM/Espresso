package io.wabm.supermarket.controller.warehouse;

import io.wabm.supermarket.controller.AbstractMasterDetailController;
import io.wabm.supermarket.util.ConsoleLog;
import io.wabm.supermarket.view.ViewPathHelper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;

/**
 * Created by MainasuK on 2016-10-16.
 */
public class WarehouseManagementController extends AbstractMasterDetailController {

    @FXML private Button commodityStockManagementButton;
    @FXML private Button commodityClassificationInfomationManagementButton;
    @FXML private Button commodityClassificationBatchManagementButton;
    @FXML private Button commodityInventoryManagementButton;

    @FXML public void initialize() {
        ConsoleLog.print("WarehouseManagementController init");

        FXMLLoader loder = new FXMLLoader();
        loder.setLocation(ViewPathHelper.class.getResource("warehouse/CommodityStockManagementView.fxml"));
        setDetailViewFrom(loder);
    }

    @FXML private void commodityStockManagementButtonPressed() {
        ConsoleLog.print("Button pressed");

        FXMLLoader loder = new FXMLLoader();
        loder.setLocation(ViewPathHelper.class.getResource("warehouse/CommodityStockManagementView.fxml"));
        setDetailViewFrom(loder);
    }

    @FXML private void commodityClassificationInfomationManagementButtonPressed() {
        ConsoleLog.print("Button pressed");

        FXMLLoader loder = new FXMLLoader();
        loder.setLocation(ViewPathHelper.class.getResource("warehouse/CommodityClassificationInfomationManagementView.fxml"));
        setDetailViewFrom(loder);
    }
    @FXML private void commodityClassificationBatchManagementButtonPressed() {
        ConsoleLog.print("Button pressed");

        FXMLLoader loder = new FXMLLoader();
        loder.setLocation(ViewPathHelper.class.getResource("warehouse/CommodityClassificationBatchManagementView.fxml"));
        setDetailViewFrom(loder);
    }
    @FXML private void commodityInventoryManagementButtonPressed() {
        ConsoleLog.print("Button pressed");

        FXMLLoader loder = new FXMLLoader();
        loder.setLocation(ViewPathHelper.class.getResource("warehouse/CommodityInventoryManagementView.fxml"));
        setDetailViewFrom(loder);
    }
}
