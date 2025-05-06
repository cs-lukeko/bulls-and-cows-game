package bullsandcows;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AIHard extends Computer {

    List<Code> possibleGuesses = null;

    public AIHard() {
        super();
    }

    public List<Code> getPossibleGuesses() {
        return possibleGuesses;
    }

    @Override
    public void makeGuess() {
        if (possibleGuesses == null) {
            initialisePossibleGuesses();
        }
        Code guess = possibleGuesses.get((int) (Math.random() * possibleGuesses.size()));
        setGuess(guess);
        System.out.print(getGuess());
        System.out.println();
    }

    public void initialisePossibleGuesses() {
        // Get upper limit of possible guesses i.e. 9999 for a 4-digit game
        String upperStr = "";
        for (int i = 0; i < Game.codeLength; i++) {
            upperStr += "9";
        }
        int upperInt = Integer.parseInt(upperStr);

        List<Code> possibleGuesses = new ArrayList<>();
        for (int i = 0; i < upperInt ; i++) {
            Code possibleGuess = new Code(String.format("%0" + Game.codeLength + "d", i));
            if (possibleGuess.isValidCode()) {
                possibleGuesses.add(possibleGuess);
            }
        }

        this.possibleGuesses = possibleGuesses;
    }

    public void updatePossibleGuesses(Code guess, int bulls, int cows) {
        Iterator<Code> iterator = this.possibleGuesses.iterator();
        while (iterator.hasNext()) {
            Code currentPossibleGuess = iterator.next();
            if (!isPossibleGuess(guess, bulls, cows, currentPossibleGuess)) {
                iterator.remove();
            }
        }
    }

    public boolean isPossibleGuess(Code currentGuess, int bulls, int cows, Code currentPossibleGuess) {
        int[] bullsAndCows = GameUtils.calculateBullsAndCows(currentPossibleGuess, currentGuess);
        int possibleBulls = bullsAndCows[0];
        int possibleCows = bullsAndCows[1];

        return bulls == possibleBulls && cows == possibleCows;
    }
}
