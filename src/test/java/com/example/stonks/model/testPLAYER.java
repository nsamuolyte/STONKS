package com.example.stonks.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class testPLAYER {

    private PLAYER player;
    private STOCK stock;

    @BeforeEach
    void setUp() {
        player = new PLAYER("Jonas", 1000.0);
        stock = new STOCK("AAPL", 100.0);
    }

    @Test
    void testConstructorAndGetters() {
        assertEquals("Jonas", player.getName());
        assertEquals(1000.0, player.getBalance(), 0.0001);
        assertEquals(0, player.getOwnedStocks());
    }

    @Test
    void testSetters() {
        player.setName("Mantas");
        player.setBalance(500.0);
        player.setOwnedStocks(5);

        assertEquals("Mantas", player.getName());
        assertEquals(500.0, player.getBalance(), 0.0001);
        assertEquals(5, player.getOwnedStocks());
    }

    @Test
    void testBuyStockSuccess() {
        boolean result = player.buyStock(stock, 1, 0.0);

        assertTrue(result, "Pirkimas turėtų pavykti");
        assertEquals(900.0, player.getBalance(), 0.0001, "Balansas turėtų sumažėti 100");
        assertEquals(1, player.getOwnedStocks(), "Turėtų turėti 1 akciją");
    }

    @Test
    void testBuyStockFailsWhenNotEnoughBalance() {
        player.setBalance(50.0);
        boolean result = player.buyStock(stock, 1, 0.0);

        assertFalse(result, "Pirkimas neturėtų pavykti");
        assertEquals(50.0, player.getBalance(), 0.0001, "Balansas neturėtų pasikeisti");
        assertEquals(0, player.getOwnedStocks());
    }

    @Test
    void testSellStockSuccess() {
        player.setOwnedStocks(2);
        boolean result = player.sellStock(stock, 1, 0.1); // 10% fee

        assertTrue(result, "Pardavimas turėtų pavykti");
        assertEquals(1, player.getOwnedStocks());
        // Pajamos = 100 * 1 * (1 - 0.1) = 90
        assertEquals(1090.0, player.getBalance(), 0.0001);
    }

    @Test
    void testSellStockFailsWhenNotEnoughOwned() {
        player.setOwnedStocks(0);
        boolean result = player.sellStock(stock, 1, 0.05);

        assertFalse(result, "Pardavimas neturėtų pavykti");
        assertEquals(0, player.getOwnedStocks());
        assertEquals(1000.0, player.getBalance(), 0.0001);
    }

    @Test
    void testGetPortfolio() {
        player.setOwnedStocks(2);
        stock = new STOCK("AAPL", 150.0);
        double portfolioValue = player.getPorfolio(stock);

        assertEquals(1300.0, portfolioValue, 0.0001);
    }
}
