package com.example.stonks.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestAssetObserver {
    static class TestObserver implements AssetObserver {
        boolean notified = false;
        double lastPrice;
        int lastTime;

        @Override
        public void onPriceUpdated(ASSET asset, double newPrice, int time) {
            notified = true;
            lastPrice = newPrice;
            lastTime = time;
        }
    }

    @Test
    void assetNotifiesObserverOnPriceUpdate() {
        ASSET stock = ASSETfactory.createStock("TEST", 100.0);
        TestObserver observer = new TestObserver();
        stock.addObserver(observer);

        int time = 1;
        double oldPrice = stock.getPrice();
        stock.updatePrice(time);

        assertTrue(observer.notified, "Observer should be notified");
        assertEquals(time, observer.lastTime, "Time should be passed correctly");
        assertNotEquals(oldPrice, observer.lastPrice, "Price should change");
    }
}
