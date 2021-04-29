package sample.controllers;

import sample.JavaFxApplication;
import sample.utils.ConnectionPerfomance;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * JavaFX scene controller
 */
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

    /**
     * Empty initializer
     */
    public EmployeeRegisterController(){}

    /**
     * sets the main for this controller
     * @param main JavaFxApplication main
     */
    public void setMain(JavaFxApplication main){
        this.main = main;
    }

    /**
     * sets the stage for this controller
     * @param registerStage Javafx stage
     */
    public void setRegisterStage(Stage registerStage) {
        this.registerStage = registerStage;
    }

    /**
     * set fonts for the fiels
     */
    @FXML
    private void initialize() {
        this.loginLabel.setStyle("-fx-font-family: 'Times New Roman'");
        this.passLabel.setStyle("-fx-font-family: 'Times New Roman'");
        this.login.setPromptText("Personal Code");
        this.login.setStyle("-fx-font-family: Didot");
        this.password.setPromptText("Personal Password");
        this.password.setStyle("-fx-font-family: Didot");
    }

    /**
     * Handles action on "Enter" button with validation of the fields and sending a request for the database
     */
    @FXML
    private void handleEnter(){
            if (!login.getText().isBlank()) {
                if (!password.getText().isBlank()) {
                    try {
                        String result = ConnectionPerfomance.excecuteValidation("http://localhost:9090/api/tests/" +
                                "LogPas_Id=" + login.getText() + "_password=" +
                                URLEncoder.encode(password.getText(), StandardCharsets.UTF_8));
                        if (result.equals("true")) {
                            main.setEmployeeId(Long.parseLong(login.getText()));
                            main.initRootLayout();
                            main.showPersonOverview();
                            this.registerStage.close();
                        } else if (result.equals("false")) {
                            this.login.setText(login.getText());
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.initOwner(this.registerStage);
                            alert.setTitle("Sign In failed");
                            alert.setHeaderText("Wrong Login or Password");
                            alert.setContentText("Please, enter the correct login and password. Login is an " +
                                    "employee's company system code");
                            alert.showAndWait();
                        } else {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.initOwner(this.registerStage);
                                alert.setTitle("No connection");
                                alert.setHeaderText("Not established connection with the server");
                                alert.setContentText("Connection with the server was not established. try again later. Halting th system...");
                                alert.showAndWait();

                        }
                    }catch (java.net.ConnectException e){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.initOwner(this.registerStage);
                        alert.setTitle("No connection");
                        alert.setHeaderText("Not established connection with the server");
                        alert.setContentText("Connection with the server was not established. try again later. Halting th system...");
                        alert.showAndWait();
                    }catch (IOException e){
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.initOwner(this.registerStage);
                        alert.setTitle("Sign In failed");
                        alert.setHeaderText("Wrong Login or Password");
                        alert.setContentText("Please, enter the correct login and password. Login is an " +
                                "employee's company system code");
                        alert.showAndWait();
                    }
                } else {
                    password.setPromptText("FILL THE PASSWORD");
                    password.setStyle("-fx-font-family: 'Times New Roman'");
                    password.setStyle("-fx-prompt-text-fill: #800000");
                }
            } else {
                login.setPromptText("FILL THE LOGIN");
                login.setStyle("-fx-font-family: 'Times New Roman'");
                login.setStyle("-fx-prompt-text-fill: #800000");
                if (password.getText().isBlank()) {
                    password.setPromptText("FILL THE PASSWORD");
                    password.setStyle("-fx-font-family: 'Times New Roman'");
                    password.setStyle("-fx-prompt-text-fill: #800000");
                } else {
                    password.setText("");
                }
            }
    }
}
