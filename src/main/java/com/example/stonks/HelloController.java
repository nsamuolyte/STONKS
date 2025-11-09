package com.example.stonks;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.io.IOException;

public class HelloController
{
    @FXML
    private TextField vardoField;

    @FXML
    protected void pradejimasBT ()
    {
        String vardas = vardoField.getText();
        //Tuscio vardo lauko verifikacija
        if (vardas.trim().isEmpty())
        {
            vardoField.setPromptText("Zaidejo vardo laukas negali likti tuscias!");
            return;
        }
        try {
            // Užkraunam naują sceną (žaidimo langą)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("birza-view.fxml"));
            Parent root = loader.load();

            // Pasiimam naujo lango kontrolerį
            GameController gameController;
            gameController = loader.getController();
            gameController.setPlayerName(vardas); // <- perduodame žaidėjo vardą

            // Pakeičiam sceną
            Stage stage = (Stage) vardoField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Birža – žaidėjas: " + vardas);
        }
        catch (IOException e) { e.printStackTrace();}
    }
}
