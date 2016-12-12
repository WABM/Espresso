package io.wabm.supermarket.misc.javafx.tablecell;

import java.time.LocalDate;

import javafx.scene.control.*;

public class DatePickerCell<T> extends TableCell<T, LocalDate> {

    protected DatePicker datePicker = new DatePicker();

    public DatePickerCell(TableColumn<T, LocalDate> column) {
        super();

        datePicker.editableProperty().bind(column.editableProperty());
        datePicker.disableProperty().bind(column.editableProperty().not());

        datePicker.setDayCellFactory(datePicker -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) { return ; }

                if (item.isAfter(LocalDate.now())) {
                    setDisable(true);
                    setStyle("-fx-background-color: #ffc0cb;");
                }
            }
        });

        datePicker.setOnShowing(event -> {
            final TableView<T> tableView = getTableView();
            tableView.getSelectionModel().select(getTableRow().getIndex());
            tableView.edit(tableView.getSelectionModel().getSelectedIndex(), column);
        });
        datePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (isEditing()) {
                commitEdit(newValue);
            }
        });
    }

    @Override
    public void updateItem(LocalDate item, boolean empty) {
        super.updateItem(item, empty);

        setText(null);

        if (empty) {
            setGraphic(null);
        } else {
            setGraphic(datePicker);
            datePicker.setValue(item);
        }
    }

}