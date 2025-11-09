package com.example.stonks.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class PLAYER
{
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance.get();
    }

    public void setBalance(double balance) {
        this.balance.set(balance);
    }

    public int getOwnedStocks() {
        return ownedStocks;
    }

    public void setOwnedStocks(int ownedStocks) {
        this.ownedStocks = ownedStocks;
    }

    private String name;
    private DoubleProperty balance; //kad automatiskai atsinaujintu
    private int ownedStocks;

    public PLAYER (String name, double balance)
    {
        this.name = name;
        this.balance = new SimpleDoubleProperty(balance);
    }

    public boolean buyStock(STOCK stock, int amount, double feeRate)
    {
        double kaina = stock.getPrice();
        if (kaina <= getBalance())
        {
            setBalance(getBalance() - kaina * amount * (1 - feeRate));
            ownedStocks++;
            return true;
        }
        return false;
    }

    public boolean sellStock(STOCK stock, int amount, double feeRate)
    {
        if (amount <= ownedStocks)
        {
            double income = stock.getPrice() * amount * (1 - feeRate);
            setBalance(getBalance() + income);
            ownedStocks = ownedStocks - amount;
            return true;
        }
        return false;
    }

    public double getPorfolio(STOCK stock)
    {
        return ownedStocks * stock.getPrice() + getBalance();
    }

}
