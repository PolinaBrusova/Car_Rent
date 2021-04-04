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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;


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
        this.primaryStage.setResizable(false);
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
            findAllCars();
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
            BorderPane register = loader.load();
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
        this.personData.clear();
        try {
            JSONArray jsonArray = ConnectionPerfomance.excecuteManyGET("http://localhost:9090/api/tests/AllClients");
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

    public HashMap<Boolean, Client> showPersonEditDialog(Client person) {
        try {
            HashMap<Boolean, Client> dictionary = new HashMap<>();
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
            dictionary.put(controller.isOkClicked(), controller.getPerson());
            return dictionary;
        } catch (IOException e) {
            HashMap<Boolean, Client> dictionary = new HashMap<>();
            dictionary.put(false, null);
            e.printStackTrace();
            return dictionary;
        }
    }

    public void findAllCars() throws IOException {
        this.existingCars.clear();
        JSONArray jsonArray = ConnectionPerfomance.excecuteManyGET("http://localhost:9090/api/tests/AllCars");
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
            car.setAvailable(jsonArray.getJSONObject(i).getBoolean("available"));
            JSONObject comf_lvl = ConnectionPerfomance.excecuteOnlyGET("http://localhost:9090/api/tests/LevelByCarId=", jsonArray.getJSONObject(i).get("id").toString(), "ComfortLevel");
            ComfortLevel comfortLevel = new ComfortLevel();
            comfortLevel.setId(comf_lvl.get("id").toString());
            comfortLevel.setLevel(comf_lvl.get("level").toString());
            comfortLevel.setDeposit(Long.parseLong(comf_lvl.get("deposit").toString()));
            comfortLevel.setRentPrice(Long.parseLong(comf_lvl.get("rentPrice").toString()));
            comfortLevel.setMinExperience(Integer.parseInt(comf_lvl.get("minExperience").toString()));
            car.setComfortLevel(comfortLevel);
            this.existingCars.add(car);
        }
    }

    public void showCarOwerview() throws IOException {
        findAllCars();
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(JavaFxApplication.class.getResource("views/carOverview.fxml"));
            AnchorPane carOverview = loader.load();
            rootLayout.setCenter(carOverview);
            CarOverviewController controller = loader.getController();
            controller.setMain(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showLevelOverview() throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(JavaFxApplication.class.getResource("views/comfortlevelOverview.fxml"));
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
}
