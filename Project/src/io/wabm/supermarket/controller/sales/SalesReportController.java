package io.wabm.supermarket.controller.sales;

import io.wabm.supermarket.controller.SceneController;
import io.wabm.supermarket.misc.pojo.InventoryReport;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.model.management.SalesModel;
import io.wabm.supermarket.model.sales.InventoryReportModel;
import io.wabm.supermarket.model.sales.SalesGetModel;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.control.*;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2016/12/11 0011.
 */
public class SalesReportController extends SceneController {
    private SalesModel model;
    private InventoryReportModel<InventoryReport> reportModel;
    private SalesGetModel getModel;

    @FXML
    CategoryAxis xAxis;
    @FXML
    BarChart barChart;
    @FXML
    BarChart barChart1;
    @FXML
    ComboBox yearBox;
    @FXML
    ComboBox monthBox;
    @FXML
    Button lookButton;
    @FXML private Label cost;
    @FXML private Label earn;
    @FXML private Label profit;
    @FXML private Label all;

    @FXML
    TableView<InventoryReport> tableView;
    @FXML
    TableColumn<InventoryReport, String> commodityID;
    @FXML
    TableColumn<InventoryReport, String> commodityName;
    @FXML
    TableColumn<InventoryReport, Integer> quantity;
    @FXML
    TableColumn<InventoryReport, BigDecimal> price;
    @FXML
    TableColumn<InventoryReport, String> tpye;
    @FXML
    TableColumn<InventoryReport, String> employeeName;
    @FXML
    TableColumn<InventoryReport, String> date;

    @FXML
    ComboBox yearBoxC;
    @FXML
    ComboBox yearBox1;
    @FXML
    ComboBox monthBoxC;
    @FXML
    Button lookButtonC;
    @FXML
    Button lookButton1;

    @FXML
    public void initialize() {
        setupControl();
        setupModel();
        setupTableView();
        setupTableViewColumn();
    }

    @FXML
    private void lookButtomPressed() {
        ConsoleLog.print("lookButton pressed");

        String year = yearBox.getValue().toString();
        String month = monthBox.getValue().toString();

        model.fetchData(year, month, isSuccess -> {
            ConsoleLog.print("Fetch is " + (isSuccess ? "success" : "failed"));
            return null;
        },all);
    }

    @FXML
    private void lookButtomPressed1() {
        ConsoleLog.print("lookButton1 pressed");

        String year = yearBox1.getValue().toString();

        getModel.fetchData(year, isSuccess -> {
            ConsoleLog.print("Fetch is " + (isSuccess ? "success" : "failed"));
            return null;
        },cost,earn,profit);
    }

    @FXML
    private void lookButtomCPressed() {
        ConsoleLog.print("lookButton pressed");

        String year = yearBoxC.getValue().toString();
        String month = monthBoxC.getValue().toString();

        reportModel.ChooseData(year, month, isSuccess -> {
            ConsoleLog.print("Fetch is " + (isSuccess ? "success" : "failed"));
            return null;
        });
    }

    private void setupControl() {
        barChart.setAnimated(true);
        //使柱体居中
        barChart.setBarGap(0);
        barChart1.setBarGap(0);

        for (int i = 1990; i <= 2030; i++) {
            yearBox.getItems().addAll(String.valueOf(i));
            yearBoxC.getItems().addAll(String.valueOf(i));
            yearBox1.getItems().addAll(String.valueOf(i));
        }
        for (int i = 1; i <= 9; i++) {
            monthBox.getItems().addAll("0" + i);
            monthBoxC.getItems().addAll("0" + i);
        }
        for (int i = 10; i <= 12; i++) {
            monthBox.getItems().addAll(i + "");
            monthBoxC.getItems().addAll(i + "");
        }
    }

    private void setupModel() {
        model = new SalesModel(barChart);
        getModel = new SalesGetModel(barChart1);
        reportModel = new InventoryReportModel(tableView);
        reportModel.fetchData(isSuccess -> {
            ConsoleLog.print("Fetch is " + (isSuccess ? "success" : "failed"));
            return null;
        });
    }

    private void setupTableView() {
        tableView.setEditable(true);
    }

    private void setupTableViewColumn() {
        commodityID.setCellValueFactory(cellData -> cellData.getValue().commodityIDProperty());
        commodityName.setCellValueFactory(cellData -> cellData.getValue().commodityNameProperty());
        quantity.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());
        price.setCellValueFactory(cellData -> cellData.getValue().priceProperty());
        tpye.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getTypeString()));
        employeeName.setCellValueFactory(cellData -> cellData.getValue().employeeNameProperty());
        date.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
    }
}
