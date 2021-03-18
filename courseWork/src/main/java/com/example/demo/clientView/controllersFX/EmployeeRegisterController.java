package com.example.demo.clientView.controllersFX;

import com.example.demo.clientView.JavaFxApplication;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

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
    private void handleEnter() throws IOException {
        if(!login.getText().isBlank()){
            if(!password.getText().isBlank()){
                StringBuilder result = new StringBuilder();
                URL url = new URL("http://localhost:9090/api/tests/emp_log_pas/id="+login.getText()+"/password="+password.getText());
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                try (var reader = new BufferedReader(
                        new InputStreamReader(httpURLConnection.getInputStream()))) {
                    for (String line; (line = reader.readLine()) != null;) {
                        result.append(line);
                    }
                }
                if (!result.toString().isEmpty()){
                    System.out.println(result.toString());
                }else{
                    System.out.println("hgbtjhgbd");
                }
                main.showPersonOverview();
            }
        }
    }
}
