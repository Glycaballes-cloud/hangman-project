    package fr.quentincillierre.hangman.controller;

    import java.net.URL;
    import java.util.Optional;

import fr.quentincillierre.hangman.model.Category;
import fr.quentincillierre.hangman.model.Difficulty;
    import fr.quentincillierre.hangman.model.GameState;
    import fr.quentincillierre.hangman.model.HangmanModel;
    import fr.quentincillierre.hangman.model.PlayerStats;
    import fr.quentincillierre.hangman.model.StatsManager;
    import fr.quentincillierre.hangman.model.WordRepository;
    import javafx.animation.Animation;
    import javafx.animation.ScaleTransition;
    import javafx.application.Platform;
    import javafx.fxml.FXML;
    import javafx.fxml.FXMLLoader;
    import javafx.scene.Cursor;
    import javafx.scene.Parent;
    import javafx.scene.Scene;
    import javafx.scene.control.Alert;
    import javafx.scene.control.Button;
    import javafx.scene.control.ButtonType;
    import javafx.scene.control.DialogPane;
    import javafx.scene.control.Label;
    import javafx.scene.effect.DropShadow;
    import javafx.scene.image.Image;
    import javafx.scene.image.ImageView;
    import javafx.scene.layout.AnchorPane;
    import javafx.scene.layout.GridPane;
    import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

    public class GameController {

        //==========================
        // FXML COMPONENTS
        //==========================

        @FXML private ImageView backgroundImage;
        @FXML private ImageView titleImage;
        
        @FXML private ImageView timerPanelImage;
        @FXML private ImageView livesPanelImage;
        @FXML private ImageView restartButton;
        @FXML private ImageView resultPanelImage;
        @FXML
private ImageView restartOverlayButtonImage;

        @FXML
private AnchorPane overlayPane;

@FXML
private ImageView overlayImage;



@FXML
private Label overlayInfo;

@FXML
private ImageView continueButtonImage;

@FXML
private ImageView playAgainButtonImage;

@FXML
private ImageView leaveTownButtonImage;


        @FXML private ImageView hangmanImageView;
        @FXML private ImageView wrongLettersPanelImage;
        @FXML private ImageView hintButtonImage;

        @FXML private Label timerLabel;
        @FXML private Label livesLabel;
        @FXML private Label wordLabel;
        @FXML
    private Button musicButton;

    private boolean musicPlaying = true;
        @FXML private Label wrongLettersLabel;
        @FXML private Label categoryLabel;
        @FXML private Label resultLabel;
        @FXML private Label hintLabel;
        @FXML private Label playerNameLabel;
        @FXML
private ImageView pauseButtonImage;

@FXML
private ImageView quitButtonImage;



        @FXML private GridPane keyboardGrid;

    @FXML
    private void toggleMusic() {

        if (musicPlaying) {
            soundManager.pauseBackground();
            musicButton.setText("🔇");
        } else {
            soundManager.playBackground();
            musicButton.setText("🔊");
        }

        musicPlaying = !musicPlaying;
    }

        //==========================
        // MANAGERS
        //==========================

        private TimerManager timerManager;
        private KeyboardManager keyboardManager;
        private HintManager hintManager;
        private ScoreManager scoreManager;
        private UIManager uiManager;
        private final AnimationManager animationManager = new AnimationManager();


        //==========================
        // MODEL
        //==========================

        private GameState gameState;
        private HangmanModel model;

        //==========================
        // STATS
        //==========================

        private StatsManager statsManager;
        private PlayerStats playerStats;
        private SoundManager soundManager;

        //==========================
        // VARIABLES
        //==========================
        private Animation timerPulse;
        private Difficulty currentDifficulty = Difficulty.MEDIUM;
        private Category currentCategory = Category.RANDOM;
        private String playerName = "Guest";

        private int score;
        private boolean paused;
        private boolean gameFinished;
private void addHoverEffect(ImageView image) {
    
    if (image == null) {
        return;
    }

    image.setCursor(Cursor.HAND);

    DropShadow glow = new DropShadow();
    glow.setColor(Color.GOLD);
    glow.setRadius(20);

    image.setOnMouseEntered(e -> {

        soundManager.playHover();

        ScaleTransition st =
                new ScaleTransition(Duration.millis(150), image);

        st.setToX(1.08);
        st.setToY(1.08);
        st.play();

        image.setEffect(glow);

    });

    image.setOnMouseExited(e -> {

        ScaleTransition st =
                new ScaleTransition(Duration.millis(150), image);

        st.setToX(1);
        st.setToY(1);
        st.play();

        image.setEffect(null);

    });
}

@FXML
public void initialize() {

    soundManager = new SoundManager();

    addHoverEffect(pauseButtonImage);
    addHoverEffect(quitButtonImage);
    addHoverEffect(restartButton);
    addHoverEffect(hintButtonImage);

    timerManager = new TimerManager(currentDifficulty.getTime());
    keyboardManager = new KeyboardManager(keyboardGrid);
    hintManager = new HintManager();
    scoreManager = new ScoreManager();

    soundManager.playBackground();

    timerPulse = animationManager.pulse(timerLabel);

    uiManager = new UIManager(
            wordLabel,
            wrongLettersLabel,
            resultLabel,
            timerLabel,
            livesLabel,
            hintLabel,
            categoryLabel,
            hangmanImageView
    );

    gameState = new GameState();

    loadImages();

    setupButtons();

    paused = false;
    gameFinished = false;
    continueButtonImage.setVisible(false);
    restartOverlayButtonImage.setVisible(false);
    leaveTownButtonImage.setVisible(false);
    playAgainButtonImage.setVisible(false);
}

        //==========================
        // SETTERS
        //==========================

        public void setPlayerName(String name) {

            if (name == null || name.isBlank()) {
                playerName = "Guest";
            } else {
                playerName = name;
            }

            statsManager = new StatsManager(playerName);

            playerStats = statsManager.loadStats();

            if (playerStats == null) {
                playerStats = new PlayerStats();
                playerStats.setPlayerName(playerName);
            }

            playerNameLabel.setText("Player: " + playerName);
        }

        public void setDifficulty(Difficulty difficulty) {

            if (difficulty != null) {
                currentDifficulty = difficulty;
            }

        }

        public void setCategory(Category category) {

            if (category != null) {
                currentCategory = category;
            }

        }

        public void handleKeyboardInput(String key) {

        if (gameFinished || paused || model == null) {
            return;
        }

        if (key == null || key.isEmpty()) {
            return;
        }

        char letter = Character.toUpperCase(key.charAt(0));

        if (letter >= 'A' && letter <= 'Z') {
            keyboardManager.pressLetter(letter);
        }
    }
        //==========================
        // IMAGE LOADING
        //==========================

        private void loadImages() {
            loadImage(playAgainButtonImage, "/pictures/playagain-button.png");
            loadImage(restartOverlayButtonImage, "/pictures/restart-panel.png");
                loadImage(leaveTownButtonImage, "/pictures/leave-town-button.png");
            loadImage(continueButtonImage, "/pictures/continue-button.png");
            loadImage(backgroundImage, "/pictures/background.png");
            loadImage(titleImage, "/pictures/hangman-title.png");
            loadImage(timerPanelImage, "/pictures/timer-panel.png");
            loadImage(livesPanelImage, "/pictures/lives-panel.png");
            loadImage(restartButton, "/pictures/restart-panel.png");
            loadImage(resultPanelImage, "/pictures/result-panel.jpg");
            loadImage(wrongLettersPanelImage, "/pictures/wrongletters-panel.png");
            loadImage(hintButtonImage, "/pictures/hint-button.png");
            loadImage(pauseButtonImage, "/pictures/pause-button.png");
            loadImage(quitButtonImage, "/pictures/quit-button.png");

        }
      private void showPauseOverlay() {
    overlayPane.setVisible(true);
    loadImage(overlayImage, "/pictures/pause-overlay.png");
    playAgainButtonImage.setVisible(false);
    continueButtonImage.setVisible(true);
    restartOverlayButtonImage.setVisible(true);
    leaveTownButtonImage.setVisible(true);
}
private void showVictoryOverlay() {

    overlayPane.setVisible(true);

    loadImage(
            overlayImage,
            "/pictures/victory-overlay.png"
    );

    continueButtonImage.setVisible(false);
    restartOverlayButtonImage.setVisible(false);

    playAgainButtonImage.setVisible(true);
    leaveTownButtonImage.setVisible(true);

}
private void showGameOverOverlay() {

    overlayPane.setVisible(true);

    loadImage(
            overlayImage,
            "/pictures/gameover-overlay.png"
    );

    continueButtonImage.setVisible(false);
    restartOverlayButtonImage.setVisible(false);

    playAgainButtonImage.setVisible(true);
    leaveTownButtonImage.setVisible(true);
}
private void showTimeUpOverlay() {

    overlayPane.setVisible(true);

    loadImage(
            overlayImage,
            "/pictures/timeout-overlay.png"
    );

    continueButtonImage.setVisible(false);
    restartOverlayButtonImage.setVisible(false);

    playAgainButtonImage.setVisible(true);
    leaveTownButtonImage.setVisible(true);
}
private void hideOverlay() {

    overlayPane.setVisible(false);

    continueButtonImage.setVisible(false);
    restartOverlayButtonImage.setVisible(false);
    leaveTownButtonImage.setVisible(false);

}
        private void loadImage(ImageView imageView, String path) {

            URL url = getClass().getResource(path);

            if (url != null) {
                imageView.setImage(new Image(url.toExternalForm()));
            }

        }
        

        //==========================
        // BUTTONS
        //==========================

     private void setupButtons() {
        
        playAgainButtonImage.setOnMouseClicked(e -> {

    soundManager.playClick();

    hideOverlay();

    restartGame();

});
        leaveTownButtonImage.setOnMouseClicked(e -> {

    soundManager.playClick();

    hideOverlay();

    quitGame();

});
        restartOverlayButtonImage.setOnMouseClicked(e -> {

    soundManager.playClick();

    hideOverlay();

    restartGame();

});

        continueButtonImage.setOnMouseClicked(e -> {

    soundManager.playClick();

    pauseGame();

});

   restartButton.setOnMouseClicked(e -> {
    soundManager.playClick();
    showRestartOverlay();
});

    hintButtonImage.setOnMouseClicked(e -> {
        soundManager.playClick();
        useHint();
    });


    pauseButtonImage.setOnMouseClicked(e -> {
        soundManager.playClick();
        pauseGame();
    });


    quitButtonImage.setOnMouseClicked(e -> {
        soundManager.playClick();
        quitGame();
    });

}
       private void showRestartOverlay() {

    overlayPane.setVisible(true);

    loadImage(
        overlayImage,
        "/pictures/restart-overlay.png"
    );


    continueButtonImage.setVisible(false);
    playAgainButtonImage.setVisible(false);

    restartOverlayButtonImage.setVisible(true);
    leaveTownButtonImage.setVisible(true);
}

        //==========================
        // START GAME
        //==========================

        public void startGame() {

            startNewGame();

        }

        private void startNewGame() {

            timerManager.stop();

            paused = false;
            gameFinished = false;
            score = 0;

            timerPulse.stop();
    timerLabel.setScaleX(1);
    timerLabel.setScaleY(1);
    timerLabel.setTextFill(Color.BLACK);
            hintManager.reset();

            playerNameLabel.setText("Player: " + playerName);

            WordRepository repository = new WordRepository();

            String word = repository.getRandomWord(currentCategory);

            model = new HangmanModel(
                    word,
                    currentDifficulty.getLives()
            );

            categoryLabel.setText(
                    "Category: "
                            + repository.getLastCategory().getDisplayName()
            );

        keyboardManager.generateKeyboard(letter -> {

        int wrongsBefore = model.getCurrentWrongs();

        model.tryLetter(letter);

    if (model.getCurrentWrongs() > wrongsBefore) {

        soundManager.playWrong();

        animationManager.shake(hangmanImageView);
        animationManager.flash(wordLabel);

    } else {

        soundManager.playCorrect();

        animationManager.pop(wordLabel);
    }
        refreshUI();

    });

            keyboardGrid.setDisable(false);

            hintLabel.setText("");

            hintButtonImage.setVisible(false);

            refreshUI();

            startTimer();

            Platform.runLater(() -> {

        Scene scene = keyboardGrid.getScene();

        if (scene != null) {

            scene.setOnKeyPressed(event -> {
                handleKeyboardInput(event.getText());
            });

            scene.getRoot().requestFocus();
        }
    });

        }
            //==========================
        // TIMER
        //==========================

        private void startTimer() {
            timerManager.reset(currentDifficulty.getTime());
            timerManager.start(
        () -> {
            timerLabel.setText(
                String.format(
                    "%02d:%02d",
                    timerManager.getTimeRemaining() / 60,
                    timerManager.getTimeRemaining() % 60
                )
            );
            if (timerManager.getTimeRemaining() <= 10
        && timerPulse.getStatus() != Animation.Status.RUNNING) {

    timerPulse.play();

    timerLabel.setTextFill(Color.RED);
}
        },
        () -> {
            if (!gameFinished) {
                gameFinished = true;
                keyboardGrid.setDisable(true);

                calculateScore();

                overlayInfo.setText(
        "The Word Was:\n"
        + model.getWordToGuess()
);

                showTimeUpOverlay();

saveGameStats(false);
            }
        }
    );

            timerLabel.setText(

                    String.format(
                            "%02d:%02d",
                            timerManager.getTimeRemaining() / 60,
                            timerManager.getTimeRemaining() % 60));

        }

        //==========================
        // RESTART
        //==========================

    @FXML
    private void restartGame() {

        timerPulse.stop();

timerLabel.setScaleX(1);
timerLabel.setScaleY(1);
timerLabel.setTextFill(Color.BROWN);

        animationManager.fadeOut(hangmanImageView);

        startNewGame();

        animationManager.fadeIn(hangmanImageView);

    }

        //==========================
        // PAUSE
        //==========================

     @FXML
private void pauseGame() {

    if (soundManager != null) {
        soundManager.playClick();
    }


    if (!paused) {

        // PAUSE GAME
        paused = true;

        if (timerManager != null) {
            timerManager.pause();
        }

        keyboardGrid.setDisable(true);
        showPauseOverlay();

        // Change pause image to resume image
        loadImage(
            pauseButtonImage,
            "/pictures/resume-button.png"
        );

        overlayInfo.setText("");


        // Make button slightly transparent
        pauseButtonImage.setOpacity(0.75);


    } else {


        // RESUME GAME
        paused = false;

        if (timerManager != null) {
            timerManager.resume();
        }

        keyboardGrid.setDisable(false);
        hideOverlay();


        // Change resume image back to pause image
        loadImage(
            pauseButtonImage,
            "/pictures/pause-button.png"
        );

        overlayInfo.setText("");

        hideOverlay();


        // Restore opacity
        pauseButtonImage.setOpacity(1.0);

    }

}

        //==========================
        // QUIT
        //==========================

    @FXML
    private void quitGame() {
        soundManager.playClick();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle("Quit Game");
        alert.setHeaderText("Quit Current Game");
        alert.setContentText("Are you sure you want to quit?");

        // Apply custom CSS to dialog
        DialogPane dialogPane = alert.getDialogPane();

        dialogPane.getStylesheets().add(
                getClass().getResource("/style.css").toExternalForm()
        );

        dialogPane.getStyleClass().add("quit-dialog");

        Optional<ButtonType> result =
                alert.showAndWait();

        if (result.isPresent()
                && result.get() == ButtonType.OK) {

            timerManager.stop();

            try {

                FXMLLoader loader =
                        new FXMLLoader(
                                getClass().getResource(
                                        "/fr/quentincillierre/hangman/application/menu-view.fxml"
                                ));

                Parent root = loader.load();

                Stage stage =
                       (Stage) quitButtonImage.getScene().getWindow();

                stage.setScene(
                        new Scene(root, 1300, 800)
                );

            } catch (Exception e) {

                e.printStackTrace();

            }
        }
    }

        //==========================
        // HINT
        //==========================

        @FXML
        private void useHint() {

            if (hintManager.useHint(model)) {

                hintLabel.setText("Hint used!");

                hintButtonImage.setVisible(false);

                refreshUI();

            }

        }

        //==========================
        // REFRESH UI
        //==========================

     private void refreshUI() {

    if (model == null)
        return;

    wordLabel.setText(
            model.getHiddenWord());

    wrongLettersLabel.setText(
            model.getWrongLetters());

    int lives =
            currentDifficulty.getLives()
                    - model.getCurrentWrongs();

    StringBuilder hearts =
            new StringBuilder();

    for (int i = 0; i < lives; i++) {

        hearts.append("♥ ");

    }

    livesLabel.setText(
            hearts.toString());

    if (model.getCurrentWrongs() >= 4
        && hintManager.canUseHint()) {

        hintButtonImage.setVisible(true);

    }

    if (!gameFinished
            && (model.isWin() || model.isLose())) {

        gameFinished = true;

        timerManager.stop();

        keyboardGrid.setDisable(true);

        // FORCE LAST HANGMAN IMAGE
        uiManager.updateHangmanImage(model);

        wordLabel.setText(
                model.getWordToGuess());

        calculateScore();

        if (model.isWin()) {

            animationManager.jump(hangmanImageView);

            soundManager.playWin();

       
            showVictoryOverlay();

            saveGameStats(true);

        } else {

    animationManager.swing(hangmanImageView);

    soundManager.playLose();

    overlayInfo.setText(
        "The Word Was:\n"
        + model.getWordToGuess()
      
);

    showGameOverOverlay();
    saveGameStats(false);

}

        // game just ended — stop the timer pulse/red warning
        timerPulse.stop();

    } else {

        uiManager.updateHangmanImage(model);

    }

}    //==========================
        // SCORE
        //==========================

        private void calculateScore() {

            int lives =
                    currentDifficulty.getLives()
                            - model.getCurrentWrongs();

            score = scoreManager.calculateScore(

                    currentDifficulty,

                    timerManager.getTimeRemaining(),

                    lives,

                    hintManager.getHintsUsed()

            );

        }

        //==========================
        // SAVE PLAYER STATS
        //==========================

        private void saveGameStats(boolean won) {

            if (playerStats == null) {

                playerStats = new PlayerStats();

                playerStats.setPlayerName(playerName);

            }

            playerStats.setGamesPlayed(

                    playerStats.getGamesPlayed() + 1

            );

            if (won) {

                playerStats.setWins(

                        playerStats.getWins() + 1

                );

            } else {

                playerStats.setLosses(

                        playerStats.getLosses() + 1

                );

            }

            playerStats.setHintsUsed(

                    playerStats.getHintsUsed()
                            + hintManager.getHintsUsed()

            );

             if (won && (playerStats.getBestTime() == 0
                    || timerManager.getTimeRemaining()
                    >= playerStats.getBestTime()) ){

                playerStats.setBestTime(

                        timerManager.getTimeRemaining()

                );

            }

            statsManager.saveStats(playerStats);

        }

    }