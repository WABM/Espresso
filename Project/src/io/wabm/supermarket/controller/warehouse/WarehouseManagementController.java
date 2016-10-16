package io.wabm.supermarket.controller.warehouse;

import io.wabm.supermarket.application.Main;
import io.wabm.supermarket.util.ConsoleLog;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

/**
 * Created by MainasuK on 2016-10-16.
 */
public class WarehouseManagementController {

    @FXML
    Button button;

    @FXML
    AnchorPane detailView;

    @FXML public void initialize() {
        ConsoleLog.print("WarehouseManagementController init");

        FXMLLoader loder = new FXMLLoader();
        loder.setLocation(Main.class.getResource("../view/warehouse/CommodityStockManagementView.fxml"));
        setDetailViewFrom(loder);
    }

    private void setDetailViewFrom(FXMLLoader loader) {
        try {
            BorderPane view = loader.load();
            detailView.getChildren().setAll(view);

            // Set view to fill detail view with full size
            AnchorPane.setTopAnchor(view, 0.0);
            AnchorPane.setBottomAnchor(view, 0.0);
            AnchorPane.setLeftAnchor(view, 0.0);
            AnchorPane.setRightAnchor(view, 0.0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
