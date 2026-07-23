package fr.quentincillierre.hangman.controller;

import fr.quentincillierre.hangman.model.HangmanModel;

public class HintManager {

    private int hintsUsed;
    private int maxHints;

    public HintManager() {
        this(1);
    }

    public HintManager(int maxHints) {

        this.maxHints = Math.max(0, maxHints);
        this.hintsUsed = 0;

    }

    // =====================================
    // Hint Logic
    // =====================================

    public boolean useHint(HangmanModel model) {

        if (model == null) {
            return false;
        }

        if (!canUseHint()) {
            return false;
        }

        if (model.isWin() || model.isLose()) {
            return false;
        }

        String word = model.getWordToGuess();

        for (char letter : word.toCharArray()) {

            if (!model.getGuessedLetter().contains(letter)) {

                model.tryLetter(letter);

                hintsUsed++;

                return true;

            }

        }

        return false;
    }

    // =====================================
    // Hint Availability
    // =====================================

    public boolean canUseHint() {

        return hintsUsed < maxHints;

    }

    public boolean isHintAvailable() {

        return canUseHint();

    }

    // =====================================
    // Getters
    // =====================================

    public int getHintsUsed() {

        return hintsUsed;

    }

    public int getRemainingHints() {

        return Math.max(0, maxHints - hintsUsed);

    }

    public int getMaxHints() {

        return maxHints;

    }

    // =====================================
    // Setters
    // =====================================

    public void setMaxHints(int maxHints) {

        this.maxHints = Math.max(0, maxHints);

        if (hintsUsed > this.maxHints) {
            hintsUsed = this.maxHints;
        }

    }

    // =====================================
    // Reset
    // =====================================

    public void reset() {

        hintsUsed = 0;

    }

}