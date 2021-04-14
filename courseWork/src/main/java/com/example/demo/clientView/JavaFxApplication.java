package com.example.demo.clientView;

import com.example.demo.ServerSide.models.Car;
import com.example.demo.ServerSide.models.ComfortLevel;
import com.example.demo.clientView.controllersFX.*;
import com.example.demo.ServerSide.models.Client;
import com.example.demo.utils.ConnectionPerfomance;
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

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;


public class JavaFxApplication extends Application {
    private Stage primaryStage;
    private BorderPane rootLayout;
    private ObservableList<Client> personData = FXCollections.observableArrayList();
    private long employeeId;
    private ObservableList<Car> existingCars = FXCollections.observableArrayList();

    public JavaFxApplication() { }

    @Override
    public void start(Stage primaryStage){
        this.primaryStage = primaryStage;
        this.primaryStage.setResizable(false);
        this.primaryStage.setTitle("Address Application");

        showLoginPage();
    }

    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(JavaFxApplication.class.getResource("controllersFX/root.fxml"));
            rootLayout = loader.load();
            RootManagerController controller = loader.getController();
            controller.setDialogStage(primaryStage);
            controller.setMain(this);
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
            findAllCars();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showLoginPage() {
        try {
            Stage stage = new Stage();
            stage.setTitle("Вход в систему");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(primaryStage);
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(JavaFxApplication.class.getResource("controllersFX/employeeRegister.fxml"));
            AnchorPane register = loader.load();
            register.setStyle("-fx-background-color: #FFEFD5");
            Scene scene = new Scene(register);
            stage.setScene(scene);
            EmployeeRegisterController controller = loader.getController();
            controller.setMain(this);
            controller.setRegisterStage(stage);
            stage.showAndWait();

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

    public ObservableList<Client> getPersonData() {
        return personData;
    }

    public ObservableList<Car> getExistingCars() {
        return existingCars;
    }

    public void showPersonOverview() {
        this.personData.clear();
        try {
            JSONArray jsonArray = ConnectionPerfomance.excecuteManyGET("http://localhost:9090/api/tests/AllClients");
            for (int i=0; i< jsonArray.length(); i++){
                Client person = new Client();
                person.setId(Long.valueOf(jsonArray.getJSONObject(i).get("id").toString()));
                person.setFirstName(URLDecoder.decode(jsonArray.getJSONObject(i).get("firstName").toString(), StandardCharsets.UTF_8));
                person.setLastName(URLDecoder.decode(jsonArray.getJSONObject(i).get("lastName").toString(), StandardCharsets.UTF_8));
                person.setPassport(URLDecoder.decode(jsonArray.getJSONObject(i).get("passport").toString(), StandardCharsets.UTF_8));
                person.setPhoneNumber("+"+URLDecoder.decode(jsonArray.getJSONObject(i).get("phoneNumber").toString(), StandardCharsets.UTF_8));
                person.setLiscenceDate(jsonArray.getJSONObject(i).get("liscenceDate").toString());
                personData.add(person);
            }
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(JavaFxApplication.class.getResource("controllersFX/main.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();
            rootLayout.setCenter(personOverview);
            PersonOverviewController controller = loader.getController();
            controller.setMain(this);
        } catch (java.net.ConnectException e){
            this.handleNoConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HashMap<Boolean, Client> showPersonEditDialog(Client person) {
        try {
            HashMap<Boolean, Client> dictionary = new HashMap<>();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(JavaFxApplication.class.getResource("controllersFX/PersonEditDialog.fxml"));
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
            dictionary.put(controller.isOkClicked(), controller.getPerson());
            return dictionary;
        } catch (IOException e) {
            HashMap<Boolean, Client> dictionary = new HashMap<>();
            dictionary.put(false, null);
            e.printStackTrace();
            return dictionary;
        }
    }

    public void findAllCars(){
        try {
            this.existingCars.clear();
            JSONArray jsonArray = ConnectionPerfomance.excecuteManyGET("http://localhost:9090/api/tests/AllCars");
            for (int i = 0; i < jsonArray.length(); i++) {
                Car car = new Car();
                car.setId(Long.valueOf(jsonArray.getJSONObject(i).get("id").toString()));
                car.setBrand(URLDecoder.decode(jsonArray.getJSONObject(i).get("brand").toString(), StandardCharsets.UTF_8));
                car.setCarcase(URLDecoder.decode(jsonArray.getJSONObject(i).get("carcase").toString(), StandardCharsets.UTF_8));
                car.setGearbox(URLDecoder.decode(jsonArray.getJSONObject(i).get("gearbox").toString(), StandardCharsets.UTF_8));
                car.setDoorNumber(Integer.parseInt(jsonArray.getJSONObject(i).get("doorNumber").toString()));
                car.setSeats(Integer.parseInt(jsonArray.getJSONObject(i).get("seats").toString()));
                car.setReleaseYear(Integer.parseInt(jsonArray.getJSONObject(i).get("releaseYear").toString()));
                car.setColor(URLDecoder.decode(jsonArray.getJSONObject(i).get("color").toString(), StandardCharsets.UTF_8));
                car.setAvailable(jsonArray.getJSONObject(i).getBoolean("available"));
                JSONObject comf_lvl = ConnectionPerfomance.excecuteOnlyGET("http://localhost:9090/api/tests/LevelByCarId=", jsonArray.getJSONObject(i).get("id").toString(), "ComfortLevel");
                ComfortLevel comfortLevel = new ComfortLevel();
                comfortLevel.setId(comf_lvl.get("id").toString());
                comfortLevel.setLevel(URLDecoder.decode(comf_lvl.get("level").toString(), StandardCharsets.UTF_8));
                comfortLevel.setDeposit(Long.parseLong(comf_lvl.get("deposit").toString()));
                comfortLevel.setRentPrice(Long.parseLong(comf_lvl.get("rentPrice").toString()));
                comfortLevel.setMinExperience(Integer.parseInt(comf_lvl.get("minExperience").toString()));
                car.setComfortLevel(comfortLevel);
                this.existingCars.add(car);
            }
        }catch (java.net.ConnectException e){
            this.handleNoConnection();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void showCarOwerview(){
        findAllCars();
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(JavaFxApplication.class.getResource("controllersFX/carOverview.fxml"));
            AnchorPane carOverview = loader.load();
            rootLayout.setCenter(carOverview);
            CarOverviewController controller = loader.getController();
            controller.setMain(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showRentOwerview(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(JavaFxApplication.class.getResource("controllersFX/rentOverview.fxml"));
            AnchorPane rentOverview = loader.load();
            rootLayout.setCenter(rentOverview);
            RentOverviewController controller = loader.getController();
            controller.setMain(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showLevelOverview(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(JavaFxApplication.class.getResource("controllersFX/comfortlevelOverview.fxml"));
            AnchorPane comfortOverview = loader.load();
            rootLayout.setCenter(comfortOverview);
            ComfortOverviewController controller = loader.getController();
            controller.setMain(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void handleNoConnection(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(primaryStage);
        alert.setTitle("No connection");
        alert.setHeaderText("Not established connection with the server");
        alert.setContentText("Connection with the server was not established. try again later. Halting th system...");
        ButtonType answer = alert.showAndWait().orElse(ButtonType.OK);
        if (ButtonType.OK.equals(answer)) {
            try {
                this.primaryStage.close();
                this.stop();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }
}
