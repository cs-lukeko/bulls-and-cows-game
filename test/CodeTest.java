package test.java;

// This is a collection of unit tests that test some methods that are critical to the game's core functionality. In the case of the guessIsUnique() and randomisePlayerOrder() methods, it is difficult to confirm correct behaviour purely with the Game code - therefore, these tests should provide evidence that the methods are working as intended.

// INDEX
// 01. validCode() - Tests whether a user or computer code (secret code or guess) is valid
// 02. validCode() - Tests whether a user or computer code (secret code or guess) is valid
// 03. guessIsUnique() - Tests whether the Medium AI's guess is unique
// 04. guessIsUnique() - Tests whether the Medium AI's guess is unique
// 05. guessIsUnique() - Tests whether the Medium AI's guess is unique
// 06. randomisePlayerOrder() - Tests whether the players are swapping playing order successfully
// 07. createAIPlayer() - tests whether the correct AI player is created depending on input parameter
// 08. createAIPlayer() - tests whether the correct AI player is created depending on input parameter
// 09. createAIPlayer() - tests whether the correct AI player is created depending on input parameter

import bullsandcows.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CodeTest {

    @Test
    // 01. validCode() - Tests whether a user or computer code (secret code or guess) is valid
    void validCode_shouldReturnTrue_whenCodeContainsValidDigits() {
        assertTrue(new Code("1234").isValidCode());
        assertTrue(new Code("4975").isValidCode());
        assertTrue(new Code("9081").isValidCode());
    }

    @Test
    // 02. validCode() - Tests whether a user or computer code (secret code or guess) is valid
    void validCode_shouldReturnFalse_whenCodeContainsInvalidDigits() {
        assertFalse(new Code("1123").isValidCode());
        assertFalse(new Code("12345").isValidCode());
        assertFalse(new Code("-1").isValidCode());
        assertFalse(new Code("null").isValidCode());
    }

    @Test
    // 03. guessIsUnique() - Tests whether the Medium AI's guess is unique
    void guessIsUnique_shouldReturnTrue_whenNoPreviousGuesses() {
        AIMedium ai = new AIMedium();
        ai.setGuess(new Code("1234"));

        assertTrue(ai.guessIsUnique());
    }

    @Test
    // 04. guessIsUnique() - Tests whether the Medium AI's guess is unique
    void guessIsUnique_shouldReturnTrue_whenGuessIsNotInPreviousGuesses() {
        AIMedium ai = new AIMedium();
        ai.setGuess(new Code("1234"));
        ai.updatePreviousGuesses();

        ai.setGuess(new Code("5678"));
        assertTrue(ai.guessIsUnique());
    }

    @Test
    // 05. guessIsUnique() - Tests whether the Medium AI's guess is unique
    void guessIsUnique_shouldReturnFalse_whenGuessIsInPreviousGuesses() {
        AIMedium ai = new AIMedium();
        ai.setGuess(new Code("1234"));
        ai.updatePreviousGuesses();

        ai.setGuess(new Code("1234"));
        assertFalse(ai.guessIsUnique());
    }

    @Test
    // 06. randomisePlayerOrder() - Tests whether the players are swapping playing order successfully
    void randomisePlayerOrder_shouldSometimesSwapPlayers() {
        Player p1 = new User();
        Player p2 = new Computer();

        int userFirstCount = 0;
        int computerFirstCount = 0;

        for (int i = 0; i < 1000; i++) {
            Player[] players = {p1, p2};
            GameUtils.randomisePlayerOrder(players);

            if (players[0].getName().equals("You")) {
                userFirstCount++;
            } else if (players[0].getName().equals("Computer")) {
                computerFirstCount++;
            }
        }

        System.out.println("User went first " + userFirstCount + " times, Computer went first " + computerFirstCount + " times.");

        // Both players should be first at least once
        assertTrue(userFirstCount > 0, "User never went first — something's wrong");
        assertTrue(computerFirstCount > 0, "Computer never went first — something's wrong");
    }

    @Test
    // 07. createAIPlayer() - tests whether the correct AI player is created depending on input parameter
    void createAIPlayer_shouldReturnAIEasy_whenDifficultyIsEasy() {
        Game game = new Game();
        Computer ai = game.createAIPlayer(Game.DIFF_EASY); // assuming constant is public

        assertTrue(ai instanceof AIEasy);
    }

    @Test
    // 08. createAIPlayer() - tests whether the correct AI player is created depending on input parameter
    void createAIPlayer_shouldReturnAIMedium_whenDifficultyIsMedium() {
        Game game = new Game();
        Computer ai = game.createAIPlayer(Game.DIFF_MEDIUM);

        assertTrue(ai instanceof AIMedium);
    }

    @Test
    // 09. createAIPlayer() - tests whether the correct AI player is created depending on input parameter
    void createAIPlayer_shouldReturnAIEasy_whenDifficultyIsInvalid() {
        Game game = new Game();
        Computer ai = game.createAIPlayer(-1); // Invalid difficulty

        assertTrue(ai instanceof AIEasy);
    }
}