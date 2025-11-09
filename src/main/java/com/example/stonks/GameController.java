package com.example.stonks;

import com.example.stonks.model.STOCK;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class GameController {

    @FXML
    private Label playerNameLabel;

    @FXML
    private Label priceLabel; // kad rodytų paskutinę kainą

    @FXML
    private LineChart<Number, Number> priceChart;

    @FXML
    private NumberAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    private XYChart.Series<Number, Number> series = new XYChart.Series<>();
    private STOCK stock = new STOCK("STONKS", 100.0);
    private int time = 0;
    private Timeline timeline;

    public void initialize()
    {
        priceChart.getData().add(series);
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> updateChart()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        priceChart.setLegendVisible(false);
        priceChart.getData().add(series);

        // Atnaujinti kas 1 sekundę
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> updateChart()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void updateChart() {
        stock.updatePrice(); // atnaujina modelio kainą
        time++;

        double price = stock.getPrice();
        series.getData().add(new XYChart.Data<>(time, price));
        priceLabel.setText(String.format("Kaina: %.2f €", price));

        // kad grafikas neužaugtų be galo
        if (series.getData().size() > 50)
            series.getData().remove(0);

        // atnaujinam ašis
        xAxis.setLowerBound(Math.max(0, time - 50));
        xAxis.setUpperBound(time + 1);
    }

    public void setPlayerName(String name) {
        playerNameLabel.setText("Žaidėjas: " + name);
    }
}
