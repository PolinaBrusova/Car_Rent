package com.example.demo.clientView;

import com.example.demo.ServerSide.models.Car;
import com.example.demo.ServerSide.models.ComfortLevel;
import com.example.demo.clientView.controllersFX.EmployeeRegisterController;
import com.example.demo.clientView.controllersFX.PersonEditingController;
import com.example.demo.clientView.controllersFX.PersonOverviewController;
import com.example.demo.clientView.controllersFX.RootManagerController;
import com.example.demo.ServerSide.models.Client;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;


public class JavaFxApplication extends Application {
    private Stage primaryStage;
    private BorderPane rootLayout;
    private ObservableList<Client> personData = FXCollections.observableArrayList();
    private long employeeId;
    private ObservableList<Car> existingCars = FXCollections.observableArrayList();

    public JavaFxApplication() { }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Address Application");

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
            Stage stage = new Stage();
            stage.setTitle("Sign in");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(primaryStage);
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(JavaFxApplication.class.getResource("views/employeeRegister.fxml"));
            BorderPane register = (BorderPane) loader.load();
            Scene scene = new Scene(register);
            stage.setScene(scene);
            EmployeeRegisterController controller = loader.getController();
            controller.setMain(this);
            controller.setRegisterStage(stage);
            stage.showAndWait();
            try{
                findAllCars();
            }catch (Exception e){
                try {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.initOwner(stage);
                    alert.setTitle("No connection");
                    alert.setHeaderText("Not established connection with the server");
                    alert.setContentText("Connection with the server was not established. try again later. Halting th system...");
                    ButtonType answer = alert.showAndWait().orElse(ButtonType.OK);
                    if (ButtonType.OK.equals(answer)) {
                        stage.close();
                        this.stop();
                    }
                }catch (Exception k){
                    k.printStackTrace();
                }
            }

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

    public ObservableList<Car> getExistingCars() {
        return existingCars;
    }

    public void showPersonOverview() {
        try {
            StringBuilder result = new StringBuilder();
            URL url = new URL("http://localhost:9090/api/tests/AllClients");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            try (var reader = new BufferedReader(
                    new InputStreamReader(httpURLConnection.getInputStream()))) {
                for (String line; (line = reader.readLine()) != null;) {
                    result.append(line);
                }
            }
            JSONArray jsonArray = new JSONArray(result.toString());
            for (int i=0; i< jsonArray.length(); i++){
                Client person = new Client();
                person.setId(Long.valueOf(jsonArray.getJSONObject(i).get("id").toString()));
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

    public void findAllCars() throws IOException {
        StringBuilder result = new StringBuilder();
        URL url = new URL("http://localhost:9090/api/tests/AllCars");
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        try (var reader = new BufferedReader(
                new InputStreamReader(httpURLConnection.getInputStream()))) {
            for (String line; (line = reader.readLine()) != null;) {
                result.append(line);
            }
        }
        JSONArray jsonArray = new JSONArray(result.toString());
        for (int i=0; i< jsonArray.length(); i++){
            Car car = new Car();
            car.setId(Long.valueOf(jsonArray.getJSONObject(i).get("id").toString()));
            car.setBrand(jsonArray.getJSONObject(i).get("brand").toString());
            car.setCarcase(jsonArray.getJSONObject(i).get("carcase").toString());
            car.setGearbox(jsonArray.getJSONObject(i).get("gearbox").toString());
            car.setDoorNumber(Integer.parseInt(jsonArray.getJSONObject(i).get("doorNumber").toString()));
            car.setSeats(Integer.parseInt(jsonArray.getJSONObject(i).get("seats").toString()));
            car.setReleaseYear(Integer.parseInt(jsonArray.getJSONObject(i).get("releaseYear").toString()));
            car.setColor(jsonArray.getJSONObject(i).get("color").toString());
            StringBuilder result2 = new StringBuilder();
            URL url2 = new URL("http://localhost:9090/api/tests/LevelByCarId="+jsonArray.getJSONObject(i).get("id").toString());
            HttpURLConnection httpURLConnection2 = (HttpURLConnection) url2.openConnection();
            httpURLConnection2.setRequestMethod("GET");
            try (var reader = new BufferedReader(
                    new InputStreamReader(httpURLConnection2.getInputStream()))) {
                for (String line; (line = reader.readLine()) != null;) {
                    result2.append(line);
                }
            }
            ComfortLevel comfortLevel = new ComfortLevel();
            JSONObject comf_lvl = new JSONObject(result2.toString().replace("ComfortLevel", "").replace("=", ":"));
            comfortLevel.setId(comf_lvl.get("id").toString());
            comfortLevel.setLevel(comf_lvl.get("level").toString());
            comfortLevel.setDeposit(Long.parseLong(comf_lvl.get("deposit").toString()));
            comfortLevel.setRentPrice(Long.parseLong(comf_lvl.get("rentPrice").toString()));
            comfortLevel.setMinExperience(Integer.parseInt(comf_lvl.get("minExperience").toString()));
            car.setComfortLevel(comfortLevel);
            System.out.println(car.toString());
            this.existingCars.add(car);
        }
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
