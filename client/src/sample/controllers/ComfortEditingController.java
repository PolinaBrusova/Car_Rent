package sample.controllers;

import sample.models.ComfortLevel;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * JavaFX scene controller
 */
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

    /**
     * sets the stage for this controller
     * @param dialogStage Javafx stage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Sets level for the edit
     * @param comfortLevel ComfortLevel
     */
    public void setLevel(ComfortLevel comfortLevel) {
        this.comfortLevel = comfortLevel;
        idField.setText(comfortLevel.getId());
        mainField.setText(comfortLevel.getLevel());
        depositField.setText(comfortLevel.getDeposit().toString());
        priceField.setText(comfortLevel.getRentPrice().toString());
        expField.setText(String.valueOf(comfortLevel.getMinExperience()));
    }

    /**
     * returns the value of the isOkClicked variable
     * @return boolean isOkClicked
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * returns the value of the comfortLevel variable
     * @return ComfortLevel object
     */
    public ComfortLevel getLevel() {return comfortLevel;}

    /**
     * Handles action on "Ok" button validating the fields and closing the datge
     */
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

    /**
     * Handle clicking on "Cancel" button closing the stage
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    /**
     * Validates the fields and shows an error message if any issues were met
     * @return boolean result of validation
     */
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
