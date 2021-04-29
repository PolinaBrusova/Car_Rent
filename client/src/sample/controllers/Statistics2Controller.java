package sample.controllers;

import sample.JavaFxApplication;
import sample.utils.ConnectionPerfomance;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.IOException;

/**
 * JavaFX scene controller
 */
public class Statistics2Controller {

    @FXML
    private BarChart<String, Integer> chart;
    final CategoryAxis x = new CategoryAxis();
    final NumberAxis y = new NumberAxis();
    private Stage stage;
    private JavaFxApplication main;

    /**
     * Empty initializer
     */
    public Statistics2Controller(){ }

    /**
     * Gets data from the database and loads it into BarChart
     */
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

    /**
     * sets the stage for this controller
     * @param stage Javafx stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * sets the main for this controller
     * @param main JavaFxApplication main
     */
    public void setMain(JavaFxApplication main) {
        this.main = main;
    }

    /**
     * Handles action on "Close" button closing the stage
     */
    @FXML
    private void handleClose(){
        this.stage.close();
    }
}

