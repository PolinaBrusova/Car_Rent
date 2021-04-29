package sample.controllers;

import sample.models.ComfortLevel;
import sample.JavaFxApplication;
import sample.utils.ConnectionPerfomance;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

/**
 * JavaFX scene controller
 */
public class ComfortOverviewController {
    @FXML
    private TableView<ComfortLevel> comfortTable;
    @FXML
    private TableColumn<ComfortLevel,String> letterColumn;
    @FXML
    private Label letterLabel;
    @FXML
    private Label mainLabel;
    @FXML
    private Label depositLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private Label expLabel;

    private JavaFxApplication main;

    /**
     * Empty initializer
     */
    public ComfortOverviewController(){}

    /**
     * fills the table columns with data
     */
    @FXML
    private void initialize(){
        letterColumn.setCellValueFactory(cellData -> cellData.getValue().getLetterProperty());
        showComfortOverviewDetails(null);
        comfortTable.getSelectionModel().selectedItemProperty().addListener(
                (observable,oldValue,newValue) -> showComfortOverviewDetails(newValue)
        );
    }

    /**
     * sets the main for this controller
     * @param main JavaFxApplication main
     */
    public void setMain(JavaFxApplication main){
        this.main = main;
        comfortTable.setItems(getLevels());
    }

    /**
     * Handles action on "Edit" button
     */
    @FXML
    private void handleEditLevel(){
        try {
            ComfortLevel selectedLevel = comfortTable.getSelectionModel().getSelectedItem();
            if (selectedLevel != null) {
                HashMap<Boolean, ComfortLevel> answer = showComfortEditDialog(selectedLevel);
                boolean okClicked = (boolean) answer.keySet().toArray()[0];
                ComfortLevel comfortLevel = answer.get(okClicked);
                if (okClicked) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("id", comfortLevel.getId());
                    jsonObject.put("level", URLEncoder.encode(comfortLevel.getLevel(), StandardCharsets.UTF_8));
                    jsonObject.put("deposit", comfortLevel.getDeposit());
                    jsonObject.put("rentPrice", comfortLevel.getRentPrice());
                    jsonObject.put("minExperience", comfortLevel.getMinExperience());
                    ConnectionPerfomance.excecutePUT("http://localhost:9090/api/tests/updateComfortLevel", jsonObject);
                    this.main.showCarOwerview();
                    showComfortOverviewDetails(comfortLevel);
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.initOwner(main.getPrimaryStage());
                alert.setTitle("Нет выделения");
                alert.setHeaderText("Уровень комфорта не выбран");
                alert.setContentText("Пожалуйста, выберите уровень комфорта в таблице");

                alert.showAndWait();
            }
        }catch (java.net.ConnectException e){
            this.main.handleNoConnection();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Shows detail of the chosen item from table
     * @param comfortLevel chosen comfort level item
     */
    private void showComfortOverviewDetails(ComfortLevel comfortLevel){
        if(comfortLevel != null){
            letterLabel.setText(comfortLevel.getId());
            mainLabel.setText(comfortLevel.getLevel());
            depositLabel.setText(String.valueOf(comfortLevel.getDeposit()));
            priceLabel.setText(String.valueOf(comfortLevel.getRentPrice()));
            expLabel.setText(String.valueOf(comfortLevel.getMinExperience()));
        }
        else{
            letterLabel.setText("");
            mainLabel.setText("");
            depositLabel.setText("");
            priceLabel.setText("");
            expLabel.setText("");
        }
    }

    /**
     * Shows edit dialog
     * @param comfortLevel ComfortLevel for edit
     * @return HashMap of Boolean, ComfortLevel with the status of clicking on "Ok" button and edited Comfort Level object
     */
    private HashMap<Boolean, ComfortLevel> showComfortEditDialog(ComfortLevel comfortLevel){
        try {
            HashMap<Boolean, ComfortLevel> dictionary = new HashMap<>();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(JavaFxApplication.class.getResource("views/comfortEditDialog.fxml"));
            AnchorPane page = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Заполнение уровня комфорта");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(main.getPrimaryStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            ComfortEditingController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setLevel(comfortLevel);
            dialogStage.showAndWait();
            dictionary.put(controller.isOkClicked(), controller.getLevel());
            return dictionary;
        } catch (IOException e) {
            HashMap<Boolean, ComfortLevel> dictionary = new HashMap<>();
            dictionary.put(false, null);
            e.printStackTrace();
            return dictionary;
        }
    }

    /**
     * gets data from the database to fill the table
     * @return ObservableList of ComfortLevel with received data
     */
    public ObservableList<ComfortLevel> getLevels(){
        try {
            ObservableList<ComfortLevel> levels = FXCollections.observableArrayList();
            JSONArray jsonArray = ConnectionPerfomance.excecuteManyGET("http://localhost:9090/api/tests/AllComfortLevels");
            for (int i = 0; i < jsonArray.length(); i++) {
                ComfortLevel comfortLevel = new ComfortLevel();
                comfortLevel.setId(jsonArray.getJSONObject(i).get("id").toString());
                comfortLevel.setLevel(URLDecoder.decode(jsonArray.getJSONObject(i).get("level").toString(), StandardCharsets.UTF_8));
                comfortLevel.setDeposit(Long.parseLong(jsonArray.getJSONObject(i).get("deposit").toString()));
                comfortLevel.setRentPrice(Long.parseLong(jsonArray.getJSONObject(i).get("rentPrice").toString()));
                comfortLevel.setMinExperience(Integer.parseInt(jsonArray.getJSONObject(i).get("minExperience").toString()));
                levels.add(comfortLevel);
            }
            return levels;
        }catch (java.net.ConnectException e){
            this.main.handleNoConnection();
            return null;
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

}
