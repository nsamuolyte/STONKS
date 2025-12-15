package com.example.stonks.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class testSTOCK
{
    private STOCK stock;

    @BeforeEach
    void setup() {
        stock = new STOCK("TEST", 100);
        System.out.println("\n--- Naujas STOCK testas ---");
    }

    @Test
    void constructor_setsInitialValues() {
        System.out.println("TESTAS: constructor_setsInitialValues");

        System.out.println("Pavadinimas: " + stock.getName());
        System.out.println("Pradinė kaina: " + stock.getPrice());

        assertEquals("TEST", stock.getName());
        assertEquals(100.0, stock.getPrice(), 0.001);

        System.out.println("✔ constructor_setsInitialValues PRAĖJO");
    }

    @Test
    void updatePrice_changesPriceAndNotNegative() {
        System.out.println("TESTAS: updatePrice_changesPriceAndNotNegative");

        double oldPrice = stock.getPrice();
        System.out.println("Sena kaina: " + oldPrice);

        stock.updatePrice(1);

        double newPrice = stock.getPrice();
        System.out.println("Nauja kaina: " + newPrice);

        assertNotEquals(oldPrice, newPrice);
        assertTrue(newPrice >= 1);

        System.out.println("✔ updatePrice_changesPriceAndNotNegative PRAĖJO");
    }

}
