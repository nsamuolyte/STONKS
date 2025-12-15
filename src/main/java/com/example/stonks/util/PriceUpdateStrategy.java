package com.example.stonks.util;

public interface PriceUpdateStrategy {
    double getNextPrice(double currentPrice);
}
