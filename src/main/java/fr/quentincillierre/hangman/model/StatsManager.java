package fr.quentincillierre.hangman.model;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class StatsManager {

    private final File statsFile;

    public StatsManager(String playerName) {

        File folder = new File("stats");

        if (!folder.exists()) {
            folder.mkdirs();
        }

        if (playerName == null || playerName.isBlank()) {
            playerName = "Guest";
        }

        statsFile = new File(folder, playerName.trim() + ".txt");
    }

    // =====================================
    // Save Statistics
    // =====================================

    public void saveStats(PlayerStats stats) {

        if (stats == null) {
            return;
        }

        try (PrintWriter writer = new PrintWriter(statsFile, StandardCharsets.UTF_8)) {

            writer.println(stats.getPlayerName());
            writer.println(stats.getGamesPlayed());
            writer.println(stats.getWins());
            writer.println(stats.getLosses());
            writer.println(stats.getBestTime());
            writer.println(stats.getHintsUsed());

        } catch (IOException e) {

            System.err.println("Unable to save statistics.");

            e.printStackTrace();
        }

    }

    // =====================================
    // Load Statistics
    // =====================================

    public PlayerStats loadStats() {

        PlayerStats stats = new PlayerStats();

        if (!statsFile.exists()) {

            stats.setPlayerName(
                    statsFile.getName().replace(".txt", "")
            );

            return stats;
        }

        try {

            List<String> lines = Files.readAllLines(
                    statsFile.toPath(),
                    StandardCharsets.UTF_8
            );

            if (lines.size() >= 6) {

                stats.setPlayerName(lines.get(0));

                stats.setGamesPlayed(Integer.parseInt(lines.get(1)));

                stats.setWins(Integer.parseInt(lines.get(2)));

                stats.setLosses(Integer.parseInt(lines.get(3)));

                stats.setBestTime(Integer.parseInt(lines.get(4)));

                stats.setHintsUsed(Integer.parseInt(lines.get(5)));

            }

        } catch (Exception e) {

            System.err.println("Unable to load statistics.");

            e.printStackTrace();

        }

        return stats;

    }

    // =====================================
    // Delete Statistics
    // =====================================

    public boolean deleteStats() {

        return statsFile.exists() && statsFile.delete();

    }

    // =====================================
    // Check if Save Exists
    // =====================================

    public boolean hasStatistics() {

        return statsFile.exists();

        

    }
    public static List<PlayerStats> loadAllStats() {

    List<PlayerStats> players = new ArrayList<>();

    File folder = new File("stats");

    File[] files = folder.listFiles(
            (dir, name) -> name.endsWith(".txt")
    );

    if (files == null) {
        return players;
    }

    for (File file : files) {

        String playerName =
                file.getName().replace(".txt", "");

        StatsManager manager =
                new StatsManager(playerName);

        players.add(manager.loadStats());
    }

    return players;
}
}
