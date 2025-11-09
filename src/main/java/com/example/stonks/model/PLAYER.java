package com.example.stonks.model;

public class PLAYER
{
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getOwnedStocks() {
        return ownedStocks;
    }

    public void setOwnedStocks(int ownedStocks) {
        this.ownedStocks = ownedStocks;
    }

    private String name;
    private double balance;
    private int ownedStocks;

    public PLAYER (String name, double balance)
    {
        this.name = name;
        this.balance = balance;
    }

    public boolean buyStock(STOCK stock, int amount, double feeRate)
    {
        double kaina = stock.getPrice();
        if (kaina <= balance)
        {
            balance = balance - kaina;
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
            balance = balance + income;
            ownedStocks = ownedStocks - amount;
            return true;
        }
        return false;
    }

    public double getPorfolio(STOCK stock)
    {
        return ownedStocks * stock.getPrice() + balance;
    }

}
