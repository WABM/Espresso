package io.wabm.supermarket.misc.util;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.util.Callback;

/**
 * Created by MainasuK on 2016-11-27.
 */
public class WABMThread {

    private Service<Void> thread;
    private Callback<Void, Void> callback;

    public WABMThread() {
        callback = (v) -> null;
        thread = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        callback.call(null);
                        return null;
                    }
                };
            }
        };
    }

    public void run(Callback<Void, Void> callback) {
        this.callback = callback;

        thread.start();
    }
}
