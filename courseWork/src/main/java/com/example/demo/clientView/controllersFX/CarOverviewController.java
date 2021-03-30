package com.example.demo.clientView.controllersFX;

import com.example.demo.ServerSide.models.Car;
import com.example.demo.clientView.JavaFxApplication;
import com.example.demo.utils.ConnectionPerfomance;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.IOException;
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
    private void handleDeleteCar() throws IOException {
        int selectedIndex = carTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initOwner(main.getPrimaryStage());
            alert.setTitle("Submit deleting");
            alert.setHeaderText("Delete this car?");
            alert.setContentText("Are you sure if you want to delete this car?");
            ButtonType answer = alert.showAndWait().orElse(ButtonType.OK);
            if (answer.equals(ButtonType.OK)){
                ConnectionPerfomance.excecuteDELETE("http://localhost:9090/api/tests/deleteCar="+carTable.getItems().get(selectedIndex).getId());
                this.main.showCarOwerview();
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(main.getPrimaryStage());
            alert.setTitle("No selection");
            alert.setHeaderText("No Person selection");
            alert.setContentText("Please, select person in the table");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleEditCar() throws IOException {
        Car selectedCar = carTable.getSelectionModel().getSelectedItem();
        if (selectedCar != null) {
            HashMap<Boolean, Car> answer = showCarEditDialog(selectedCar);
            boolean okClicked = (boolean) answer.keySet().toArray()[0];
            Car car = answer.get(okClicked);
            if (okClicked){
                //TODO добавть update для бд
                main.showCarOwerview();
                showCarOverviewDetails(car);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(main.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");

            alert.showAndWait();
        }
    }

    @FXML
    private void handleNewCar() throws IOException {
        Car tempCar = new Car();
        HashMap<Boolean, Car> answer = showCarEditDialog(tempCar);
        boolean okClicked = (boolean) answer.keySet().toArray()[0];
        Car car = answer.get(okClicked);
        if (okClicked) {
            JSONObject inputCar = new JSONObject();
            inputCar.put("brand", car.getBrand());
            inputCar.put("carcase", car.getCarcase());
            inputCar.put("color", car.getColor());
            inputCar.put("doorNumber", car.getDoorNumber());
            inputCar.put("gearbox", car.getGearbox());
            inputCar.put("releaseYear", car.getReleaseYear());
            inputCar.put("seats", car.getSeats());
            JSONObject lvl = new JSONObject();
            lvl.put("id", car.getComfortLevel().getId());
            lvl.put("deposit", car.getComfortLevel().getDeposit());
            lvl.put("minExperience", car.getComfortLevel().getMinExperience());
            lvl.put("rentPrice", car.getComfortLevel().getRentPrice());
            lvl.put("level", car.getComfortLevel().getLevel());
            inputCar.put("comfortLevel", lvl);
            ConnectionPerfomance.excecutePost("http://localhost:9090/api/tests/addCar", inputCar);
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
        }
    }

    private HashMap<Boolean, Car> showCarEditDialog(Car car){
        try {
            HashMap<Boolean, Car> dictionary = new HashMap<>();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(JavaFxApplication.class.getResource("views/CarEditDialog.fxml"));
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

}
