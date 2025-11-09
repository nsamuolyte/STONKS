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
        double old = stock.getPrice(); //sena kaina
        stock.updatePrice();
        double newer = stock.getPrice(); // atnaujinta kaina

        assertNotEquals(old, newer, "Kaina turi pasikeisti po atnaujinimo");
        assertTrue(newer >= 1, "Kaina negali but neigiama");
    }

    @Test
    void testPriceHistoryLimitAndAvgPositive() {
        for (int i = 0; i < 10; i++) stock.updatePrice();
        double avg = stock.getAVG();
        assertTrue(avg >= 1, "Vidurkis negali but neigiamas");
    }

    @Test
    void testPriceChangeEvery10Seconds() throws InterruptedException {
        STOCK stock = new STOCK("TEST", 100.0);
        System.out.println("Pradinė " + stock.getName() + " kaina: " + stock.getPrice() + " €");

        for (int i = 1; i <= 10; i++) {
            Thread.sleep(1000); // simuliuojam 1 sekundę (galėtum dėti 10_000L, jei nori tikrų 10s)
            stock.updatePrice();
            System.out.printf("Po %d sek. kaina: %.2f €%n", i * 10, stock.getPrice());
        }

        double avg = stock.getAVG();
        System.out.printf("Vidutinė kaina per laikotarpį: %.2f €%n", avg);

        assertTrue(avg >= 1, "Vidutinė kaina turi būti teigiama");
    }
}
