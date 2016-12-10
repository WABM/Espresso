package io.wabm.supermarket.model;

import io.wabm.supermarket.application.Main;
import javafx.collections.FXCollections;
import javafx.scene.control.TableView;
import org.springframework.jdbc.core.JdbcOperations;

/**
 * Created by MainasuK on 2016-11-20.
 */
public class TableViewModel<T> extends Model<T> {

    protected TableView<T> tableView;

    protected JdbcOperations jdbcOperations;

    public TableViewModel(TableView<T> tableView) {
        this.tableView = tableView;

        setupList();
        setupTableViewDataSource();
        setupJdbcOperations();
    }

    protected void setupList() {
        list = FXCollections.observableArrayList();
    }

    protected void setupTableViewDataSource() {
        tableView.setItems(list);
    }

    protected void setupJdbcOperations() {
        jdbcOperations = Main.getJdbcOperations();
    }

    public void refresh() {
        tableView.refresh();
    }

    @Override
    public void add(T item) {
        list.add(item);
    }

    @Override
    public boolean delete(T item) {
        return list.remove(item);
    }
}
