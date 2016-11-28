package io.wabm.supermarket.controller.management;



import io.wabm.supermarket.controller.SceneController;
import io.wabm.supermarket.misc.pojo.Employee;
import io.wabm.supermarket.model.management.EmployeeInformationModel;
import io.wabm.supermarket.protocol.StageSetableContoller;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.view.ViewPathHelper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

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
        sexColumn.setCellValueFactory(cellData -> cellData.getValue().sexProperty());
        departmentColumn.setCellValueFactory(cellData -> cellData.getValue().departmentProperty());
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
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ViewPathHelper.class.getResource("management/Delete employee.fxml"));
            AnchorPane pane=loader.load();

            Stage stage = new Stage();
            stage.setTitle("我不知道写什么好");
            stage.initModality(Modality.APPLICATION_MODAL);

            Scene scene=new Scene(pane);
            stage.setScene(scene);

            StageSetableContoller contoller=loader.getController();
            contoller.setStage(stage);

            stage.showAndWait();

        }catch(IOException e){
            e.printStackTrace();
        }
    }
    @FXML private void addButtonPressed(){
        ConsoleLog.print("Button pressed");
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ViewPathHelper.class.getResource("management/Add employee.fxml"));
            AnchorPane pane=loader.load();

            Stage stage = new Stage();
            stage.setTitle("我不知道写什么好1");
            stage.initModality(Modality.APPLICATION_MODAL);

            Scene scene=new Scene(pane);
            stage.setScene(scene);

            StageSetableContoller contoller=loader.getController();
            contoller.setStage(stage);

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

            StageSetableContoller contoller=loader.getController();
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

            StageSetableContoller contoller=loader.getController();
            contoller.setStage(stage);

            stage.showAndWait();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
