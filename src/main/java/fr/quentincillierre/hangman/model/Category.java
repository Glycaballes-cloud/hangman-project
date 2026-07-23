package fr.quentincillierre.hangman.model;

public enum Category {

    ANIMALS("Animals"),
    TECHNOLOGY("Technology"),
    FRUITS("Fruits"),
    COUNTRIES("Countries"),
    SPORTS("Sports"),
    RANDOM("Random");

    private final String displayName;

    Category(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}