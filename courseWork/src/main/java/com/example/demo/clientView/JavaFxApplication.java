package com.example.demo.clientView;

import com.example.demo.clientView.controllersFX.EmployeeRegisterController;
import com.example.demo.clientView.controllersFX.PersonEditingController;
import com.example.demo.clientView.controllersFX.PersonOverviewController;
import com.example.demo.clientView.controllersFX.RootManagerController;
import com.example.demo.models.Client;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class JavaFxApplication extends Application {
    private Stage primaryStage;
    private BorderPane rootLayout;
    private ObservableList<Client> personData = FXCollections.observableArrayList();
    private long employeeId;

    public JavaFxApplication() { }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Address Application");

        initRootLayout();
        showLoginPage();
    }

    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(JavaFxApplication.class.getResource("views/root.fxml"));
            rootLayout = loader.load();
            RootManagerController controller = loader.getController();
            controller.setDialogStage(primaryStage);
            controller.setMain(this);
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showLoginPage() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(JavaFxApplication.class.getResource("views/employeeRegister.fxml"));
            AnchorPane register = (AnchorPane) loader.load();
            rootLayout.setCenter(register);
            EmployeeRegisterController controller = loader.getController();
            controller.setMain(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    public BorderPane getRootLayout() {
        return rootLayout;
    }

    public ObservableList<Client> getPersonData() {
        return personData;
    }

    public void showPersonOverview() {
        try {
            StringBuilder result = new StringBuilder();
            URL url = new URL("http://localhost:9090/api/tests/clients/all");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            try (var reader = new BufferedReader(
                    new InputStreamReader(httpURLConnection.getInputStream()))) {
                for (String line; (line = reader.readLine()) != null;) {
                    result.append(line);
                }
            }
            System.out.println(result);
            JSONArray jsonArray = new JSONArray(result.toString());
            for (int i=0; i< jsonArray.length(); i++){
                Client person = new Client();
                person.setFirstName(jsonArray.getJSONObject(i).get("firstName").toString());
                person.setLastName(jsonArray.getJSONObject(i).get("lastName").toString());
                person.setPassport(jsonArray.getJSONObject(i).get("passport").toString());
                person.setPhoneNumber(jsonArray.getJSONObject(i).get("phoneNumber").toString());
                person.setLiscenceDate(jsonArray.getJSONObject(i).get("liscenceDate").toString());
                personData.add(person);
            }
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(JavaFxApplication.class.getResource("views/main.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();
            rootLayout.setCenter(personOverview);
            PersonOverviewController controller = loader.getController();
            controller.setMain(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean showPersonEditDialog(Client person) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(JavaFxApplication.class.getResource("views/PersonEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Person");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            PersonEditingController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPerson(person);
            dialogStage.showAndWait();
            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
