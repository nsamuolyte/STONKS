package com.example.stonks.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

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

    public int getOwnedStocks() { return ownedStocks.get(); }

    public void setOwnedStocks(int ownedStocks) {
        this.ownedStocks.set(ownedStocks);
    }

    private String name;
    public DoubleProperty balance; //kad automatiskai atsinaujintu
    public IntegerProperty ownedStocks;

    public PLAYER (String name, double balance)
    {
        this.name = name;
        this.balance = new SimpleDoubleProperty(balance);
        this.ownedStocks = new SimpleIntegerProperty(0);
    }

    public boolean buyStock(STOCK stock, int amount, double feeRate)
    {
        double kaina = stock.getPrice();
        if (kaina <= getBalance())
        {
            setBalance(getBalance() - kaina * amount * (1 - feeRate));
            setOwnedStocks(getOwnedStocks() + amount);
            return true;
        }
        return false;
    }

    public boolean sellStock(STOCK stock, int amount, double feeRate)
    {
        if (amount <= getOwnedStocks())
        {
            double income = stock.getPrice() * amount * (1 - feeRate);
            setBalance(getBalance() + income);
            setOwnedStocks(getOwnedStocks() - amount);
            return true;
        }
        return false;
    }

    public double getPorfolio(STOCK stock)
    {
        return getOwnedStocks() * stock.getPrice() + getBalance();
    }

}
