package com.example.demo.clientView.controllersFX;

import javafx.fxml.FXML;
import javafx.stage.Stage;

/**
 * JavaFX scene controller
 */
public class ClientReqController {
    private Stage clientReqStage;

    public ClientReqController(){ }

    @FXML
    private void initialize() {
    }

    public void setClientReqStage(Stage stage) {
        this.clientReqStage = stage;
    }

    /**
     * Handles action on "Close" button
     */
    @FXML
    private void handleClose(){
        this.clientReqStage.close();
    }
}
