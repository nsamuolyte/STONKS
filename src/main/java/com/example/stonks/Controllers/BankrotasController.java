package com.example.stonks.Controllers;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class BankrotasController {

    @FXML
    private ImageView mouseImage;

    private boolean restartChosen = false;       // Tęsti žaidimą
    private boolean bankrutuotiChosen = false;   // Išeiti į meniu

    @FXML
    private void testiOn()
    {
        restartChosen = true;        // Tęsia žaidimą
        bankrutuotiChosen = false;
        close();
    }

    @FXML
    private void bankrutuotiOn()
    {
        restartChosen = false;       // Ne tęsia
        bankrutuotiChosen = true;    // Pasirinko bankrutuoti
        close();
    }

    private void close()
    {
        Stage stage = (Stage) mouseImage.getScene().getWindow();
        stage.close();
    }

    public boolean isRestartChosen() { return restartChosen; }
    public boolean isBankrutuotiChosen() { return bankrutuotiChosen; }

}
