package io.wabm.supermarket.application;

import io.wabm.supermarket.controller.LoginController;
import io.wabm.supermarket.misc.config.DBConfig;
import io.wabm.supermarket.misc.pojo.Employee;
import io.wabm.supermarket.misc.util.SingleLogin;
import io.wabm.supermarket.view.ViewPathHelper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcOperations;

import java.io.IOException;

public class Main extends Application {

    private Stage primaryStage;
    private static JdbcOperations jdbcOperations = (new AnnotationConfigApplicationContext(DBConfig.class)).getBean(JdbcOperations.class);;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        // Set title
        primaryStage.setTitle("超市管理系统");

        // Set min windows size
        initRootlayout();
        //initRootlayoutWithoutLogin();
    }


    private void initRootlayout() {
        Parent root = null;
        FXMLLoader loader = new FXMLLoader();
        try {
            loader.setLocation(ViewPathHelper.class.getResource("Login.fxml"));
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        LoginController controller = loader.getController();

        controller.setStage(primaryStage, "Main.fxml");

        Scene scene = new Scene(root);
        scene.getStylesheets().add(ViewPathHelper.class.getResource("Login.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    private void initRootlayoutWithoutLogin(){
        Parent root = null;
        FXMLLoader loader = new FXMLLoader();
        try {
            loader.setLocation(ViewPathHelper.class.getResource("Main.fxml"));
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);

        // Set min windows size
        primaryStage.setMinWidth(1000);
        primaryStage.setMinHeight(625);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static JdbcOperations getJdbcOperations() {
        return jdbcOperations;
    }
}
