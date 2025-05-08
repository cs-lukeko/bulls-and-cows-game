package bullsandcows;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class HexaComputer {
    private List<String> codes;

    public HexaComputer(List<String> codes) {
        if (codes == null || codes.isEmpty()) {
            throw new IllegalArgumentException("Codes cannot be empty!");
        }
        this.codes = validateCodes(codes);
    }

    private List<String> validateCodes(List<String> codes) {
        List<String> validCodes = new ArrayList<>();
        for (String code : codes) {
            if (isValidCode(code)) {
                validCodes.add(code);
            }
        }
        if (validCodes.isEmpty()) {
            throw new IllegalArgumentException("No valid codes found!");
        }
        return validCodes;
    }

    private boolean isValidCode(String code) {
        if (code == null || code.isEmpty()) { // Handles null or empty code
            return false;
        }
        if (code.length() != 6) { // Handles invalid length
            return false;
        }
        HashSet<Character> charSet = new HashSet<>();
        for (int i = 0; i < code.length(); i++) {
            char c = code.charAt(i);
            if (!Character.isLetterOrDigit(c)) { // Is alphanumeric
                return false;
            } else if ((c < 'a' || c > 'f') && (c < '0' || c > '9')) { // Letters are between a and f and numbers between 0 and 9
                return false;
            } else if (!charSet.add(c)) { // Is unique
                return false;
            }
        }
        return true;
    }

    public String getCode(int index) {
        if (index < 0 || index >= codes.size()) {
            throw new IndexOutOfBoundsException("Invalid index!");
        }
        return codes.get(index);
    }
}
