package com.example.demo.clientView.controllersFX;

import com.example.demo.clientView.JavaFxApplication;
import com.example.demo.utils.ConnectionPerfomance;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.IOException;

public class RootManagerController {

    private JavaFxApplication main;
    private Stage searchStage;

    @FXML
    private Label emplInfo;

    public RootManagerController(){ }

    public void setMain(JavaFxApplication main){
        this.main = main;
        setEmplInfo();
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
            loader.setLocation(JavaFxApplication.class.getResource("controllersFX/searchStage.fxml"));
            AnchorPane page = loader.load();
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

    @FXML
    private void handleSales(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(JavaFxApplication.class.getResource("controllersFX/saleInfo.fxml"));
            AnchorPane page = loader.load();
            Stage salesInfoStage = new Stage();
            salesInfoStage.setTitle("SALES");
            salesInfoStage.initModality(Modality.WINDOW_MODAL);
            salesInfoStage.initOwner(main.getPrimaryStage());
            Scene scene = new Scene(page);
            salesInfoStage.setScene(scene);
            SalesInfoController controller = loader.getController();
            controller.setSalesInfogStage(salesInfoStage);
            salesInfoStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleClientReq(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(JavaFxApplication.class.getResource("controllersFX/clientReq.fxml"));
            AnchorPane page = loader.load();
            Stage clientReqStage= new Stage();
            clientReqStage.setTitle("CLIENT REQUIREMENTS");
            clientReqStage.initModality(Modality.WINDOW_MODAL);
            clientReqStage.initOwner(main.getPrimaryStage());
            Scene scene = new Scene(page);
            clientReqStage.setScene(scene);
            ClientReqController controller = loader.getController();
            controller.setClientReqStage(clientReqStage);
            clientReqStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCars() throws IOException {
        main.showCarOwerview();
    }

    @FXML
    private void handleRents() throws IOException {
        main.showRentOwerview();
    }

    @FXML
    private void handleClients(){
        main.showPersonOverview();
    }

    @FXML
    private void handleLevels() throws IOException {
        main.showLevelOverview();
    }

    @FXML
    private void handleExit(){
        this.main.getPrimaryStage().close();
        this.main.showLoginPage();
    }

    private void setEmplInfo(){
        try{
            JSONObject jsonObject = ConnectionPerfomance.excecuteOnlyGET("http://localhost:9090/api/tests/getEmployee=", String.valueOf(main.getEmployeeId()), "Employee");
            JSONObject jsonObject1 = ConnectionPerfomance.excecuteOnlyGET("http://localhost:9090/api/tests/positionById=",  String.valueOf(jsonObject.getLong("id")),"Position");
            this.emplInfo.setText(jsonObject.getString("firstName")+" "+jsonObject.getString("lastName")+", "+jsonObject1.getString("name"));
        }catch (Exception e){
            e.printStackTrace();

        }
    }
}
