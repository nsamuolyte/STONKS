package com.example.stonks;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class BankrotasController {

    @FXML
    private ImageView mouseImage;

    private boolean restartChosen = false;

    @FXML
    public void initialize() {
        mouseImage.setImage(new Image(getClass().getResourceAsStream("poor.png")));
    }

    @FXML
    private void onYes() {
        restartChosen = true;
        close();
    }

    @FXML
    private void onNo() {
        restartChosen = false;
        close();
    }

    private void close() {
        Stage stage = (Stage) mouseImage.getScene().getWindow();
        stage.close();
    }

    public boolean isRestartChosen() { return restartChosen; }
}
