package fr.quentincillierre.hangman.model;

public class GameState {

    private Difficulty difficulty;
    private Category category;
    private String playerName;

    private int maxLives;
    private int currentLives;

    private int score;
    private int hintCount;

    private boolean paused;
    private boolean gameFinished;

    public GameState() {

        difficulty = Difficulty.MEDIUM;
        category = Category.RANDOM;
        playerName = "Guest";

        maxLives = difficulty.getLives();
        currentLives = maxLives;

        score = 0;
        hintCount = 0;

        paused = false;
        gameFinished = false;
    }

    // ==========================
    // Difficulty
    // ==========================

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {

        if (difficulty == null) {
            return;
        }

        this.difficulty = difficulty;

        this.maxLives = difficulty.getLives();
        this.currentLives = maxLives;
    }

    // ==========================
    // Category
    // ==========================

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {

        if (category != null) {
            this.category = category;
        }

    }

    // ==========================
    // Player
    // ==========================

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {

        if (playerName == null || playerName.isBlank()) {
            this.playerName = "Guest";
        } else {
            this.playerName = playerName.trim();
        }

    }

    // ==========================
    // Lives
    // ==========================

    public int getMaxLives() {
        return maxLives;
    }

    public int getCurrentLives() {
        return currentLives;
    }

    public void setCurrentLives(int currentLives) {
        this.currentLives = Math.max(0, Math.min(currentLives, maxLives));
    }

    public void loseLife() {

        if (currentLives > 0) {
            currentLives--;
        }

    }

    // ==========================
    // Score
    // ==========================

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = Math.max(score, 0);
    }

    // ==========================
    // Hints
    // ==========================

    public int getHintCount() {
        return hintCount;
    }

    public void setHintCount(int hintCount) {
        this.hintCount = Math.max(hintCount, 0);
    }

    public void useHint() {
        hintCount++;
    }

    // ==========================
    // Pause
    // ==========================

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    // ==========================
    // Game Finished
    // ==========================

    public boolean isGameFinished() {
        return gameFinished;
    }

    public void setGameFinished(boolean gameFinished) {
        this.gameFinished = gameFinished;
    }

    // ==========================
    // Reset
    // ==========================

    public void reset() {

        currentLives = maxLives;
        score = 0;
        hintCount = 0;
        paused = false;
        gameFinished = false;
    }

}