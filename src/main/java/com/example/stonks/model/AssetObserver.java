package com.example.stonks.model;

import javafx.scene.chart.XYChart;

public interface AssetObserver {
    void onPriceUpdated(ASSET asset, double newPrice, int time);
}
