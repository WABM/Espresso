package io.wabm.supermarket.controller.management;

import io.wabm.supermarket.application.Main;
import io.wabm.supermarket.controller.AbstractMasterDetailController;
import io.wabm.supermarket.util.ConsoleLog;
import io.wabm.supermarket.view.ViewPathHelper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;

/**
 * Created by MainasuK on 2016-10-17.
 */
public class ManagementSystemController extends AbstractMasterDetailController {

    @FXML Button employeeInformationManagementButton;
    @FXML Button salesAchievementButton;

    @FXML public void initialize() {
        ConsoleLog.print("ManagementSystemController init");

        FXMLLoader loder = new FXMLLoader();
        loder.setLocation(ViewPathHelper.class.getResource("management/EmployeeInfomationManagementView.fxml"));
        setDetailViewFrom(loder);
    }

    @FXML private void employeeInformationManagementButtonPressed() {
        ConsoleLog.print("Button pressed");

        FXMLLoader loder = new FXMLLoader();
        loder.setLocation(ViewPathHelper.class.getResource("management/EmployeeInfomationManagementView.fxml"));
        setDetailViewFrom(loder);
    }

    @FXML private void salesAchievementButtonPressed() {
        ConsoleLog.print("Button pressed");

        FXMLLoader loder = new FXMLLoader();
        loder.setLocation(ViewPathHelper.class.getResource("management/SalesAchievementView.fxml"));
        setDetailViewFrom(loder);
    }
}
