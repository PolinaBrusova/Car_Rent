package com.example.demo.clientView.controllersFX;

import javafx.fxml.FXML;
import javafx.stage.Stage;

/**
 * JavaFX scene controller
 */
public class AboutController {
    private Stage stage;

    public AboutController(){ }

    @FXML
    private void initialize() {
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Handles action on "close" button with ckosing the stage
     */
    @FXML
    private void handleClose(){
        this.stage.close();
    }
}
