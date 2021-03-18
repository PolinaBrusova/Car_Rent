package com.example.demo.controllersFX;

import com.example.demo.JavaFxApplication;
import com.example.demo.models.Client;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class RootManagerController {

    private JavaFxApplication main;
    private Stage searchStage;

    @FXML
    private TableView<Client> personTableView;

    public RootManagerController(){}

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
    private void handleSearch(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(JavaFxApplication.class.getResource("views/searchStage.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            searchStage = new Stage();
            searchStage.setTitle("Search");
            searchStage.initModality(Modality.WINDOW_MODAL);
            searchStage.initOwner(main.getPrimaryStage());
            Scene scene = new Scene(page);
            searchStage.setScene(scene);
            SearchWindowController controller = loader.getController();
            controller.setDialogStage(searchStage);
            controller.setMain(main);
            searchStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private  void handleClose(){
        main.getPrimaryStage().close();
    }
}
