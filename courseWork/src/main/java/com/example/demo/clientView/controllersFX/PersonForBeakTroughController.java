package com.example.demo.clientView.controllersFX;

import com.example.demo.ServerSide.models.Car;
import com.example.demo.ServerSide.models.Client;
import com.example.demo.clientView.JavaFxApplication;
import com.example.demo.utils.ConnectionPerfomance;
import com.example.demo.utils.PhoneUtil;
import com.sun.xml.bind.api.impl.NameConverter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

public class PersonForBeakTroughController {
    private ObservableList<Car> cars = FXCollections.observableArrayList();
    private JavaFxApplication main;
    private Stage searchStage;

    @FXML
    private TextField phoneField;
    @FXML
    private DatePicker startDate;
    @FXML
    private DatePicker endDate;



    public PersonForBeakTroughController(){}

    public void setMain(JavaFxApplication main){
        this.main = main;
    }

    @FXML
    private void initialize() {
    }

    public void setDialogStage(Stage searchStage) {
        this.searchStage = searchStage;
    }

    public void setCars(ObservableList<Car> cars) {
        this.cars = cars;
    }

    @FXML
    private void handleSearch(){
        if (!phoneField.getText().isBlank()) {
            if (PhoneUtil.validPhone(phoneField.getText())){
                if (startDate.getValue()!=null && endDate.getValue()!=null && (startDate.getValue().isAfter(LocalDate.now()) || startDate.getValue().equals(LocalDate.now())) && endDate.getValue().isAfter(LocalDate.now())){
                    if (startDate.getValue().isBefore(endDate.getValue())){
                        try {
                            Client client = clientExistence(phoneField.getText());
                            if (client.getId() != null) {
                                if (clientIsNotRenting(client)) {
                                    if (client.getExperience() >= cars.get(0).getComfortLevel().getMinExperience()) {
                                        try {
                                            FXMLLoader loader = new FXMLLoader();
                                            loader.setLocation(JavaFxApplication.class.getResource("controllersFX/choice.fxml"));
                                            AnchorPane page = loader.load();
                                            this.main.getPrimaryStage().setTitle("Заполнение требований");
                                            Scene scene = new Scene(page);
                                            this.main.getPrimaryStage().setScene(scene);
                                            ChoiceController controller = loader.getController();
                                            controller.setStage(this.main.getPrimaryStage());
                                            controller.setStart(startDate.getValue());
                                            controller.setEnd(endDate.getValue());
                                            controller.setClient(client);
                                            controller.setMain(this.main);
                                            controller.setCars(cars);
                                            searchStage.close();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    } else {
                                        Alert alert = new Alert(Alert.AlertType.ERROR);
                                        alert.initOwner(searchStage);
                                        alert.setTitle("Аренда невозможна");
                                        alert.setHeaderText("Клиент не может арендовать эту машину");
                                        alert.setContentText("Опыта вождения клиента не хватает для аренды машины " +
                                                "данного уровня комфорта. Выберите другой автомобиль!");
                                        alert.showAndWait();
                                    }

                                } else {
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.initOwner(searchStage);
                                    alert.setTitle("Клиент арендатор");
                                    alert.setHeaderText("Клиент уже арендует машину");
                                    alert.setContentText("Данный клиент уже арендует машину в выбранные даты");
                                    alert.showAndWait();
                                }
                            } else {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.initOwner(searchStage);
                                alert.setTitle("Клиент не найден");
                                alert.setHeaderText("Система не нашла клиента с таким номером телефона");
                                alert.setContentText("Создание нового клиента...");
                                alert.showAndWait();
                                Client tempPerson = new Client();
                                tempPerson.setPhoneNumber(phoneField.getText());
                                //TODO: Make adding new client (why the id does not generates tho?
                        /*if(main.showPersonEditDialog(tempPerson)){
                            main.getPersonData().add(tempPerson);
                        }*/
                                searchStage.close();
                                //Добавить переход с новым клиентом сразу на оформление требований
                            }
                        }catch (java.net.ConnectException e){
                            this.searchStage.close();
                            this.main.handleNoConnection();
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }else{
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.initOwner(searchStage);
                        alert.setTitle("Неверные даты аренды");
                        alert.setHeaderText("введены неверные даты аренды");
                        alert.setContentText("Дата начала аренды должна быть раньше даты конца аренды!");
                        alert.showAndWait();
                    }
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.initOwner(searchStage);
                    alert.setTitle("Незаполнены даты");
                    alert.setHeaderText("Пустые поля дат аренды");
                    alert.setContentText("Пожалуйста, заполните правильно даты аренды");
                    alert.showAndWait();
                }


            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initOwner(searchStage);
                alert.setTitle("Некорректный номер");
                alert.setHeaderText("номер телефона введен некорректно");
                alert.setContentText("Напишите корректный номер телефона по формату:\n+79878767653");
                alert.showAndWait();
            }

        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(searchStage);
            alert.setTitle("Пустые поля");
            alert.setHeaderText("Заполните пустые поля");
            alert.setContentText("Заполните номер телефона для поиска клиента");
            alert.showAndWait();
        }
    }

    private Client clientExistence(String phone) throws IOException{
            JSONObject jsonObject = ConnectionPerfomance.excecuteOnlyGET("http://localhost:9090/api/tests/getClient/phone=", URLEncoder.encode(phone, StandardCharsets.UTF_8), "Client");
            Client client1 = new Client();
            if (!jsonObject.isEmpty()) {
                client1.setId(Long.valueOf(jsonObject.get("id").toString()));
                client1.setFirstName(jsonObject.get("firstName").toString());
                client1.setLastName(jsonObject.get("lastName").toString());
                client1.setPassport(jsonObject.get("passport").toString());
                client1.setPhoneNumber(jsonObject.get("phoneNumber").toString());
                client1.setLiscenceDate(jsonObject.get("liscenceDate").toString());
            }
            return client1;
    }

    private boolean clientIsNotRenting(Client client) throws IOException{
        return ConnectionPerfomance.excecuteValidation("http://localhost:9090/api/tests/Client=" + client.getId() + "/isRenting").matches("true");
    }
}
