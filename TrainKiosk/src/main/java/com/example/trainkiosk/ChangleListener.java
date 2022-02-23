package com.example.trainkiosk;

/*
 This listener class is called to set maximum length of any text field.

 Example -
 TextField textField;
 ChangeListener listener = new ChangeListener(TextField, 5); // set max length as 5
 textField.textProperty().addListener(listener);
 */


import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;


/**
 *
 * @author vikas
 */
class ChangeListener implements javafx.beans.value.ChangeListener<String> {


    private final int maxLength;
    private final TextField textField;


    public ChangeListener(TextField textField, int maxLength) {
        this.textField= textField;
        this.maxLength = maxLength;
    }


    public int getMaxLength() {
        return maxLength;
    }


    @Override
    public void changed(ObservableValue<? extends String> ov, String oldValue, String newValue) {
        if (newValue == null) {
            return;
        }

        if (!newValue.matches("\\d*")) {
            newValue = newValue.replaceAll("[^\\d]", "");
        }

        if (newValue.length() > maxLength) {
            textField.setText(oldValue);
        } else {
            textField.setText(newValue);
        }
    }
}
