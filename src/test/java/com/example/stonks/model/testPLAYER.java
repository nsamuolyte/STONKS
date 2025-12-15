package com.example.stonks.model;

import com.example.stonks.Controllers.GameController;
import com.example.stonks.Controllers.GameStateManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.mockito.Mockito;

class testPLAYER {

    private PLAYER player;
    private ASSET asset;

    @BeforeEach
    void setup() {
        player = new PLAYER("Test", 1000);
        asset = new STOCK("TEST", 100);
        System.out.println("\n--- Naujas PLAYER testas ---");
    }

    @Test
    void buyStock_success() {
        System.out.println("TESTAS: buyStock_success");

        boolean result = player.buyStock(asset, 5, 0.02);

        System.out.println("Rezultatas: " + result);
        System.out.println("Turimos akcijos: " + player.getOwnedStocks());
        System.out.println("Balansas: " + player.getBalance());

        assertTrue(result);
        assertEquals(5, player.getOwnedStocks());
        assertTrue(player.getBalance() < 1000);

        System.out.println("✔ buyStock_success PRAĖJO");
    }

    @Test
    void buyStock_notEnoughMoney() {
        System.out.println("TESTAS: buyStock_notEnoughMoney");

        boolean result = player.buyStock(asset, 100, 0.02);

        System.out.println("Rezultatas: " + result);
        System.out.println("Balansas po bandymo: " + player.getBalance());

        assertFalse(result);
        assertEquals(0, player.getOwnedStocks());
        assertEquals(1000, player.getBalance());

        System.out.println("✔ buyStock_notEnoughMoney PRAĖJO");
    }
}