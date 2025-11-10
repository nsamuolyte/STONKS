package com.example.stonks;

import com.example.stonks.model.STOCK;
import com.example.stonks.model.PLAYER;
import javafx.animation.Animation;
import javafx.beans.binding.Bindings;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.scene.control.TextInputDialog;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.Optional;

public class GameController {

    @FXML
    private Text saskaitaTXT;

    @FXML
    private Text akcijosTXT;

    private PLAYER player;

    @FXML
    private Label playerNameLabel;

    @FXML
    private Label priceLabel; // kad rodytų paskutinę kainą

    @FXML
    private LineChart<Number, Number> priceChart;

    @FXML
    private NumberAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private ComboBox<String> speedCB;

    public int numb = 1; //greicio skaicius
    private XYChart.Series<Number, Number> series = new XYChart.Series<>();
    private STOCK stock = new STOCK("STONKS", 100.0);
    private int time = 0;
    private Timeline timeline;

    public void initialize() {
        String vardas = playerNameLabel.getText();
        player = new PLAYER(vardas, 1000.0);

        priceChart.getData().add(series);
        priceChart.setLegendVisible(false);

        numb = 1;
        timeline = new Timeline(new KeyFrame(Duration.seconds(numb), e -> updateChart()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play(); // paleidžiam animaciją

        saskaitaTXT.textProperty().bind(Bindings.format("%.2f €", player.balance));
        akcijosTXT.textProperty().bind(Bindings.format("%d", player.ownedStocks));

        speedCB.getItems().addAll("Lėtai", "Normaliai", "Greitai");
        speedCB.setValue("Greitai");

        speedCB.setOnAction(e -> {
            String pasirinkimas = speedCB.getValue();
            switch (pasirinkimas) {
                case "Lėtai" -> numb = 5;
                case "Normaliai" -> numb = 2;
                case "Greitai" -> numb = 1;
            }

            // Sustabdom seną grafiką
            timeline.stop();

            // Sukuriam naują su nauju greičiu
            timeline = new Timeline(new KeyFrame(Duration.seconds(numb), ev -> updateChart()));
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
        });
    }


    private void updateChart() {
        stock.updatePrice(); // atnaujina modelio kainą
        time++;

        double price = stock.getPrice();
        series.getData().add(new XYChart.Data<>(time, price));
        priceLabel.setText(String.format("Kaina: %.2f €", price));

        // atnaujinam ašis
        xAxis.setLowerBound(Math.max(0, time - 50));
        xAxis.setUpperBound(time + 1);
    }

    public void setPlayerName(String name) {
        playerNameLabel.setText("Žaidėjas: " + name);
    }

    @FXML
    public void stopBTon(ActionEvent actionEvent) {
        timeline.stop();
    }

    @FXML
    public void playBTon(ActionEvent actionEvent) {
        timeline.play();
    }

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
                    showBankrotasDialog(); // neturi pinigų → parodyti bankrotą
                } else {
                    checkForBankruptcy(); // jei visgi minusas – irgi parodyti
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
                    showBankrotasDialog(); // neturi tiek akcijų → parodyti bankrotą
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


        priceLabel.setText(String.format("Kaina: %.2f €", stock.getPrice()));

        timeline.play();
    }


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
                // Jei žaidėjas pasirinko TĘSTI — tiesiog pratęsiam animaciją
                timeline.play();
            } else {
                // Jei NE — grįžtam į pradžios langą
                exitToMainMenu();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



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

    @FXML
    private void checkForBankruptcy() {
        if (player.getBalance() < 0 || player.getOwnedStocks() < 0) {
            timeline.pause();
            showBankrotasDialog();
        }
        System.out.println("👉 Tikrinam bankrotą... balansas = " + player.getBalance() + ", akcijos = " + player.getOwnedStocks());
        if (player.getBalance() < 0 || player.getOwnedStocks() < 0) {
            System.out.println("💀 Bankrotas suveikė!");
            timeline.pause();
            showBankrotasDialog();
        }
    }





}
