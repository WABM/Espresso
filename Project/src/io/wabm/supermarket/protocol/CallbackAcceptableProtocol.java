package io.wabm.supermarket.protocol;

import javafx.util.Callback;

/**
 * Created by MainasuK on 2016-11-28.
 */
public interface CallbackAcceptableProtocol<P, R> {
    void set(Callback<P, R> callback);
}
