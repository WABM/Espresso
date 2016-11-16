package io.wabm.supermarket.controller;

import io.wabm.supermarket.protocol.SceneControllerProtocol;
import javafx.fxml.FXMLLoader;

/**
 * Created by MainasuK on 2016-11-16.
 */
public class SceneController implements SceneControllerProtocol {

    private ScenesController scenesController;

    /**
     * Inject {@link #scenesController} via {@link SceneControllerProtocol}
     * @param scenesController
     */
    @Override
    public void setScenesController(ScenesController scenesController) {
        this.scenesController = scenesController;
    }

    protected void navigationTo(FXMLLoader loader) {
        final String name = loader.getLocation().toString();

        if (!scenesController.isContains(name)) {
            scenesController.loadScene(name, loader);
        }
        scenesController.setScene(name);
    }
}
