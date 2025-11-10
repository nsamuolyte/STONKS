package com.example.stonks;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class BankrotasController {

    @FXML
    private ImageView mouseImage;

    private boolean restartChosen = false; // čia "true" reiškia tęsti, "false" – grįžti į meniu

    @FXML
    private void onYes() { // tęsti žaidimą
        restartChosen = true;
        close();
    }

    @FXML
    private void onNo() { // grįžti į hello-view
        restartChosen = false;
        close();
    }

    private void close() {
        Stage stage = (Stage) mouseImage.getScene().getWindow();
        stage.close();
    }

    public boolean isRestartChosen() {
        return restartChosen;
    }
}
