package io.wabm.supermarket.application;

import io.wabm.supermarket.view.ViewPathHelper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        // Set title
        primaryStage.setTitle("超市管理系统");

        // Set min windows size
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);

        initRootlayout();
    }

    private void initRootlayout() {
        Parent root = null;
        try {
            root = FXMLLoader.load(ViewPathHelper.class.getResource("Main.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
