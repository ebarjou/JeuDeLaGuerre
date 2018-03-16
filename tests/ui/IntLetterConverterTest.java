package ui;

import org.junit.Test;import game.gameState.GameState;

import static org.junit.Assert.*;

public class IntLetterConverterTest {

    @Test
    public void getIntFromLetters() {
        assertTrue(IntLetterConverter.getIntFromLetters("a") == 0);
        assertTrue(IntLetterConverter.getIntFromLetters("z") == 25);
    }

    @Test
    public void getLettersFromInt() {
        assertTrue(IntLetterConverter.getLettersFromInt(0).equalsIgnoreCase("a"));
        assertTrue(IntLetterConverter.getLettersFromInt(25).equalsIgnoreCase("z"));
    }
}