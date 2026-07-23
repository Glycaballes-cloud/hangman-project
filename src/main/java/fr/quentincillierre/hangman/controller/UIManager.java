package fr.quentincillierre.hangman.controller;

import java.net.URL;

import fr.quentincillierre.hangman.model.HangmanModel;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class UIManager {

    private final Label wordLabel;
    private final Label wrongLettersLabel;
    private final Label resultLabel;
    private final Label timerLabel;
    private final Label livesLabel;
    private final Label hintLabel;
    private final Label categoryLabel;
    private final ImageView hangmanImageView;

    public UIManager(
        
            Label wordLabel,
            Label wrongLettersLabel,
            Label resultLabel,
            Label timerLabel,
            Label livesLabel,
            Label hintLabel,
            Label categoryLabel,
            ImageView hangmanImageView
    ) {

        this.wordLabel = wordLabel;
        this.wrongLettersLabel = wrongLettersLabel;
        this.resultLabel = resultLabel;
        this.timerLabel = timerLabel;
        this.livesLabel = livesLabel;
        this.hintLabel = hintLabel;
        this.categoryLabel = categoryLabel;
        this.hangmanImageView = hangmanImageView;

    }
    

    // =====================================
    // Word
    // =====================================

    public void updateWord(HangmanModel model) {

        if (model != null) {
            wordLabel.setText(model.getHiddenWord());
        }

    }

    // =====================================
    // Wrong Letters
    // =====================================

    public void updateWrongLetters(HangmanModel model) {

        if (model != null) {
            wrongLettersLabel.setText(model.getWrongLetters());
        }

    }

    // =====================================
    // Hangman Image
    // =====================================
// =====================================
// Hangman Image
// =====================================

public void updateHangmanImage(HangmanModel model) {

    if (model == null) {
        return;
    }

    int imageStage =
            (model.getCurrentWrongs() * 10)
            / model.getMaxWrongs();

    // Force full hangman when player loses
    if (model.isLose()) {
        imageStage = 10;
    }

    URL imageURL =
            getClass().getResource(
                    "/pictures/"
                            + imageStage
                            + "-hangman.png");

    if (imageURL != null) {

        hangmanImageView.setImage(
                new Image(
                        imageURL.toExternalForm()
                )
        );

    }

}

    // =====================================
    // Timer
    // =====================================

    public void updateTimer(String formattedTime) {

        timerLabel.setText(formattedTime);

    }

    // =====================================
    // Lives
    // =====================================

    public void updateLives(int lives) {

        livesLabel.setText("Lives\n" + lives);

    }

    // =====================================
    // Hint
    // =====================================

    public void updateHint(String hint) {

        hintLabel.setText(hint);

    }

    // =====================================
    // Category
    // =====================================

    public void updateCategory(String category) {

        categoryLabel.setText(category);

    }

    // =====================================
    // Result
    // =====================================

    public void showPlaying() {

        resultLabel.setText("Playing...");

    }

    public void showWin() {

        resultLabel.setText("YOU\nWIN!");

    }

    

    public void showLose() {

        resultLabel.setText("GAME\nOVER");

    }

    // =====================================
    // Refresh Entire UI
    // =====================================

    public void refresh(
            HangmanModel model,
            int lives,
            String formattedTime
    ) {

        updateWord(model);

        updateWrongLetters(model);

        updateHangmanImage(model);

        updateLives(lives);

        updateTimer(formattedTime);

    }

}