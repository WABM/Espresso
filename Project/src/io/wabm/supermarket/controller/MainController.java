package io.wabm.supermarket.controller;

import io.wabm.supermarket.util.ConsoleLog;
import io.wabm.supermarket.controller.warehouse.WarehouseManagementController;
import javafx.fxml.FXML;

/**
 * Created by MainasuK on 2016-10-16.
 */
public class MainController {

    @FXML WarehouseManagementController tabController;

    @FXML public void initialize() {
        ConsoleLog.print("MainController init");
    }

}
