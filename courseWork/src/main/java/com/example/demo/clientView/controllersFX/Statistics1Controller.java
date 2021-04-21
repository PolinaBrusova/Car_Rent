package com.example.demo.clientView.controllersFX;

import com.example.demo.clientView.JavaFxApplication;
import com.example.demo.utils.ConnectionPerfomance;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.IOException;

/**
 * JavaFX scene controller
 */
public class Statistics1Controller {
    @FXML
    private LineChart<String, Float> chart;
    final CategoryAxis x = new CategoryAxis();
    final NumberAxis y = new NumberAxis();
    private Stage stage;
    private JavaFxApplication main;

    public Statistics1Controller(){ }

    /**
     * Gets data from the database and loads it into LineChart
     */
    @FXML
    private void initialize() {
        try{
            JSONObject data = new JSONObject(ConnectionPerfomance.excecuteValidation("http://localhost:9090/api/tests/statistics"));
            x.setLabel("Месяц");
            y.setLabel("Доходность аренд");
            chart.setTitle("Прибыль от аренд по месяцам за 2021 год");
            XYChart.Series<String, Float> series = new XYChart.Series<>();
            series.setName("Сумма в рублях");
            if (data.has("JANUARY")) {
                series.getData().add(new XYChart.Data("Янв", data.get("JANUARY")));
            }else{
                series.getData().add(new XYChart.Data("Янв", 0));
            }
            if (data.has("FEBRUARY")) {
                series.getData().add(new XYChart.Data("Фев", data.get("FEBRUARY")));
            }else{
                series.getData().add(new XYChart.Data("Фев", 0));
            }
            if (data.has("MARCH")) {
                series.getData().add(new XYChart.Data("Мар", data.get("MARCH")));
            }else{
                series.getData().add(new XYChart.Data("Мар", 0));
            }
            if (data.has("APRIL")) {
                series.getData().add(new XYChart.Data("Апр", data.get("APRIL")));
            }else{
                series.getData().add(new XYChart.Data("Апр", 0));
            }
            if (data.has("MAY")) {
                series.getData().add(new XYChart.Data("Май", data.get("MAY")));
            }else{
                series.getData().add(new XYChart.Data("Май", 0));
            }
            if (data.has("JUNE")) {
                series.getData().add(new XYChart.Data("Июнь", data.get("JUNE")));
            }else{
                series.getData().add(new XYChart.Data("Июнь", 0));
            }
            if (data.has("JULY")) {
                series.getData().add(new XYChart.Data("Июль", data.get("JULY")));
            }else{
                series.getData().add(new XYChart.Data("Июль", 0));
            }
            if (data.has("AUGUST")) {
                series.getData().add(new XYChart.Data("Авг", data.get("AUGUST")));
            }else{
                series.getData().add(new XYChart.Data("Авг", 0));
            }
            if (data.has("SEPTEMBER")) {
                series.getData().add(new XYChart.Data("Сент", data.get("SEPTEMBER")));
            }else{
                series.getData().add(new XYChart.Data("Сент", 0));
            }
            if (data.has("OCTOBER")) {
                series.getData().add(new XYChart.Data("Окт", data.get("OCTOBER")));
            }else{
                series.getData().add(new XYChart.Data("Окт", 0));
            }
            if (data.has("NOVEMBER")) {
                series.getData().add(new XYChart.Data("Нояб", data.get("NOVEMBER")));
            }else{
                series.getData().add(new XYChart.Data("Нояб", 0));
            }
            if (data.has("DECEMBER")) {
                series.getData().add(new XYChart.Data("Дек", data.get("DECEMBER")));
            }else{
                series.getData().add(new XYChart.Data("Дек", 0));
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

    /**
     * Handles action on "Close" button closing the stage
     */
    @FXML
    private void handleClose(){
        this.stage.close();
    }
}
