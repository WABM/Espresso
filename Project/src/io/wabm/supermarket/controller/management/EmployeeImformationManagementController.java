package io.wabm.supermarket.controller.management;

import io.wabm.supermarket.protocol.StageSetableContoller;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.view.ViewPathHelper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by liu on 2016-10-25 .
 */
public class EmployeeImformationManagementController {
    @FXML Button deleteButton;
    @FXML Button addButton;
    @FXML Button modifyButton;
    @FXML Button queryButton;
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
