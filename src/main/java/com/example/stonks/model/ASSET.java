package com.example.stonks.model;

import com.example.stonks.util.PriceUpdateStrategy;

import java.util.ArrayList;
import java.util.List;

public abstract class ASSET {

    protected String name;
    protected double price;
    protected PriceUpdateStrategy priceUpdateStrategy;
    private List<AssetObserver> observers = new ArrayList<>();


    public ASSET(String name, double price, PriceUpdateStrategy strategy) {
        this.name = name;
        this.price = price;
        this.priceUpdateStrategy = strategy;
    }
    public void addObserver(AssetObserver o) {
        observers.add(o);
    }

    public void notifyObservers(int time) {
        for (AssetObserver o : observers) {
            o.onPriceUpdated(this, this.price, time);
        }
    }

    public String getName() { return name; }

    public double getPrice() { return price; }

    public void setPrice(double price) {
        if (price < 0) price = 0;
        this.price = price;
    }

    public void setPriceUpdateStrategy(PriceUpdateStrategy priceUpdateStrategy) {
        this.priceUpdateStrategy = priceUpdateStrategy;
    }

    public void updatePrice(int time) {
        if (priceUpdateStrategy != null) {
            this.price = priceUpdateStrategy.getNextPrice(this.price);
        }
        notifyObservers(time);
    }
}
