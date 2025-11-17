package com.example.stonks.model;

import java.util.Random;

public class STOCK extends ASSET implements PriceUpdate {

    private final Random random = new Random();

    public STOCK(String name, double price) { super(name, price); }
    @Override
    public void updatePrice()
    {
        double change = (random.nextDouble() * 10) - 5;
        price = Math.max(1, price + change);
    }
}

