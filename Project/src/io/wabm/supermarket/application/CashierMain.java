package io.wabm.supermarket.application;

/**
 * Created by MainasuK on 2016-12-2.
 */

import io.wabm.supermarket.misc.config.DBConfig;
import io.wabm.supermarket.view.ViewPathHelper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcOperations;

import java.io.IOException;

public class CashierMain extends Application {

    private Stage primaryStage;
    private static JdbcOperations jdbcOperations = (new AnnotationConfigApplicationContext(DBConfig.class)).getBean(JdbcOperations.class);;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        // Set title
        primaryStage.setTitle("超市收银系统");

        // Set min windows size
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);

        initRootlayout();
    }

    private void initRootlayout() {
        Parent root = null;
        try {
            root = FXMLLoader.load(ViewPathHelper.class.getResource("CashierMain.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static JdbcOperations getJdbcOperations() {
        return jdbcOperations;
    }
}
