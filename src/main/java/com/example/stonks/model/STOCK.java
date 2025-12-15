package com.example.stonks.model;

import com.example.stonks.util.RandomVolatileStrategy;

public class STOCK extends ASSET  { // 1.1 Paveldi is ASSET

    public STOCK(String name, double price) {
        super(name, price, new RandomVolatileStrategy());
    }
}

