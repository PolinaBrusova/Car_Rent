package com.example.demo.clientView.controllersFX;

import com.example.demo.ServerSide.models.Car;
import com.example.demo.clientView.JavaFxApplication;
import com.example.demo.ServerSide.models.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

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
    private ComboBox<String> cb5;
    @FXML
    private ComboBox<String> cb6;

    private ObservableList<String> brands;
    private ObservableList<String> carcase;
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
        LiscenceLabel.setText(person.getLiscenceDate());
    }

    public void setStage(Stage stage) {
        this.stage=stage;
    }

    public void handleSearch(){
        System.out.println("search");
    }

    private void createLists(){
        this.brands = FXCollections.observableArrayList();
        this.carcase = FXCollections.observableArrayList();
        this.gearbox = FXCollections.observableArrayList();
        this.doorNumber = FXCollections.observableArrayList();
        this.seats = FXCollections.observableArrayList();
        this.levels = FXCollections.observableArrayList();
        this.brands.add("-");
        this.carcase.add("-");
        this.gearbox.add("-");
        this.doorNumber.add("-");
        this.seats.add("-");
        this.levels.add("-");
        this.fillCarInfo();
        this.setupComboB();

    }

    private void fillCarInfo(){
        for (Car car: this.main.getExistingCars()){
            if(!brands.contains(car.getBrand())){brands.add(car.getBrand());}
            if(!carcase.contains(car.getCarcase())){carcase.add(car.getCarcase());}
            if(!gearbox.contains(car.getGearbox())){gearbox.add(car.getGearbox());}
            if(!doorNumber.contains(String.valueOf(car.getDoorNumber()))){doorNumber.add(String.valueOf(car.getDoorNumber()));}
            if(!seats.contains(String.valueOf(car.getSeats()))){seats.add(String.valueOf(car.getSeats()));}
            if(!levels.contains(car.getComfortLevel().getId())){levels.add(car.getComfortLevel().getId());}
        }
    }

    private void setupComboB(){
        cb1.setItems(brands);
        cb1.setValue(brands.get(0));
        cb2.setItems(carcase);
        cb2.setValue(carcase.get(0));
        cb3.setItems(gearbox);
        cb3.setValue(gearbox.get(0));
        cb4.setItems(doorNumber);
        cb4.setValue(doorNumber.get(0));
        cb5.setItems(seats);
        cb5.setValue(seats.get(0));
        cb6.setItems(levels);
        cb6.setValue(levels.get(0));
    }
}
