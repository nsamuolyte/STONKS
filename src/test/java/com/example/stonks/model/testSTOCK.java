package com.example.stonks.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class testSTOCK
{
    private STOCK stock;

    @BeforeEach
    public void setup()
    {
        stock = new STOCK("TEST", 100);
    }

    @Test
    public void testStock()
    {
        assertEquals("TEST", stock.getName());
        assertEquals(100.0, stock.getPrice(), 0.0001);
    }

    @Test
    void testUpdatePrice()
    {
        double old = stock.getPrice();
        stock.updatePrice();
        double newer = stock.getPrice();

        assertNotEquals(old, newer, "Kaina turi pasikeisti po atnaujinimo");
        assertTrue(newer >= 1, "Kaina negali but neigiama");
    }


}
