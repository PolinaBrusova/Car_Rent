package com.example.demo.clientView.controllersFX;

import javafx.fxml.FXML;
import javafx.stage.Stage;

public class SalesInfoController {
    private Stage salesInfoStage;

    public SalesInfoController(){ }

    @FXML
    private void initialize() {
    }

    public void setSalesInfogStage(Stage stage) {
        this.salesInfoStage = stage;
    }

    @FXML
    private void handleClose(){
        this.salesInfoStage.close();
    }
}
