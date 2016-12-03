package io.wabm.supermarket.controller;

import io.wabm.supermarket.application.Main;
import io.wabm.supermarket.misc.pojo.Employee;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.misc.util.WABMThread;
import io.wabm.supermarket.view.ViewPathHelper;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcOperations;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.List;

/**
 * Created by Administrator on 2016/12/1 0001.
 */
public class LoginController {
    private Stage Loginstage;
    private JdbcOperations jdbcOperations;
    private final String kSelectUser = "SELECT f.* FROM wabm.Employee f WHERE username = ? AND password = ? LIMIT 1;";
    private List<Employee> templist;
    private Parent root = null;
    private Service<Void> backgroundThread;
    private Boolean b = false;

    @FXML
    Button loginButton;
    @FXML
    TextField usernameText;
    @FXML
    TextField passwordText;

    @FXML
    private void Login() {
        check();
    }

    public void setLoginstage(Stage Loginstage) {
        this.Loginstage = Loginstage;
    }

    /*private void check(){
        String username = usernameText.getText();
        String password = passwordText.getText();
        jdbcOperations = Main.getJdbcOperations();

        backgroundThread = new Service<Boolean>() {
            protected Task<Boolean> createTask() {
                Boolean f = new Boolean(false);
                Boolean t = new Boolean(true);
                return new Task<Boolean>() {
                    @Override
                    protected Boolean call() throws Exception {
                        try {
                            templist = jdbcOperations.query(kSelectAll, (ResultSet resultSet, int i) -> {
                                Employee employee;
                                employee = new Employee(
                                        resultSet.getInt("employee_id"),
                                        resultSet.getString("name"),
                                        resultSet.getString("birth_date"),     //date类型的，要转换为string类型
                                        resultSet.getInt("sex_status"),
                                        resultSet.getString("phone"),
                                        resultSet.getString("position_status"),
                                        resultSet.getString("entry_date"),   //数据库中取出来是date
                                        resultSet.getString("username"),
                                        resultSet.getString("password")
                                );
                                return employee;
                            });
                        } catch (DataAccessException e) {
                            e.printStackTrace();
                        }
                        int i=0;
                        while (i < templist.size()) {
                            if (templist.get(i).getUsername().equals(usernameText.getText())
                                    && templist.get(i).getPassword().equals(passwordText.getText())) {
                                    return t;
                            }
                            i++;
                        }
                        return f;
                    }
                    @Override
                    protected void succeeded() {
                        super.succeeded();
                        //updateMessage();
                    }
                };
            }
        };
        backgroundThread.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                b = (Boolean) event.getSource().getValue();
                System.out.println(event.getSource().getValue());
            }
        });
        backgroundThread.start();
        */
    private void check() {
        String username = usernameText.getText();
        String password = passwordText.getText();
        jdbcOperations = Main.getJdbcOperations();

        new WABMThread().run(_void -> {
            Employee employee = jdbcOperations.queryForObject(kSelectUser, (resultSet, i) -> new Employee(
                    resultSet.getInt("employee_id"),
                    resultSet.getString("name"),
                    resultSet.getString("birth_date"),     //date类型的，要转换为string类型
                    resultSet.getInt("sex_status"),
                    resultSet.getString("phone"),
                    resultSet.getString("position_status"),
                    resultSet.getString("entry_date"),   //数据库中取出来是date
                    resultSet.getString("username"),
                    resultSet.getString("password")
            ), username, password);

            if (employee != null) {
                ConsoleLog.print(employee.getName());

                Platform.runLater(() -> {
                    try {
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(ViewPathHelper.class.getResource("Main.fxml"));
                        root = loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Loginstage.setScene(new Scene(root));
                });
            }

            return null;
        });

//        backgroundThread = new Service<Void>() {
//            @Override
//            protected Task<Void> createTask() {
//                return new Task<Void>() {
//                    @Override
//                    protected Void call() throws Exception {
//                        ConsoleLog.print("Start background task…");
//                        boolean f = false;
//                        try {
//                            templist = jdbcOperations.query(kSelectUser, (ResultSet resultSet, int i) -> {
//                                Employee employee;
//                                employee = new Employee(
//                                        resultSet.getInt("employee_id"),
//                                        resultSet.getString("name"),
//                                        resultSet.getString("birth_date"),     //date类型的，要转换为string类型
//                                        resultSet.getInt("sex_status"),
//                                        resultSet.getString("phone"),
//                                        resultSet.getString("position_status"),
//                                        resultSet.getString("entry_date"),   //数据库中取出来是date
//                                        resultSet.getString("username"),
//                                        resultSet.getString("password")
//                                );
//                                return employee;
//                            });
//                        } catch (DataAccessException e) {
//                            e.printStackTrace();
//                        }
//
//                        int i=0;
//                        while (i < templist.size()) {
//                            if (templist.get(i).getUsername().equals(usernameText.getText())
//                                    && templist.get(i).getPassword().equals(passwordText.getText())) {
//                                Platform.runLater(() -> {
//                                    try {
//
//                                        FXMLLoader loader = new FXMLLoader();
//                                        loader.setLocation(ViewPathHelper.class.getResource("Main.fxml"));
//                                        root = loader.load();
//
//
//                                    } catch (IOException e) {
//                                        e.printStackTrace();
//                                    }
//                                    Loginstage.setScene(new Scene(root));
//                                });
//                                f = true;
//                            }
//                            i++;
//                        }
//                        if (!f) {
//                            Alert alert = new Alert(Alert.AlertType.ERROR);
//                            alert.setContentText("用户名或密码错误，请重新登录！");
//                            alert.show();
//                        }
//                        return null;
//                    }
//                };
//            }
//        };
//        backgroundThread.start();
    } //end check
}
