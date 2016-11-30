package io.wabm.supermarket.controller.management;



import io.wabm.supermarket.controller.SceneController;
import io.wabm.supermarket.misc.javafx.alert.SimpleErrorAlert;
import io.wabm.supermarket.misc.javafx.alert.SimpleSuccessAlert;
import io.wabm.supermarket.misc.pojo.Employee;
import io.wabm.supermarket.model.management.EmployeeInformationModel;
import io.wabm.supermarket.protocol.CallbackAcceptableProtocol;
import io.wabm.supermarket.protocol.StageSetableController;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.view.ViewPathHelper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.dao.DataAccessException;

import java.io.IOException;
import java.util.Optional;

/**
 * Created by liu on 2016-10-25 .
 */
public class EmployeeImformationManagementController extends SceneController{


    private EmployeeInformationModel<Employee> model;

    @FXML TableView<Employee> tableView;
    @FXML TableColumn<Employee, Integer> idColumn;
    @FXML TableColumn<Employee, String> nameColumn;
    @FXML TableColumn<Employee, String> ageColumn;
    @FXML TableColumn<Employee, String> sexColumn;
    @FXML TableColumn<Employee, String> departmentColumn;
    @FXML TableColumn<Employee, String> phoneColumn;
    @FXML TableColumn<Employee, String> entrydateColumn;


    @FXML Button deleteButton;
    @FXML Button addButton;
    @FXML Button modifyButton;
    @FXML Button queryButton;

    @FXML public void initialize() {
        ConsoleLog.print("EmployeeImformationManagermentController init");
        // Stub for develop
        setupControl();
        setupModel();
        setupTableView();
        setupTableViewColumn();

    }


    private void setupTableViewColumn() {
        idColumn.setCellValueFactory(cellData -> cellData.getValue().employeeIDProperty().asObject());

        ageColumn.setCellValueFactory(cellData -> cellData.getValue().birthdateProperty());

        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        sexColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getSexString()));
        departmentColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDepartmentString()));
        phoneColumn.setCellValueFactory(cellData -> cellData.getValue().phoneProperty());
        entrydateColumn.setCellValueFactory(cellData -> cellData.getValue().entrydateProperty());
    }

    private void setupModel() {
        model = new EmployeeInformationModel<>(tableView);
        model.fetchData(isSuccess -> {
            ConsoleLog.print("Fetch is " + (isSuccess ? "success" : "failed"));
            return null;
        });
    }

    private void setupTableView() {
    }

    private void setupControl() {
    }


    @FXML private void deleteButtonPressed(){
        ConsoleLog.print("Button pressed");
        Employee employee = tableView.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("删除员工");
        alert.setHeaderText("确认删除");
        alert.setContentText("删除 " + employee.getName());
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            ConsoleLog.print("Delete " + employee.getName() + " …");

            model.delete(employee, exception -> {
                if (null == exception) {
                    new SimpleSuccessAlert("删除成功", "", employee.getName() + " 删除成功").show();
                } else {
                    new SimpleErrorAlert("删除失败", "删除数据遇到了错误", "请重试").show();
                }
                return null;
            });
        } else {
            ConsoleLog.print("Delete process cancel");
        }
    }

        /*try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ViewPathHelper.class.getResource("management/Delete employee.fxml"));
            AnchorPane pane=loader.load();

            Stage stage = new Stage();
            stage.setTitle("我不知道写什么好");
            stage.initModality(Modality.APPLICATION_MODAL);

            Scene scene=new Scene(pane);
            stage.setScene(scene);

            StageSetableController contoller=loader.getController();
            contoller.setStage(stage);

            stage.showAndWait();

        }catch(IOException e){
            e.printStackTrace();
        }
    }*/
    @FXML private void addButtonPressed(){
        ConsoleLog.print("Button pressed");
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ViewPathHelper.class.getResource("management/Add employee.fxml"));
            AnchorPane pane=loader.load();

            Stage stage = new Stage();
            stage.setTitle("添加员工");
            stage.initModality(Modality.APPLICATION_MODAL);

            Scene scene=new Scene(pane);
            stage.setScene(scene);

            StageSetableController controller=loader.getController();
            controller.setStage(stage);
            
            ((CallbackAcceptableProtocol<Employee, DataAccessException>) controller).set((employee) -> {
                ConsoleLog.print("Add employee callback called");
                final DataAccessException[] e = {null};

                model.add(employee, (exception) -> {
                    e[0] = exception;
                    if (null != exception) {
                        exception.printStackTrace();
                    } else {
                        ConsoleLog.print("Insert employee success");
                    }

                    return null;
                });
                return e[0];
            });
            stage.showAndWait();

        }catch(IOException e){
            e.printStackTrace();
        }
    }
    @FXML private void modifyButtonPressed(){
        ConsoleLog.print("Button pressed");
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ViewPathHelper.class.getResource("management/Modify employee.fxml"));
            AnchorPane pane=loader.load();

            Stage stage = new Stage();
            stage.setTitle("我不知道写什么好");
            stage.initModality(Modality.APPLICATION_MODAL);

            Scene scene=new Scene(pane);
            stage.setScene(scene);

            StageSetableController contoller=loader.getController();
            contoller.setStage(stage);

            stage.showAndWait();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    @FXML private void queryButtonPressed(){
        ConsoleLog.print("Button pressed");
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ViewPathHelper.class.getResource("management/Query employee information.fxml"));
            AnchorPane pane=loader.load();

            Stage stage = new Stage();
            stage.setTitle("我不知道写什么好");
            stage.initModality(Modality.APPLICATION_MODAL);

            Scene scene=new Scene(pane);
            stage.setScene(scene);

            StageSetableController contoller=loader.getController();
            contoller.setStage(stage);

            stage.showAndWait();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
