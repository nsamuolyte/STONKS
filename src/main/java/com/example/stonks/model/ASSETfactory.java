package com.example.stonks.model;

public class ASSETfactory {

    //Factory Method
    public static ASSET createStock(String name, double price) { return new STOCK(name, price); }
    public static ASSET createCommodity(String name, double price) { return new COMMODITY(name, price);}

    public static ASSET createAsset(String type, String name, double price)
    {
        return switch (type.toLowerCase())
        {
            case "stock" -> new STOCK(name, price);
            case "commodity" -> new COMMODITY(name, price);
            default -> throw new IllegalArgumentException("Unknown asset type: " + type);
        };
    }
}
