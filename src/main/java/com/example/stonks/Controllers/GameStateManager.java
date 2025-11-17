package com.example.stonks.Controllers;

import com.example.stonks.HelloApplication;
import com.example.stonks.model.PLAYER;
import com.example.stonks.model.STOCK;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GameStateManager {

    public boolean checkBankruptcy(PLAYER player) {
        return player.getBalance() < 0 || player.getOwnedStocks() < 0;
    }

    public void showBankruptcyDialog(Runnable onContinue, Runnable onExit) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/stonks/bankrotas-view.fxml"));
            Parent root = loader.load();
            BankrotasController ctrl = loader.getController();

            Stage s = new Stage();
            s.initModality(Modality.APPLICATION_MODAL);
            s.setScene(new Scene(root));
            s.setTitle("Bankrotas");
            s.showAndWait();

            if (ctrl.isBankrutuotiChosen()) onExit.run();
            else onContinue.run();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
