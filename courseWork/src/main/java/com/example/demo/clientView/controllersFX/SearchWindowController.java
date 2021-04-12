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

    @FXML
    private void handleSearch(){
        if (!phoneField.getText().isBlank()) {
            if (PhoneUtil.validPhone(phoneField.getText())){
                try {
                    Client client = clientExistence(phoneField.getText());
                    if (client != null && client.getId() != null) {
                        if (clientIsNotRenting(client)) {
                            try {
                                FXMLLoader loader = new FXMLLoader();
                                loader.setLocation(JavaFxApplication.class.getResource("controllersFX/requirements.fxml"));
                                AnchorPane page = loader.load();
                                this.main.getPrimaryStage().setTitle("FILLING REQUIREMENTS");
                                Scene scene = new Scene(page);
                                this.main.getPrimaryStage().setScene(scene);
                                RequirementsController controller = loader.getController();
                                controller.setStage(this.main.getPrimaryStage());
                                controller.setPerson((Client) main.getPersonData().stream().filter(item -> item.getPhoneNumber().equals(phoneField.getText())).toArray()[0]);
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
                        alert.setTitle("No such a client");
                        alert.setHeaderText("Client Not found in the database");
                        alert.setContentText("Redirecting on adding a client...");
                        ButtonType answer = alert.showAndWait().orElse(ButtonType.OK);
                        if (answer.equals(ButtonType.OK)) {
                            Client tempPerson = new Client();
                            tempPerson.setPhoneNumber(phoneField.getText());
                            searchStage.close();
                            this.main.showPersonOverview();
                            this.main.showPersonEditDialog(tempPerson);
                        }
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
                alert.setTitle("Invalid Nubmer");
                alert.setHeaderText("Entered an invalid phone number");
                alert.setContentText("Write the phone number correctly using the folowing formats:\n+79878767653\nOR\n89878767653");
                alert.showAndWait();
            }

        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(searchStage);
            alert.setTitle("Empty field");
            alert.setHeaderText("Fill an empty field");
            alert.setContentText("Fill the telephone number for the search");
            alert.showAndWait();
        }
    }

    private Client clientExistence(String phone) throws IOException{
            JSONObject jsonObject = ConnectionPerfomance.excecuteOnlyGET("http://localhost:9090/api/tests/getClient/phone=", URLEncoder.encode(phone, StandardCharsets.UTF_8), "");
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


