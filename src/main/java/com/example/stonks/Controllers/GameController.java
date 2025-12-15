package com.example.stonks.Controllers;

import com.example.stonks.model.ASSET;
import com.example.stonks.model.ASSETfactory;
import com.example.stonks.model.PLAYER;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.Map;

import static com.example.stonks.util.Constants.*;

public class GameController {

    @FXML private Slider slider;
    @FXML private Text saskaitaTXT;
    @FXML private Text akcijosTXT;
    @FXML private Label playerNameLabel;
    @FXML private Label priceLabel;
    @FXML private LineChart<Number, Number> priceChart;
    @FXML private NumberAxis xAxis;
    @FXML private ComboBox<String> speedCB;
    @FXML private ComboBox<String> assetTypeCB;

    private PLAYER player;
    private ASSET asset;

    private ChartController chart;
    private TradingController trading;
    private GameStateManager state;

    private final Map<ASSET, ChartController.ChartState> chartStates = new HashMap<>();

    private Timeline timeline;
    private int time = 0;
    private int speed = 1;

    public void initialize() {

        chart = new ChartController();
        trading = new TradingController();
        state = new GameStateManager();

        slider.setShowTickLabels(false);
        slider.setShowTickMarks(false);

        assetTypeCB.getItems().addAll("Akcija", "Auksas");
        assetTypeCB.setValue("Akcija");

        String name = playerNameLabel.getText().replace("Žaidėjas: ", "");
        player = new PLAYER(name, DEFAULT_PLAYER_BALANCE);

        saskaitaTXT.textProperty().bind(Bindings.format("%.2f €", player.balance));
        akcijosTXT.textProperty().bind(Bindings.format("%d", player.ownedStocks));

        asset = createAssetFromSelection();
        chart.init(priceChart, xAxis, priceLabel, slider, asset, chartStates.get(asset));

        startTimeline();

        speedCB.getItems().addAll("Lėtai", "Normaliai", "Greitai");
        speedCB.setValue("Greitai");

        speedCB.setOnAction(e -> {
            switch (speedCB.getValue()) {
                case "Lėtai"     -> setSpeed(5);
                case "Normaliai" -> setSpeed(2);
                case "Greitai"   -> setSpeed(1);
            }
        });

        assetTypeCB.setOnAction(e -> switchAssetType());
    }

    private ASSET createAssetFromSelection() {
        return assetTypeCB.getValue().equals("Akcija")
                ? ASSETfactory.createStock("SLYVACOINAS", DEFAULT_STOCK_PRICE)
                : ASSETfactory.createCommodity("Auksas", DEFAULT_STOCK_PRICE);
    }

    private void startTimeline() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(speed), e -> {
            time++;
            asset.updatePrice(time);
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void setSpeed(int s) {
        this.speed = s;
        timeline.stop();
        startTimeline();
    }

    private void switchAssetType() {

        chartStates.putIfAbsent(asset, chart.getState());
        asset = createAssetFromSelection();
        ChartController.ChartState stateToLoad = chartStates.get(asset);

        chart.init(priceChart, xAxis, priceLabel, slider, asset, stateToLoad);
    }

    @FXML
    public void buyBTon() {
        timeline.pause();
        boolean ok = trading.showBuyDialog(player, asset);
        afterTrade(ok);
        timeline.play();
    }

    @FXML
    public void sellBTon() {
        timeline.pause();
        boolean ok = trading.showSellDialog(player, asset);
        afterTrade(ok);
        timeline.play();
    }

    public void afterTrade(boolean ok) {
        if (!ok || state.checkBankruptcy(player)) {
            timeline.pause();
            state.showBankruptcyDialog(
                    () -> timeline.play(),
                    this::exitToMainMenu
            );
        }
    }

    @FXML
    public void restartBTon() {

        time = 0;
        asset.setPrice(DEFAULT_STOCK_PRICE);
        player.setBalance(DEFAULT_PLAYER_BALANCE);
        player.setOwnedStocks(0);

        chartStates.clear();

        chart.init(priceChart, xAxis, priceLabel, slider, asset, null);
    }

    private void exitToMainMenu() {
        try {
            javafx.stage.Stage stage = (javafx.stage.Stage) playerNameLabel.getScene().getWindow();
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(
                    getClass().getResource("/com/example/stonks/hello-view.fxml")
            );
            javafx.scene.Parent root = loader.load();
            stage.setScene(new javafx.scene.Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setPlayerName(String name) {
        if (playerNameLabel != null) {
            playerNameLabel.setText("Žaidėjas: " + name);
        }
    }

    public void stopBTon(ActionEvent event) { timeline.pause(); }

    public void playBTon(ActionEvent event) { timeline.play(); }
}
