package com.example.demo.clientView.controllersFX;

import com.example.demo.clientView.JavaFxApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class RequirementsController {
    private JavaFxApplication main;
    private Person person;
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

    public void setPerson(Person person) {
        this.person = person;
        FirstNameLabel.setText(person.getFirstName());
        LastNameLabel.setText(person.getLastName());
        PhoneLabel.setText(person.getPhone());
        PassportLabel.setText(person.getPassport());
        LiscenceLabel.setText(person.getLiscence().toString());
    }

    public void setStage(Stage stage) {
        this.stage=stage;
    }
}
