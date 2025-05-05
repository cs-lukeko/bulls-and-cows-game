package bullsandcows;

public class Code {

    private String code;

    public Code(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    // Combines all three code check methods
    public boolean isValidCode() {
        return checkTypeInts() && checkLength() && checkUnique();
    }

    // Checks whether the length of input is correct
    public boolean checkLength() {
        if (code.length() == Game.CODE_NUM_DIGITS) {
            return true;
        }
        System.out.print("Incorrect number of digits. ");
        return false;
    }

    // Checks whether the input is digits (as opposed to e.g., chars)
    public boolean checkTypeInts() {
        char[] charArray = code.toCharArray();
        for (char c : charArray) {
            if (c >= '0' && c <= '9' ) {
                continue;
            } else {
                System.out.print("Code must consist of integer digits. ");
                return false;
            }
        }
        return true;
    }

    // Checks whether the digits are unique
    public boolean checkUnique() {
        char[] charArray = code.toCharArray();
        for (int i = 0; i < code.length(); i++) {
            for (int j = 0; j < i; j++) {
                if (charArray[i] == charArray[j]) {
                    System.out.print("Digits must be unique. ");
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return code;
    }

    @Override
    public boolean equals(Object code) {
        if (code instanceof Code other) {
            return this.code.equals(other.code);
        }
        return false;
    }
}
