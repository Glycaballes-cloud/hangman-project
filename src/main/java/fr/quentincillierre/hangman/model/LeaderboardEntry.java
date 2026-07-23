package fr.quentincillierre.hangman.model;

public class LeaderboardEntry {

    

    private int rank;
    private String player;
    private int wins;
    private int games;
    private int winRate;

    public LeaderboardEntry(
            int rank,
            String player,
            int wins,
            int games,
            int winRate) {

        this.rank = rank;
        this.player = player;
        this.wins = wins;
        this.games = games;
        this.winRate = winRate;
    }

    public int getRank() {
        return rank;
    }

    public String getPlayer() {
        return player;
    }

    public int getWins() {
        return wins;
    }

    public int getGames() {
        return games;
    }

    public int getWinRate() {
        return winRate;
    }
}