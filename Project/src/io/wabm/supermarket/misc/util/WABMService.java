package io.wabm.supermarket.misc.util;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.util.Callback;

/**
 * Created by MainasuK on 2016-11-27.
 */
public class WABMService<V> extends Service<V> {

    private Callback<Void, Void> callback;

    public WABMService(Callback<Void, Void> callback) {
        this.callback = callback;
    }

    @Override
    protected Task<V> createTask() {
        return new Task<V>() {
            @Override
            protected V call() throws Exception {
                callback.call(null);
                return null;
            }
        };
    }

}
