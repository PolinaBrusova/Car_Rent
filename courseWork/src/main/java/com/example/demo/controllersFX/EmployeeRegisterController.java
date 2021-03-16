package com.example.demo.controllersFX;

import com.example.demo.JavaFxApplication;
import com.example.demo.models.ComfortLevel;
import com.example.demo.repositories.CarRepository;
import com.example.demo.repositories.ComfortLevelRepository;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class EmployeeRegisterController {
    
    private Stage loginStage;
    private JavaFxApplication main;
    @FXML
    private TextField login;
    @FXML
    private PasswordField password;

    public EmployeeRegisterController(){}

    public void setMain(JavaFxApplication main){
        this.main = main;
    }

    @FXML
    private void initialize() {
    }

    public void setLoginStage(Stage loginStage) {
        this.loginStage = loginStage;
    }
    
    @FXML
    private void handleEnter(){
        if(!login.getText().isBlank()){
            if(!password.getText().isBlank()){
                main.showPersonOverview();
            }
        }
    }
}
