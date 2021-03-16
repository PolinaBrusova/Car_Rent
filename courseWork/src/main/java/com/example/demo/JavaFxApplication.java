package com.example.demo;

import com.example.demo.controllersFX.EmployeeRegisterController;
import com.example.demo.controllersFX.PersonEditingController;
import com.example.demo.controllersFX.PersonOverviewController;
import com.example.demo.controllersFX.RootManagerController;
import com.example.demo.models.Person;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;


public class JavaFxApplication extends Application {
    private Stage primaryStage;
    private BorderPane rootLayout;
    private ObservableList<Person> personData = FXCollections.observableArrayList();
    private long employeeId;

    public JavaFxApplication() {
        for (int i = 0; i < 20; i++) {
            personData.add(new Person("First Name" + i, "Last Name" + i));
        }
    }

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

    public ObservableList<Person> getPersonData() {
        return personData;
    }

    public void showPersonOverview() {
        try {
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

    public boolean showPersonEditDialog(Person person) {
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
