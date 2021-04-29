package sample.controllers;

import javafx.fxml.FXML;
import javafx.stage.Stage;

/**
 * JavaFX scene controller
 */
public class SalesInfoController {
    private Stage salesInfoStage;

    /**
     * Empty initializer
     */
    public SalesInfoController(){ }

    @FXML
    private void initialize() {
    }

    /**
     * sets the stage for this controller
     * @param stage Javafx stage
     */
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
