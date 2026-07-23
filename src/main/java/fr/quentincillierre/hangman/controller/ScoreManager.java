package fr.quentincillierre.hangman.controller;

import fr.quentincillierre.hangman.model.Difficulty;

public class ScoreManager {

    /**
     * Calculates the player's final score.
     *
     * Formula:
     * Base Score (Difficulty)
     * + Remaining Time
     * + Lives Bonus
     * - Hint Penalty
     */
    public int calculateScore(
            Difficulty difficulty,
            int timeRemaining,
            int currentLives,
            int hintsUsed
    ) {

        if (difficulty == null) {
            difficulty = Difficulty.MEDIUM;
        }

        int score = difficulty.getBaseScore();

        // Time Bonus
        score += Math.max(0, timeRemaining);

        // Lives Bonus
        score += Math.max(0, currentLives) * 20;

        // Hint Penalty
        score -= Math.max(0, hintsUsed) * 30;

        return Math.max(score, 0);
    }

    /**
     * Returns the bonus earned from remaining lives.
     */
    public int calculateLivesBonus(int currentLives) {
        return Math.max(0, currentLives) * 20;
    }

    /**
     * Returns the bonus earned from remaining time.
     */
    public int calculateTimeBonus(int timeRemaining) {
        return Math.max(0, timeRemaining);
    }

    /**
     * Returns the penalty from hints used.
     */
    public int calculateHintPenalty(int hintsUsed) {
        return Math.max(0, hintsUsed) * 30;
    }

}