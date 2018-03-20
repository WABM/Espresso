package io.wabm.supermarket.application;

import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lulu on 2018/3/18.
 */
public class PrimaryStage {
    private Stage primaryStage;

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
        SimpleDateFormat format = new SimpleDateFormat();
        try {
            format.format();
            Date
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
