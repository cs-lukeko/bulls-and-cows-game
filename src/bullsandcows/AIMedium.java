package bullsandcows;

public class AIMedium extends Computer {

    public AIMedium() {
        super();
    }

    private Code[] previousGuesses = null;

    public Code[] getPreviousGuesses() {
        return previousGuesses;
    }

    public void updatePreviousGuesses() {
        Code[] previousGuesses = getPreviousGuesses();
        Code newGuess = getGuess();

        if (previousGuesses == null) { // First guess
            this.previousGuesses = new Code[1];
            this.previousGuesses[0] = newGuess;
        }
        else { // Subsequent guesses
            Code[] newArray = new Code[previousGuesses.length + 1];
            for (int i = 0; i < previousGuesses.length; i++) {
                newArray[i] = previousGuesses[i];
            }
            newArray[previousGuesses.length] = newGuess;
            this.previousGuesses = newArray;
        }
    }

    public boolean guessIsUnique() {
        Code guess = getGuess();
        if (previousGuesses == null) { // First guess
            return true;
        }
        for (Code previousGuess : previousGuesses) { // Subsequent guesses
            if (previousGuess.equals(guess)) {
                return false;
            }
        }
        return true;
    }
}