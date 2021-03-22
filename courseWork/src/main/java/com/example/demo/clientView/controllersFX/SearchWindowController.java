package com.example.demo.clientView.controllersFX;

import com.example.demo.clientView.JavaFxApplication;
import com.example.demo.ServerSide.models.Client;
import com.example.demo.utils.PhoneUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

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
    private void handleSearch() throws IOException {
        if (!phoneField.getText().isBlank()) {
            if (PhoneUtil.validPhone(phoneField.getText())){
                Client client = clientExistence(phoneField.getText());
                if (client.getId() != null){
                    try {
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(JavaFxApplication.class.getResource("views/requirements.fxml"));
                        AnchorPane page = loader.load();
                        Stage requirementStage = new Stage();
                        requirementStage.setTitle("Search");
                        requirementStage.initModality(Modality.WINDOW_MODAL);
                        requirementStage.initOwner(main.getPrimaryStage());
                        Scene scene = new Scene(page);
                        requirementStage.setScene(scene);
                        RequirementsController controller = loader.getController();
                        controller.setStage(requirementStage);
                        controller.setPerson((Client) main.getPersonData().stream().filter(item -> item.getPhoneNumber().equals(phoneField.getText())).toArray()[0]);
                        searchStage.close();
                        requirementStage.showAndWait();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.initOwner(searchStage);
                    alert.setTitle("No such a client");
                    alert.setHeaderText("Client Not found in the database");
                    alert.setContentText("Redirecting on adding a client...");
                    alert.showAndWait();
                    Client tempPerson = new Client();
                    tempPerson.setPhoneNumber(phoneField.getText());
                    //TODO: Make adding new client (why the id does not generates tho?
                    if(main.showPersonEditDialog(tempPerson)){
                        main.getPersonData().add(tempPerson);
                    }
                    searchStage.close();
                    //Добавить переход с новым клиентом сразу на оформление требований
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

    private Client clientExistence(String phone) throws IOException {
        StringBuilder result = new StringBuilder();
        URL url = new URL("http://localhost:9090/api/tests/getClient/phone="+phone);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        try (var reader = new BufferedReader(
                new InputStreamReader(httpURLConnection.getInputStream()))) {
            for (String line; (line = reader.readLine()) != null;) {
                result.append(line);
            }
        }
        Client client1 = new Client();
        if(!result.toString().isBlank()) {
            JSONObject client = new JSONObject(result.toString());
            client1.setId(Long.valueOf(client.get("id").toString()));
            client1.setFirstName(client.get("firstName").toString());
            client1.setLastName(client.get("lastName").toString());
            client1.setPassport(client.get("passport").toString());
            client1.setPhoneNumber(client.get("phoneNumber").toString());
            client1.setLiscenceDate(client.get("liscenceDate").toString());
        }
        return client1;
    }
}

