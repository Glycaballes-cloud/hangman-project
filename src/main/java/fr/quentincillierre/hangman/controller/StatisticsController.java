package fr.quentincillierre.hangman.controller;

import java.net.URL;

import fr.quentincillierre.hangman.model.PlayerStats;
import fr.quentincillierre.hangman.model.StatsManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class StatisticsController {

    @FXML
    private ImageView backgroundImage;

    @FXML
    private Label playerLabel;

    @FXML
    private Label gamesLabel;

    @FXML
    private Label winsLabel;

    @FXML
    private Label lossesLabel;

    @FXML
    private Label winRateLabel;

    @FXML
    private Label bestTimeLabel;

    @FXML
    private Label hintsLabel;

    @FXML
    private Button backButton;

    private StatsManager statsManager;

    @FXML
    public void initialize() {

        loadBackground();

    }

    /**
     * Receives player name from MenuController/GameController
     */
    public void setPlayer(String playerName) {

        System.out.println(
                "Statistics received player = "
                        + playerName
        );

        if (playerName == null || playerName.isBlank()) {

            playerName = "Guest";

        }

        statsManager = new StatsManager(playerName);

        loadStatistics();

    }

    /**
     * Loads statistics data
     */
    private void loadStatistics() {

        if (statsManager == null) {

            System.out.println(
                    "StatsManager is not initialized."
            );

            return;

        }

        PlayerStats stats = statsManager.loadStats();

        System.out.println(
                "Loaded player = "
                        + stats.getPlayerName()
        );

        System.out.println(
                "Games = "
                        + stats.getGamesPlayed()
        );

        playerLabel.setText(
                "Player : "
                        + stats.getPlayerName()
        );

        gamesLabel.setText(
                "Games Played : "
                        + stats.getGamesPlayed()
        );

        winsLabel.setText(
                "Wins : "
                        + stats.getWins()
        );

        lossesLabel.setText(
                "Losses : "
                        + stats.getLosses()
        );

        int winRate = 0;

        if (stats.getGamesPlayed() > 0) {

            winRate =
                    (stats.getWins() * 100)
                    / stats.getGamesPlayed();

        }

        winRateLabel.setText(
                "Win Rate : "
                        + winRate
                        + "%"
        );

        bestTimeLabel.setText(
                "Best Time : "
                        + stats.getBestTime()
                        + " sec"
        );

        hintsLabel.setText(
                "Hints Used : "
                        + stats.getHintsUsed()
        );

    }

    /**
     * Loads background image
     */
    private void loadBackground() {

        URL url =
                getClass().getResource(
                        "/pictures/background.png"
                );

        if (url != null) {

            backgroundImage.setImage(
                    new Image(url.toExternalForm())
            );

        }

    }

    /**
     * Return to menu
     */
    @FXML
    private void goBack() {

        try {

            FXMLLoader loader =
                    new FXMLLoader(
                            getClass().getResource(
                                    "/fr/quentincillierre/hangman/application/menu-view.fxml"
                            )
                    );

            Parent root = loader.load();

            Stage stage =
                    (Stage) backButton.getScene().getWindow();

            stage.setScene(
                    new Scene(root, 1300, 800)
            );

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

}