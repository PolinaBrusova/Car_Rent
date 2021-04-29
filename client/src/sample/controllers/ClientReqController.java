package sample.controllers;

import javafx.fxml.FXML;
import javafx.stage.Stage;

/**
 * JavaFX scene controller
 */
public class ClientReqController {
    private Stage clientReqStage;

    /**
     * Empty initializer
     */
    public ClientReqController(){ }

    @FXML
    private void initialize() {
    }

    /**
     * sets the stage for this controller
     * @param stage Javafx stage
     */
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
