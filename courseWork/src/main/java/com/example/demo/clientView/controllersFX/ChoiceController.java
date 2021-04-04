package com.example.demo.clientView.controllersFX;

import com.example.demo.ServerSide.models.Car;
import com.example.demo.ServerSide.models.Client;
import com.example.demo.ServerSide.models.Discount;
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
import org.json.JSONObject;

import java.io.IOException;
import java.time.LocalDate;
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
    @FXML
    private Label FirstNameLabel;
    @FXML
    private Label LastNameLabel;
    @FXML
    private Label PassportLabel;
    @FXML
    private Label PhoneLabel;
    @FXML
    private Label LiscenceLabel;
    @FXML
    private Label daysLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private Label saleLabel;
    @FXML
    private Label saleSumLabel;
    @FXML
    private Label finalLabel;


    private Stage stage;

    private JavaFxApplication main;

    private Client client;

    private LocalDate start;

    private LocalDate end;

    private Discount discount;

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

    public void setCars(ObservableList<Car> cars) {
        this.carTable.setItems(cars);
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public void setClient(Client client) throws IOException {
        this.client = client;
        this.FirstNameLabel.setText(client.getFirstName());
        this.LastNameLabel.setText(client.getLastName());
        this.LiscenceLabel.setText(DateUtil.formatForPeople(DateUtil.parse(client.getLiscenceDate())));
        this.PassportLabel.setText(client.getPassport());
        this.PhoneLabel.setText(client.getPhoneNumber());
        this.daysLabel.setText(String.valueOf(end.compareTo(start)));
        setSales();
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
            priceLabel.setText(String.valueOf(car.getComfortLevel().getRentPrice()*end.compareTo(start)+car.getComfortLevel().getDeposit()));
            saleSumLabel.setText(String.valueOf(Float.parseFloat(priceLabel.getText())*(this.discount.getPercent()/100)));
            finalLabel.setText(String.valueOf(Float.parseFloat(priceLabel.getText())-Float.parseFloat(saleSumLabel.getText())));
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
            priceLabel.setText("");
            finalLabel.setText("");
            saleSumLabel.setText("");
        }
    }

    private void setSales() throws IOException {
        JSONObject discountBody = ConnectionPerfomance.excecuteOnlyGET("http://localhost:9090/api/tests/discount_for_client=", String.valueOf(this.client.getId()), "discount");
        Discount discount = new Discount();
        discount.setId(discountBody.getString("id"));
        discount.setPercent(discountBody.getFloat("percent"));
        this.discount = discount;
        this.saleLabel.setText(discount.getId());
    }

}
