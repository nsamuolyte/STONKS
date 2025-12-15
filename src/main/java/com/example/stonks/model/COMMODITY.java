package com.example.stonks.model;

import com.example.stonks.util.StableStrategy;

public class COMMODITY extends ASSET { // 1.1 Paveldi is ASSET

    public COMMODITY(String name, double price) {
        super(name, price, new StableStrategy());
    }
}
