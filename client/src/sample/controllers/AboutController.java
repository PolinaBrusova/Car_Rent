package sample.controllers;

import javafx.fxml.FXML;
import javafx.stage.Stage;

/**
 * JavaFX scene controller
 */
public class AboutController {
    private Stage stage;

    /**
     * Empty initializer
     */
    public AboutController(){ }

    @FXML
    private void initialize() {
    }

    /**
     * sets the stage for this controller
     * @param stage Javafx stage
     */
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
