package com.example.demo.clientView.controllersFX;

import com.example.demo.ServerSide.models.Car;
import com.example.demo.clientView.JavaFxApplication;
import com.example.demo.ServerSide.models.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

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
        String gear = cb1.getSelectionModel().getSelectedItem();
        String doors = cb2.getSelectionModel().getSelectedItem();
        String seat = cb3.getSelectionModel().getSelectedItem();
        String level = cb4.getSelectionModel().getSelectedItem();
        //TODO сформировать запрос на бд с введенными параметрами, сделать открытие нового окна с вариантами, переданными от бд
        //TODO Если вариантов нет, то просто алерт
        try {
            Stage stage = new Stage();
            stage.setTitle("RENT");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(this.main.getPrimaryStage());
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(JavaFxApplication.class.getResource("views/Choice.fxml"));
            AnchorPane choice = loader.load();
            Scene scene = new Scene(choice);
            stage.setScene(scene);
            ChoiceController controller = loader.getController();
            controller.setMain(this.main);
            controller.setStage(stage);
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
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
            if(!gearbox.contains(car.getGearbox())){gearbox.add(car.getGearbox());}
            if(!doorNumber.contains(String.valueOf(car.getDoorNumber()))){doorNumber.add(String.valueOf(car.getDoorNumber()));}
            if(!seats.contains(String.valueOf(car.getSeats()))){seats.add(String.valueOf(car.getSeats()));}
            if(!levels.contains(car.getComfortLevel().getId())){levels.add(car.getComfortLevel().getId());}
        }
    }

    private void setupComboB(){
        cb1.setItems(gearbox);
        cb1.setValue(gearbox.get(0));
        cb1.getSelectionModel().selectedItemProperty().addListener(
                (observable,oldValue,newValue) -> System.out.println(newValue));
        cb2.setItems(doorNumber);
        cb2.setValue(doorNumber.get(0));
        cb3.setItems(seats);
        cb3.setValue(seats.get(0));
        cb4.setItems(levels);
        cb4.setValue(levels.get(0));
    }
}
