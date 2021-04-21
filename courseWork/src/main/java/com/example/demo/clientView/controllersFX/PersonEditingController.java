package com.example.demo.clientView.controllersFX;

import com.example.demo.ServerSide.models.Client;
import com.example.demo.clientView.JavaFxApplication;
import com.example.demo.utils.ConnectionPerfomance;
import com.example.demo.utils.DateUtil;
import com.example.demo.utils.PhoneUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * JavaFX scene controller
 */
public class PersonEditingController {

    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField passportField;
    @FXML
    private TextField liscenceField;


    private Stage dialogStage;
    private Client person;
    private JavaFxApplication main;
    private boolean okClicked = false;

    @FXML
    private void initialize() {
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setPerson(Client person) {
        this.person = person;
        firstNameField.setText(person.getFirstName());
        lastNameField.setText(person.getLastName());
        phoneField.setText(person.getPhoneNumber());
        passportField.setText(person.getPassport());
        try {
            liscenceField.setText(DateUtil.formatForPeople(DateUtil.parse(person.getLiscenceDate())));
        }catch (NullPointerException e){
            liscenceField.setText("");
        }
        liscenceField.setPromptText("дд.мм.гггг");
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    public Client getPerson() {return person;}

    public void setMain(JavaFxApplication main) {
        this.main = main;
    }

    /**
     * Handles action on "OK" button validating the fields
     */
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            person.setFirstName(firstNameField.getText());
            person.setLastName(lastNameField.getText());
            person.setPhoneNumber(phoneField.getText());
            person.setPassport(passportField.getText());
            person.setLiscenceDate(DateUtil.format(DateUtil.parse(liscenceField.getText())));

            okClicked = true;
            dialogStage.close();
        }
    }

    /**
     * Handles action on "Cancel" button closing the stage
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    /**
     * Validates filled fields and shows an error message is there were any issues
     * @return boolean result of validation
     */
    private boolean isInputValid() {
        String errorMessage = "";
        String regex = "^\\p{L}+[\\p{L}\\p{Z}\\p{P}]*";

        if (firstNameField.getText() == null || firstNameField.getText().length() == 0) {
            errorMessage += "Заполните имя клиента!\n";
        }else{
            if (!firstNameField.getText().matches(regex)){
                errorMessage += "Имя клиента содержит недопустимые символы!\n";
            }
        }
        if (lastNameField.getText() == null || lastNameField.getText().length() == 0) {
            errorMessage += "Заполните фамилию клиента!\n";
        }else{
            if (!lastNameField.getText().matches(regex)){
                errorMessage += "Фамилия клиента содержит недопустимые символы!\n";
            }
        }
        if (phoneField.getText() == null || phoneField.getText().length() == 0) {
            errorMessage += "Заполните мобильный телефон!\n";
        }else{
            if (!PhoneUtil.validPhone(phoneField.getText())) {
                errorMessage += "Мобильный номер введен неверно!\n";
            }
            else{
                try {
                    JSONObject jsonObject = ConnectionPerfomance.excecuteOnlyGET("http://localhost:9090/api/tests/getClient/phone=", URLEncoder.encode(phoneField.getText(), StandardCharsets.UTF_8), "Client");
                    if (!jsonObject.isEmpty()) {
                        errorMessage += "Клиент с таким номером уже есть в базе!\n";
                    }
                }catch (java.net.ConnectException e){
                    this.main.handleNoConnection();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }

        if (passportField.getText() == null || passportField.getText().length() == 0) {
            errorMessage += "Заполните поле пасспорта!\n";
        }else{
            if(passportField.getText().length()>10){
                errorMessage += "Введите корректный идентификатор пасспорта!\n";
            }
        }
        if (liscenceField.getText() == null || liscenceField.getText().length() == 0) {
            errorMessage += "Заполните поле даты получения прав!\n";
        } else {
            if (!DateUtil.validDate(liscenceField.getText())) {
                errorMessage += "Неверно заполнена дата получения прав. Используйте формат дд.мм.гггг!\n";
            }
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Неверно заполненные поля");
            alert.setHeaderText("Пожалуйста, корректно заполните все необходимые поля!");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}