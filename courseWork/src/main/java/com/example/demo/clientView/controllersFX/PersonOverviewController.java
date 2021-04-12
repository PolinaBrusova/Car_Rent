package com.example.demo.clientView.controllersFX;

import com.example.demo.clientView.JavaFxApplication;
import com.example.demo.ServerSide.models.Client;
import com.example.demo.utils.ConnectionPerfomance;
import com.example.demo.utils.DateUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;


public class PersonOverviewController {
    @FXML
    private TableView<Client> personTable;
    @FXML
    private TableColumn<Client,String> firstNameColumn;
    @FXML
    private TableColumn<Client,String> lastNameColumn;
    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label phoneLabel;
    @FXML
    private Label passportLabel;
    @FXML
    private Label liscenceLabel;
    private JavaFxApplication main;

    public PersonOverviewController(){}

    @FXML
    private void initialize(){

        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().getFirstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().getLastNameProperty());

        showPersonsOverviewDetails(null); //очищаем справа
        personTable.getSelectionModel().selectedItemProperty().addListener(
                (observable,oldValue,newValue) -> showPersonsOverviewDetails(newValue)
        ); //при изменении слушатель изменит данные на новые из таблицы

    }
    public void setMain(JavaFxApplication main){
        this.main = main;
        personTable.setItems(main.getPersonData());
    }

    @FXML
    private void handleDeletePerson(){
        int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initOwner(main.getPrimaryStage());
            alert.setTitle("Submit deleting");
            alert.setHeaderText("Delete this client?");
            alert.setContentText("Are you sure if you want to delete this client?");
            ButtonType answer = alert.showAndWait().orElse(ButtonType.OK);
            if (answer.equals(ButtonType.OK)){
                try {
                    ConnectionPerfomance.excecuteDELETE("http://localhost:9090/api/tests/deleteClient=" + personTable.getItems().get(selectedIndex).getId());
                    this.main.showPersonOverview();
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
            alert.setHeaderText("No Person selection");
            alert.setContentText("Please, select person in the table");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleBreakThrough(){
        int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0){
            try {
                if (clientIsNotRenting(personTable.getItems().get(selectedIndex))) {
                    try {
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(JavaFxApplication.class.getResource("controllersFX/requirements.fxml"));
                        AnchorPane page = loader.load();
                        this.main.getPrimaryStage().setTitle("FILLING REQUIREMENTS");
                        Scene scene = new Scene(page);
                        this.main.getPrimaryStage().setScene(scene);
                        RequirementsController controller = loader.getController();
                        controller.setStage(this.main.getPrimaryStage());
                        controller.setPerson(personTable.getItems().get(selectedIndex));
                        controller.setMain(this.main);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.initOwner(main.getPrimaryStage());
                    alert.setTitle("Клиент уже арендует");
                    alert.setHeaderText("Клиент на данные даты уже арендует машину");
                    alert.setContentText("Клиент арендует машину. Клиент должен прийти после окончания аренды.");
                    alert.showAndWait();
                }
            }catch (java.net.ConnectException e){
                this.main.handleNoConnection();
            }catch (IOException e){
                e.printStackTrace();
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
    private void handleEditPerson(){
        Client selectedPerson = personTable.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            HashMap<Boolean, Client> answer = main.showPersonEditDialog(selectedPerson);
            boolean okClicked = (boolean) answer.keySet().toArray()[0];
            Client client = answer.get(okClicked);
            if (okClicked) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", client.getId());
                jsonObject.put("firstName", client.getFirstName());
                jsonObject.put("lastName", client.getLastName());
                jsonObject.put("phoneNumber", client.getPhoneNumber());
                jsonObject.put("passport", client.getPassport());
                jsonObject.put("liscenceDate", client.getLiscenceDate());
                try {
                    ConnectionPerfomance.excecutePUT("http://localhost:9090/api/tests/updateClient", jsonObject);
                    this.main.showPersonOverview();
                    showPersonsOverviewDetails(client);
                }catch (java.net.ConnectException e){
                    this.main.handleNoConnection();
                }catch (IOException e){
                    e.printStackTrace();
                }
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
    private void handleNewPerson(){
        Client tempPerson = new Client();
        HashMap<Boolean, Client> answer = main.showPersonEditDialog(tempPerson);
        boolean okClicked = (boolean) answer.keySet().toArray()[0];
        Client client = answer.get(okClicked);
        if (okClicked) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("firstName", URLEncoder.encode(client.getFirstName(), StandardCharsets.UTF_8));
            jsonObject.put("lastName", URLEncoder.encode(client.getLastName(), StandardCharsets.UTF_8));
            jsonObject.put("phoneNumber", URLEncoder.encode(client.getPhoneNumber(), StandardCharsets.UTF_8));
            jsonObject.put("passport", URLEncoder.encode(client.getPassport(), StandardCharsets.UTF_8));
            jsonObject.put("liscenceDate", URLEncoder.encode(client.getLiscenceDate(), StandardCharsets.UTF_8));
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

    private void showPersonsOverviewDetails(Client person){
        if(person != null){
            firstNameLabel.setText(person.getFirstName());
            lastNameLabel.setText(person.getLastName());
            phoneLabel.setText(person.getPhoneNumber());
            passportLabel.setText(person.getPassport());
            liscenceLabel.setText(DateUtil.formatForPeople(DateUtil.parse(person.getLiscenceDate())));
        }
        else{
            firstNameLabel.setText("");
            lastNameLabel.setText("");
            phoneLabel.setText("");
            passportLabel.setText("");
            liscenceLabel.setText("");
        }
    }

    private boolean clientIsNotRenting(Client client) throws IOException{
        return ConnectionPerfomance.excecuteValidation("http://localhost:9090/api/tests/Client=" + client.getId() + "/isRenting").matches("true");
    }
}
