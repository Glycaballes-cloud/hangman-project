package fr.quentincillierre.hangman.model;

public class PlayerStats {

    private String playerName;
    private int gamesPlayed;
    private int wins;
    private int losses;
    private int bestTime;
    private int hintsUsed;

    public PlayerStats() {
        this("Guest");
    }

    public PlayerStats(String playerName) {
        this.playerName = (playerName == null || playerName.isBlank())
                ? "Guest"
                : playerName.trim();

        this.gamesPlayed = 0;
        this.wins = 0;
        this.losses = 0;
        this.bestTime = 0;
        this.hintsUsed = 0;
    }

    // ==========================
    // Player Name
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
    // Games Played
    // ==========================

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = Math.max(0, gamesPlayed);
    }

    // ==========================
    // Wins
    // ==========================

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = Math.max(0, wins);
    }

    // ==========================
    // Losses
    // ==========================

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = Math.max(0, losses);
    }

    // ==========================
    // Best Time
    // ==========================

    public int getBestTime() {
        return bestTime;
    }

    public void setBestTime(int bestTime) {
        this.bestTime = Math.max(0, bestTime);
    }

    // ==========================
    // Hints Used
    // ==========================

    public int getHintsUsed() {
        return hintsUsed;
    }

    public void setHintsUsed(int hintsUsed) {
        this.hintsUsed = Math.max(0, hintsUsed);
    }

    // ==========================
    // Helper Methods
    // ==========================

    public void addWin() {
        wins++;
        gamesPlayed++;
    }

    public void addLoss() {
        losses++;
        gamesPlayed++;
    }

    public void addHintsUsed(int hints) {
        if (hints > 0) {
            hintsUsed += hints;
        }
    }

    public void updateBestTime(int seconds) {

        if (seconds <= 0) {
            return;
        }

        if (bestTime == 0 || seconds < bestTime) {
            bestTime = seconds;
        }
    }

    public int getWinRate() {

        if (gamesPlayed == 0) {
            return 0;
        }

        return (wins * 100) / gamesPlayed;
    }

}