package com.example.stonks;

import com.example.stonks.model.STOCK;
import com.example.stonks.model.PLAYER;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextInputDialog;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Optional;

public class GameController {

    @FXML
    private Slider slider;

    @FXML
    private Text saskaitaTXT;

    @FXML
    private Text akcijosTXT;

    private PLAYER player;

    @FXML
    private Label playerNameLabel;

    @FXML
    private Label priceLabel;

    @FXML
    private LineChart<Number, Number> priceChart;

    @FXML
    private NumberAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private ComboBox<String> speedCB;

    private int numb = 1; // greičio intervalas sekundėmis
    private final XYChart.Series<Number, Number> series = new XYChart.Series<>();
    private STOCK stock = new STOCK("STONKS", 100.0);
    private int time = 0;
    private Timeline timeline;

    public void initialize() {
        // Inicializuojam žaidėją
        String vardas = playerNameLabel.getText().replace("Žaidėjas: ", "");
        player = new PLAYER(vardas, 1000.0);

        // Nustatom grafiką
        priceChart.getData().add(series);
        xAxis.setAutoRanging(false);
        xAxis.setForceZeroInRange(false);
        xAxis.setTickLabelsVisible(false); // (nebūtina, bet švariau atrodo)
        xAxis.setLowerBound(0);
        xAxis.setUpperBound(20); // rodom pradžioje pirmus 50 taškų
        priceChart.setLegendVisible(false);
        numb = 1;

        // Inicijuojam timeline (animaciją)
        timeline = new Timeline(new KeyFrame(Duration.seconds(numb), e -> updateChart()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        // Pririšam tekstus prie žaidėjo kintamųjų
        saskaitaTXT.textProperty().bind(Bindings.format("%.2f €", player.balance));
        akcijosTXT.textProperty().bind(Bindings.format("%d", player.ownedStocks));

        // Greičio pasirinkimas
        speedCB.getItems().addAll("Lėtai", "Normaliai", "Greitai");
        speedCB.setValue("Greitai");
        speedCB.setOnAction(e -> changeSpeed());

        slider.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (slider.isValueChanging()) { // tik kai naudotojas tempia
                int visiblePoints = 50;
                double v = newVal.doubleValue();
                xAxis.setLowerBound(Math.max(0, v - visiblePoints));
                xAxis.setUpperBound(v);
            }
        });

    }

    /** Atnaujina grafiką */
    private void updateChart() {
        stock.updatePrice();
        time++;
        double price = stock.getPrice();

        series.getData().add(new XYChart.Data<>(time, price));
        priceLabel.setText(String.format("Kaina: %.2f €", price));

        int visiblePoints = 50;

        // Leisk grafikui "testis į dešinę", o ne susispausti
        if (time > visiblePoints) {
            xAxis.setLowerBound(time - visiblePoints);
            xAxis.setUpperBound(time);
        } else {
            xAxis.setUpperBound(visiblePoints);
        }

        // Atnaujinam slider ribas
        slider.setMax(time);
        if (!slider.isValueChanging()) {
            slider.setValue(time);
        }
    }


    /** Keičiame animacijos greitį */
    private void changeSpeed() {
        String pasirinkimas = speedCB.getValue();
        switch (pasirinkimas) {
            case "Lėtai" -> numb = 5;
            case "Normaliai" -> numb = 2;
            case "Greitai" -> numb = 1;
        }

        timeline.stop();
        timeline = new Timeline(new KeyFrame(Duration.seconds(numb), e -> updateChart()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    // === Mygtukai ===
    @FXML
    public void stopBTon(ActionEvent actionEvent) { timeline.stop(); }

    @FXML
    public void playBTon(ActionEvent actionEvent) { timeline.play(); }

    @FXML
    public void buyBTon(ActionEvent event) {
        timeline.pause();
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Pirkti akcijas");
        dialog.setHeaderText("Įveskite, kiek akcijų norite nusipirkti:");
        dialog.setContentText("Kiekis:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(input -> {
            try {
                int kiekis = Integer.parseInt(input);
                boolean success = player.buyStock(stock, kiekis, 0.02);
                if (!success) {
                    showBankrotasDialog();
                } else {
                    checkForBankruptcy();
                }
            } catch (NumberFormatException e) {
                System.out.println("Neteisingas skaičius!");
            }
        });
        timeline.play();
    }

    @FXML
    public void sellBTon(ActionEvent event) {
        timeline.pause();
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Parduoti akcijas");
        dialog.setHeaderText("Įveskite, kiek akcijų norite parduoti:");
        dialog.setContentText("Kiekis:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(input -> {
            try {
                int kiekis = Integer.parseInt(input);
                boolean success = player.sellStock(stock, kiekis, 0.02);
                if (!success) {
                    showBankrotasDialog();
                } else {
                    checkForBankruptcy();
                }
            } catch (NumberFormatException e) {
                System.out.println("Neteisingas skaičius!");
            }
        });
        timeline.play();
    }

    @FXML
    public void restartBTon(ActionEvent actionEvent) {
        timeline.pause();
        player.setBalance(1000.0);
        player.setOwnedStocks(0);
        stock = new STOCK("STONKS", 100.0);
        series.getData().clear();
        time = 0;
        priceLabel.setText(String.format("Kaina: %.2f €", stock.getPrice()));
        timeline.play();
    }

    /** Parodo bankroto langą */
    @FXML
    private void showBankrotasDialog() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("bankrotas-view.fxml"));
            Parent root = loader.load();
            BankrotasController controller = loader.getController();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Bankrotas 💀");
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setScene(new Scene(root));
            dialogStage.showAndWait();

            if (controller.isRestartChosen()) {
                timeline.play(); // tęsiam žaidimą
            } else {
                exitToMainMenu(); // grįžtam į pradžią
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Grąžina į pradžios langą */
    @FXML
    private void exitToMainMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) priceChart.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Pradžios puslapis");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Tikrina ar nebankrutavo */
    @FXML
    private void checkForBankruptcy() {
        System.out.println("👉 Tikrinam bankrotą... balansas = " + player.getBalance() + ", akcijos = " + player.getOwnedStocks());
        if (player.getBalance() < 0 || player.getOwnedStocks() < 0) {
            System.out.println("💀 Bankrotas suveikė!");
            timeline.pause();
            showBankrotasDialog();
        }
    }

    public void setPlayerName(String name) {
        playerNameLabel.setText("Žaidėjas: " + name);
    }
}
