package com.example.stonks;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class GameController {

    @FXML
    private Label playerNameLabel;

    public void setPlayerName(String name) {
        playerNameLabel.setText("Žaidėjas: " + name);
    }
}