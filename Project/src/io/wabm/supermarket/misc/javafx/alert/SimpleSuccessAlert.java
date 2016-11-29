package io.wabm.supermarket.misc.javafx.alert;

import javafx.scene.control.Alert;

/**
 * Created by MainasuK on 2016-11-29.
 */
public class SimpleSuccessAlert extends Alert {

    public SimpleSuccessAlert(String title, String header, String context) {
        super((AlertType.INFORMATION));
        setTitle(title);
        setHeaderText(header);
        setContentText(context);
    }

}
