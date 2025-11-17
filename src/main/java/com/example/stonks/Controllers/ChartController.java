package com.example.stonks.Controllers;

import com.example.stonks.model.STOCK;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.util.Duration;

public class ChartController {

    private Timeline timeline;
    private int time = 0;
    private int speed = 1;
    private final XYChart.Series<Number, Number> series = new XYChart.Series<>();


    private NumberAxis xAxis;
    private Label priceLabel;
    private Slider slider;
    private STOCK stock;


    public void init(LineChart<Number, Number> chart, NumberAxis xAxis,  Label priceLabel, Slider slider, STOCK stock)
    {
        chart.setLegendVisible(false);
        this.xAxis = xAxis;
        this.priceLabel = priceLabel;
        this.slider = slider;
        this.stock = stock;

        chart.getData().add(series);

        xAxis.setAutoRanging(false);
        xAxis.setLowerBound(0);
        xAxis.setUpperBound(20);


        slider.valueProperty().addListener((obs, oldV, newV) -> {
            if (slider.isValueChanging()) {
                double v = newV.doubleValue();
                xAxis.setLowerBound(Math.max(0, v - 50));
                xAxis.setUpperBound(v);
            }
        });
        startTimeline();
    }

    private void startTimeline() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(speed), e -> updateChart()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public void setSpeed(int sec) {
        this.speed = sec;
        stop();
        startTimeline();
    }

    public void updateChart() {
        stock.updatePrice();
        time++;

        double price = stock.getPrice();
        series.getData().add(new XYChart.Data<>(time, price));

        priceLabel.setText(String.format("Kaina: %.2f €", price));

        if (time > 20)
        {
            xAxis.setLowerBound(time - 20);
            xAxis.setUpperBound(time);
        }

        slider.setMax(time);
        if (!slider.isValueChanging()) slider.setValue(time);
    }

    public void stop() { timeline.stop(); }
    public void play() { timeline.play(); }


    public void resetChart(STOCK newStock)
    {
        this.stock = newStock;
        time = 0;
        series.getData().clear();

        if (timeline != null) { timeline.stop();}
        startTimeline();
    }
}
