package fr.quentincillierre.hangman.model;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HangmanModelTest {

    private HangmanModel model;

    @BeforeEach
    void setUp() {
        model = new HangmanModel("java", 10);
    }

    @Test
    void testConstructor() {
        assertEquals("JAVA", model.getWordToGuess());
        assertEquals(0, model.getCurrentWrongs());
        assertTrue(model.getGuessedLetter().isEmpty());
    }

    @Test
    void testTryLetterWithCorrectLetter() {
        model.tryLetter('j');

        assertEquals(0, model.getCurrentWrongs());
        assertTrue(model.getGuessedLetter().contains('J'));
        assertEquals("J___", model.getHiddenWord());
    }

    @Test
    void testTryLetterWithIncorrectLetter() {
        model.tryLetter('x');

        assertEquals(1, model.getCurrentWrongs());
        assertTrue(model.getGuessedLetter().contains('X'));
        assertEquals("____", model.getHiddenWord());
    }

    @Test
    void testTryLetterWithDuplicate() {
        model.tryLetter('j');
        model.tryLetter('j');

        assertEquals(1, model.getGuessedLetter().size());
        assertEquals(0, model.getCurrentWrongs());
    }

    @Test
    void testTryLetterCaseInsensitive() {
        model.tryLetter('J');
        model.tryLetter('a');

        assertEquals("JA_A", model.getHiddenWord());
        assertEquals(0, model.getCurrentWrongs());
    }

    @Test
    void testGetHiddenWord() {
        assertEquals("____", model.getHiddenWord());

        model.tryLetter('j');
        assertEquals("J___", model.getHiddenWord());

        model.tryLetter('a');
        assertEquals("JA_A", model.getHiddenWord());

        model.tryLetter('v');
        assertEquals("JAVA", model.getHiddenWord());
    }

    @Test
    void testIsWin() {
        assertFalse(model.isWin());

        model.tryLetter('j');
        model.tryLetter('a');
        model.tryLetter('v');

        assertTrue(model.isWin());
    }

    @Test
    void testIsLose() {
        assertFalse(model.isLose());

        model.tryLetter('z');
        model.tryLetter('q');
        model.tryLetter('p');
        model.tryLetter('o');
        model.tryLetter('i');
        model.tryLetter('u');
        model.tryLetter('y');
        model.tryLetter('t');
        model.tryLetter('r');
        model.tryLetter('e');

        assertTrue(model.isLose());
    }

    @Test
    void testIsWinWithPartialGuess() {
        model.tryLetter('j');
        model.tryLetter('a');

        assertFalse(model.isWin());
    }

    @Test
    void testGetGuessedLetter() {
        model.tryLetter('j');
        model.tryLetter('a');

        Set<Character> guessed = model.getGuessedLetter();

        assertEquals(2, guessed.size());
        assertTrue(guessed.contains('J'));
        assertTrue(guessed.contains('A'));
    }

    @Test
    void testMaxWrongGuesses() {
        model.tryLetter('z');
        model.tryLetter('q');
        model.tryLetter('p');
        model.tryLetter('o');
        model.tryLetter('i');
        model.tryLetter('u');
        model.tryLetter('y');
        model.tryLetter('t');
        model.tryLetter('r');

        assertFalse(model.isLose());

        model.tryLetter('x');

        assertTrue(model.isLose());
    }
}