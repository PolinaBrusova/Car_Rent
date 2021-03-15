package com.example.demo;

import com.example.demo.controllersFX.EmployeeController;
import com.example.demo.controllersFX.PersonEditingController;
import com.example.demo.models.Person;
import com.example.demo.repositories.CarRepository;
import com.example.demo.repositories.ComfortLevelRepository;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class JavaFxApplication extends Application {

    private ConfigurableApplicationContext applicationContext;
    private Stage primaryStage;
    private ObservableList<Person> personData = FXCollections.observableArrayList();
    private FxWeaver fxWeaver;


    @Override
    public void init() {
        for (int i = 0; i < 20; i++) {
            personData.add(new Person("First Name" + i, "Last Name" + i));
        }

        String[] args = getParameters().getRaw().toArray(new String[0]);

        this.applicationContext = new SpringApplicationBuilder()
                .sources(DemoApplication.class)
                .run(args);
        fxWeaver = applicationContext.getBean(FxWeaver.class);
    }

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        primaryStage.setTitle("Address Application");

        initStartLayout();
    }

    @Override
    public void stop() {
        this.applicationContext.close();
        Platform.exit();
    }

    public ObservableList<Person> getPersonData() {
        return personData;
    }

    public ConfigurableApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public FxWeaver getFxWeaver() {
        return fxWeaver;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void initStartLayout() {
        FxControllerAndView<EmployeeController, Node> controllerAndView = fxWeaver.load(EmployeeController.class);
        controllerAndView.getView().ifPresent(parent -> {
            Scene scene = new Scene((Parent) parent);
            primaryStage.setScene(scene);
        });
        controllerAndView.getController().setLoginStage(primaryStage);
        controllerAndView.getController().setMain(this);
        primaryStage.setTitle("Login");
        primaryStage.show();
    }

    public boolean showPersonEditDialog(Person person) {
        FxControllerAndView<PersonEditingController, Node> controllerAndView = fxWeaver.load(PersonEditingController.class);
        Stage dialogStage = new Stage();
        controllerAndView.getView().ifPresent(parent -> {
            Scene scene = new Scene((Parent) parent);
            dialogStage.setScene(scene);
        });
        dialogStage.setTitle("New Person");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(primaryStage);
        controllerAndView.getController().setDialogStage(dialogStage);
        controllerAndView.getController().setPerson(person);
        dialogStage.showAndWait();
        return controllerAndView.getController().isOkClicked();
    }


}
