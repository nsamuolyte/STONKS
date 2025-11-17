package com.example.stonks.Controllers;

import com.example.stonks.model.ASSET;
import com.example.stonks.model.PLAYER;
import com.example.stonks.model.STOCK;
import javafx.scene.control.TextInputDialog;

import java.util.Optional;

public class TradingController {

    private static final double COMMISSION = 0.02; // konstanta vietoj magic number

    public boolean showBuyDialog(PLAYER player, ASSET stock) {
        return showTradeDialog("Pirkti akcijas", "Įveskite kiek pirkti:", player, stock, true);
    }

    public boolean showSellDialog(PLAYER player, ASSET stock) {
        return showTradeDialog("Parduoti akcijas", "Įveskite kiek parduoti:", player, stock, false);
    }

    // 🟣 FIXED: pakeista STOCK → ASSET
    private boolean showTradeDialog(String title, String header, PLAYER player, ASSET stock, boolean buying) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(title);
        dialog.setHeaderText(header);
        dialog.setContentText("Kiekis:");

        Optional<String> r = dialog.showAndWait();
        if (r.isEmpty()) return true;

        try {
            int amount = Integer.parseInt(r.get());
            return buying
                    ? player.buyStock(stock, amount, COMMISSION)
                    : player.sellStock(stock, amount, COMMISSION);

        } catch (Exception e) {
            return true;
        }
    }
}

