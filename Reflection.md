# Reflection of Assignment Two Part B

#### Luke Heath-Edwards - lhea727 - 8664952



Overall my design did not change significantly between Part A and Part B, which I attribute to the good use of classes and inheritance in my initial structure. For example, adding the Hard AI was straightforward as I was able to extend the existing `Computer` class. The implementation was also relatively simple since learning about Lists and Iterators. It was, however, quite difficult to incorporate into my program the `HexaComputer` class which someone else had wrote. Since we were not allowed to alter the `HexaComputer` class, I had to come up with creative ways to get it to fit into the existing game logic. If we were able to alter the class, I would have liked to make it extend my existing `Computer` class, and add a method that returned the total number of valid hex codes. I would have also moved some responsibilities from my main `Game` class into the `HexaComputer` class to improve separation of concerns. To work around these constraints, I created a global variable `hexaCodesEnabled` which could be toggled to either true or false. Then in my `Code` class, which manages all the validation of secret codes and guesses, I changed the validation depending on whether the hexa codes were enabled (see Line 30 & 80 – `Game.java`; Lines 56-60 – `Code.java`). I also implemented a counter to retrieve the total number of valid codes in the `hexadecimals.txt` file, making it more efficient later on when picking a random valid code (see Lines 82-103 – `Game.java`). 

For Task 4 – Testing the `HexaComputer` class, I wanted to create unit tests that covered as many lines of the class as possible, and I was actually able to cover 100% of lines. First, I tested that the constructor behaved correctly when provided with both valid and invalid code lists. I also tested the `getCode()` method, ensuring it handled valid and invalid inputs as expected. Lastly, I implicitly tested the `validateCodes()` method using a variety of code lists, including valid, invalid, mixed, empty, and null codes. I considered using a `@BeforeEach` annotation but there wasn’t a need for it as not all classes required a similar initial setup. I did however use lambda expressions for some of the tests which gave me good practice wih them. While creating the unit tests (specifically `TestValidateCodes_ValidList()`), I was unable to pass the test with a known valid code. This indicated to me that the HexaComputer constructor was not being successfully created, and the error code `"No valid codes found!"` suggested that the error was in the `isValidCode()` method. Upon closer inspection, it seemed that the checker for `else if (c < 'a' || c > 'f')` was not correctly taking into account numeric characters. Once I improved the code to also check for numbers, the unit test was then able to pass (see Line 42 - `HexaComputer.java`). I also noticed that the `isValidCode()` method did not handle null or empty strings, so I added three lines of code to handle this (Lines 31-33 - `HexaComputer.java`).

If I had more time to spend on this assignment, it could be valuable to improve some of the code from Part A. Particularly, converting my arrays to lists, simplifying repetitive logic, and moving responsibilities out of the main `Game` class into more specialised classes and methods to improve readability and maintainability. If I could introduce a new feature, I would like to implement an “Ultra Hard Mode” where the bulls and cows result always includes one incorrect digit, adding a layer of complexity to the guessing strategy. 

Most helpful of the resources available was ChatGPT. As with Part A, ChatGPT replaced the role of Google and was handy at clarifying my understandings of various topics. I didn't copy any code directly from ChatGPT - instead, the text responses were most helpful to guide my learning before writing the code myself. The AI auto-complete tools within IntelliJ were often more annoying than helpful, becoming distracting while I was trying to type my train of thought. It became more helpful once I had completed my code to tidy the code and find efficiencies. The most helpful part of the IntelliJ AI tools was the red and orange underlines when it detected something was wrong or could be improved. An example of an improvement it suggested is on Line 204 – `Game.java`. My initial implementation was:
```
if (currentGuesser instanceof AIHard) {
      AIHard aiHard = (AIHard) currentGuesser; // Cast currentGuesser to AIHard to access its updatePossibleGuesses method
    aiHard.updatePossibleGuesses(currentGuesser.getGuess(), bulls, cows);
}
```

Whereas the IntelliJ suggestion was more efficient with pattern matching: 
```
if (currentGuesser instanceof AIHard aiHard) { // Cast currentGuesser to AIHard to access its updatePossibleGuesses method
    aiHard.updatePossibleGuesses(currentGuesser.getGuess(), bulls, cows);
}
```