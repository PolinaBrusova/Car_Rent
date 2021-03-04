package com.example.demo.controllers;

import com.example.demo.JavaFxApplication;
import com.example.demo.models.Person;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;

@Component
@FxmlView("EmployeeRegister.fxml")
public class EmployeeController {
    
    private Stage loginStage;
    private JavaFxApplication main;
    private FxWeaver fxWeaver;
    @FXML
    private TextField login;
    @FXML
    private TextField password;
    
    @Autowired
    public EmployeeController(){}

    public void setMain(JavaFxApplication main){
        this.main = main;
        this.fxWeaver = this.main.getFxWeaver();
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
                FxControllerAndView<PersonOverviewController, Node> controllerAndView = fxWeaver.load(PersonOverviewController.class);
                Stage base = new Stage();
                controllerAndView.getView().ifPresent(parent -> {
                    Scene scene = new Scene((Parent) parent);
                    base.setScene(scene);
                });
                base.setTitle("Application");
                base.initModality(Modality.WINDOW_MODAL);
                base.initOwner(main.getPrimaryStage());
                controllerAndView.getController().setDialogStage(base);
                controllerAndView.getController().setMain(main);
                base.close();
                base.showAndWait();
            }
        }
    }
}
