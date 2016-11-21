package io.wabm.supermarket.model;

import io.wabm.supermarket.application.Main;
import io.wabm.supermarket.misc.config.DBConfig;
import io.wabm.supermarket.misc.util.ConsoleLog;
import javafx.collections.FXCollections;
import javafx.scene.control.TableView;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcOperations;

/**
 * Created by MainasuK on 2016-11-20.
 */
public class TableViewModel<T> extends Model<T> {

    private TableView<T> tableView;

    protected JdbcOperations jdbcOperations;

    public TableViewModel(TableView<T> tableView) {
        this.tableView = tableView;

        list = FXCollections.observableArrayList();
        tableView.setItems(list);

        jdbcOperations = Main.getJdbcOperations();
    }

    public void refresh() {
        tableView.refresh();
    }

    @Override
    public void add(T item) {
        list.add(item);
    }


}
