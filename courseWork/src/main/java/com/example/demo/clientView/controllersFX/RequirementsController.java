package com.example.demo.clientView.controllersFX;

import com.example.demo.ServerSide.models.Car;
import com.example.demo.ServerSide.models.ComfortLevel;
import com.example.demo.clientView.JavaFxApplication;
import com.example.demo.ServerSide.models.Client;
import com.example.demo.utils.ConnectionPerfomance;
import com.example.demo.utils.DateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.time.LocalDate;

public class RequirementsController {
    private JavaFxApplication main;
    private Client client;
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
    @FXML
    private ComboBox<String> cb1;
    @FXML
    private ComboBox<String> cb2;
    @FXML
    private ComboBox<String> cb3;
    @FXML
    private ComboBox<String> cb4;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;

    private ObservableList<String> gearbox;
    private ObservableList<String> doorNumber;
    private ObservableList<String> seats;
    private ObservableList<String> levels;

    public RequirementsController(){}

    @FXML
    private void initialize(){
    }

    public void setMain(JavaFxApplication main){
        this.main = main;
        this.createLists();
    }

    public void setPerson(Client person) {
        this.client = person;
        FirstNameLabel.setText(person.getFirstName());
        LastNameLabel.setText(person.getLastName());
        PhoneLabel.setText(person.getPhoneNumber());
        PassportLabel.setText(person.getPassport());
        LiscenceLabel.setText(DateUtil.formatForPeople(DateUtil.parse(person.getLiscenceDate())));
    }

    public void setStage(Stage stage) {
        this.stage=stage;
    }

