package sample.controllers;

import sample.models.*;
import sample.JavaFxApplication;
import sample.utils.ConnectionPerfomance;
import sample.utils.DateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

/**
 * JavaFX scene controller
 */
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

    /**
     * Empty initializer
     */
    public RentOverviewController(){}

    /**
     * fills the table columns with data
     */
    @FXML
    private void initialize(){

        carNameColumn.setCellValueFactory(cellData -> cellData.getValue().getBrandProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().getLastNameProperty());

        showRentOverviewDetails(null);
        rentTable.getSelectionModel().selectedItemProperty().addListener(
                (observable,oldValue,newValue) -> showRentOverviewDetails(newValue)
        );

    }

    /**
     * sets the main for this controller
     * @param main JavaFxApplication main
     */
    public void setMain(JavaFxApplication main){
        this.main = main;
        rentTable.setItems(getAllRents());
    }

    /**
     * Loads data from the database via request
     * @return ObservableList of Rent with received data
     */
    private ObservableList<Rent> getAllRents(){
        try {
            ObservableList<Rent> rents = FXCollections.observableArrayList();
            JSONArray rawRents = ConnectionPerfomance.excecuteManyGET("http://localhost:9090/api/tests/AllRents");
            for (int i = 0; i < rawRents.length(); i++) {
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
            return rents;
        }catch (java.net.ConnectException e){
            this.main.handleNoConnection();
            return null;
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Shows the details of the selected element from table
     * @param rent selected Rent
     */
    private void showRentOverviewDetails(Rent rent){
        if(rent != null){
            brandLabel.setText(rent.getCar().getBrand());
            gearboxLabel.setText(rent.getCar().getGearbox());
            comfortLabel.setText(rent.getCar().getComfortLevel().getId());
            startLabel.setText(DateUtil.formatForPeople(rent.getStartDate()));
            endLabel.setText(DateUtil.formatForPeople(rent.getEndDate()));
            clientLabel.setText(rent.getClient().getLastName()+" "+rent.getClient().getFirstName());
            phoneLabel.setText(("+"+rent.getClient().getPhoneNumber()).replace(" ", ""));
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

    /**
     * Converts JSONObject to a Car object
     * @param rawCar JSONObject with Car parameters
     * @return created and filled Car object
     */
    private Car fillCar(JSONObject rawCar){
        try {
            Car car = new Car();
            car.setId(rawCar.getLong("id"));
            car.setBrand(URLDecoder.decode(rawCar.getString("brand"), StandardCharsets.UTF_8));
            car.setCarcase(URLDecoder.decode(rawCar.getString("carcase"), StandardCharsets.UTF_8));
            car.setGearbox(URLDecoder.decode(rawCar.getString("gearbox"), StandardCharsets.UTF_8));
            car.setDoorNumber(rawCar.getInt("doorNumber"));
            car.setSeats(rawCar.getInt("seats"));
            car.setReleaseYear(rawCar.getInt("releaseYear"));
            car.setColor(URLDecoder.decode(rawCar.getString("color"), StandardCharsets.UTF_8));
            car.setAvailable(rawCar.getBoolean("available"));
            JSONObject comf_lvl = ConnectionPerfomance.excecuteOnlyGET("http://localhost:9090/api/tests/LevelByCarId=", String.valueOf(rawCar.getLong("id")), "ComfortLevel");
            ComfortLevel comfortLevel = new ComfortLevel();
            comfortLevel.setId(comf_lvl.get("id").toString());
            comfortLevel.setLevel(URLDecoder.decode(comf_lvl.get("level").toString(), StandardCharsets.UTF_8));
            comfortLevel.setDeposit(Long.parseLong(comf_lvl.get("deposit").toString()));
            comfortLevel.setRentPrice(Long.parseLong(comf_lvl.get("rentPrice").toString()));
            comfortLevel.setMinExperience(Integer.parseInt(comf_lvl.get("minExperience").toString()));
            car.setComfortLevel(comfortLevel);
            return car;
        }catch (java.net.ConnectException e){
            this.main.handleNoConnection();
            return null;
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Converts JSONObject to a Client object
     * @param rawClient JSONObject with Client parameters
     * @return created and filled Client object
     */
    private Client fillClient(JSONObject rawClient){
        Client client = new Client();
        client.setId(rawClient.getLong("id"));
        client.setFirstName(URLDecoder.decode(rawClient.getString("firstName"), StandardCharsets.UTF_8));
        client.setLastName(URLDecoder.decode(rawClient.getString("lastName"), StandardCharsets.UTF_8));
        client.setPassport(URLDecoder.decode(rawClient.getString("passport"), StandardCharsets.UTF_8));
        client.setPhoneNumber(URLDecoder.decode(rawClient.getString("phoneNumber"), StandardCharsets.UTF_8));
        client.setLiscenceDate(rawClient.getString("liscenceDate"));
        return client;
    }

    /**
     * Converts JSONObject to a Discount object
     * @param rawDiscount JSONObject with Discount parameters
     * @return created and filled Discount object
     */
    private Discount fillDiscount(JSONObject rawDiscount){
        Discount discount = new Discount();
        discount.setId(rawDiscount.getString("id"));
        discount.setPercent(rawDiscount.getFloat("percent"));
        return discount;
    }
}
