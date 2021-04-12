package com.example.demo.clientView.controllersFX;

import com.example.demo.ServerSide.models.*;
import com.example.demo.clientView.JavaFxApplication;
import com.example.demo.utils.ConnectionPerfomance;
import com.example.demo.utils.DateUtil;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.IOException;
import java.time.LocalDate;

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

    public void setClient(Client client){
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

    private void setSales(){
        try {
            JSONObject discountBody = ConnectionPerfomance.excecuteOnlyGET("http://localhost:9090/api/tests/discount_for_client=", String.valueOf(this.client.getId()), "discount");
            Discount discount = new Discount();
            discount.setId(discountBody.getString("id"));
            discount.setPercent(discountBody.getFloat("percent"));
            this.discount = discount;
            this.saleLabel.setText(discount.getId());
        }catch (java.net.ConnectException e){
            this.stage.close();
            this.main.handleNoConnection();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSubmitting(){
        if (carTable.getSelectionModel().getSelectedIndex()>-1){
            try {
                JSONObject rent = new JSONObject();
                rent.put("startDate", start);
                rent.put("endDate", end);
                rent.put("totalSum", Float.parseFloat(finalLabel.getText()));
                rent.put("employee", fillEmployee());
                rent.put("discount", fillDiscount());
                rent.put("client", fillClient());
                rent.put("car", fillCar());
                ConnectionPerfomance.excecutePost("http://localhost:9090/api/tests/addRent", rent);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.initOwner(main.getPrimaryStage());
                alert.setTitle("Завершение");
                alert.setHeaderText("Аренда успешно оформлена");
                alert.setContentText("Аренда внесена в базу");
                ButtonType answer = alert.showAndWait().orElse(ButtonType.OK);
                if (answer.equals(ButtonType.OK)) {
                    this.stage.close();
                    this.main.initRootLayout();
                    this.main.showRentOwerview();
                }
            }catch (java.net.ConnectException e){
                this.stage.close();
                this.main.handleNoConnection();
            }catch (IOException e){
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initOwner(main.getPrimaryStage());
                alert.setTitle("Ошибка записи аренды");
                alert.setHeaderText("Что-то пошло не так");
                alert.setContentText("Соединение с сервером было нестабильно, аренда не внесена в базу. Попробуйте снова.");
                alert.showAndWait();
            }

        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(main.getPrimaryStage());
            alert.setTitle("Ничего не выбрано");
            alert.setHeaderText("Не выбрана машина");
            alert.setContentText("Выберите машину для аренды");
            alert.showAndWait();
        }

    }

    private JSONObject fillEmployee(){
        try {
            JSONObject rawEmployee = ConnectionPerfomance.excecuteOnlyGET("http://localhost:9090/api/tests/getEmployee=", String.valueOf(main.getEmployeeId()), "Employee");
            rawEmployee.put("department", ConnectionPerfomance.excecuteOnlyGET("http://localhost:9090/api/tests/departmentById=", String.valueOf(main.getEmployeeId()), "Department"));
            rawEmployee.put("position", ConnectionPerfomance.excecuteOnlyGET("http://localhost:9090/api/tests/positionById=", String.valueOf(main.getEmployeeId()), "Position"));
            return rawEmployee;
        }catch (java.net.ConnectException e){
            this.stage.close();
            this.main.handleNoConnection();
            return null;
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    private JSONObject fillDiscount(){
        return new JSONObject(this.discount.toString().replace("Discount", "").replace("=", ":"));
    }

    private JSONObject fillClient(){
        return new JSONObject(client.toString().replace("Client", "").replace("=", ":"));
    }

    private JSONObject fillCar(){
        Car car = carTable.getItems().get(carTable.getSelectionModel().getSelectedIndex());
        return new JSONObject(car.toString().replace("Car", "").replace("=", ":").replace("ComfortLevel", ""));
    }
}
