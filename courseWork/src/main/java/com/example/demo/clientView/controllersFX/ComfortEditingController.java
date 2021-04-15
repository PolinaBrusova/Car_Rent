package com.example.demo.clientView.controllersFX;

import com.example.demo.ServerSide.models.ComfortLevel;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
    private void handleOk(){
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

    private boolean isInputValid(){
        String errorMessage = "";

        if (mainField.getText() == null || mainField.getText().length() == 0) {
            errorMessage += "Заполните расшифровку уровня комфорта!\n";
        }

        if (depositField.getText() == null || depositField.getText().length() == 0) {
            errorMessage += "Заполните сумму депозита!\n";
        }else {
            try{
                Float.parseFloat(depositField.getText());
            }catch (NumberFormatException e){
                errorMessage += "Сумма депозита должна быть числом!\n";
            }
        }

        if (priceField.getText() == null || priceField.getText().length() == 0) {
            errorMessage += "Заполните стоимость суток аренды!\n";
        }else {
            try{
                Float.parseFloat(priceField.getText());
            }catch (NumberFormatException e){
                errorMessage += "Стоимость суток аренды должна быть числом!\n";
            }
        }

        if (expField.getText() == null || expField.getText().length() == 0) {
            errorMessage += "Заполните минимальный отпыт вождения!\n";
        }else {
            try{
                Integer.parseInt(expField.getText());
            }catch (NumberFormatException e){
                errorMessage += "Минимальный опыт вождения должен быть целым числом!\n";
            }
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Некорректные поля");
            alert.setHeaderText("Пожалуйста, корректно заполните все поля");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}
