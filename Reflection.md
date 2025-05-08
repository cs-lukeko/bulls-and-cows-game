# Reflection of Assignment Two Part B

Write your reflection in this document.

*Note: You do not need to use any Markdown or html tags to organise your reflection. However, if you want to highlight any particular parts in this document using Markdown or html tags, you can refer to the [Markdown guideline](https://www.markdownguide.org/basic-syntax/).*

Notes:
While creating the unit tests for HexaComputer (specifically ```TestValidateCodes_ValidList()```), I was unable to pass the test with a known valid code. This indicated to me that the HexaComputer constructor was not being successfully created, and the error code ```"No valid codes found!"``` suggested that the error was in the ```isValidCode()``` method. Upon closer inspection, it seemed that the checker for ```else if (c < 'a' || c > 'f')``` wasn't correctly checking whether the character was 0-9 and a-f. Once I improved the code to also check for valid numbers, the unit test was then able to pass. Here is the improved code at 
### Line 42
of HexaComputer.java:
`else if ((c < 'a' || c > 'f') && (c < '0' || c > '9'))`

I also noticed while testing that there is a possible case where a code is equal to null, or a code is equal to "". I did add three lines of code to handle this in the isValidCode() method (Lines 31 - 33).
`        
if (code == null || code.isEmpty()) {
    return false;
}
`