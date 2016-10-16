package io.wabm.supermarket.controller.procurement;

import io.wabm.supermarket.application.Main;
import io.wabm.supermarket.controller.AbstractMasterDetailController;
import io.wabm.supermarket.util.ConsoleLog;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

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
        loder.setLocation(Main.class.getResource("../view/procurement/CommoditySupplyDemandView.fxml"));
        setDetailViewFrom(loder);
    }

    @FXML private void commoditySupplyDemandButtonPressed() {
        ConsoleLog.print("Button pressed");

        FXMLLoader loder = new FXMLLoader();
        loder.setLocation(Main.class.getResource("../view/procurement/CommoditySupplyDemandView.fxml"));
        setDetailViewFrom(loder);
    }

    @FXML private void commodityOrderManagementButtonPressed() {
        ConsoleLog.print("Button pressed");

        FXMLLoader loder = new FXMLLoader();
        loder.setLocation(Main.class.getResource("../view/procurement/CommodityOrderManagementView.fxml"));
        setDetailViewFrom(loder);
    }

    @FXML private void commoditySupplierManagementButtonPressed() {
        ConsoleLog.print("Button pressed");

        FXMLLoader loder = new FXMLLoader();
        loder.setLocation(Main.class.getResource("../view/procurement/CommoditySupplierManagementView.fxml"));
        setDetailViewFrom(loder);
    }


}
