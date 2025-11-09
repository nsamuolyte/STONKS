package com.example.stonks;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

public class HelloController {

    public Button pradetiBT;
    @FXML
    private TextField vardoField;

    @FXML
    protected void pradetiBTon() {
        String vardas = vardoField.getText().trim();

        // Jei tuščias vardas
        if (vardas.isEmpty()) {
            vardoField.setPromptText("Įveskite vardą!");
            return;
        }

        try {
            // Užkraunam birža-view.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/stonks/birza-view.fxml"));
            Scene scene = new Scene(loader.load());

            // Gaunam žaidimo kontrolerį
            GameController gameController = loader.getController();
            gameController.setPlayerName(vardas);

            // Pakeičiam sceną
            Stage stage = (Stage) vardoField.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Birža – žaidėjas: " + vardas);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
