package io.wabm.supermarket.controller.sales;

import io.wabm.supermarket.controller.SceneController;
import io.wabm.supermarket.misc.javafx.tablecell.HyperlinkTableCell;
import io.wabm.supermarket.misc.pojo.TransactionRecord;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.model.sales.TransationRecordModel;
import io.wabm.supermarket.protocol.CellFactorySetupCallbackProtocol;
import io.wabm.supermarket.view.ViewPathHelper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.time.LocalDate;

/**
 * Created by Administrator on 2016/11/19 0019.
 */
public class TransactionRecordController extends SceneController {

    private TransationRecordModel<TransactionRecord> model;

    @FXML private TableView<TransactionRecord> tableView;

    @FXML private TableColumn<TransactionRecord,String> recordID;
    @FXML private TableColumn<TransactionRecord,String> emploee_ID;
    @FXML private TableColumn<TransactionRecord,Double> totalprice;
    //@FXML private TableColumn<TransactionRecord,LocalDate> date;
    @FXML private TableColumn<TransactionRecord,String> date;
    @FXML private TableColumn<TransactionRecord, Hyperlink> actionColumn;

    @FXML private Button queryButton;
    @FXML private DatePicker datePickerFront;
    @FXML private DatePicker datePickerRear;

    @FXML public void initialize(){
        ConsoleLog.print("TransactionRecordController init");

        setupControl();
        setupModel();
        setupTableView();
        setupTableViewColumn();
    }
    @FXML public void queryButtonPressed(){
        ConsoleLog.print("queryButton pressed");

        String front = datePickerFront.getValue().toString();
        String rear = datePickerRear.getValue().toString();
        model.fetchData(front,rear,isSuccess -> {
            ConsoleLog.print("Fetch is " + (isSuccess ? "success" : "failed"));
            return null;
        });
    }

    private void setupControl() {
        datePickerFront.setValue(LocalDate.now().minusMonths(1));
        datePickerRear.setValue(LocalDate.now());
    }
    private void setupModel() {
        String front = datePickerFront.getValue().toString();
        String rear = datePickerRear.getValue().toString();

        model = new TransationRecordModel<>(tableView);
        model.fetchData(front,rear,isSuccess -> {
            ConsoleLog.print("Fetch is " + (isSuccess ? "success" : "failed"));
            return null;
        });
    }
    private void setupTableView(){
        tableView.setEditable(true);
    }
    private void setupTableViewColumn() {
        recordID.setCellValueFactory(new PropertyValueFactory<TransactionRecord, String>("recordID"));
        emploee_ID.setCellValueFactory(new PropertyValueFactory<TransactionRecord, String>("emploee_ID"));
        totalprice.setCellValueFactory(new PropertyValueFactory<TransactionRecord, Double>("totalprice"));
        //date.setCellValueFactory(new PropertyValueFactory<TransactionRecord, LocalDate>("date"));
        date.setCellValueFactory(new PropertyValueFactory<TransactionRecord, String>("date"));

        actionColumn.setCellFactory(actionColumnSetupCallback);
        actionColumn.setCellValueFactory(cellData -> {

            return new SimpleObjectProperty<>(new Hyperlink("查看"));
        });

        datePickerRear.setDayCellFactory(new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(DatePicker param) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item.isBefore(
                                datePickerFront.getValue().plusDays(1))
                                ) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;");
                        }
                    }
                };
            }
        });
        datePickerFront.setDayCellFactory(new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(DatePicker param) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item.isAfter(
                                datePickerRear.getValue().minusDays(1))
                                ) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;");
                        }
                    }
                };
            }
        });
    }

    private TransactionRecordDetailController transactionRecordDetailController;
    private CellFactorySetupCallbackProtocol<TransactionRecord, Hyperlink> actionColumnSetupCallback = (column) -> new HyperlinkTableCell() {
        @Override
        protected void updateItem(Hyperlink item, boolean empty) {
            super.updateItem(item, empty);

            setAlignment(Pos.CENTER);

            // Check empty first
            if (!empty) {
                item.setOnAction(event -> {
                    TransactionRecord transactionRecord = (TransactionRecord) getTableRow().getItem();

                    FXMLLoader loder = new FXMLLoader();
                    loder.setLocation(ViewPathHelper.class.getResource("sales/TransactionRecordDetailView.fxml"));

                    // Bind controller when you first call it.
                    navigationTo(loder, controller -> {

                        if (transactionRecordDetailController == null) {
                            ConsoleLog.print("Bind controller success");
                            transactionRecordDetailController = ((TransactionRecordDetailController) controller);
                        }
                        return null;
                    });
                    // Then dispatch the task to controller you hold.
                    transactionRecordDetailController.fetchWith(Integer.parseInt(transactionRecord.getRecordID()));
                });
            }
        }
    };
}
