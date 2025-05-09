package bullsandcows;

public class Computer extends Player {

    public Computer() {
        super();
        setName("Computer");
    }

    private Code generateCode() {
        int[] digits = new int[Game.codeLength];
        digits[0] = (int) (Math.random() * 10); // The first number can be any random digit
        for (int i = 1; i < digits.length; i++) { // From second digit onwards, compare uniqueness
            int newDigit = (int) (Math.random() * 10);
            while (!digitIsUnique(newDigit, digits, i)) {
                newDigit = (int) (Math.random() * 10);
            }
        digits[i] = newDigit;
        }

        // Convert the int array to a Code
        String codeString = "";
        for (int digit : digits) {
            codeString += digit;
        }
        return new Code(codeString);
    }

    private boolean digitIsUnique(int digit, int[] digits, int currentLength) {
        for (int i = 0; i < currentLength; i++) {
            if (digits[i] == digit) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void createSecretCode() {
        setSecretCode(generateCode());
    }

    @Override
    public void makeGuess() {
        setGuess(generateCode());
        System.out.print(getGuess());
        System.out.println();
    }
}



