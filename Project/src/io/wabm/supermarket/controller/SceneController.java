package io.wabm.supermarket.controller;

import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.protocol.SceneControllerProtocol;
import javafx.fxml.FXMLLoader;
import javafx.util.Callback;

import java.io.IOException;

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
        navigationTo(loader, (controller) -> null);
    }

    /**
     * Use this method to present a view over current view
     * @param loader
     * @param config You can configure your controller here. But cast controller to your type first.
     *               This callback will call just after load scene first time. So you have to store it.
     *
     */
    protected <T> void navigationTo(FXMLLoader loader, Callback<T, Void> config) {
        final String name = loader.getLocation().toString();

        if (!scenesController.isContains(name)) {
            scenesController.loadScene(name, loader, config);
        }

        scenesController.setScene(name);

    }
}
