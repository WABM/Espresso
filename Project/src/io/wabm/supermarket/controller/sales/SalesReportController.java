package io.wabm.supermarket.controller.sales;

import io.wabm.supermarket.controller.SceneController;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.model.management.SalesModel;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

/**
 * Created by Administrator on 2016/12/11 0011.
 */
public class SalesReportController extends SceneController {
    private SalesModel model;

    @FXML CategoryAxis xAxis;
    @FXML BarChart barChart;
    @FXML ComboBox yearBox;
    @FXML ComboBox monthBox;
    @FXML Button lookButton;

    @FXML public void initialize(){
        setupControl();
        setupModel();
    }
    @FXML private void lookButtomPressed(){
        ConsoleLog.print("lookButton pressed");

        String year=yearBox.getValue().toString();
        String month=monthBox.getValue().toString();

        model.fetchData(year,month,isSuccess -> {
            ConsoleLog.print("Fetch is " + (isSuccess ? "success" : "failed"));
            return null;
        });
    }
    private void setupControl(){
        barChart.setAnimated(false);

        for (int i=2016;i<=2030;i++)
            yearBox.getItems().addAll(String.valueOf(i));
        for (int i=1;i<=12;i++)
            monthBox.getItems().addAll(String.valueOf(i));
    }
    private void setupModel(){
        model= new SalesModel(barChart);
    }
}
