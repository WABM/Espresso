package io.wabm.supermarket.controller.management;

import io.wabm.supermarket.controller.SceneController;
import io.wabm.supermarket.misc.pojo.Hotsale;
import io.wabm.supermarket.model.sales.HotsaleModel;
import io.wabm.supermarket.protocol.StageSetableController;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.view.ViewPathHelper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by liu on 2016-10-25 .
 */
public class SalesAchievementviewController extends SceneController {
    private HotsaleModel<Hotsale> model;

    @FXML
    private Button queryButton;
    @FXML
    private DatePicker datePicker;

    @FXML
    private TableView<Hotsale> tableView;
    @FXML
    private TableColumn<Hotsale, String> top;
    @FXML
    private TableColumn<Hotsale, String> commodityID;
    @FXML
    private TableColumn<Hotsale, String> barcode;
    @FXML
    private TableColumn<Hotsale, String> name;
    @FXML
    private TableColumn<Hotsale, Integer> classificationID;
    @FXML
    private TableColumn<Hotsale, String> specification;
    @FXML
    private TableColumn<Hotsale, String> unit;
    @FXML
    private TableColumn<Hotsale, Integer> quantity;
    @FXML
    private TableColumn<Hotsale, String> price;
    @FXML
    private TableColumn<Hotsale, String> totalPrice;

    @FXML
    public void initialize() {
        ConsoleLog.print("HotlistController init");
        setupControl();
        setupModel();
        setupTableView();
        setupTableViewColumn();
        test();
    }

    @FXML public void queryButtonPressed() {
        ConsoleLog.print("queryButton pressed");
        System.out.println(datePicker.getValue());
    }

    private void setupControl() {
    }

    private void setupModel() {
        model = new HotsaleModel<>(tableView);
        model.fetchData(isSuccess -> {
            ConsoleLog.print("Fetch is " + (isSuccess ? "success" : "failed"));
            return null;
        });
        /*  model = new HotsaleModel(tableView);
        model.fetchData(isSuccess -> {
            ConsoleLog.print("Fetch is " + (isSuccess ? "success" : "failed"));
            return null;
        });
        */
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
    private void test(){
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

        datePicker.setConverter(converter);
        datePicker.setPromptText(pattern.toLowerCase());
        datePicker.requestFocus();
    }
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