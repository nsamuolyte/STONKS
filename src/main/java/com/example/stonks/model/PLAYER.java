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

    public boolean buyStock(STOCK stock, int amount, double feeRate) {
        double totalCost = stock.getPrice() * amount * (1 + feeRate);

        if (amount <= 0) return false; // negali pirkti 0 ar mažiau
        if (totalCost > getBalance()) return false; // neturi tiek pinigų

        setBalance(getBalance() - totalCost);
        setOwnedStocks(getOwnedStocks() + amount);
        return true;
    }

    public boolean sellStock(STOCK stock, int amount, double feeRate) {
        if (amount <= 0) return false;
        if (amount > getOwnedStocks()) return false; // negali parduoti daugiau nei turi

        double income = stock.getPrice() * amount * (1 - feeRate);
        setBalance(getBalance() + income);
        setOwnedStocks(getOwnedStocks() - amount);
        return true;
    }


    public double getPorfolio(STOCK stock)
    {
        return getOwnedStocks() * stock.getPrice() + getBalance();
    }

}
