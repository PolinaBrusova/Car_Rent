package com.example.demo.clientView.controllersFX;

import com.example.demo.ServerSide.models.Car;
import com.example.demo.ServerSide.models.ComfortLevel;
import com.example.demo.clientView.JavaFxApplication;
import com.example.demo.utils.ConnectionPerfomance;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;

public class CarEditingController {
    @FXML
    private TextField brandField;
    @FXML
    private TextField carcaseField;
    @FXML
    private TextField colorField;
    @FXML
    private TextField doorField;
    @FXML
    private TextField gearField;
    @FXML
    private TextField releaseField;
    @FXML
    private TextField seatsField;
    @FXML
    private TextField comfortField;


    private Stage dialogStage;
    private Car car;
    private boolean okClicked = false;
    private JavaFxApplication main;

    @FXML
    private void initialize() {
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setMain(JavaFxApplication main) {
        this.main = main;
    }

    public void setCar(Car car) {
        this.car = car;
        brandField.setText(car.getBrand());
        carcaseField.setText(car.getCarcase());
        colorField.setText(car.getColor());
        doorField.setText(String.valueOf(car.getDoorNumber()));
        gearField.setText(car.getGearbox());
        releaseField.setText(String.valueOf(car.getReleaseYear()));
        releaseField.setPromptText("yyyy");
        seatsField.setText(String.valueOf(car.getSeats()));
        if (car.getComfortLevel() == null){
            comfortField.setText("");
        }else{
            comfortField.setText(car.getComfortLevel().getId());
        }
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    public Car getCar() {return car;}

    @FXML
    private void handleOk(){
        try {
            if (isInputValid()) {
                car.setBrand(brandField.getText());
                car.setCarcase(carcaseField.getText());
                car.setColor(colorField.getText());
                car.setDoorNumber(Integer.parseInt(doorField.getText()));
                car.setGearbox(gearField.getText());
                car.setReleaseYear(Integer.parseInt(releaseField.getText()));
                car.setSeats(Integer.parseInt(seatsField.getText()));
                JSONObject jsonObject = ConnectionPerfomance.excecuteOnlyGET("http://localhost:9090/api/tests/getComfortLevel/", comfortField.getText(), "ComfortLevel");
                ComfortLevel comfortLevel = new ComfortLevel();
                comfortLevel.setId(jsonObject.getString("id"));
                comfortLevel.setLevel(jsonObject.getString("level"));
                comfortLevel.setDeposit(jsonObject.getLong("deposit"));
                comfortLevel.setRentPrice(jsonObject.getLong("rentPrice"));
                comfortLevel.setMinExperience(jsonObject.getInt("minExperience"));
                car.setComfortLevel(comfortLevel);
                okClicked = true;
                dialogStage.close();
            }
        }catch (java.net.ConnectException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(this.dialogStage);
            alert.setTitle("No connection");
            alert.setHeaderText("Not established connection with the server");
            alert.setContentText("Connection with the server was not established. try again later. Halting th system...");
            ButtonType answer = alert.showAndWait().orElse(ButtonType.OK);
            if (ButtonType.OK.equals(answer)) {
                try {
                    this.dialogStage.close();
                    this.main.stop();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    private boolean isInputValid(){
        String errorMessage = "";

        if (brandField.getText() == null || brandField.getText().length() == 0) {
            errorMessage += "No valid brand name!\n";
        }
        if (carcaseField.getText() == null || carcaseField.getText().length() == 0) {
            errorMessage += "No valid carcase name!\n";
        }
        if (colorField.getText() == null || colorField.getText().length() == 0) {
            errorMessage += "No valid color!\n";
        }

        if (doorField.getText() == null || doorField.getText().length() == 0 || Integer.parseInt(doorField.getText())<2 || Integer.parseInt(doorField.getText())>10) {
            errorMessage += "No valid number of doors!\n";
        }

        if (gearField.getText() == null || gearField.getText().length() == 0 || (!gearField.getText().matches("Auto") && !gearField.getText().matches("Mech"))) {
            errorMessage += "No valid gearbox mane!\n";
        }

        if (releaseField.getText() == null || releaseField.getText().length() == 0 || Integer.parseInt(releaseField.getText()) < 1887 || Integer.parseInt(releaseField.getText()) > Calendar.getInstance().get(Calendar.YEAR)){
            errorMessage += "No valid release year!\n";
        }

        if (seatsField.getText() == null || seatsField.getText().length() == 0 || Integer.parseInt(seatsField.getText()) < 2 || Integer.parseInt(seatsField.getText()) > 60){
            errorMessage += "No valid number of seats!\n";
        }

        if (comfortField.getText() == null || comfortField.getText().length() == 0 ){
            errorMessage += "No valid comfort level!\n";
        }else{
            try {
                JSONObject jsonObject = ConnectionPerfomance.excecuteOnlyGET("http://localhost:9090/api/tests/getComfortLevel/", comfortField.getText(), "ComfortLevel");
                if (jsonObject.isEmpty()) {
                    errorMessage += "No valid comfort level!\n";
                }
            }catch (java.net.ConnectException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initOwner(this.dialogStage);
                alert.setTitle("No connection");
                alert.setHeaderText("Not established connection with the server");
                alert.setContentText("Connection with the server was not established. try again later. Halting th system...");
                ButtonType answer = alert.showAndWait().orElse(ButtonType.OK);
                if (ButtonType.OK.equals(answer)) {
                    try {
                        this.dialogStage.close();
                        this.main.stop();
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}
