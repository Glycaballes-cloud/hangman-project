package fr.quentincillierre.hangman.model;

import java.util.LinkedHashSet;
import java.util.Set;

public class HangmanModel {

    private final String wordToGuess;
    private final int maxWrongs;

    private int currentWrongs;

    private final Set<Character> guessedLetter;

    public HangmanModel(String wordToGuess, int maxWrongs) {

        if (wordToGuess == null || wordToGuess.isBlank()) {
            wordToGuess = "COMPUTER";
        }

        this.wordToGuess = wordToGuess.trim().toUpperCase();
        this.maxWrongs = maxWrongs;
        this.currentWrongs = 0;
        this.guessedLetter = new LinkedHashSet<>();
    }

    // ==========================
    // Getters
    // ==========================

    public String getWordToGuess() {
        return wordToGuess;
    }

    public int getCurrentWrongs() {
        return currentWrongs;
    }

    public int getMaxWrongs() {
        return maxWrongs;
    }

    public Set<Character> getGuessedLetter() {
        return guessedLetter;
    }

    // ==========================
    // Game Logic
    // ==========================

    public boolean tryLetter(Character letter) {

        if (letter == null) {
            return false;
        }

        letter = Character.toUpperCase(letter);

        // Already guessed
        if (guessedLetter.contains(letter)) {
            return false;
        }

        guessedLetter.add(letter);

        if (!wordToGuess.contains(letter.toString())) {
            currentWrongs++;
            return false;
        }

        return true;
    }

    // ==========================
    // Hidden Word
    // ==========================

    public String getHiddenWord() {

        StringBuilder hiddenWord = new StringBuilder();

        for (char letter : wordToGuess.toCharArray()) {

            if (guessedLetter.contains(letter)) {
                hiddenWord.append(letter);
            } else {
                hiddenWord.append('_');
            }

        }

        return hiddenWord.toString();
    }

    // ==========================
    // Wrong Letters
    // ==========================

    public String getWrongLetters() {

        StringBuilder builder = new StringBuilder();

        int count = 0;

        for (Character letter : guessedLetter) {

            if (!wordToGuess.contains(letter.toString())) {

                builder.append(letter).append(" ");

                count++;

                if (count % 5 == 0) {
                    builder.append("\n");
                }

            }

        }

        return builder.toString().trim();
    }

    // ==========================
    // Hint
    // ==========================

    public String getHint() {

        switch (wordToGuess) {

            case "JAVA":
                return "Programming language";

            case "SANDBANK":
                return "Found near the sea.";

            case "COMPUTER":
                return "Electronic device.";

            case "ELEPHANT":
                return "Largest land animal.";

            default:
                return "";
        }

    }

    // ==========================
    // Win / Lose
    // ==========================

    public boolean isWin() {

        for (char letter : wordToGuess.toCharArray()) {

            if (!guessedLetter.contains(letter)) {
                return false;
            }

        }

        return true;
    }

    public boolean isLose() {
        return currentWrongs >= maxWrongs;
    }

    // ==========================
    // Reset
    // ==========================

    public void reset() {

        guessedLetter.clear();
        currentWrongs = 0;

    }

}