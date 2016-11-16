package io.wabm.supermarket.controller;

import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.protocol.SceneControllerProtocol;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

import java.io.Console;
import java.util.HashMap;

/**
 * Created by MainasuK on 2016-11-16.
 */
public class ScenesController extends StackPane {

    /**
     * Screens' [String : Node] dictionary
     */
    private HashMap<String, Node> scenes;

    public ScenesController() {
        super();

        scenes = new HashMap<>();
    }

    public boolean isContains(String name) {
        return scenes.containsKey(name);
    }

    public Node getScreen(String name) {
        return scenes.get(name);
    }

    public boolean loadScene(String name, FXMLLoader loader) {
        try {
            Parent parent = loader.load();
            Object controller = loader.getController();

            if (controller != null && controller instanceof SceneController) {
                ((SceneController) controller).setScenesController(this);
            } else {
                ConsoleLog.warning("Controller should be extends SceneController to support scenes navigation");
            }

            addScene(name, parent);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean unloadScene(String name) {
        if (scenes.remove(name) != null) {
            return true;
        } else {
            ConsoleLog.warning(name + " not exists");
            return false;
        }
    }

    public boolean setScene(final String name) {
        // Get node out from map
        Node node = scenes.get(name);

        // Check scene if load or not
        if (node == null) {
            ConsoleLog.warning(name + " haven't loaded");
            return false;
        }

        // If no scene exists in stack pane
        if (getChildren().isEmpty()) {
            getChildren().add(node);

            return true;
        } else {
            getChildren().remove(0);
            getChildren().add(0, node);

            ConsoleLog.print(getChildren().toString());
            return true;
        }

    }

    private void addScene(String name, Node node) {
        scenes.put(name, node);
    }
}
