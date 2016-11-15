package io.wabm.supermarket.model;

import io.wabm.supermarket.protocol.DataSource;
import javafx.collections.ObservableList;

/**
 * Created by MainasuK on 2016-11-14.
 */
public abstract class Model<T> implements DataSource<T> {
    protected ObservableList<T> list;
}
