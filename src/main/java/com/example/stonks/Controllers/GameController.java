package com.example.stonks.Controllers;

import com.example.stonks.model.PLAYER;
import com.example.stonks.model.STOCK;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.text.Text;

public class GameController {

    @FXML private Slider slider;
    @FXML private Text saskaitaTXT;
    @FXML private Text akcijosTXT;
    @FXML private Label playerNameLabel;
    @FXML private Label priceLabel;
    @FXML private LineChart<Number, Number> priceChart;
    @FXML private NumberAxis xAxis;
    @FXML private ComboBox<String> speedCB;

    private PLAYER player;
    private STOCK stock = new STOCK("SLYVACOINAS", 100);

    private ChartController chart;
    private TradingController trading;
    private GameStateManager state;

    public void initialize() {

        chart = new ChartController();
        trading = new TradingController();
        state = new GameStateManager();
        slider.setShowTickLabels(false);
        slider.setShowTickMarks(false);


        String name = playerNameLabel.getText().replace("Žaidėjas: ", "");
        player = new PLAYER(name, 1000);

        chart.init(priceChart, xAxis, priceLabel, slider, stock);

        saskaitaTXT.textProperty().bind(Bindings.format("%.2f €", player.balance));
        akcijosTXT.textProperty().bind(Bindings.format("%d", player.ownedStocks));

        speedCB.getItems().addAll("Lėtai", "Normaliai", "Greitai");
        speedCB.setValue("Greitai");

        speedCB.setOnAction(e -> {
            switch (speedCB.getValue()) {
                case "Lėtai" -> chart.setSpeed(5);
                case "Normaliai" -> chart.setSpeed(2);
                case "Greitai" -> chart.setSpeed(1);
            }
        });
    }

    @FXML
    public void buyBTon() {
        chart.stop();
        boolean ok = trading.showBuyDialog(player, stock);
        afterTrade(ok);
        chart.play();
    }

    @FXML
    public void sellBTon() {
        chart.stop();
        boolean ok = trading.showSellDialog(player, stock);
        afterTrade(ok);
        chart.play();
    }

    private void afterTrade(boolean ok) {
        if (!ok || state.checkBankruptcy(player)) {
            chart.stop();
            state.showBankruptcyDialog(
                    () -> chart.play(),
                    () -> exitToMainMenu()
            );
        }
    }

    @FXML
    public void restartBTon() {
        chart.stop();

        player.setBalance(1000);
        player.setOwnedStocks(0);

        stock.setPrice(100);
        chart.resetChart(stock);

        chart.play();
    }


    private void exitToMainMenu() {
        System.out.println("TODO: perjungti į hello-view.fxml");
    }

    public void setPlayerName(String name) {
        playerNameLabel.setText("Žaidėjas: " + name);
    }

    public void stopBTon(ActionEvent event) { chart.stop(); }

    public void playBTon(ActionEvent event) { chart.play(); }
}
