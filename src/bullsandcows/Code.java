package bullsandcows;

public class Code {

    private String code;
    private String errorString = "";

    public Code(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getErrorString() {
        return errorString;
    }

    public void setErrorString(String errorString) {
        this.errorString = errorString;
    }

    // Combines all three code check methods
    public boolean isValidCode() {
        if (!checkTypeInts()) {
            setErrorString("Code must consist of integer digits. ");
        }
        else if (!checkLength()) {
            setErrorString("Code must be " + Game.CODE_NUM_DIGITS + " digits long. ");
        }
        else if (!checkUnique()) {
            setErrorString("Code must contain unique digits only. ");
        }
        return checkTypeInts() && checkLength() && checkUnique();
    }

    // Checks whether the length of input is correct
    public boolean checkLength() {
        if (code.length() == Game.CODE_NUM_DIGITS) {
            return true;
        }
        return false;
    }

    // Checks whether the input is digits (as opposed to e.g., chars)
    public boolean checkTypeInts() {
        char[] charArray = code.toCharArray();
        for (char c : charArray) {
            if (c >= '0' && c <= '9' ) {
                continue;
            } else {
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
