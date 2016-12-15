package io.wabm.supermarket.model;

import javafx.collections.transformation.FilteredList;
import javafx.scene.control.TableView;

import java.util.function.Predicate;

/**
 * Created by MainasuK on 2016-12-10.
 */
public class FilteredTableViewModel<T> extends TableViewModel<T> {

    private FilteredList<T> filteredList;

    public FilteredTableViewModel(TableView<T> tableView) {
        super(tableView);
    }

    @Override
    protected void setupList() {
        super.setupList();
        filteredList = new FilteredList<T>(list);
    }

    @Override
    protected void setupTableViewDataSource() {
        tableView.setItems(filteredList);
    }

    public void setPredicate(Predicate<T> predicate) {
        filteredList.setPredicate(predicate);
    }

}
