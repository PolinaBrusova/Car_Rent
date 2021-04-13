package com.example.demo.clientView.controllersFX;

import javafx.fxml.FXML;
import javafx.stage.Stage;

public class AboutController {
    private Stage stage;

    public AboutController(){ }

    @FXML
    private void initialize() {
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void handleClose(){
        this.stage.close();
    }
}
