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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import java.io.IOException;

public class Main extends Application {

    private Stage primaryStage;
    private static AnnotationConfigApplicationContext databaseContext = new AnnotationConfigApplicationContext(DBConfig.class);
    private static JdbcOperations jdbcOperations = databaseContext.getBean(JdbcOperations.class);
    private static DataSourceTransactionManager transactionManager = databaseContext.getBean(DataSourceTransactionManager.class);
    private static PrimaryStage stage =databaseContext.getBean(PrimaryStage.class);

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        stage.setPrimaryStage(primaryStage);
        // Set title
        primaryStage.setTitle("超市管理系统");

        // Set min windows size
        //initRootlayout();
        initRootlayoutWithoutLogin();
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
        // Set min windows size
        primaryStage.setMinWidth(1000);
        primaryStage.setMinHeight(825);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    //Debug调试 跳过登陆
    private void initRootlayoutWithoutLogin(){
        SingleLogin.getInstance().initEmployee(new Employee(0, "Dev", "2016-1-1", 1, "10000", 0, "2016-1-1", "admin", "admin",true));
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
        primaryStage.setMinHeight(825);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static JdbcOperations getJdbcOperations() {
        return jdbcOperations;
    }

    public static DataSourceTransactionManager getTransactionManager() {
        return transactionManager;
    }

    public static PrimaryStage getPrimaryStage() {return stage;}
}
