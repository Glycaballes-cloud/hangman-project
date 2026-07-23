package fr.quentincillierre.hangman.controller;

import java.util.function.Consumer;

import javafx.animation.ScaleTransition;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

public class KeyboardManager {

    private final GridPane keyboardGrid;

    public KeyboardManager(GridPane keyboardGrid) {
        this.keyboardGrid = keyboardGrid;
    }

    // =====================================
    // Generate Keyboard
    // =====================================

    public void generateKeyboard(Consumer<Character> onLetterPressed) {

        keyboardGrid.getChildren().clear();

        keyboardGrid.setHgap(8);
        keyboardGrid.setVgap(8);

        for (char letter = 'A'; letter <= 'Z'; letter++) {

            Button button = new Button(String.valueOf(letter));

            button.setPrefSize(48, 48);
            button.setFocusTraversable(false);
            button.setStyle(normalStyle());

            final char currentLetter = letter;

    button.setOnAction(event -> {

    button.setDisable(true);

    ScaleTransition press = new ScaleTransition(Duration.millis(100), button);

    press.setToX(0.85);
    press.setToY(0.85);
    press.setAutoReverse(true);
    press.setCycleCount(2);

    press.setOnFinished(e -> {

        onLetterPressed.accept(currentLetter);
        button.setStyle(disabledStyle());

    });

    press.play();

});

button.setOnMouseEntered(e -> {

    ScaleTransition hover = new ScaleTransition(Duration.millis(120), button);
    hover.setToX(1.08);
    hover.setToY(1.08);
    hover.play();

});

button.setOnMouseExited(e -> {

    ScaleTransition hover = new ScaleTransition(Duration.millis(120), button);
    hover.setToX(1.0);
    hover.setToY(1.0);
    hover.play();

});

            int index = letter - 'A';

            keyboardGrid.add(button, index % 13, index / 13);
        }
    }

    // =====================================
    // Physical Keyboard Support
    // =====================================

    public void pressLetter(char letter) {

        letter = Character.toUpperCase(letter);

        for (Node node : keyboardGrid.getChildren()) {

            if (node instanceof Button button
                    && !button.isDisable()
                    && button.getText().charAt(0) == letter) {

                button.fire();
                return;
            }
        }
    }

    // =====================================
    // Disable One Letter
    // =====================================

    public void disableLetter(char letter) {

        letter = Character.toUpperCase(letter);

        for (Node node : keyboardGrid.getChildren()) {

            if (node instanceof Button button
                    && button.getText().charAt(0) == letter) {

                button.setDisable(true);
                button.setStyle(disabledStyle());
                return;
            }
        }
    }

    // =====================================
    // Enable All Letters
    // =====================================

    public void enableAll() {

        keyboardGrid.setDisable(false);

        for (Node node : keyboardGrid.getChildren()) {

            if (node instanceof Button button) {

                button.setDisable(false);
                button.setStyle(normalStyle());
            }
        }
    }

    // =====================================
    // Disable Entire Keyboard
    // =====================================

    public void disableKeyboard() {
        keyboardGrid.setDisable(true);
    }

    // =====================================
    // Enable Entire Keyboard
    // =====================================

    public void enableKeyboard() {
        keyboardGrid.setDisable(false);
    }

    // =====================================
    // Reset Keyboard
    // =====================================

    public void reset() {
        enableAll();
    }

    // =====================================
    // Styles
    // =====================================

    private String normalStyle() {

        return "-fx-background-color: linear-gradient(#F9E7B5,#D6A35A);"
                + "-fx-border-color:#6B3E16;"
                + "-fx-border-width:2;"
                + "-fx-border-radius:10;"
                + "-fx-background-radius:10;"
                + "-fx-font-size:18;"
                + "-fx-font-weight:bold;"
                + "-fx-text-fill:#3B2200;";
    }

    private String disabledStyle() {

        return "-fx-background-color:#8C8C8C;"
                + "-fx-border-color:#555555;"
                + "-fx-border-width:2;"
                + "-fx-border-radius:10;"
                + "-fx-background-radius:10;"
                + "-fx-font-size:18;"
                + "-fx-font-weight:bold;"
                + "-fx-text-fill:white;";
    }
}