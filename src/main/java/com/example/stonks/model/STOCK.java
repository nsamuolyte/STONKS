package com.example.stonks.model;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;


public class STOCK
{
    private String name;
    private double price;
    private Random random = new Random();
    private Queue<Double> priceHistory = new LinkedList<>();

    public STOCK(String name, double price)
    {
        this.name = name;
        this.price = price;
    }
    public String getName() {
        return name;
    }
     public double getPrice()
     {
         return price;
     }

     public void updatePrice()
    {
        double change = (random.nextDouble() * 10) - 5;
        price = price + change;
        if (price < 1) price = 1;
        priceHistory.add(price);
        if (priceHistory.size() > 5) priceHistory.poll();
    }

    public double getAVG()
    {
        if (priceHistory.isEmpty()) return price;
        double sum = 0;
        for (double p : priceHistory) sum = sum + p;
        return sum / priceHistory.size();
    }

}
