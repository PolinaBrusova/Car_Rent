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
            searchStage.setTitle("Поиск");
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
            salesInfoStage.setTitle("Скидки");
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
            clientReqStage.setTitle("Требования к клиенту сервиса проката");
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
    private void handleCars(){
        main.showCarOwerview();
    }

    @FXML
    private void handleRents(){
        main.showRentOwerview();
    }

    @FXML
    private void handleClients(){
        main.showPersonOverview();
    }

    @FXML
    private void handleLevels(){
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
        }catch (java.net.ConnectException e){
            this.main.handleNoConnection();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAbout(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(JavaFxApplication.class.getResource("controllersFX/about.fxml"));
            AnchorPane page = loader.load();
            Stage stage= new Stage();
            stage.setTitle("Об авторе");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(main.getPrimaryStage());
            Scene scene = new Scene(page);
            stage.setScene(scene);
            AboutController controller = loader.getController();
            controller.setStage(stage);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleIncomeStatictics(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(JavaFxApplication.class.getResource("controllersFX/statistics1.fxml"));
            AnchorPane page = loader.load();
            Stage stage= new Stage();
            stage.setTitle("Статистика по прибыльности");
            stage.setResizable(false);
            stage.initOwner(main.getPrimaryStage());
            Scene scene = new Scene(page);
            stage.setScene(scene);
            Statistics1Controller controller = loader.getController();
            controller.setStage(stage);
            controller.setMain(this.main);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLengthStatictics(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(JavaFxApplication.class.getResource("controllersFX/statistics2.fxml"));
            AnchorPane page = loader.load();
            Stage stage= new Stage();
            stage.setTitle("Статистика по длительности аренды");
            stage.setResizable(false);
            stage.initOwner(main.getPrimaryStage());
            Scene scene = new Scene(page);
            stage.setScene(scene);
            Statistics2Controller controller = loader.getController();
            controller.setStage(stage);
            controller.setMain(this.main);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
