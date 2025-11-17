package com.example.stonks.model;

public abstract class ASSET {
    protected String name;
    protected double price;

    public ASSET (String name, double price)
    {
        this.name = name;
        this.price = price;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }

    public void setPrice(double price) { this.price = price; }


    public abstract void updatePrice();
}
