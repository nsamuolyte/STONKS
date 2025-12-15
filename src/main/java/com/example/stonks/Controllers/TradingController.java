package com.example.stonks.Controllers;

import com.example.stonks.model.ASSET;
import com.example.stonks.model.PLAYER;
import javafx.scene.control.TextInputDialog;

import java.util.Optional;

import static com.example.stonks.util.Constants.COMMISSION_PERCENT;

public class TradingController {

    public boolean showBuyDialog(PLAYER player, ASSET asset) {
        return showTradeDialog("Pirkti akcijas", "Įveskite kiek pirkti:", player, asset, true);
    }

    public boolean showSellDialog(PLAYER player, ASSET asset) {
        return showTradeDialog("Parduoti akcijas", "Įveskite kiek parduoti:", player, asset, false);
    }

    private boolean showTradeDialog(String title, String header, PLAYER player, ASSET asset, boolean buying) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(title);
        dialog.setHeaderText(header);
        dialog.setContentText("Kiekis:");

        Optional<String> r = dialog.showAndWait();
        if (r.isEmpty()) return true;

        try {
            int amount = Integer.parseInt(r.get());
            return buying
                    ? player.buyStock(asset, amount, COMMISSION_PERCENT)
                    : player.sellStock(asset, amount, COMMISSION_PERCENT);

        } catch (Exception e) {
            return true;
        }
    }
}
