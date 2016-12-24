package io.wabm.supermarket.controller;

import io.wabm.supermarket.application.Main;
import io.wabm.supermarket.misc.javafx.alert.SimpleErrorAlert;
import io.wabm.supermarket.misc.pojo.Employee;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.misc.util.SingleLogin;
import io.wabm.supermarket.misc.util.WABMThread;
import io.wabm.supermarket.protocol.StageSetableController;
import io.wabm.supermarket.view.ViewPathHelper;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcOperations;

import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2016/12/1 0001.
 */
public class LoginController {
    private Stage stage;
    private JdbcOperations jdbcOperations;
    private final String kSelectUser = "SELECT f.* FROM wabm.employee f WHERE username = ? AND password = ? and valid=1 LIMIT 1;";
    private List<Employee> templist;
    private Parent root = null;
    private String fxml;
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

    public void setStage(Stage stage, String fxml) {
        this.stage = stage;
        this.fxml = fxml;
    }

    private void setEmployee(Employee employee)
    {
        SingleLogin.getInstance().initEmployee(employee);
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
            try {
                Employee employee = jdbcOperations.queryForObject(kSelectUser, (resultSet, i) -> new Employee(
                        resultSet.getInt("employee_id"),
                        resultSet.getString("name"),
                        resultSet.getString("birth_date"),     //date类型的，要转换为string类型
                        resultSet.getInt("sex_status"),
                        resultSet.getString("phone"),
                        resultSet.getInt("position_status"),
                        resultSet.getString("entry_date"),   //数据库中取出来是date
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getBoolean("valid")
                ), username, password);

                setEmployee(employee);
                ConsoleLog.print(SingleLogin.getInstance().getEmployee().getName());

                if (employee.getDepartmentString() !="收银员" && fxml.equals("CashierMain.fxml")
                        && employee.isValid()) {
                    Platform.runLater(() -> {
                        SimpleErrorAlert simpleErrorAlert = new SimpleErrorAlert("错误","请与管理员联系","没有权限！");
                        simpleErrorAlert.show();
                    });
                } else if (employee.isValid()){
                    Platform.runLater(() -> {
                        try {
                            FXMLLoader loader = new FXMLLoader();
                            loader.setLocation(ViewPathHelper.class.getResource(fxml));
                            root = loader.load();

                            // If is stage acceptable
                            if (loader.getController() instanceof StageSetableController) {
                                ConsoleLog.print("Set stage");
                                ((StageSetableController) loader.getController()).setStage(stage);
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        stage.setMinWidth(1000);
                        stage.setMinHeight(625);
                        stage.setScene(new Scene(root));
                    });
                }

            } catch (EmptyResultDataAccessException exception) {
                ConsoleLog.print("username or password error");

                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("用户名或密码错误，请重新登录！");
                    alert.show();
                });
            } catch (DataAccessException exception) {
                exception.printStackTrace();
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
//                                    stage.setScene(new Scene(root));
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
