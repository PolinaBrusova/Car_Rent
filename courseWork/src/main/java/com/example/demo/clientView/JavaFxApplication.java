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

/**
 * JavaFX scene controller
 */
public class JavaFxApplication extends Application {
    private Stage primaryStage;
    private BorderPane rootLayout;
    private ObservableList<Client> personData = FXCollections.observableArrayList();
    private long employeeId;
    private ObservableList<Car> existingCars = FXCollections.observableArrayList();

    /**
     * Empty initializer
     */
    public JavaFxApplication() { }

    /**
     * starts the show
     * @param primaryStage JavaFx stage
     */
    @Override
    public void start(Stage primaryStage){
        this.primaryStage = primaryStage;
        this.primaryStage.setResizable(false);
        this.primaryStage.setTitle("Rent-A-Car Москва");

        showLoginPage();
    }

    /**
     * Initializer root layout (Border pane) for other windows to lay on
     */
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

    /**
     * shows page with authorization
     */
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

    /**
     * returns the value of the primary stage attribute
     * @return JavaFX stage (primary)
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * returns the id of the logged employee
     * @return Long id
     */
    public long getEmployeeId() {
        return employeeId;
    }

    /**
     * sets the id of the logged employee
     * @param employeeId Long value for id
     */
    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * returns the data for the tableView
     * @return ObservableList<Client> personData
     */
    public ObservableList<Client> getPersonData() {
        return personData;
    }

    /**
     * returns the list of cars
     * @return ObservableList<Car> existingCars
     */
    public ObservableList<Car> getExistingCars() {
        return existingCars;
    }

    /**
     * initializes Anchor pane (new scene) with information about clients of the service
     */
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
                person.setPhoneNumber(("+"+URLDecoder.decode(jsonArray.getJSONObject(i).get("phoneNumber").toString(), StandardCharsets.UTF_8)).replace(" ",""));
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

    /**
     * Loads editing window
     * @param person selected Client from the table
     * @return HashMap<Boolean, Client> where boolean reference for clicking on "OK" button
     * and Client contains edited client
     */
    public HashMap<Boolean, Client> showPersonEditDialog(Client person) {
        try {
            HashMap<Boolean, Client> dictionary = new HashMap<>();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(JavaFxApplication.class.getResource("controllersFX/PersonEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Заполнение клиента");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            PersonEditingController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPerson(person);
            controller.setMain(this);
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

    /**
     * gets all cars existing in the database and fills the variable
     */
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

    /**
     * initializes Anchor pane (new scene) with information about cars of the service
     */
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

    /**
     * initializes Anchor pane (new scene) with information about retns of the service
     */
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

    /**
     * initializes Anchor pane (new scene) with information about comfort levels of the service
     */
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

    /**
     * stops the application
     * @throws Exception if any errors occur
     */
    @Override
    public void stop() throws Exception {
        super.stop();
    }

    /**
     * Enter point
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Shows alert about problems with connection
     */
    public void handleNoConnection(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(primaryStage);
        alert.setTitle("Соединение потеряно");
        alert.setHeaderText("Соединение с сервером не установлено");
        alert.setContentText("В процессе соединение с сервером было разорвано или изначально не установлено. Закрываю приложение...");
        alert.showAndWait();
    }
}
