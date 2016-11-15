package io.wabm.supermarket.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

/**
 * Created by MainasuK on 2016-10-16.
 */
public abstract class AbstractMasterDetailController {

    /**
     * The pane to contain view for present detail infomations.
     */
    @FXML AnchorPane detailView;

    /**
     * Use pass-in FXMLLoader to load view and set it into {@link #detailView}
     * @param loader A loader to load FXML view
     */
    protected void setDetailViewFrom(FXMLLoader loader) {
        try {
            BorderPane view = loader.load();
            detailView.getChildren().setAll(view);

            // Set view to fill detail view with full size
            AnchorPane.setTopAnchor(view, 0.0);
            AnchorPane.setBottomAnchor(view, 0.0);
            AnchorPane.setLeftAnchor(view, 0.0);
            AnchorPane.setRightAnchor(view, 0.0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
