package fr.quentincillierre.hangman.controller;

import java.net.URL;
import java.util.List;

import fr.quentincillierre.hangman.model.LeaderboardEntry;
import fr.quentincillierre.hangman.model.PlayerStats;
import fr.quentincillierre.hangman.model.StatsManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class LeaderboardController {

    @FXML
    private TableView<LeaderboardEntry> leaderboardTable;

    @FXML
private ImageView backgroundImage;

    private final ObservableList<LeaderboardEntry> data =
            FXCollections.observableArrayList();

            @FXML
private Button backButton;

@FXML
private TableColumn<LeaderboardEntry, Integer> rankColumn;

@FXML
private TableColumn<LeaderboardEntry, String> playerColumn;

@FXML
private TableColumn<LeaderboardEntry, Integer> winsColumn;

@FXML
private TableColumn<LeaderboardEntry, Integer> gamesColumn;

@FXML
private TableColumn<LeaderboardEntry, Integer> winRateColumn;
private final SoundManager soundManager = SoundManager.getInstance();

@FXML
private void goBack() {
        soundManager.playClick();
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

    @FXML
    public void initialize() {
        URL url =
        getClass().getResource("/pictures/background.png");

        rankColumn.setCellValueFactory(
        new PropertyValueFactory<>("rank"));

playerColumn.setCellValueFactory(
        new PropertyValueFactory<>("player"));

winsColumn.setCellValueFactory(
        new PropertyValueFactory<>("wins"));

gamesColumn.setCellValueFactory(
        new PropertyValueFactory<>("games"));

winRateColumn.setCellValueFactory(
        new PropertyValueFactory<>("winRate"));

if (url != null) {
    backgroundImage.setImage(
            new Image(url.toExternalForm())
    );
}
        List<PlayerStats> players =
                StatsManager.loadAllStats();

        players.sort(
                (a, b) ->
                        Integer.compare(
                                b.getWins(),
                                a.getWins()
                        )
        );

        int rank = 1;

        for (PlayerStats player : players) {

            data.add(
                    new LeaderboardEntry(
                            rank++,
                            player.getPlayerName(),
                            player.getWins(),
                            player.getGamesPlayed(),
                            player.getWinRate()
                    )
            );
        }

        leaderboardTable.setItems(data);
    }
}