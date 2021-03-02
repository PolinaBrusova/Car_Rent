package com.example.demo;

import com.example.demo.controllers.MyController;
import com.example.demo.controllers.PersonEditingController;
import com.example.demo.controllers.PersonOverviewController;
import com.example.demo.models.Person;
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


    @Override
    public void init() {
        for (int i = 0; i < 20; i++) {
            personData.add(new Person("First Name" + i, "Last Name" + i));
        }

        String[] args = getParameters().getRaw().toArray(new String[0]);

        this.applicationContext = new SpringApplicationBuilder()
                .sources(DemoApplication.class)
                .run(args);
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

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void initStartLayout() {
        FxWeaver fxWeaver = applicationContext.getBean(FxWeaver.class);
        FxControllerAndView<PersonOverviewController, Node> controllerAndView = fxWeaver.load(PersonOverviewController.class);
        controllerAndView.getView().ifPresent(parent -> {
            Scene scene = new Scene((Parent) parent, 800, 600);
            primaryStage.setScene(scene);
        });

        controllerAndView.getController().setDialogStage(primaryStage);
        controllerAndView.getController().setMain(this);
        primaryStage.setTitle("Application");
        primaryStage.show();
    }

    /*public boolean showPersonEditDialog(Person person) {
        try {
            FxWeaver fxWeaver = applicationContext.getBean(FxWeaver.class);
            Parent root = fxWeaver.loadView(PersonEditingController.class);
            Scene scene = new Scene(root);
            AnchorPane page = (AnchorPane) fxWeaver.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Person");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("views/PersonEditDialog.fxml"));
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
    }*/


}
