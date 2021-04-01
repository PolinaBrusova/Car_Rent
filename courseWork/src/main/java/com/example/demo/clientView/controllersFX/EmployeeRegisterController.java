package com.example.demo.clientView.controllersFX;

import com.example.demo.clientView.JavaFxApplication;
import com.example.demo.utils.ConnectionPerfomance;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class EmployeeRegisterController {

    private JavaFxApplication main;
    @FXML
    private TextField login;
    @FXML
    private PasswordField password;

    private Stage registerStage;

    public EmployeeRegisterController(){}

    public void setMain(JavaFxApplication main){
        this.main = main;
    }

    public void setRegisterStage(Stage registerStage) {
        this.registerStage = registerStage;
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
                String result = ConnectionPerfomance.excecuteValidation("http://localhost:9090/api/tests/LogPas_Id="+login.getText()+"_password="+password.getText());
                if (result.equals("true")){
                    main.setEmployeeId(Long.parseLong(login.getText()));
                    main.initRootLayout();
                    main.showPersonOverview();
                    this.registerStage.close();
                }else if (result.equals("false")){
                    this.login.setText(login.getText());
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.initOwner(this.registerStage);
                    alert.setTitle("Sign In failed");
                    alert.setHeaderText("Wrong Login or Password");
                    alert.setContentText("Please, enter the correct login and password. Login is an " +
                            "employee's company system code");
                    alert.showAndWait();
                }else{
                    try {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.initOwner(this.registerStage);
                        alert.setTitle("No connection");
                        alert.setHeaderText("Not established connection with the server");
                        alert.setContentText("Connection with the server was not established. try again later. Halting th system...");
                        ButtonType answer = alert.showAndWait().orElse(ButtonType.OK);
                        if (ButtonType.OK.equals(answer)) {
                            this.registerStage.close();
                            this.main.stop();
                        }
                    }catch (Exception k){
                        k.printStackTrace();
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
