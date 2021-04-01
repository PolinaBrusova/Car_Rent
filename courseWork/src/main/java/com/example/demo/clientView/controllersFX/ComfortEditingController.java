package com.example.demo.clientView.controllersFX;

import com.example.demo.ServerSide.models.Car;
import com.example.demo.ServerSide.models.ComfortLevel;
import com.example.demo.utils.ConnectionPerfomance;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;

public class ComfortEditingController {
    @FXML
    private Label idField;
    @FXML
    private TextField mainField;
    @FXML
    private TextField depositField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField expField;


    private Stage dialogStage;
    private ComfortLevel comfortLevel;
    private boolean okClicked = false;

    @FXML
    private void initialize() {
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setLevel(ComfortLevel comfortLevel) {
        this.comfortLevel = comfortLevel;
        idField.setText(comfortLevel.getId());
        mainField.setText(comfortLevel.getLevel());
        depositField.setText(comfortLevel.getDeposit().toString());
        priceField.setText(comfortLevel.getRentPrice().toString());
        expField.setText(String.valueOf(comfortLevel.getMinExperience()));
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    public ComfortLevel getLevel() {return comfortLevel;}

    @FXML
    private void handleOk() throws IOException {
        if (isInputValid()) {
            comfortLevel.setLevel(mainField.getText());
            comfortLevel.setDeposit(Long.parseLong(depositField.getText()));
            comfortLevel.setRentPrice(Long.parseLong(priceField.getText()));
            comfortLevel.setMinExperience(Integer.parseInt(expField.getText()));
            okClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    private boolean isInputValid() throws IOException {
        String errorMessage = "";

        if (mainField.getText() == null || mainField.getText().length() == 0) {
            errorMessage += "No valid level name!\n";
        }

        if (depositField.getText() == null || depositField.getText().length() == 0) {
            errorMessage += "No valid deposit value!\n";
        }

        if (priceField.getText() == null || priceField.getText().length() == 0) {
            errorMessage += "No valid rent price value!\n";
        }

        if (expField.getText() == null || expField.getText().length() == 0) {
            errorMessage += "No valid minimal experience value!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}
