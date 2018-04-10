package io.wabm.supermarket.controller.sales;

import io.wabm.supermarket.controller.SceneController;
import io.wabm.supermarket.misc.pojo.Hotsale;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.model.sales.HotsaleModel;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by Administrator on 2016/11/20 0020.
 */
public class HotsaleController extends SceneController {

    private HotsaleModel<Hotsale> model;

    @FXML private Button queryButton;
    @FXML ComboBox yearBox;
    @FXML ComboBox monthBox;
    //@FXML private DatePicker datePicker;

    @FXML private TableView<Hotsale> tableView;
    @FXML private TableColumn<Hotsale, String> top;
    @FXML private TableColumn<Hotsale, String> commodityID;
    @FXML private TableColumn<Hotsale, String> barcode;
    @FXML private TableColumn<Hotsale, String> name;
    @FXML private TableColumn<Hotsale, Integer> classificationID;
    @FXML private TableColumn<Hotsale, String> specification;
    @FXML private TableColumn<Hotsale, String> unit;
    @FXML private TableColumn<Hotsale, Integer> quantity;
    @FXML private TableColumn<Hotsale, String> price;
    @FXML private TableColumn<Hotsale, String> totalPrice;

    @FXML public void initialize() {
        ConsoleLog.print("HotsaleController init");
        setupControl();
        setupModel();
        setupTableView();
        setupTableViewColumn();
    }

    @FXML public void queryButtonPressed() {
        ConsoleLog.print("queryButton pressed");
        //String year = String.valueOf(datePicker.getValue().getYear());
        //String month = String.valueOf(datePicker.getValue().getMonthValue());
        String year=yearBox.getValue().toString();
        String month=monthBox.getValue().toString();
        model.fetchData(year,month,isSuccess -> {
            ConsoleLog.print("Fetch is " + (isSuccess ? "success" : "failed"));
            return null;
        });
    }

    private void setupControl() {
        //test();
        for (int i=1990;i<=2030;i++) {
            yearBox.getItems().addAll(String.valueOf(i));
        }
        for (int i=1;i<=9;i++) {
            monthBox.getItems().addAll("0"+i);
        }
        for (int i=10;i<=12;i++){
            monthBox.getItems().addAll(i+"");
        }
    }

    private void setupModel() {
        //String year = String.valueOf(datePicker.getValue().getYear());
        //String month = String.valueOf(datePicker.getValue().getMonthValue());
        String year=String.valueOf(LocalDate.now().getYear());
        String month=String.valueOf(LocalDate.now().getMonthValue());
        yearBox.setValue(year);
        monthBox.setValue(month);
        model = new HotsaleModel<>(tableView);
        model.fetchData(year,month,isSuccess -> {
            ConsoleLog.print("Fetch is " + (isSuccess ? "success" : "failed"));
            return null;
        });
    }

    private void setupTableView() {
        tableView.setEditable(true);
    }

    private void setupTableViewColumn() {
        top.setCellValueFactory(new PropertyValueFactory<Hotsale, String>("top"));
        commodityID.setCellValueFactory(new PropertyValueFactory<Hotsale, String>("commodityID"));
        barcode.setCellValueFactory(new PropertyValueFactory<Hotsale, String>("barcode"));
        name.setCellValueFactory(new PropertyValueFactory<Hotsale, String>("name"));
        classificationID.setCellValueFactory(new PropertyValueFactory<Hotsale, Integer>("classificationID"));
        specification.setCellValueFactory(new PropertyValueFactory<Hotsale, String>("specification"));
        unit.setCellValueFactory(new PropertyValueFactory<Hotsale, String>("unit"));
        quantity.setCellValueFactory(new PropertyValueFactory<Hotsale, Integer>("quantity"));
        price.setCellValueFactory(new PropertyValueFactory<Hotsale, String>("price"));
        totalPrice.setCellValueFactory(new PropertyValueFactory<Hotsale, String>("totalPrice"));
    }

    /*private void test(){
        final String pattern = "yyyy-MM";
        StringConverter converter = new StringConverter<LocalDate>() {
            DateTimeFormatter dateTimeFormatter =
                    DateTimeFormatter.ofPattern(pattern);
            @Override
            public String toString(LocalDate date) {
                if (date != null)
                    return dateTimeFormatter.format(date);
                else
                    return "";
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()){
                    return LocalDate.parse(string,dateTimeFormatter);
                }else
                    return null;
            }
        };
        datePicker.setValue(LocalDate.now().minusMonths(1));
        datePicker.setConverter(converter);
        datePicker.setPromptText(pattern.toLowerCase());
        datePicker.requestFocus();
    }*/
}
