package com.example.stonks.Controllers;

import com.example.stonks.model.PLAYER;
import com.example.stonks.model.STOCK;
import javafx.scene.control.TextInputDialog;

import java.util.Optional;

public class TradingController {

    public boolean showBuyDialog(PLAYER player, STOCK stock) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Pirkti akcijas");
        dialog.setHeaderText("Įveskite kiek pirkti:");
        dialog.setContentText("Kiekis:");

        Optional<String> r = dialog.showAndWait();
        if (r.isEmpty()) return true;

        try {
            int amount = Integer.parseInt(r.get());
            return player.buyStock(stock, amount, 0.02);
        } catch (Exception e) {
            return true;
        }
    }

    public boolean showSellDialog(PLAYER player, STOCK stock) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Parduoti akcijas");
        dialog.setHeaderText("Įveskite kiek parduoti:");
        dialog.setContentText("Kiekis:");

        Optional<String> r = dialog.showAndWait();
        if (r.isEmpty()) return true;

        try {
            int amount = Integer.parseInt(r.get());
            return player.sellStock(stock, amount, 0.02);
        } catch (Exception e) {
            return true;
        }
    }
}
