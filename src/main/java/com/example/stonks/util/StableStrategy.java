package com.example.stonks.util;

public class StableStrategy implements PriceUpdateStrategy {

    @Override
    public double getNextPrice(double currentPrice) {
        double change = (Math.random() - 0.5) * 4;
        double newPrice = currentPrice + change;
        return Math.max(1, newPrice);
    }
}
