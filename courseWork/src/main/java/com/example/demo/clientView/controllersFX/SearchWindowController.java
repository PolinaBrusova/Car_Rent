package com.example.demo.clientView.controllersFX;

import com.example.demo.clientView.JavaFxApplication;
import com.example.demo.ServerSide.models.Client;
import com.example.demo.utils.ConnectionPerfomance;
import com.example.demo.utils.PhoneUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

/**
 * JavaFX scene controller
 */
public class SearchWindowController {

    private JavaFxApplication main;
    private Stage searchStage;

    @FXML
    private TextField phoneField;


    public SearchWindowController(){}

    public void setMain(JavaFxApplication main){
        this.main = main;
    }

    @FXML
    private void initialize() {
    }

    public void setDialogStage(Stage searchStage) {
        this.searchStage = searchStage;
    }

    /**
     * Handles action on "Search" button
     * Verification of the phone number field
     * Loads Requirements page if owner of the number is found
     * Loads Creating Client if nothing is found
     */
    @FXML
    private void handleSearch(){
        if (!phoneField.getText().isBlank()) {
            if (PhoneUtil.validPhone(phoneField.getText())){
                    Client client = clientExistence(phoneField.getText());
                    if (client != null && client.getId() != null) {
                        if (clientIsNotRenting(client)) {
                            try {
                                FXMLLoader loader = new FXMLLoader();
                                loader.setLocation(JavaFxApplication.class.getResource("controllersFX/requirements.fxml"));
                                AnchorPane page = loader.load();
                                this.main.getPrimaryStage().setTitle("Заполнение требований");
                                Scene scene = new Scene(page);
                                this.main.getPrimaryStage().setScene(scene);
                                RequirementsController controller = loader.getController();
                                controller.setStage(this.main.getPrimaryStage());
                                controller.setPerson(client);
                                controller.setMain(this.main);
                                searchStage.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.initOwner(searchStage);
                            alert.setTitle("Клиент уже арендует.");
                            alert.setHeaderText("Клиент с данным номером уже арендует");
                            alert.setContentText("Данный клиент уже арендует автомобиль. Он не может начать новую аренду");
                            alert.showAndWait();
                        }

                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.initOwner(searchStage);
                        alert.setTitle("Клиент не найден");
                        alert.setHeaderText("Система не нашла клиента с таким номером телефона");
                        alert.setContentText("Создание нового клиента...");
                        ButtonType answer = alert.showAndWait().orElse(ButtonType.OK);
                        if (answer.equals(ButtonType.OK)) {
                            Client tempPerson = new Client();
                            tempPerson.setPhoneNumber(phoneField.getText());
                            searchStage.close();
                            this.main.showPersonOverview();
                            HashMap<Boolean, Client> result =this.main.showPersonEditDialog(tempPerson);
                            boolean okClicked = (boolean) result.keySet().toArray()[0];
                            Client editedClient = result.get(okClicked);
                            if (okClicked) {
                                JSONObject jsonObject = new JSONObject();
                                jsonObject.put("firstName", URLEncoder.encode(editedClient.getFirstName(), StandardCharsets.UTF_8));
                                jsonObject.put("lastName", URLEncoder.encode(editedClient.getLastName(), StandardCharsets.UTF_8));
                                jsonObject.put("phoneNumber", URLEncoder.encode(editedClient.getPhoneNumber(), StandardCharsets.UTF_8));
                                jsonObject.put("passport", URLEncoder.encode(editedClient.getPassport(), StandardCharsets.UTF_8));
                                jsonObject.put("liscenceDate", URLEncoder.encode(editedClient.getLiscenceDate(), StandardCharsets.UTF_8));
                                try {
                                    ConnectionPerfomance.excecutePost("http://localhost:9090/api/tests/addClient", jsonObject);
                                    this.main.showPersonOverview();
                                }catch (java.net.ConnectException e){
                                    this.main.handleNoConnection();
                                }catch (IOException e){
                                    e.printStackTrace();
                                }
                            }
                        }
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

    /**
     * Verification of the existence of the client with specified phone number
     * @param phone String value of the phone number
     * @return Client object found (null if not found)
     */
    private Client clientExistence(String phone){
        try {
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
        }catch (java.net.ConnectException e){
            this.main.handleNoConnection();
            return null;
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Checks if the client is renting something right at the moment
     * @param client Client object for the check
     * @return boolean result of the check
     */
    private boolean clientIsNotRenting(Client client){
        try{
            return ConnectionPerfomance.excecuteValidation("http://localhost:9090/api/tests/Client="+client.getId()+"/isRenting").matches("true");
        }catch (java.net.ConnectException e){
            this.searchStage.close();
            this.main.handleNoConnection();
            return false;
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }
}


