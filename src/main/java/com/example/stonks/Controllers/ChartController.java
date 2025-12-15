package com.example.stonks.Controllers;

import com.example.stonks.model.ASSET;
import com.example.stonks.model.AssetObserver;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

import static com.example.stonks.util.Constants.AXIS_WINDOW;
import static com.example.stonks.util.Constants.SLIDER_OFFSET;

public class ChartController implements AssetObserver {

    private NumberAxis xAxis;
    private Label priceLabel;
    private Slider slider;
    private LineChart<Number, Number> chart;

    // Išsaugoma kiekvieno asset atskira grafinė būsena
    public static class ChartState
    {
        public XYChart.Series<Number, Number> series = new XYChart.Series<>();
        public int time = 0;
    }

    private ChartState state;

    // Ar grafikas turėtų auto-scroll’inti?
    private boolean autoScroll = true;

    public void init(LineChart<Number, Number> chart,  NumberAxis xAxis, Label priceLabel,
                     Slider slider, ASSET asset, ChartState existingState)
    {
        this.chart = chart;
        this.xAxis = xAxis;
        this.priceLabel = priceLabel;
        this.slider = slider;

        if (existingState == null) {
            this.state = new ChartState();
        } else {
            this.state = existingState;
        }

        asset.addObserver(this); //-> observer

        setupChart();
        setupAxis();
        setupSlider();
        restoreState();
    }

    private void setupChart() {
        chart.setLegendVisible(false);
        chart.getData().clear();
        chart.getData().add(state.series);
    }

    private void setupAxis() {
        xAxis.setAutoRanging(false);
        xAxis.setLowerBound(0);
        xAxis.setUpperBound(AXIS_WINDOW);
    }

    private void setupSlider() {
        slider.valueProperty().addListener((obs, oldV, newV) -> {
            if (slider.isValueChanging()) {
                autoScroll = false;
                double v = newV.doubleValue();
                xAxis.setLowerBound(Math.max(0, v - SLIDER_OFFSET));
                xAxis.setUpperBound(v);
            } else {
                autoScroll = true;
            }
        });
    }

    private void restoreState() {
        if (state.time > AXIS_WINDOW) {
            xAxis.setLowerBound(state.time - AXIS_WINDOW);
            xAxis.setUpperBound(state.time);
        }

        slider.setMax(state.time);
        slider.setValue(state.time);
    }

    @Override
    public void onPriceUpdated(ASSET asset, double newPrice, int time) {
        state.time = time;

        state.series.getData().add(new XYChart.Data<>(time, newPrice));
        priceLabel.setText(String.format("Kaina: %.2f €", newPrice));

        slider.setMax(time);

        if (autoScroll) {
            slider.setValue(time);
        }

        updateAxis();
    }

    private void updateAxis() {
        if (state.time > AXIS_WINDOW) {
            xAxis.setLowerBound(state.time - AXIS_WINDOW);
            xAxis.setUpperBound(state.time);
        }
    }

    public ChartState getState() {
        return state;
    }
}
