package bullsandcows;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ResultsFile {

    private String[] results;

    public ResultsFile() {
        results = new String[1];
        results[0] = "Bulls & Cows Game Results:\n";
    }

    public void setResults(String[] results) {
        this.results = results;
    }

    public void append(String result) {
        String[] newStringArray = new String[results.length + 1];
        for (int i = 0; i < results.length; i++) {
            newStringArray[i] = results[i];
        }
        newStringArray[newStringArray.length - 1] = result; // Append the new string
        setResults(newStringArray); // Update results string array
    }

    public void save(String fileName) {
        File newFile = new File(fileName);
        try (BufferedWriter bW = new BufferedWriter(new FileWriter(newFile))) {
            for (int i = 0; i < results.length; i++) {
                bW.write(results[i] + "\n");
            }
        }
        catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println("Successfully saved to " + fileName + "\n");
    }

    public boolean fileNameValid(String fileName) {
        // Check for null or blank input
        if (fileName == null || fileName.trim().isEmpty()) {
            return false;
        }

        // Disallow .txt or any dot in the name
        if (fileName.toLowerCase().endsWith(".txt") || fileName.contains(".")) {
            return false;
        }

        // Trim spaces and check for illegal characters (e.g. /, \, :, *, ?, \, <, >, |)
        fileName = fileName.trim();
        String illegalChars = "[\\\\/:*?\"<>|]";
        if (fileName.matches(".*" + illegalChars + ".*")) {
            return false;
        }
        return true;
    }
}
