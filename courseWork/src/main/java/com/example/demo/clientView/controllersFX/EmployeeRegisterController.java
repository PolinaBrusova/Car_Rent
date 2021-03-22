package com.example.demo.clientView.controllersFX;

import com.example.demo.clientView.JavaFxApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class EmployeeRegisterController {

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
        this.login.setPromptText("Personal Code");
        this.password.setPromptText("Personal Password");
    }
    
    @FXML
    private void handleEnter() throws IOException{

        if(!login.getText().isBlank()){
            if(!password.getText().isBlank()){
                StringBuilder result = new StringBuilder();
                URL url = new URL("http://localhost:9090/api/tests/LogPas_Id="+login.getText()+"_password="+password.getText());
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                try (var reader = new BufferedReader(
                        new InputStreamReader(httpURLConnection.getInputStream()))) {
                    for (String line; (line = reader.readLine()) != null; ) {
                        result.append(line);
                    }
                }catch (Exception e){
                    try {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.initOwner(main.getPrimaryStage());
                        alert.setTitle("No connection");
                        alert.setHeaderText("Not established connection with the server");
                        alert.setContentText("Connection with the server was not established. try again later. Halting th system...");
                        ButtonType answer = alert.showAndWait().orElse(ButtonType.OK);
                        if (ButtonType.OK.equals(answer)) {
                            this.main.getPrimaryStage().close();
                            this.main.stop();
                        }
                    }catch (Exception k){
                        k.printStackTrace();
                    }
                }
                if (!result.toString().isEmpty()){
                    if (result.toString().equals("true")) {
                        main.showPersonOverview();
                    }else{
                        this.login.setText(login.getText());
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.initOwner(main.getPrimaryStage());
                        alert.setTitle("Sign In failed");
                        alert.setHeaderText("Wrong Login or Password");
                        alert.setContentText("Please, enter the correct login and password. Login is an " +
                                "employee's company system code");
                        alert.showAndWait();
                    }
                }
            }else {
                password.setPromptText("FILL THE PASSWORD");
                password.setStyle("-fx-prompt-text-fill: red");
            }
        }else {
            login.setPromptText("FILL THE LOGIN");
            login.setStyle("-fx-prompt-text-fill: red");
            if (password.getText().isBlank()){
                password.setPromptText("FILL THE PASSWORD");
                password.setStyle("-fx-prompt-text-fill: red");
            }else{
                password.setText("");
            }
        }
    }
}
