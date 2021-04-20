package com.example.demo.clientView.controllersFX;

import com.example.demo.clientView.JavaFxApplication;
import com.example.demo.utils.ConnectionPerfomance;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.IOException;

public class Statistics2Controller {
    @FXML
    private BarChart<String, Integer> chart;
    final CategoryAxis x = new CategoryAxis();
    final NumberAxis y = new NumberAxis();
    private Stage stage;
    private JavaFxApplication main;

    public Statistics2Controller(){ }

    @FXML
    private void initialize() {
        try{
            JSONObject data = new JSONObject(ConnectionPerfomance.excecuteValidation("http://localhost:9090/api/tests/lengthStatistics"));
            x.setLabel("количество дней");
            y.setLabel("Количество аренд");
            chart.setTitle("Сравнительная статистика продолжительности аренд");
            XYChart.Series<String, Integer> series = new XYChart.Series<>();
            series.setName("Количество аренд с определенной длительностью");
            for (String key: data.keySet()){
                series.getData().add(new XYChart.Data(key, data.get(key)));
            }
            chart.getData().add(series);
        }catch (java.net.ConnectException e) {
            this.stage.close();
            this.main.handleNoConnection();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setMain(JavaFxApplication main) {
        this.main = main;
    }

    @FXML
    private void handleClose(){
        this.stage.close();
    }
}

