package bullsandcows;

public abstract class Player {
    private String name;
    private Code secretCode;
    private Code guess;
    private int attempts;

    public Player() {
        this.name = "";
        this.secretCode = new Code("");
        this.guess = new Code("");
        this.attempts = 0;
    }

    public abstract void createSecretCode();

    public abstract void makeGuess();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        if (getName().equals("You")) {
            return "Your";
        }
        else {
            return getName() + "'s";
        }
    }

    public Code getSecretCode() {
        return secretCode;
    }

    public void setSecretCode(Code secretCode) {
        this.secretCode = secretCode;
    }

    public Code getGuess() {
        return guess;
    }

    public void setGuess(Code guess) {
        this.guess = guess;
    }

    public int getAttempts() {
        return attempts;
    }

    public void setAttempts(int attempts){
        this.attempts = attempts;
    }
}
