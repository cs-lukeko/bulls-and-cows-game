package bullsandcows;

public class GameUtils {
// Class that contains static methods that are used throughout the game and do not depend on specific instances of the game.

    public static int selectMode(String mode) {
        return Integer.parseInt(mode);
    }

    public static int selectDifficulty(String difficulty) {
        return Integer.parseInt(difficulty);
    }

    public static int selectCodeLengthOption(String length) {
        if (Integer.parseInt(length) == 1) {
            return Game.CODE_LENGTH_DEFAULT;
        }
        else if (Integer.parseInt(length) == 2) {
            return Game.CODE_LENGTH_HEXA;
        }
        else {
            return -1;
        }
    }

    public static void randomisePlayerOrder(Player[] players) {
        if (Math.random() < 0.5) {
            // Swap players
            Player temp = players[0];
            players[0] = players[1];
            players[1] = temp;
        }
        System.out.println(players[0].getName() + " will go first!\n");
    }

    public static int[] calculateBullsAndCows(Code playerGuess, Code opponentSecretCode) {
        int bulls = 0; // The number is correct and in the correct location
        int cows = 0; // The number is correct but in the wrong location

        char[] guess = playerGuess.getCode().toCharArray();
        char[] secretCode = opponentSecretCode.getCode().toCharArray();

        for (int i = 0; i < Game.codeLength; i++) {
            if (guess[i] == secretCode[i]) { // If exact match
                bulls++;
            }
            else if (opponentSecretCode.getCode().indexOf(guess[i]) > -1) { // If partial match
                cows++;
            }
        }
        return new int[] { bulls, cows };
    }

    public static String bullsAndCowsString(int bulls, int cows) {
        // Translate into proper English (e.g., not "1 cows", or "3 bull" - correct the plurality)
        String bullString = "bulls";
        String cowString = "cows";
        if (bulls == 1) {
            bullString = "bull";
        }
        if (cows == 1) {
            cowString = "cow";
        }
        return bulls + " " + bullString + " and " + cows + " " + cowString;
    }

    public static boolean evaluateWinCondition(int bulls) {
        return bulls == Game.codeLength;
    }
}
