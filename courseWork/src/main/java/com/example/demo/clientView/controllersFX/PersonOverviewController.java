package com.example.demo.clientView.controllersFX;

import com.example.demo.clientView.JavaFxApplication;
import com.example.demo.ServerSide.models.Client;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


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
    private void handleDeletePerson() throws IOException {
        int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initOwner(main.getPrimaryStage());
            alert.setTitle("Submit deleting");
            alert.setHeaderText("Delete this client?");
            alert.setContentText("Are you sure if you want to delete this client?");
            ButtonType answer = alert.showAndWait().orElse(ButtonType.OK);
            if (answer.equals(ButtonType.OK)){
                System.out.println(personTable.getItems().get(selectedIndex).getId());
                StringBuilder result = new StringBuilder();
                URL url = new URL("http://localhost:9090/api/tests/deleteClient="+personTable.getItems().get(selectedIndex).getId());
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("DELETE");
                try (var reader = new BufferedReader(
                        new InputStreamReader(httpURLConnection.getInputStream()))) {
                    for (String line; (line = reader.readLine()) != null; ) {
                        result.append(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                personTable.getItems().remove(selectedIndex);
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
    private void handleEditPerson() {
        Client selectedPerson = personTable.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            boolean okClicked = main.showPersonEditDialog(selectedPerson);
            if (okClicked) {
                showPersonsOverviewDetails(selectedPerson);
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
    private void handleNewPerson() throws IOException {
        Client tempPerson = new Client();
        boolean okClicked = main.showPersonEditDialog(tempPerson);
        if (okClicked) {
            StringBuilder result = new StringBuilder();
            URL url = new URL("http://localhost:9090/api/tests/clients/"+tempPerson.toString());
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            try (var reader = new BufferedReader(
                    new InputStreamReader(httpURLConnection.getInputStream()))) {
                for (String line; (line = reader.readLine()) != null; ) {
                    result.append(line);
                }
            } catch (IOException e) {
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
            liscenceLabel.setText(person.getLiscenceDate());
        }
        else{
            firstNameLabel.setText("");
            lastNameLabel.setText("");
            phoneLabel.setText("");
            passportLabel.setText("");
            liscenceLabel.setText("");
        }
    }

}
