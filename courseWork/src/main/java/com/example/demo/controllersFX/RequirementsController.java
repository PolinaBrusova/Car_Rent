package com.example.demo.controllersFX;

import com.example.demo.JavaFxApplication;
import com.example.demo.models.Person;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@FxmlView("requirements.fxml")
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
