package com.example.stonks.model;

import com.example.stonks.Controllers.ChartController;
import com.example.stonks.Controllers.GameController;
import com.example.stonks.Controllers.GameStateManager;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;

import static org.mockito.Mockito.*;

class GameControllerTest {

    @Test
    void testBankruptcyDialogShownAfterFailedTrade() throws Exception {
        GameController gc = new GameController();

        // Sukuriame MOCK'us
        GameStateManager mockState = mock(GameStateManager.class);
        ChartController mockChart = mock(ChartController.class);

        // Įrašome mock GameStateManager
        Field stateField = GameController.class.getDeclaredField("state");
        stateField.setAccessible(true);
        stateField.set(gc, mockState);

        // Įrašome mock ChartController
        Field chartField = GameController.class.getDeclaredField("chart");
        chartField.setAccessible(true);
        chartField.set(gc, mockChart);

        // KVIEČIAM tikrinamą metodą
        gc.afterTrade(false);

        // Tikrinimai
        verify(mockChart).stop(); // ← pirmas kvietimas
        verify(mockState).showBankruptcyDialog(any(), any());

        System.out.println("testBankruptcyDialogShownAfterFailedTrade: OK ✓");
    }
}
