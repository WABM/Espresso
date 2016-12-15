package io.wabm.supermarket.controller;

import io.wabm.supermarket.misc.pojo.Employee;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.controller.warehouse.WarehouseManagementController;
import io.wabm.supermarket.misc.util.SingleLogin;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;

/**
 * Created by MainasuK on 2016-10-16.
 */
public class MainController {

    @FXML WarehouseManagementController tabController;
    @FXML Tab saleTab;
    @FXML Tab procurementTab;
    @FXML Tab managementTab;
    @FXML Tab warehouseTab;

    @FXML public void initialize() {
        ConsoleLog.print("MainController init");

        permissionSale();
        permissionManagement();
        permissionProcurement();
        permissionWarehouse();
    }
    private boolean permissionSale(){
        Employee employee = SingleLogin.getInstance().getEmployee();
        ConsoleLog.print(employee.getDepartmentString());
        if (employee.getDepartmentString()=="销售管理员"
                ||employee.getDepartmentString()=="总管理员") {
            return true;
        } else {
            saleTab.setDisable(true);
            return false;
        }
    }
    private boolean permissionProcurement(){
        Employee employee = SingleLogin.getInstance().getEmployee();
        ConsoleLog.print(employee.getDepartmentString());
        if (employee.getDepartmentString()=="采购管理员"
                ||employee.getDepartmentString()=="总管理员") {
            return true;
        }else {
            procurementTab.setDisable(true);
            return false;
        }
    }
    private boolean permissionManagement(){
        Employee employee = SingleLogin.getInstance().getEmployee();
        ConsoleLog.print(employee.getDepartmentString());
        if (employee.getDepartmentString()=="总管理员") {
            return true;
        }else {
            managementTab.setDisable(true);
            return false;
        }
    }
    private boolean permissionWarehouse(){
        Employee employee = SingleLogin.getInstance().getEmployee();
        ConsoleLog.print(employee.getDepartmentString());
        if (employee.getDepartmentString()=="仓库管理员"
                ||employee.getDepartmentString()=="总管理员") {
            return true;
        }else {
            warehouseTab.setDisable(true);
            return false;
        }
    }
}
