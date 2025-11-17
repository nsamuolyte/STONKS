package com.example.stonks.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class PLAYER {
    private String name;
    public DoubleProperty balance;
    public IntegerProperty ownedStocks;

    public PLAYER(String name, double balance) {
        this.name = name;
        this.balance = new SimpleDoubleProperty(balance);
        this.ownedStocks = new SimpleIntegerProperty(0);
    }

    public boolean buyStock(ASSET stock, int amount, double feeRate)
    {
        double totalCost = stock.getPrice() * amount * (1 + feeRate);

        if (amount <= 0) return false;
        if (totalCost > getBalance()) return false;

        setBalance(getBalance() - totalCost);
        setOwnedStocks(getOwnedStocks() + amount);
        return true;
    }

    public boolean sellStock(ASSET stock, int amount, double feeRate) {
        if (amount <= 0) return false;
        if (amount > getOwnedStocks()) return false;

        double income = stock.getPrice() * amount * (1 - feeRate);

        setBalance(getBalance() + income);
        setOwnedStocks(getOwnedStocks() - amount);
        return true;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getBalance() { return balance.get(); }
    public void setBalance(double balance) { this.balance.set(balance); }

    public int getOwnedStocks() { return ownedStocks.get(); }
    public void setOwnedStocks(int ownedStocks) { this.ownedStocks.set(ownedStocks); }
}
