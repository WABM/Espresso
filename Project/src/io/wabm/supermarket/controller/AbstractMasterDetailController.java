package io.wabm.supermarket.controller;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;


/**
 * Created by MainasuK on 2016-10-16.
 */
public abstract class AbstractMasterDetailController {

    /**
     * The pane to contain view for present detail infomations.
     */
    @FXML AnchorPane detailView;

    @FXML Label leftStatus;
    @FXML Label rightStatus;

    /**
     * The controller to control multi-scenes
     * The controller extends SceneController will be inject with it
     */
    private ScenesController scenesController;

    /**
     * Use pass-in FXMLLoader to load view and set it into {@link #detailView}
     * @param loader A loader to load FXML view
     */
    protected void setDetailViewFrom(FXMLLoader loader) {
        scenesController = new ScenesController();

        final String name = loader.getLocation().toString();

        if (!scenesController.isContains(name)) {
            scenesController.loadScene(name, loader);
        }
        scenesController.setScene(name);

        detailView.getChildren().setAll(scenesController);

        // Set view to fill detail view with full size
        AnchorPane.setTopAnchor(scenesController, 0.0);
        AnchorPane.setBottomAnchor(scenesController, 0.0);
        AnchorPane.setLeftAnchor(scenesController, 0.0);
        AnchorPane.setRightAnchor(scenesController, 0.0);
    }

    protected void setLeftStatusText(String statusText) {
        leftStatus.setText(statusText);
    }

    protected void setRightStatusText(String statusText) {
        rightStatus.setText(statusText);
    }

}
