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
    private boolean isSearching = false;

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
    @FXML Button removeButton;

    @FXML public void initialize() {
        ConsoleLog.print("EmployeeImformationManagermentController init");
        // Stub for develop

        setupModel();
        setupTableView();
        setupTableViewColumn();
        setupControl();
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
        tableView.setRowFactory(tableView -> new TableRow<Employee>() {
            @Override
            protected void updateItem(Employee item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty) {
                    setDisable(!item.isValid());
                    setEditable(item.isValid());
                }
            }
        });

        tableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    removeButton.setDisable(newValue == null);
                    modifyButton.setDisable(newValue == null);
                }
        );
    }

    private void setupControl() {
        removeButton.setDisable(true);
        modifyButton.setDisable(true);
    }


    @FXML private void removeButtonPressed(){
        ConsoleLog.print("Button pressed");
        Employee employee = tableView.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("删除员工");
        alert.setHeaderText("确认删除");
        alert.setContentText("删除 " + employee.getName());

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            ConsoleLog.print("Delete " + employee.getName() + " …");

            model.remove(employee, exception -> {
                if (null == exception) {
                    tableView.refresh();
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
            stage.setTitle("修改员工信息");
            stage.initModality(Modality.APPLICATION_MODAL);

            Scene scene=new Scene(pane);
            stage.setScene(scene);

            StageSetableController controller = loader.getController();
            controller.setStage(stage);

            ((ModifyEmployeeController) controller).setEmployee(tableView.getSelectionModel().getSelectedItem());
            ((CallbackAcceptableProtocol<Employee, DataAccessException>) controller).set((employee) -> {
                ConsoleLog.print("modify employee callback called");
                final DataAccessException[] e = {null};

                model.update(employee, (exception) -> {
                    e[0] = exception;
                    if (null != exception) {
                        exception.printStackTrace();
                    } else {
                        tableView.refresh();
                        ConsoleLog.print("update employee success");
                    }

                    return null;
                });

                return e[0];
            });

            // Show the dialog and wait until the user closes it.
            // (This event thread is blocked until close)
            stage.showAndWait();
           // StageSetableController contoller=loader.getController();
            //contoller.setStage(stage);
        }catch(IOException e){
            e.printStackTrace();
        }



    }
    @FXML private void queryButtonPressed(){
        ConsoleLog.print("Button pressed");

        if (isSearching) {
            isSearching = !isSearching;
            queryButton.setText("查询");
            model.setPredicate(employee -> true);

            return ;
        }

        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ViewPathHelper.class.getResource("management/Query employee information.fxml"));
            AnchorPane pane=loader.load();

            Stage stage = new Stage();
            stage.setTitle("查询员工");
            stage.initModality(Modality.APPLICATION_MODAL);

            Scene scene=new Scene(pane);
            stage.setScene(scene);

            StageSetableController controller=loader.getController();
            controller.setStage(stage);

            ((CallbackAcceptableProtocol<String[], Void>) controller).set((strings) -> {
                        ConsoleLog.print("filter employee callback called");
                        if ((strings[0] == null || "".equals(strings[0])) &&
                                (strings[1] == null || "".equals(strings[1])) &&
                                (strings[2] == null || "".equals(strings[2]))) {
                            model.setPredicate(employee -> true);
                            return null;
                        }
                        queryButton.setText("重置");
                        isSearching = true;

                        model.setPredicate(employee -> {
                            boolean hasID, hasName, hasPhone;

                            hasID = ("" + employee.getEmployeeID()).contains(strings[0]);
                            hasName = employee.getName().contains(strings[1]);
                           // hasdepartment = employee.getDepartment().contains(strings[2]);
                            hasPhone = employee.getPhone().contains(strings[2]);

                            // Debug

                            return hasID && hasName && hasPhone;
                        });

                        return null;
                    });

            stage.showAndWait();

        }catch(IOException e){
            e.printStackTrace();
        }
    }


}
