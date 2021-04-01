package com.example.demo.clientView.controllersFX;

import com.example.demo.ServerSide.models.Car;
import com.example.demo.ServerSide.models.Client;
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

public class ChoiceController {
    @FXML
    private TableView<Car> carTable;
    @FXML
    private TableColumn<Car,String> brandColumn;
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

    private Stage stage;

    private JavaFxApplication main;

    public ChoiceController(){}

    @FXML
    private void initialize(){

        brandColumn.setCellValueFactory(cellData -> cellData.getValue().getBrandProperty());

        showCarOverviewDetails(null); //очищаем справа
        carTable.getSelectionModel().selectedItemProperty().addListener(
                (observable,oldValue,newValue) -> showCarOverviewDetails(newValue)
        ); //при изменении слушатель изменит данные на новые из таблицы

    }
    public void setMain(JavaFxApplication main){
        this.main = main;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
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
