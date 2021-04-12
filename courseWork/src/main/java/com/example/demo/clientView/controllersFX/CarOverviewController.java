package com.example.demo.clientView.controllersFX;

import com.example.demo.ServerSide.models.Car;
import com.example.demo.clientView.JavaFxApplication;
import com.example.demo.utils.ConnectionPerfomance;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class CarOverviewController {
    @FXML
    private TableView<Car> carTable;
    @FXML
    private TableColumn<Car,String> brandColumn;
    @FXML
    private TableColumn<Car,String> gearBoxColumn;
    @FXML
    private Label brandLabel;
    @FXML
    private Label carcaseLabel;
    @FXML
    private Label colorLabel;
    @FXML
    private Label doorLabel;
    @FXML
    private Label gearLabel;
    @FXML
    private Label releaseLabel;
    @FXML
    private Label seatsLabel;
    @FXML
    private Label comfortLabel;
    @FXML
    private Label freeLabel;

    private JavaFxApplication main;

    public CarOverviewController(){}

    @FXML
    private void initialize(){

        brandColumn.setCellValueFactory(cellData -> cellData.getValue().getBrandProperty());
        gearBoxColumn.setCellValueFactory(cellData -> cellData.getValue().getGearProperty());

        showCarOverviewDetails(null); //очищаем справа
        carTable.getSelectionModel().selectedItemProperty().addListener(
                (observable,oldValue,newValue) -> showCarOverviewDetails(newValue)
        ); //при изменении слушатель изменит данные на новые из таблицы

    }
    public void setMain(JavaFxApplication main){
        this.main = main;
        carTable.setItems(main.getExistingCars());
    }


    @FXML
    private void handleDeleteCar(){
        int selectedIndex = carTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initOwner(main.getPrimaryStage());
            alert.setTitle("Submit deleting");
            alert.setHeaderText("Delete this car?");
            alert.setContentText("Are you sure if you want to delete this car?");
            ButtonType answer = alert.showAndWait().orElse(ButtonType.OK);
            if (answer.equals(ButtonType.OK)){
                try {
                    ConnectionPerfomance.excecuteDELETE("http://localhost:9090/api/tests/deleteCar=" + carTable.getItems().get(selectedIndex).getId());
                    this.main.showCarOwerview();
                }catch (java.net.ConnectException e){
                    this.main.handleNoConnection();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(main.getPrimaryStage());
            alert.setTitle("No selection");
            alert.setHeaderText("No car selection");
            alert.setContentText("Please, select car in the table");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleBreakTrough(){
        int selectedIndex = carTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0){
            try {
                if (validCar(carTable.getItems().get(selectedIndex))) {
                    try {
                        ObservableList<Car> cars = FXCollections.observableArrayList();
                        cars.add(carTable.getItems().get(selectedIndex));
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(JavaFxApplication.class.getResource("controllersFX/PersonForBreakTrough.fxml"));
                        AnchorPane page = loader.load();
                        Stage searchStage = new Stage();
                        searchStage.setTitle("Choose Person");
                        searchStage.initModality(Modality.WINDOW_MODAL);
                        searchStage.initOwner(this.main.getPrimaryStage());
                        Scene scene = new Scene(page);
                        searchStage.setScene(scene);
                        PersonForBeakTroughController controller = loader.getController();
                        controller.setDialogStage(searchStage);
                        controller.setMain(main);
                        controller.setCars(cars);
                        searchStage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.initOwner(main.getPrimaryStage());
                    alert.setTitle("Машина занята");
                    alert.setHeaderText("Выбранный автомобиль недоступен");
                    alert.setContentText("Нельзя перейтии к оформлению, так как эта машина сейчас арендуется или находится на техобслуживании.\nВыберите другой автомобиль.");
                    alert.showAndWait();
                }
            }catch (java.net.ConnectException e){
                this.main.handleNoConnection();
            }catch (IOException e){
                e.printStackTrace();
            }

        } else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(main.getPrimaryStage());
            alert.setTitle("No selection");
            alert.setHeaderText("No car selection");
            alert.setContentText("Please, select car in the table");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleEditCar(){
        Car selectedCar = carTable.getSelectionModel().getSelectedItem();
        if (selectedCar != null) {
            HashMap<Boolean, Car> answer = showCarEditDialog(selectedCar);
            boolean okClicked = (boolean) answer.keySet().toArray()[0];
            Car car = answer.get(okClicked);
            if (okClicked) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", car.getId());
                jsonObject.put("brand", car.getBrand());
                jsonObject.put("carcase", car.getCarcase());
                jsonObject.put("color", car.getColor());
                jsonObject.put("doorNumber", car.getDoorNumber());
                jsonObject.put("gearbox", car.getGearbox());
                jsonObject.put("releaseYear", car.getReleaseYear());
                jsonObject.put("seats", car.getSeats());
                jsonObject.put("available", car.isAvailable());
                jsonObject.put("comfortLevel", car.getComfortLevel().toString().replace("ComfortLevel", "").replace("=", ":"));
                try {
                    ConnectionPerfomance.excecutePUT("http://localhost:9090/api/tests/updateCar", jsonObject);
                    this.main.showCarOwerview();
                    showCarOverviewDetails(car);
                } catch (java.net.ConnectException e){
                    this.main.handleNoConnection();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(main.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No car Selected");
            alert.setContentText("Please select a car in the table.");

            alert.showAndWait();
        }
    }

    @FXML
    private void handleNewCar(){
        Car tempCar = new Car();
        HashMap<Boolean, Car> answer = showCarEditDialog(tempCar);
        boolean okClicked = (boolean) answer.keySet().toArray()[0];
        Car car = answer.get(okClicked);
        if (okClicked) {
            JSONObject inputCar = new JSONObject();
            inputCar.put("brand", URLEncoder.encode(car.getBrand(), StandardCharsets.UTF_8));
            inputCar.put("carcase", URLEncoder.encode(car.getCarcase(), StandardCharsets.UTF_8));
            inputCar.put("color", URLEncoder.encode(car.getColor(), StandardCharsets.UTF_8));
            inputCar.put("doorNumber", car.getDoorNumber());
            inputCar.put("gearbox", URLEncoder.encode(car.getGearbox(), StandardCharsets.UTF_8));
            inputCar.put("releaseYear", car.getReleaseYear());
            inputCar.put("seats", car.getSeats());
            inputCar.put("available", true);
            JSONObject lvl = new JSONObject();
            lvl.put("id", car.getComfortLevel().getId());
            lvl.put("deposit", car.getComfortLevel().getDeposit());
            lvl.put("minExperience", car.getComfortLevel().getMinExperience());
            lvl.put("rentPrice", car.getComfortLevel().getRentPrice());
            lvl.put("level", URLEncoder.encode(car.getComfortLevel().getLevel(), StandardCharsets.UTF_8));
            inputCar.put("comfortLevel", lvl);
            try {
                ConnectionPerfomance.excecutePost("http://localhost:9090/api/tests/addCar", inputCar);
            }catch (java.net.ConnectException e){
                this.main.handleNoConnection();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        main.showCarOwerview();
    }

    private void showCarOverviewDetails(Car car){
        if(car != null){
            brandLabel.setText(car.getBrand());
            carcaseLabel.setText(car.getCarcase());
            colorLabel.setText(car.getColor());
            doorLabel.setText(String.valueOf(car.getDoorNumber()));
            gearLabel.setText(car.getGearbox());
            releaseLabel.setText(String.valueOf(car.getReleaseYear()));
            seatsLabel.setText(String.valueOf(car.getSeats()));
            comfortLabel.setText(car.getComfortLevel().getId());
            if (car.isAvailable()){
                freeLabel.setText("Нет");
            }else{
                freeLabel.setText("Да");
            }
        }
        else{
            brandLabel.setText("");
            carcaseLabel.setText("");
            colorLabel.setText("");
            doorLabel.setText("");
            gearLabel.setText("");
            releaseLabel.setText("");
            seatsLabel.setText("");
            comfortLabel.setText("");
            freeLabel.setText("");
        }
    }

    private HashMap<Boolean, Car> showCarEditDialog(Car car){
        try {
            HashMap<Boolean, Car> dictionary = new HashMap<>();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(JavaFxApplication.class.getResource("controllersFX/CarEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Car");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(main.getPrimaryStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            CarEditingController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setCar(car);
            controller.setMain(this.main);
            dialogStage.showAndWait();
            dictionary.put(controller.isOkClicked(), controller.getCar());
            return dictionary;
        } catch (IOException e) {
            HashMap<Boolean, Car> dictionary = new HashMap<>();
            dictionary.put(false, null);
            e.printStackTrace();
            return dictionary;
        }
    }
    private boolean validCar(Car car) throws IOException{
        if(car.isAvailable()){
            String free = ConnectionPerfomance.excecuteValidation("http://localhost:9090/api/tests/Car=" + car.getId() + "/rents");
            return free.matches("true");
        }else{
            return false;
        }
    }

}
