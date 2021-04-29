package sample.controllers;

import sample.models.Car;
import sample.models.ComfortLevel;
import sample.JavaFxApplication;
import sample.utils.ConnectionPerfomance;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;

/**
 * JavaFX scene controller
 */
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

    /**
     * sets the stage for this controller
     * @param dialogStage Javafx stage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * sets the main for this controller
     * @param main JavaFxApplication main
     */
    public void setMain(JavaFxApplication main) {
        this.main = main;
    }

    /**
     * Fills the scene text fields with the Car's attributes
     * @param car Car object
     */
    public void setCar(Car car) {
        this.car = car;
        brandField.setText(car.getBrand());
        carcaseField.setText(car.getCarcase());
        colorField.setText(car.getColor());
        doorField.setText(String.valueOf(car.getDoorNumber()));
        gearField.setText(car.getGearbox());
        releaseField.setText(String.valueOf(car.getReleaseYear()));
        releaseField.setPromptText("гггг");
        seatsField.setText(String.valueOf(car.getSeats()));
        if (car.getComfortLevel() == null){
            comfortField.setText("");
        }else{
            comfortField.setText(car.getComfortLevel().getId());
        }
    }

    /**
     * returns the value of the isOkClicked variable
     * @return boolean isOkClicked
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * returns edited car
     * @return Car car
     */
    public Car getCar() {return car;}

    /**
     * Handles action on "OK" button with validation of the fields
     */
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
            this.main.handleNoConnection();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Handle action on "cancel" button with closing the stage
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    /**
     * validates the fields and alert the user if there are any incorrect fields
     * @return boolean result of validation
     */
    private boolean isInputValid(){
        String errorMessage = "";

        if (brandField.getText() == null || brandField.getText().length() == 0) {
            errorMessage += "Введите название марки машины!\n";
        }
        if (carcaseField.getText() == null || carcaseField.getText().length() == 0) {
            errorMessage += "Введите тип кузова машины!\n";
        }
        if (colorField.getText() == null || colorField.getText().length() == 0) {
            errorMessage += "Введите цвет корпуса машины!\n";
        }

        if (doorField.getText() == null || doorField.getText().length() == 0) {
            errorMessage += "Заполните поле с количеством дверей!\n";
        }else {
            try{
                if(Integer.parseInt(doorField.getText())<2 || Integer.parseInt(doorField.getText())>10){
                    errorMessage += "Введите корректное число дверей!\n";
                }
            }catch (NumberFormatException e){
                errorMessage += "Число дверей должно быть целым числом!\n";
            }
        }

        if (gearField.getText() == null || gearField.getText().length() == 0) {
            errorMessage += "Заполните тип коробки передач!\n";
        }else{
            if(!gearField.getText().matches("Auto") && !gearField.getText().matches("Mech")){
                errorMessage += "Корректно заполните тип коробки передач: Auto/Mech!\n";
            }
        }

        if (releaseField.getText() == null || releaseField.getText().length() == 0){
            errorMessage += "Заполните год выпуска автомобиля!\n";
        }else{
            try{
                if(Integer.parseInt(releaseField.getText()) < 1887 || Integer.parseInt(releaseField.getText()) > Calendar.getInstance().get(Calendar.YEAR)){
                    errorMessage += "Введите корректный год выпуска!!\n";
                }
            }catch (NumberFormatException e){
                errorMessage += "год выпуска должен быть четырехзначным числом!\n";
            }
        }

        if (seatsField.getText() == null || seatsField.getText().length() == 0){
            errorMessage += "Заполните количество мест!\n";
        }else{
            try{
                if(Integer.parseInt(seatsField.getText()) < 2 || Integer.parseInt(seatsField.getText()) > 60){
                    errorMessage += "Введите корректное количество мест!\n";}
            }catch (NumberFormatException e){
                errorMessage += "Количество мест должно быть цеоым числом!\n";
            }
        }

        if (comfortField.getText() == null || comfortField.getText().length() == 0 ){
            errorMessage += "Заполните уровень комфорта автомобиля!\n";
        }else{
            try {
                JSONObject jsonObject = ConnectionPerfomance.excecuteOnlyGET("http://localhost:9090/api/tests/getComfortLevel/", comfortField.getText(), "ComfortLevel");
                if (jsonObject.isEmpty()) {
                    errorMessage += "Заполните существующий уровень комфорта!\n";
                }
            }catch (java.net.ConnectException e){
                this.main.handleNoConnection();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Некорректные данные");
            alert.setHeaderText("Пожалуйста, исправьте некорректные поля!");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}
