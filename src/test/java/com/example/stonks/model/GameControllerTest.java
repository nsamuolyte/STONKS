package com.example.stonks.model;

import com.example.stonks.Controllers.ChartController;
import com.example.stonks.Controllers.GameController;
import com.example.stonks.Controllers.GameStateManager;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class GameControllerTest {

    @Test
    void detectsBankruptcy_whenBalanceNegative() {
        System.out.println("\nTESTAS: detectsBankruptcy_whenBalanceNegative");

        PLAYER player = new PLAYER("Test", -10);
        GameStateManager gsm = new GameStateManager();

        boolean result = gsm.checkBankruptcy(player);
        System.out.println("Bankrotas aptiktas: " + result);

        assertTrue(result);
        System.out.println("✔ detectsBankruptcy_whenBalanceNegative PRAĖJO");
    }

    @Test
    void noBankruptcy_whenBalancePositive() {
        System.out.println("\nTESTAS: noBankruptcy_whenBalancePositive");

        PLAYER player = new PLAYER("Test", 100);
        GameStateManager gsm = new GameStateManager();

        boolean result = gsm.checkBankruptcy(player);
        System.out.println("Bankrotas aptiktas: " + result);

        assertFalse(result);
        System.out.println("✔ noBankruptcy_whenBalancePositive PRAĖJO");
    }
}
