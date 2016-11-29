package io.wabm.supermarket.misc.util;

import javafx.scene.control.TextField;

/**
 * Created by MainasuK on 2016-11-28.
 */
public class ValidCheckHelper {
    public String checkTypeAndLength(TextField textField, String fieldName, int length) {
        String errorMessage = "";
        String text = textField.getText();

        if (isNullDataIn(textField)) {
            errorMessage += "请输入" + fieldName + "\n";
            return errorMessage;
        } else if (isTooLongData(text, length)) {
            errorMessage += fieldName + "长度过长\n";
            return errorMessage;
        } else {
            return errorMessage;
        }
    }

    public String checkTypeAndLengthForInteger(TextField textField, String fieldName, int length) {
        String error = checkTypeAndLength(textField, fieldName, length);
        if (error == "") {
            try {
                int num = Integer.parseInt(textField.getText());
                if (num < 0) {
                    error += fieldName + "需为正整数\n";
                }
            } catch (NumberFormatException e) {
                error += fieldName + "需为整数\n";
            }
        }
        return error;
    }

    public Boolean isTooLongData(String text, int ofLength) {
        return (text.length() > ofLength);
    }

    public Boolean isNullDataIn(TextField textField) {
        String text = textField.getText();

        return (text == null || text.length() == 0);
    }
}
