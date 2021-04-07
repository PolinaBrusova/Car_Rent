package com.example.demo.clientView.controllersFX;

import com.example.demo.clientView.JavaFxApplication;
import com.example.demo.utils.ConnectionPerfomance;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class EmployeeRegisterController {

    private JavaFxApplication main;
    @FXML
    private Label loginLabel;
    @FXML
    private Label passLabel;
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
        this.loginLabel.setStyle("-fx-font-family: 'Times New Roman'");
        this.passLabel.setStyle("-fx-font-family: 'Times New Roman'");
        this.login.setPromptText("Personal Code");
        this.login.setStyle("-fx-font-family: Didot");
        this.password.setPromptText("Personal Password");
        this.password.setStyle("-fx-font-family: Didot");
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
                password.setStyle("-fx-font-family: 'Times New Roman'");
                password.setStyle("-fx-prompt-text-fill: #800000");
            }
        }else {
            login.setPromptText("FILL THE LOGIN");
            login.setStyle("-fx-font-family: 'Times New Roman'");
            login.setStyle("-fx-prompt-text-fill: #800000");
            if (password.getText().isBlank()){
                password.setPromptText("FILL THE PASSWORD");
                password.setStyle("-fx-font-family: 'Times New Roman'");
                password.setStyle("-fx-prompt-text-fill: #800000");
            }else{
                password.setText("");
            }
        }
    }
}
