package io.wabm.supermarket.controller.procurement;

import io.wabm.supermarket.controller.AbstractMasterDetailController;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.view.ViewPathHelper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;

/**
 * Created by MainasuK on 2016-10-16.
 */
public class ProcureManagementController extends AbstractMasterDetailController {

    @FXML Button commoditySupplyDemandButton;
    @FXML Button commodityOrderManagementButton;
    @FXML Button commoditySupplierManagementButton;

    @FXML public void initialize() {
        ConsoleLog.print("ProcureManagementController init");

        FXMLLoader loder = new FXMLLoader();
        loder.setLocation(ViewPathHelper.class.getResource("procurement/CommoditySupplyDemandView.fxml"));
        setDetailViewFrom(loder);
    }

    @FXML private void commoditySupplyDemandButtonPressed() {
        ConsoleLog.print("Button pressed");

        FXMLLoader loder = new FXMLLoader();
        loder.setLocation(ViewPathHelper.class.getResource("procurement/CommoditySupplyDemandView.fxml"));
        setDetailViewFrom(loder);
    }

    @FXML private void commodityOrderManagementButtonPressed() {
        ConsoleLog.print("Button pressed");

        FXMLLoader loder = new FXMLLoader();
        loder.setLocation(ViewPathHelper.class.getResource("procurement/CommodityOrderManagementView.fxml"));
        setDetailViewFrom(loder);
    }

    @FXML private void commoditySupplierManagementButtonPressed() {
        ConsoleLog.print("Button pressed");

        FXMLLoader loder = new FXMLLoader();
        loder.setLocation(ViewPathHelper.class.getResource("procurement/CommoditySupplierManagementView.fxml"));
        setDetailViewFrom(loder);
    }


}
