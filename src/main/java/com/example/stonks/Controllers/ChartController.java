package com.example.stonks.Controllers;

import com.example.stonks.model.ASSET;
import com.example.stonks.model.STOCK;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.util.Duration;

public class ChartController
{

    private static final int AXIS_WINDOW = 20;        // vietoj 20
    private static final int SLIDER_OFFSET = 50;      // vietoj 50

    private Timeline timeline;
    private int time = 0;
    private int speed = 1;
    private boolean autoScroll = true;

    private final XYChart.Series<Number, Number> series = new XYChart.Series<>();

    private NumberAxis xAxis;
    private Label priceLabel;
    private Slider slider;
    private ASSET asset;

    public void init(LineChart<Number, Number> chart, NumberAxis xAxis,
                     Label priceLabel, Slider slider, ASSET stock)
    {
        this.xAxis = xAxis;
        this.priceLabel = priceLabel;
        this.slider = slider;
        this.asset = stock;

        setupChart(chart);
        setupAxis();
        setupSlider();

        startTimeline();
    }

    private void setupChart(LineChart<Number, Number> chart) {
        chart.setLegendVisible(false);
        chart.getData().add(series);
    }

    private void setupAxis() {
        xAxis.setAutoRanging(false);
        xAxis.setLowerBound(0);
        xAxis.setUpperBound(AXIS_WINDOW);
    }

    private void setupSlider()
    {
        slider.valueProperty().addListener((obs, oldV, newV) -> {
            if (slider.isValueChanging()) {
                autoScroll = false;
                double v = newV.doubleValue();
                xAxis.setLowerBound(Math.max(0, v - SLIDER_OFFSET));
                xAxis.setUpperBound(v);
            }

                autoScroll = true;
        });
    }

    private void startTimeline() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(speed), e -> updateChart()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public void setSpeed(int seconds) {
        this.speed = seconds;
        stop();
        startTimeline();
    }

    public void updateChart() {
        asset.updatePrice();
        time++;

        double price = asset.getPrice();
        series.getData().add(new XYChart.Data<>(time, price));
        priceLabel.setText(String.format("Kaina: %.2f €", price));

        updateAxis();
        updateSlider();
    }

    private void updateAxis() {
        if (time > AXIS_WINDOW) {
            xAxis.setLowerBound(time - AXIS_WINDOW);
            xAxis.setUpperBound(time);
        }
    }

    private void updateSlider() {
        slider.setMax(time);

        if (autoScroll)
        {
            slider.setValue(time);

            xAxis.setLowerBound(Math.max(0, time - AXIS_WINDOW));
            xAxis.setUpperBound(time);
        }
    }

    public void stop() {
        if (timeline != null) timeline.stop();
    }

    public void play() {
        if (timeline != null) timeline.play();
    }

    public void resetChart(ASSET newStock) {
        this.asset = newStock;
        time = 0;
        series.getData().clear();

        stop();
        startTimeline();
    }
}
