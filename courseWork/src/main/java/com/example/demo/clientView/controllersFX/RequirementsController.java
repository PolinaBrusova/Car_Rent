package com.example.demo.clientView.controllersFX;

import com.example.demo.clientView.JavaFxApplication;
import com.example.demo.ServerSide.models.Client;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class RequirementsController {
    private JavaFxApplication main;
    private Client client;
    private Stage stage;
    @FXML
    private Label FirstNameLabel;
    @FXML
    private Label LastNameLabel;
    @FXML
    private Label PhoneLabel;
    @FXML
    private Label PassportLabel;
    @FXML
    private Label LiscenceLabel;



    public RequirementsController(){}

    @FXML
    private void initialize(){
    }

    public void setMain(JavaFxApplication main){
        this.main = main;
    }

    public void setPerson(Client person) {
        this.client = person;
        FirstNameLabel.setText(person.getFirstName());
        LastNameLabel.setText(person.getLastName());
        PhoneLabel.setText(person.getPhoneNumber());
        PassportLabel.setText(person.getPassport());
        LiscenceLabel.setText(person.getLiscenceDate().toString());
    }

    public void setStage(Stage stage) {
        this.stage=stage;
    }
}
