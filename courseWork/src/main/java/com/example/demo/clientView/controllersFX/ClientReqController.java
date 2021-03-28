package com.example.demo.clientView.controllersFX;

import javafx.fxml.FXML;
import javafx.stage.Stage;

public class ClientReqController {
    private Stage clientReqStage;

    public ClientReqController(){ }

    @FXML
    private void initialize() {
    }

    public void setClientReqStage(Stage stage) {
        this.clientReqStage = stage;
    }

    @FXML
    private void handleClose(){
        this.clientReqStage.close();
    }
}
