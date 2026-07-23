package fr.quentincillierre.hangman.model;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WordRepository {

    private final Random random = new Random();

    private Category lastCategory = Category.RANDOM;

    /**
     * Returns a random word from the selected category.
     */
    public String getRandomWord(Category category) {

        if (category == null) {
            category = Category.RANDOM;
        }

        // If RANDOM is selected, randomly choose a category
        if (category == Category.RANDOM) {

            Category[] categories = {
                    Category.ANIMALS,
                    Category.TECHNOLOGY,
                    Category.FRUITS,
                    Category.COUNTRIES,
                    Category.SPORTS
            };

            category = categories[random.nextInt(categories.length)];
        }

        lastCategory = category;

        String fileName = "/" + category.name().toLowerCase() + ".txt";

        return loadRandomWord(fileName);
    }

    /**
     * Returns a random word from words.txt
     */
    public String getRandomWord() {

        lastCategory = Category.RANDOM;

        return loadRandomWord("/words.txt");
    }

    /**
     * Loads all words from a file and returns one randomly.
     */
    private String loadRandomWord(String fileName) {

        List<String> words = new ArrayList<>();

        try (InputStream input = getClass().getResourceAsStream(fileName)) {

            if (input == null) {
                return "COMPUTER";
            }

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(input));

            String line;

            while ((line = reader.readLine()) != null) {

                line = line.trim();

                if (!line.isEmpty()) {
                    words.add(line.toUpperCase());
                }
            }

        } catch (Exception e) {

            e.printStackTrace();

            return "COMPUTER";
        }

        if (words.isEmpty()) {
            return "COMPUTER";
        }

        return words.get(random.nextInt(words.size()));
    }

    /**
     * Returns the category used for the current word.
     */
    public Category getLastCategory() {
        return lastCategory;
    }

}