    public void handleSearch(){
        StringBuilder alertmessage = new StringBuilder();
        if (startDatePicker.getValue()==null){
            alertmessage.append("Введите дату начала аренды!\n");
            if (endDatePicker.getValue()==null){
                alertmessage.append("Введите дату окончания аренды!\n");
            }
        }else{
            if(startDatePicker.getValue().isBefore(LocalDate.now())){
                alertmessage.append("Дата начала аренды должна быть не раньше сегодняшнего дня!");
            }
            if(endDatePicker.getValue().isBefore(LocalDate.now()) || endDatePicker.getValue().equals(LocalDate.now())){
                alertmessage.append("Дата окончания аренды должна быть не раньше завтрашнего дня!");
            }
            if(startDatePicker.getValue().isAfter(endDatePicker.getValue())){
                alertmessage.append("Дата окончания аренды должна быть позже даты начала!");
            }
        }
        if (alertmessage.length()==0){
            try {
                LocalDate start = startDatePicker.getValue();
                LocalDate end = endDatePicker.getValue();
                String gear = cb1.getSelectionModel().getSelectedItem();
                String doors = cb2.getSelectionModel().getSelectedItem();
                String seat = cb3.getSelectionModel().getSelectedItem();
                String level = cb4.getSelectionModel().getSelectedItem();
                JSONArray suitableCars = ConnectionPerfomance.excecuteManyGET("http://localhost:9090/api/tests/" +
                        "ApproproateCars/gearBox=" + gear + "&doors=" + doors + "&seats=" + seat + "&comfortLevel=" + level + "&startDate=" + start + "&exp=" + client.getExperience());
                if (suitableCars.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.initOwner(main.getPrimaryStage());
                    alert.setTitle("Машин не найдено");
                    alert.setHeaderText("Машин по Ваши требованиям не найдено");
                    alert.setContentText("Пожалуйста, поменяйте свои требования в каких-то позициях, если можете");

                    alert.showAndWait();
                } else {
                    ObservableList<Car> foundCars = FXCollections.observableArrayList();
                    for (int i = 0; i < suitableCars.length(); i++) {
                        Car foundCar = new Car();
                        foundCar.setId(Long.valueOf(suitableCars.getJSONObject(i).get("id").toString()));
                        foundCar.setBrand(suitableCars.getJSONObject(i).get("brand").toString());
                        foundCar.setCarcase(suitableCars.getJSONObject(i).get("carcase").toString());
                        foundCar.setGearbox(suitableCars.getJSONObject(i).get("gearbox").toString());
                        foundCar.setDoorNumber(Integer.parseInt(suitableCars.getJSONObject(i).get("doorNumber").toString()));
                        foundCar.setSeats(Integer.parseInt(suitableCars.getJSONObject(i).get("seats").toString()));
                        foundCar.setReleaseYear(Integer.parseInt(suitableCars.getJSONObject(i).get("releaseYear").toString()));
                        foundCar.setColor(suitableCars.getJSONObject(i).get("color").toString());
                        JSONObject comf_lvl = ConnectionPerfomance.excecuteOnlyGET("http://localhost:9090/api/tests/LevelByCarId=", suitableCars.getJSONObject(i).get("id").toString(), "ComfortLevel");
                        ComfortLevel comfortLevel = new ComfortLevel();
                        comfortLevel.setId(comf_lvl.get("id").toString());
                        comfortLevel.setLevel(comf_lvl.get("level").toString());
                        comfortLevel.setDeposit(Long.parseLong(comf_lvl.get("deposit").toString()));
                        comfortLevel.setRentPrice(Long.parseLong(comf_lvl.get("rentPrice").toString()));
                        comfortLevel.setMinExperience(Integer.parseInt(comf_lvl.get("minExperience").toString()));
                        foundCar.setComfortLevel(comfortLevel);
                        foundCars.add(foundCar);
                    }
                    try {
                        Stage stage = new Stage();
                        stage.setTitle("Аренда");
                        stage.initModality(Modality.WINDOW_MODAL);
                        stage.initOwner(this.main.getPrimaryStage());
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(JavaFxApplication.class.getResource("controllersFX/Choice.fxml"));
                        AnchorPane choice = loader.load();
                        Scene scene = new Scene(choice);
                        stage.setScene(scene);
                        ChoiceController controller = loader.getController();
                        controller.setMain(this.main);
                        controller.setStage(stage);
                        controller.setStart(start);
                        controller.setEnd(end);
                        controller.setClient(this.client);
                        controller.setCars(foundCars);
                        stage.showAndWait();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }catch (java.net.ConnectException e){
                this.stage.close();
                this.main.handleNoConnection();
            }catch (IOException e){
                e.printStackTrace();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(this.stage);
            alert.setTitle("Незаполненные даты");
            alert.setHeaderText("Пожалуйста, заполните необходимые поля");
            alert.setContentText(String.valueOf(alertmessage));

            alert.showAndWait();
        }


    }

    public void handleBack(){
        this.main.initRootLayout();
        this.main.showPersonOverview();
    }

    private void createLists(){
        this.gearbox = FXCollections.observableArrayList();
        this.doorNumber = FXCollections.observableArrayList();
        this.seats = FXCollections.observableArrayList();
        this.levels = FXCollections.observableArrayList();
        this.gearbox.add("-");
        this.doorNumber.add("-");
        this.seats.add("-");
        this.levels.add("-");
        this.fillCarInfo();
        this.setupComboB();

    }

    private void fillCarInfo(){
        for (Car car: this.main.getExistingCars()){
            if(car.getComfortLevel().getMinExperience()<=this.client.getExperience()){
                if(!gearbox.contains(car.getGearbox())){gearbox.add(car.getGearbox());}
                if(!doorNumber.contains(String.valueOf(car.getDoorNumber()))){doorNumber.add(String.valueOf(car.getDoorNumber()));}
                if(!seats.contains(String.valueOf(car.getSeats()))){seats.add(String.valueOf(car.getSeats()));}
                if(!levels.contains(car.getComfortLevel().getId())){levels.add(car.getComfortLevel().getId());}
            }
        }
    }

    private void setupComboB(){
        cb1.setItems(gearbox);
        cb1.setValue(gearbox.get(0));
        cb2.setItems(doorNumber);
        cb2.setValue(doorNumber.get(0));
        cb3.setItems(seats);
        cb3.setValue(seats.get(0));
        cb4.setItems(levels);
        cb4.setValue(levels.get(0));
    }
}
