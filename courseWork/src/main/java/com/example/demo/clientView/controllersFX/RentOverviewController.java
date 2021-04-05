package com.example.demo.clientView.controllersFX;

import com.example.demo.ServerSide.models.*;
import com.example.demo.clientView.JavaFxApplication;
import com.example.demo.utils.ConnectionPerfomance;
import com.example.demo.utils.DateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

public class RentOverviewController {
    @FXML
    private TableView<Rent> rentTable;
    @FXML
    private TableColumn<Rent,String> carNameColumn;
    @FXML
    private TableColumn<Rent,String> lastNameColumn;
    @FXML
    private Label brandLabel;
    @FXML
    private Label gearboxLabel;
    @FXML
    private Label comfortLabel;
    @FXML
    private Label startLabel;
    @FXML
    private Label endLabel;
    @FXML
    private Label clientLabel;
    @FXML
    private Label phoneLabel;
    @FXML
    private Label saleLabel;
    @FXML
    private Label finalLabel;

    private JavaFxApplication main;

    public RentOverviewController(){}

    @FXML
    private void initialize(){

        carNameColumn.setCellValueFactory(cellData -> cellData.getValue().getBrandProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().getLastNameProperty());

        showRentOverviewDetails(null); //очищаем справа
        rentTable.getSelectionModel().selectedItemProperty().addListener(
                (observable,oldValue,newValue) -> showRentOverviewDetails(newValue)
        ); //при изменении слушатель изменит данные на новые из таблицы

    }
    public void setMain(JavaFxApplication main) throws IOException {
        this.main = main;
        rentTable.setItems(getAllRents());
    }

    private ObservableList<Rent> getAllRents() throws IOException {
        ObservableList<Rent> rents = FXCollections.observableArrayList();
        JSONArray rawRents = ConnectionPerfomance.excecuteManyGET("http://localhost:9090/api/tests/AllRents");
        for (int i=0; i<rawRents.length(); i++){
            Rent rent = new Rent();
            JSONObject rawRent = rawRents.getJSONObject(i);
            rent.setId(rawRent.getLong("id"));
            rent.setEndDate(DateUtil.parse(rawRent.getString("endDate")));
            rent.setStartDate(DateUtil.parse(rawRent.getString("startDate")));
            rent.setTotalSumm(rawRent.getFloat("totalSumm"));
            JSONObject rawCar = ConnectionPerfomance.excecuteOnlyGET("http://localhost:9090/api/tests/CarByRentId=", String.valueOf(rent.getId()), "Car");
            rent.setCar(fillCar(rawCar));
            JSONObject rawClient = ConnectionPerfomance.excecuteOnlyGET("http://localhost:9090/api/tests/ClientByRentId=", String.valueOf(rent.getId()), "Client");
            rent.setClient(fillClient(rawClient));
            JSONObject rawDiscount = ConnectionPerfomance.excecuteOnlyGET("http://localhost:9090/api/tests/DiscountByRentId=", String.valueOf(rent.getId()), "Discount");
            rent.setDiscount(fillDiscount(rawDiscount));
            rents.add(rent);
        }
        return  rents;
    }

    private void showRentOverviewDetails(Rent rent){
        if(rent != null){
            brandLabel.setText(rent.getCar().getBrand());
            gearboxLabel.setText(rent.getCar().getGearbox());
            comfortLabel.setText(rent.getCar().getComfortLevel().getId());
            startLabel.setText(DateUtil.formatForPeople(rent.getStartDate()));
            endLabel.setText(DateUtil.formatForPeople(rent.getEndDate()));
            clientLabel.setText(rent.getClient().getLastName()+" "+rent.getClient().getFirstName());
            phoneLabel.setText(rent.getClient().getPhoneNumber());
            saleLabel.setText(rent.getDiscount().getId());
            finalLabel.setText(String.valueOf(rent.getTotalSumm()));
        }
        else{
            brandLabel.setText("");
            gearboxLabel.setText("");
            comfortLabel.setText("");
            startLabel.setText("");
            endLabel.setText("");
            clientLabel.setText("");
            phoneLabel.setText("");
            saleLabel.setText("");
            finalLabel.setText("");
        }
    }

    private Car fillCar(JSONObject rawCar) throws IOException {
        Car car = new Car();
        car.setId(rawCar.getLong("id"));
        car.setBrand(rawCar.getString("brand"));
        car.setCarcase(rawCar.getString("carcase"));
        car.setGearbox(rawCar.getString("gearbox"));
        car.setDoorNumber(rawCar.getInt("doorNumber"));
        car.setSeats(rawCar.getInt("seats"));
        car.setReleaseYear(rawCar.getInt("releaseYear"));
        car.setColor(rawCar.getString("color"));
        car.setAvailable(rawCar.getBoolean("available"));
        JSONObject comf_lvl = ConnectionPerfomance.excecuteOnlyGET("http://localhost:9090/api/tests/LevelByCarId=", String.valueOf(rawCar.getLong("id")), "ComfortLevel");
        ComfortLevel comfortLevel = new ComfortLevel();
        comfortLevel.setId(comf_lvl.get("id").toString());
        comfortLevel.setLevel(comf_lvl.get("level").toString());
        comfortLevel.setDeposit(Long.parseLong(comf_lvl.get("deposit").toString()));
        comfortLevel.setRentPrice(Long.parseLong(comf_lvl.get("rentPrice").toString()));
        comfortLevel.setMinExperience(Integer.parseInt(comf_lvl.get("minExperience").toString()));
        car.setComfortLevel(comfortLevel);
        return car;
    }

    private Client fillClient(JSONObject rawClient){
        Client client = new Client();
        client.setId(rawClient.getLong("id"));
        client.setFirstName(rawClient.getString("firstName"));
        client.setLastName(rawClient.getString("lastName"));
        client.setPassport(rawClient.getString("passport"));
        client.setPhoneNumber(rawClient.getString("phoneNumber"));
        client.setLiscenceDate(rawClient.getString("liscenceDate"));
        return client;
    }

    private Discount fillDiscount(JSONObject rawDiscount){
        Discount discount = new Discount();
        discount.setId(rawDiscount.getString("id"));
        discount.setPercent(rawDiscount.getFloat("percent"));
        return discount;
    }
}
