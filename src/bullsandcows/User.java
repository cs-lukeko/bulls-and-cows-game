package bullsandcows;

public class User extends Player {

    public User() {
        super();
        setName("You");
    }

    @Override
    public void createSecretCode() {
        while (true) { // Keep prompting the user for code until it is valid
            System.out.println("Please enter your secret code:");
            Code userInput = new Code(Keyboard.readInput());
            setSecretCode(userInput);

            if (getSecretCode().isValidCode()) {
                break;
            }
            else {
                System.out.println(getSecretCode().getErrorString());
            }
        }
    }

    @Override
    public void makeGuess() {
        String codeString = Keyboard.readInput();
        Code guess = new Code(codeString);
        setGuess(guess);
    }
}