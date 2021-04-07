package com.example.demo.clientView.controllersFX;

import com.example.demo.ServerSide.models.ComfortLevel;
import com.example.demo.clientView.JavaFxApplication;
import com.example.demo.utils.ConnectionPerfomance;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

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

    public ComfortOverviewController(){}

    @FXML
    private void initialize(){

        letterColumn.setCellValueFactory(cellData -> cellData.getValue().getLetterProperty());

        showComfortOverviewDetails(null); //очищаем справа
        comfortTable.getSelectionModel().selectedItemProperty().addListener(
                (observable,oldValue,newValue) -> showComfortOverviewDetails(newValue)
        ); //при изменении слушатель изменит данные на новые из таблицы

    }
    public void setMain(JavaFxApplication main) throws IOException {
        this.main = main;
        comfortTable.setItems(getLevels());
    }

    @FXML
    private void handleEditLevel() throws IOException {
        ComfortLevel selectedLevel = comfortTable.getSelectionModel().getSelectedItem();
        if (selectedLevel != null) {
            HashMap<Boolean, ComfortLevel> answer = showComfortEditDialog(selectedLevel);
            boolean okClicked = (boolean) answer.keySet().toArray()[0];
            ComfortLevel comfortLevel = answer.get(okClicked);
            if (okClicked) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", comfortLevel.getId());
                jsonObject.put("level", comfortLevel.getLevel());
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
            alert.setTitle("No Selection");
            alert.setHeaderText("No comfort level Selected");
            alert.setContentText("Please select a comfort level in the table.");

            alert.showAndWait();
        }
    }

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

    private HashMap<Boolean, ComfortLevel> showComfortEditDialog(ComfortLevel comfortLevel){
        try {
            HashMap<Boolean, ComfortLevel> dictionary = new HashMap<>();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(JavaFxApplication.class.getResource("controllersFX/comfortEditDialog.fxml"));
            AnchorPane page = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Comfort Level");
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

    public ObservableList<ComfortLevel> getLevels() throws IOException {
        ObservableList<ComfortLevel> levels = FXCollections.observableArrayList();
        JSONArray jsonArray = ConnectionPerfomance.excecuteManyGET("http://localhost:9090/api/tests/AllComfortLevels");
        for (int i=0; i< jsonArray.length(); i++){
            ComfortLevel comfortLevel = new ComfortLevel();
            comfortLevel.setId(jsonArray.getJSONObject(i).get("id").toString());
            comfortLevel.setLevel(jsonArray.getJSONObject(i).get("level").toString());
            comfortLevel.setDeposit(Long.parseLong(jsonArray.getJSONObject(i).get("deposit").toString()));
            comfortLevel.setRentPrice(Long.parseLong(jsonArray.getJSONObject(i).get("rentPrice").toString()));
            comfortLevel.setMinExperience(Integer.parseInt(jsonArray.getJSONObject(i).get("minExperience").toString()));
            levels.add(comfortLevel);
        }
        return levels;
    }

}
