package com.example.demo.controllersFX;

import com.example.demo.JavaFxApplication;
import com.example.demo.models.Person;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class SearchWindowController {

    private JavaFxApplication main;
    private Stage searchStage;

    @FXML
    private TextField phoneField;


    public SearchWindowController(){}

    public void setMain(JavaFxApplication main){
        this.main = main;
    }

    @FXML
    private void initialize() {
    }

    public void setDialogStage(Stage searchStage) {
        this.searchStage = searchStage;
    }

    @FXML
    private void handleSearch(){
        if (!phoneField.getText().isBlank()) {
            if (phoneValidation(phoneField.getText())){
                if (clientExistence(phoneField.getText())){
                    try {
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(JavaFxApplication.class.getResource("views/requirements.fxml"));
                        AnchorPane page = loader.load();
                        Stage requirementStage = new Stage();
                        requirementStage.setTitle("Search");
                        requirementStage.initModality(Modality.WINDOW_MODAL);
                        requirementStage.initOwner(main.getPrimaryStage());
                        Scene scene = new Scene(page);
                        requirementStage.setScene(scene);
                        RequirementsController controller = loader.getController();
                        controller.setStage(requirementStage);
                        controller.setPerson((Person) main.getPersonData().stream().filter(item -> item.getPhone().equals(phoneField.getText())).toArray()[0]);
                        searchStage.close();
                        requirementStage.showAndWait();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.initOwner(searchStage);
                    alert.setTitle("No such a client");
                    alert.setHeaderText("Client Not found in the database");
                    alert.setContentText("Redirecting on adding a client...");
                    alert.showAndWait();
                    Person tempPerson = new Person();
                    tempPerson.setPhone(phoneField.getText());
                    if(main.showPersonEditDialog(tempPerson)){
                        main.getPersonData().add(tempPerson);
                    }
                    searchStage.close();
                    //Добавить переход с новым клиентом сразу на оформление требований
                }
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initOwner(searchStage);
                alert.setTitle("Invalid Nubmer");
                alert.setHeaderText("Entered an invalid phone number");
                alert.setContentText("Write the phone number correctly using the folowing formats:\n+79878767653\nOR\n89878767653");
                alert.showAndWait();
            }

        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(searchStage);
            alert.setTitle("Empty field");
            alert.setHeaderText("Fill an empty field");
            alert.setContentText("Fill the telephone number for the search");
            alert.showAndWait();
        }
    }

    private boolean phoneValidation(String phone){
        return phone.matches("^\\+\\d?-?\\d?\\d?\\d{11}$");
        //Валидация номера телефона позволяет отсеить поиск по формату +(кодСтраны)(11цифр) без скобок и тире
    }

    private boolean clientExistence(String phone){
        return main.getPersonData().stream().anyMatch(item -> item.getPhone().equals(phone));
        //Позволяет узнать, если номер клиента (и соответсвенно сам клиент) в базе
    }
}

