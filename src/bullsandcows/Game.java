package bullsandcows;

/*
The Game class serves as the main entry point for the Bulls and Cows game. It initialises the game, handles player
and difficulty setup, then runs the game loop. At the end of the game, the results can be saved to a file. The Game
class interacts with: GameUtils, Player, User, Computer, AIEasy, AIMedium, AIHard, Code, and ResultsFile.

Visit my CodeTest.java file for further testing of some of the game's methods.

Please enjoy playing my version of the Bulls and Cows game!

@author Luke Heath-Edwards
@version 2.0
*/

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Game {
    public static final int MAX_ATTEMPTS = 7;
    public static final int MODE_SINGLE = 1;
    public static final int MODE_MULTI = 2;
    public static final int DIFF_EASY = 1;
    public static final int DIFF_MEDIUM = 2;
    public static final int DIFF_HARD = 3;
    public static final int CODE_LENGTH_DEFAULT = 4;
    public static final int CODE_LENGTH_HEXA = 6;
    public static int codeLength = CODE_LENGTH_DEFAULT;

    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }

    public void start() {
        int gameMode;
        int aiDifficulty;

        // Print a welcome message and get game mode
        printWelcomeMessage();

        // Instantiate resultsFile that may be saved at the end of the game
        ResultsFile resultsFile = new ResultsFile();

        gameModeSelection:
        while (true) {
            gameMode = selectGameMode();

            // Single Player mode
            if (gameMode == MODE_SINGLE) {
                // Run single-player game, instantiate players
                System.out.println("Single Player selected!\n");
                Computer computer = new Computer();
                User user = new User();
                selectCodeLengthOption();

                if (codeLength == CODE_LENGTH_HEXA) {
                    List<String> hexaCodes = new ArrayList<>();
                    while (true) {
                        // Import codes from text file
                        File hexaCodesFile = new File("hexadecimals.txt");
                        try (BufferedReader br = new BufferedReader(new FileReader(hexaCodesFile))) {
                            String line;
                            while ((line = br.readLine()) != null) {
                                hexaCodes.add(line);
                            }
                        } catch (FileNotFoundException e) {
                            System.out.println("File not found: \"" + hexaCodesFile.getName() + "\". Please select a different option.\n");
                            continue gameModeSelection;
                        } catch (IOException e) {
                            System.out.println("Error reading file. Please select a different option.");
                            continue gameModeSelection;
                        }
                        break;
                    }

                    HexaComputer hexaComputer = new HexaComputer(hexaCodes);

                    // Confirm number of valid hexa codes from hexadecimals.txt
                    int numberOfValidHexaCodes = 0;
                    try {
                        while (true) {
                            hexaComputer.getCode(numberOfValidHexaCodes);
                            numberOfValidHexaCodes++;
                        }
                    } catch (IndexOutOfBoundsException e) {
                        // numberOfValidHexaCodes will now be equal to the number of valid calls to getCode() - once it throws an exception, we know there are no more valid codes
                    }

                    // Generate computer's secret code
                    int index = (int) (Math.random() * (numberOfValidHexaCodes));
                    Code secretCode;
                    while (true) {
                        try {
                            secretCode = new Code(hexaComputer.getCode(index));
                            break;
                        } catch (IndexOutOfBoundsException e) {
                            System.out.println("Error generating secret code. Please try again.");
                        }
                    }
                    computer.setSecretCode(secretCode);
                    resultsFile.append("Computer's code was: " + computer.getSecretCode() + "\n---");
                } else if (codeLength == CODE_LENGTH_DEFAULT) {
                    // Create computer object and generate computer's secret code
                    computer.createSecretCode();
                    resultsFile.append("Computer's code was: " + computer.getSecretCode() + "\n---");
                }

                while (user.getAttempts() < MAX_ATTEMPTS) {
                    // Get guess from user and validate
                    System.out.print(user.getDisplayName() + " guess #" + (user.getAttempts() + 1) + ": ");
                    user.makeGuess();
                    if (user.getGuess().isValidCode()) {
                        int[] bullsAndCows = GameUtils.calculateBullsAndCows(user.getGuess(), computer.getSecretCode());
                        int bulls = bullsAndCows[0];
                        int cows = bullsAndCows[1];
                        System.out.println("Result: " + GameUtils.bullsAndCowsString(bulls, cows));
                        resultsFile.append("Turn " + (user.getAttempts() + 1) + ":");
                        resultsFile.append("You guessed " + user.getGuess() + ", scoring " + GameUtils.bullsAndCowsString(bulls, cows));
                        resultsFile.append("---");

                        // Win condition
                        if (GameUtils.evaluateWinCondition(bulls)) {
                            System.out.println("You win! You guessed the computer's code in " + (user.getAttempts() + 1) + " attempts! :)");
                            resultsFile.append("You won! You guessed the computer's code in " + (user.getAttempts() + 1) + " attempts! :)");
                            break;
                        }

                        // Increment attempt
                        user.setAttempts(user.getAttempts() + 1);

                        // Loss condition
                        if (user.getAttempts() == MAX_ATTEMPTS) {
                            System.out.println("You lose! The secret code was " + computer.getSecretCode() + ". :(");
                            resultsFile.append("You lost! The secret code was " + computer.getSecretCode() + ". :(");
                            break;
                        }
                        System.out.println("---");
                    } else {
                        System.out.println(user.getGuess().getErrorString());
                        System.out.println("Please try again.");
                    }
                }
            }

            // Player vs. Computer mode
            else if (gameMode == MODE_MULTI) {
                // Choose difficulty for AI
                System.out.println("Player vs. Computer selected!\n");
                aiDifficulty = selectAIDifficulty();

                // Run multiplayer player game, instantiate players based on difficulty selection
                User user = new User();
                Computer computer = createAIPlayer(aiDifficulty);
                Player[] players = {computer, user};

                // Set secret codes for each player
                user.createSecretCode();
                resultsFile.append("Your secret code was: " + user.getSecretCode());
                computer.createSecretCode();
                resultsFile.append("Computer's secret code was: " + computer.getSecretCode());
                System.out.println();

                // Randomise player order
                GameUtils.randomisePlayerOrder(players);

                // Assign players to currentGuesser or otherPlayer roles
                game:
                while (user.getAttempts() < MAX_ATTEMPTS) {
                    resultsFile.append("---\n" +
                            "Turn " + (user.getAttempts() + 1) + ":");
                    for (int i = 0; i < players.length; i++) {
                        playerTurn:
                        while (true) {
                            Player currentGuesser = players[i];
                            Player otherPlayer = players[1 - i]; // Always selects the other player. Note: only for a two-player game.

                            // Make the guess
                            System.out.print(currentGuesser.getDisplayName() + " guess #" + (currentGuesser.getAttempts() + 1) + ": ");
                            currentGuesser.makeGuess();

                            if (currentGuesser.getGuess().isValidCode()) {
                                // If Medium difficulty, conduct an additional step of checking previous guesses, otherwise skip to the next step
                                if (currentGuesser instanceof AIMedium aiMedium) { // cast currentGuesser to AIMedium to access its previousGuesses
                                    while (true) {
                                        if (aiMedium.guessIsUnique()) {
                                            aiMedium.updatePreviousGuesses();
                                            break;
                                        }
                                        System.out.print(aiMedium.getDisplayName() + " guess #" + (aiMedium.getAttempts() + 1) + ": ");
                                        aiMedium.makeGuess();
                                    }
                                }

                                // Calculate bulls and cows for the guess
                                int[] bullsAndCows = GameUtils.calculateBullsAndCows(currentGuesser.getGuess(), otherPlayer.getSecretCode());
                                int bulls = bullsAndCows[0];
                                int cows = bullsAndCows[1];

                                // If Hard difficulty, update list of possible guesses
                                if (currentGuesser instanceof AIHard aiHard) { // Cast currentGuesser to AIHard to access its updatePossibleGuesses method
                                    aiHard.updatePossibleGuesses(currentGuesser.getGuess(), bulls, cows);
                                }

                                // Print results (formatted nicely) and update resultsFile
                                System.out.println("Result: " + GameUtils.bullsAndCowsString(bulls, cows));
                                if (i == 0) { // If first player
                                    System.out.println();
                                } else { // If second player
                                    System.out.println("---");
                                }
                                resultsFile.append(currentGuesser.getName() + " guessed " + currentGuesser.getGuess() + ", scoring " + GameUtils.bullsAndCowsString(bulls, cows));

                                if (GameUtils.evaluateWinCondition(bulls)) {
                                    if (currentGuesser.getName().equals("You")) {
                                        System.out.println("You win! You guessed the computer's code in " + (currentGuesser.getAttempts() + 1) + " attempts! :)"); // user wins
                                        resultsFile.append("---\nYou won! You guessed the computer's code in " + (currentGuesser.getAttempts() + 1) + " attempts! :)");
                                        break game;
                                    } else {
                                        System.out.println("Computer wins! It guessed your code in " + (currentGuesser.getAttempts() + 1) + " attempts. :("); // computer wins
                                        resultsFile.append("---\nComputer won! It guessed your code in " + (currentGuesser.getAttempts() + 1) + " attempts. :(");
                                        break game;
                                    }
                                }

                                // Increment attempt
                                currentGuesser.setAttempts(currentGuesser.getAttempts() + 1);
                                if (currentGuesser.getAttempts() == MAX_ATTEMPTS && i == 1) { // i == 1 ensures both players get a final turn before declaring a loss
                                    System.out.println("It's a draw! Neither player guessed the secret code. :|"); // Seven guesses exceeded
                                    resultsFile.append("---\nIt was a draw! Neither player guessed the secret code. :|");
                                }
                                break;
                            } else {
                                System.out.println(currentGuesser.getGuess().getErrorString());
                                System.out.println("Please try again.");
                            }
                        }
                    }
                }
            }
            break;
        }
        System.out.println();
        saveGame(resultsFile);
        System.out.println("Thank you for playing! Goodbye!");
    }

    private void printWelcomeMessage() {
        System.out.println("* * * * * * * *\n" +
                "Welcome to the Bulls And Cows game!\n" +
                "You have " + MAX_ATTEMPTS + " chances to guess the correct " + codeLength + "-digit code.\n" +
                "A Bull means the correct digit is in the correct location.\n" +
                "A Cow means the correct digit is in the wrong location.\n" +
                "Good luck!\n" +
                "* * * * * * * *\n");
    }

    private int selectGameMode() {
        int gameMode;
        while (true) {
            try {
                System.out.println("""
                        Which mode would you like to play?
                        1. Single Player
                        2. Player vs. Computer""");
                gameMode = GameUtils.parseGameMode(Keyboard.readInput());
                if (gameMode == MODE_SINGLE || gameMode == MODE_MULTI) {
                    break;
                } else {
                    System.out.println("Must select either 1 or 2. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter an integer only. Try again.");
            }
        }
        return gameMode;
    }

    private void selectCodeLengthOption() {
        while (true) {
            try {
                System.out.println("""
                        Which length of code would you like to guess?
                        1. 4-digit code
                        2. 6-digit code""");
                codeLength = GameUtils.selectCodeLengthOption(Keyboard.readInput());
                if (codeLength == CODE_LENGTH_DEFAULT || codeLength == CODE_LENGTH_HEXA) {
                    System.out.println(codeLength + "-digit code selected!\n");
                    break;
                } else {
                    System.out.println("Must select either 1 or 2. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter an integer only. Try again.");
            }
        }
    }

    private int selectAIDifficulty() {
        int aiDifficulty;
        while (true) {
            try {
                System.out.println("""
                        Which AI difficulty would you like to play against?
                        1. Easy
                        2. Medium
                        3. Hard""");
                aiDifficulty = GameUtils.parseAIDifficulty(Keyboard.readInput());
                if (aiDifficulty == DIFF_EASY || aiDifficulty == DIFF_MEDIUM || aiDifficulty == DIFF_HARD) {
                    break;
                }
                System.out.println("Must select either 1, 2, or 3. Please try again.");
            } catch (NumberFormatException e) {
                System.out.println("Please enter an integer. Try again.");
            }
        }
        return aiDifficulty;
    }

    public Computer createAIPlayer(int difficulty) {
        if (difficulty == DIFF_EASY) {
            System.out.println("Easy difficulty selected!\n");
            return new AIEasy();
        }
        else if (difficulty == DIFF_MEDIUM) {
            System.out.println("Medium difficulty selected!\n");
            return new AIMedium();
        }
        else if (difficulty == DIFF_HARD) {
            System.out.println("Hard difficulty selected!\n");
            return new AIHard();
        }
        else { // if null, choose default difficulty
            System.out.println("""
                    Invalid selection: default difficulty will be chosen.
                    Easy difficulty selected!
                    """);
            return new AIEasy();
        }
    }

    private void saveGame(ResultsFile resultsFile) {
        String saveYOrN;
        String fileName;

        while (true) {
            System.out.print("Would you like to save this game to a .txt file? (Y/N): ");
            saveYOrN = Keyboard.readInput();
            if (!(saveYOrN.equalsIgnoreCase("y") || saveYOrN.equalsIgnoreCase("n"))) {
                System.out.println("Invalid answer. Please try again.");
            }
            else {
                break;
            }
        }

        if (saveYOrN.equalsIgnoreCase("y")) {
            while (true) {
                System.out.print("Please enter a save file name (exclude the file type extension): ");
                fileName = Keyboard.readInput();
                if (resultsFile.fileNameValid(fileName)) {
                    fileName = fileName + ".txt";
                    resultsFile.save(fileName);
                    break;
                }
                else {
                    System.out.println("Invalid save file name. Please try again.");
                }
            }
        }
    }
}
