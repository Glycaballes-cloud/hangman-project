package fr.quentincillierre.hangman.model;

public enum Difficulty {

    EASY(90, 10, 1, 100),
    MEDIUM(60, 8, 1, 200),
    HARD(30, 6, 0, 300);

    private final int time;
    private final int lives;
    private final int hints;
    private final int baseScore;

    Difficulty(int time, int lives, int hints, int baseScore) {
        this.time = time;
        this.lives = lives;
        this.hints = hints;
        this.baseScore = baseScore;
    }

    public int getTime() {
        return time;
    }

    public int getLives() {
        return lives;
    }

    public int getHints() {
        return hints;
    }

    public int getBaseScore() {
        return baseScore;
    }

    @Override
    public String toString() {
        String name = name().toLowerCase();
        return Character.toUpperCase(name.charAt(0)) + name.substring(1);
    }
}