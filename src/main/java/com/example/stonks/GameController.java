package com.example.stonks;

import com.example.stonks.model.STOCK;
import com.example.stonks.model.PLAYER;
import javafx.animation.Animation;
import javafx.beans.binding.Bindings;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.util.Duration;
import javafx.scene.text.Text;

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

        // kad grafikas neužaugtų be galo
        if (series.getData().size() > 50)
            series.getData().remove(0);

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

    public void buyBTon(ActionEvent actionEvent) {
    }

    public void sellBTon(ActionEvent actionEvent) {
    }

    public void restartBTon(ActionEvent actionEvent) {
    }
}
