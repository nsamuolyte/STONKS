package com.example.stonks.Controllers;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class BankrotasController {

    @FXML
    private ImageView mouseImage;

    private boolean restartChosen = false;

    @FXML
    private void testiOn()
    {
        restartChosen = true;
        close();
    }

    @FXML
    private void bankrutuotiOn()
    {
        restartChosen = false;
        close();
    }

    private void close()
    {
        Stage stage = (Stage) mouseImage.getScene().getWindow();
        stage.close();
    }

    public boolean isRestartChosen() { return restartChosen; }

}
