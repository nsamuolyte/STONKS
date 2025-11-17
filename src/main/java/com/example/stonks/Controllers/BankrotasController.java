package com.example.stonks.Controllers;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class BankrotasController {

    @FXML private ImageView mouseImage;

    private boolean restartChosen = false;
    private boolean bankrutuotiChosen = false;

    @FXML
    private void testiOn()
    {
        restartChosen = true;
        bankrutuotiChosen = false;
        close();
    }

    @FXML
    private void bankrutuotiOn()
    {
        restartChosen = false;
        bankrutuotiChosen = true;
        close();
    }

    private void close()
    {
        Stage stage = (Stage) mouseImage.getScene().getWindow();
        stage.close();
    }

    public boolean isBankrutuotiChosen() { return bankrutuotiChosen; }

}
