package io.wabm.supermarket.controller.warehouse;

import io.wabm.supermarket.controller.SceneController;
import io.wabm.supermarket.misc.pojo.Commodity;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.view.ViewPathHelper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;

/**
 * Created by MainasuK on 2016-11-16.
 */
public class CommodityInformationController extends SceneController {

    @FXML TableView<Commodity> tableView;

    @FXML Button backButton;

    @FXML private void backButtonPressed() {
        ConsoleLog.print("button pressed");

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ViewPathHelper.class.getResource("warehouse/CommodityClassificationInformationManagementView.fxml"));

        navigationTo(loader);
    }

    @FXML public void initialize() {
        ConsoleLog.print("CommodityInformationController init");

    }

}
