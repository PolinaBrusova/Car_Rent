package com.example.demo.clientView.controllersFX;

import javafx.fxml.FXML;
import javafx.stage.Stage;

/**
 * JavaFX scene controller
 */
public class SalesInfoController {
    private Stage salesInfoStage;

    public SalesInfoController(){ }

    @FXML
    private void initialize() {
    }

    public void setSalesInfogStage(Stage stage) {
        this.salesInfoStage = stage;
    }

    /**
     * Handles action on "Close" button closing the stage
     */
    @FXML
    private void handleClose(){
        this.salesInfoStage.close();
    }
}
