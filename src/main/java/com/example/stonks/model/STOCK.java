package com.example.stonks.model;

import com.example.stonks.util.RandomVolatileStrategy;

public class STOCK extends ASSET  {

    public STOCK(String name, double price) {
        super(name, price, new RandomVolatileStrategy());
    }
}

