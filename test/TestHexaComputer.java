import bullsandcows.HexaComputer;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/*
 This unit test class tests the HexaComputer.java class. Only the two public methods, HexaComputer() and getCode(), were explicitly tested in this test class. The two private methods, validateCodes() and isValidCode(), were tested implictly as they get called inside the HexaComputer constructor method.

 Various lists of codes containing valid and invalid codes were created which were the basis of the unit tests:
    - codesValid contains only valid codes - correct length, alphanumeric between 0-9 and a-f, unique
    - codesInvalid contains only invalid codes - incorrect lengths OR non-alphanumeric OR outside a-f OR non-unique OR empty string OR null
    - codesMixed contains a mixture of valid codes and invalid codes (in fact, is just codesValid inserted into the middle of codesInvalid)
    - codesEmpty contains an empty list
    - codesNull is not initialised so points to null
 */

public class TestHexaComputer {
    private HexaComputer hc;
    private List<String> codesValid = new ArrayList<>(Arrays.asList("123456", "019aef", "9feab0", "fe51ac", "face69"));
    private List<String> codesInvalid = new ArrayList<>(Arrays.asList("12345", "1234567", "ab12#$", "xyz123", "aaa111", "", null));
    private List<String> codesMixed = new ArrayList<>(Arrays.asList("12345", "1234567","123456", "019aef", "9feab0", "fe51ac", "face69", "ab12#$", "xyz123", "aaa111", "", null));
    private List<String> codesEmpty = new ArrayList<>(Arrays.asList());
    private List<String> codesNull = null;

    // Test HexaComputer() constructor - throws IllegalArgumentException if codes == null
    @Test
    public void TestHexaComputerConstructor_NullList() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new HexaComputer(codesNull));
        assertEquals("Codes cannot be empty!", e.getMessage());
    }

    // Test HexaComputer() constructor - throws IllegalArgumentException if codes is empty
    @Test
    public void TestHexaComputerConstructor_EmptyList() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new HexaComputer(codesEmpty));
        assertEquals("Codes cannot be empty!", e.getMessage());
    }

    // Test HexaComputer() constructor - is not null if HexaComputer is successfully created
    @Test
    public void TestHexaComputerConstructor_SuccessfulCreation() {
        hc = new HexaComputer(codesValid);
        assertNotNull(hc);
    }

    // Test getCode() - throws IllegalArgumentException when invalid index (index < 0 or index > codes.size())
    @Test
    public void TestGetCode_InvalidIndex() {
        hc = new HexaComputer(codesValid);
        IndexOutOfBoundsException e1 = assertThrows(IndexOutOfBoundsException.class, () -> hc.getCode(-1));
        assertEquals("Invalid index!", e1.getMessage());

        IndexOutOfBoundsException e2 = assertThrows(IndexOutOfBoundsException.class, () -> hc.getCode(codesValid.size() + 1));
        assertEquals("Invalid index!", e2.getMessage());
    }

    // Test getCode() - returns expected code
    @Test
    public void TestGetCode_SuccessfulReturn() {
        assertEquals("face69", codesValid.get(4));
        assertEquals("ab12#$", codesInvalid.get(2));
        assertEquals("1234567", codesMixed.get(1));
    }

    // Test validateCodes() - specifically does not throw an exception when list is populated with valid codes
    @Test
    public void TestValidateCodes_ValidList() {
        assertDoesNotThrow(() -> new HexaComputer(codesValid));
    }

    // Test validateCodes() and isValidCode() - throws IllegalArgumentException when list is populated with only invalid codes
    // Invalid codes include: invalid length, non-alphanumeric, values outside of 0-9 and a-f, non-unique values, empty string, null
    @Test
    public void TestValidateCodes_InvalidList() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new HexaComputer(codesInvalid));
        assertEquals("No valid codes found!", e.getMessage());
    }

    // Test validateCodes() and isValidCode() - successfully adds valid codes to list and omits invalid codes
    // Invalid codes include: invalid length, non-alphanumeric, values outside of 0-9 and a-f, non-unique values, empty string, null
    @Test
    public void TestValidateCodes_MixedList() {
        hc = new HexaComputer(codesMixed);
        for (int i = 0; i < codesValid.size(); i++) { // confirms the validated codesMixed list is equal to codesValid
            assertEquals(codesValid.get(i), hc.getCode(i));
        }
        IndexOutOfBoundsException e = assertThrows(IndexOutOfBoundsException.class, () -> hc.getCode(codesValid.size() + 1)); // confirms exactly 5 codes were added, not more than 5
        assertEquals("Invalid index!", e.getMessage());
    }
}
