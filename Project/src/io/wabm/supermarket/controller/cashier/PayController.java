package io.wabm.supermarket.controller.cashier;

import io.wabm.supermarket.misc.javafx.alert.SimpleErrorAlert;
import io.wabm.supermarket.misc.util.ConsoleLog;
import io.wabm.supermarket.protocol.StageSetableController;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.springframework.dao.DataAccessException;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.regex.Pattern;


/**
 * Created by MainasuK on 2016-12-7.
 */
public class PayController implements StageSetableController {

    private Pattern inputPattern = Pattern.compile("((\\.)?[0-9]?[0-9]?)| ([0-9]{1,8}(\\.)?) |([0-9]{1,8}(\\.[0-9]?[0-9]?)?)");
    private Pattern payPattern = Pattern.compile("((\\.)[0-9][0-9]?)|([0-9]+(\\.[0-9]?[0-9]?)?)");
    private DecimalFormat decimalFormat = new DecimalFormat("#0.00");

    private Stage stage;
    private Callback<BigDecimal, DataAccessException> callback;

    private double shouldPay;
    private double change;

    @FXML Label shouldPayLabel;
    @FXML Label changeLabel;
    @FXML TextField payTextField;

    @FXML private void initialize() {
        ConsoleLog.print("PayController init");

        shouldPay = 98.7;
        shouldPayLabel.setText("¥" + decimalFormat.format(shouldPay));
        setupListener();
    }

    @FXML private void payTextFieldOnKeyPressed(KeyEvent event) {
        ConsoleLog.print(event.getCode() + " pressed");

        switch (event.getCode()) {
            case ENTER:
                handlePay(payTextField.getText().trim());
                break;
            case ESCAPE:
                stage.close();
                break;
        }   // end switch
    }

    private void setupListener() {
        payTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("") || inputPattern.matcher(newValue).matches()) {
                resetControl(Double.parseDouble(newValue));
            } else {
                payTextField.setText(oldValue);
            };
        });
    }

    private void handlePay(String text) {
        ConsoleLog.print("handle " + text);

        if (!payPattern.matcher(text).matches()) {
            showAlert();
            return ;
        }

        try {
            Double d = Double.parseDouble(text);

            ConsoleLog.print("Got " + d);

            resetControl(d);

        } catch (NumberFormatException exception) {
            showAlert();
        }
    }

    private void showAlert() {
        Alert alert = new SimpleErrorAlert("收款失败", "实付金额格式不正确", "请检查实付金额输入是否正确");
        alert.show();
    }

    private void resetControl(double pay) {
        ConsoleLog.print("Reset control");

        change = pay - shouldPay;
        if (change < 0) {
            changeLabel.setText("¥0.00");
        } else {
            changeLabel.setText("¥" + decimalFormat.format(change));
        }

    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
