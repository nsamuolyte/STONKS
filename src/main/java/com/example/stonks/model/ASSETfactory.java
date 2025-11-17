package com.example.stonks.model;

public class ASSETfactory {

    public static ASSET createStock(String name, double price) { return new STOCK(name, price);}

}
