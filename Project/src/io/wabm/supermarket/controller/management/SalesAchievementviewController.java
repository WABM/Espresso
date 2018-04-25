package io.wabm.supermarket.controller.management;

import io.wabm.supermarket.controller.SceneController;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.model.management.SalesModel;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

/**
 * Created by liu on 2016-10-25 .
 */
public class SalesAchievementviewController extends SceneController {
    //private HotsaleModel<Hotsale> model;

    private SalesModel model;
    @FXML
    private Button lookButton2;
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private ComboBox month;
    @FXML
    private ComboBox year;
    @FXML
    private BarChart barchart;

    private String c;

    @FXML
    public void initialize() {
        ConsoleLog.print("HotlistController init");
        setupControl();
        setupModel();
        year.getItems().addAll("2016","2015");
        month.getItems().addAll("1", "2", "3", "4", "5","6","7","8","9","10","11","12");
    }

    @FXML public void lookButtonPressed() {
        ConsoleLog.print("lookButton pressed");
        String a=year.getValue().toString();
        ConsoleLog.print(year.getValue().toString());
        String b=month.getValue().toString();
        ConsoleLog.print(month.getValue().toString());
        //c = a+"-"+ b;

        lookButton2.setDisable(true);
        model.fetchData(a,b,isSuccess -> {
            lookButton2.setDisable(false);
            ConsoleLog.print("Fetch is " + (isSuccess ? "success" : "failed"));
            return null;
        },null);
    }

    private void setupControl() {
        barchart.setAnimated(false);
    }

    private void setupModel() {
        model = new SalesModel(barchart);


    }
        /*  model = new HotsaleModel(tableView);
        model.fetchData(isSuccess -> {
            ConsoleLog.print("Fetch is " + (isSuccess ? "success" : "failed"));
            return null;
        });
        */




   /* private void hotgoodsButtonPressed() {
        ConsoleLog.print("Button pressed");
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ViewPathHelper.class.getResource("management/Hot list.fxml"));
            AnchorPane pane = loader.load();

            Stage stage = new Stage();
            stage.setTitle("我不知道写什么好");
            stage.initModality(Modality.APPLICATION_MODAL);

            Scene scene = new Scene(pane);
            stage.setScene(scene);

            StageSetableController contoller = loader.getController();
            contoller.setStage(stage);

            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(datePicker.getValue());
    }*/

    /*public void initialize() {
        ConsoleLog.print("SalesAchievementviewController init");
        test();
    }*/

}