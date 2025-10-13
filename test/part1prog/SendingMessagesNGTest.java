/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/EmptyTestNGTest.java to edit this template
 */
package part1prog;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.testng.Assert;
import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author RC_Student_Lab
 */
public class SendingMessagesNGTest {

    @Test
    public void testMessageWithinLimit() {
        String message = "This is a valid message within the 250 character limit.";
        String expected = "Message ready to send.";
        String actual = SendingMessages.MessageValidator.validateMessageLength(message);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testMessageExactly250Characters() {
        String message = "A".repeat(250);
        String expected = "Message ready to send.";
        String actual = SendingMessages.MessageValidator.validateMessageLength(message);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testMessageExceedsLimitBy10() {
        String message = "B".repeat(260); // 10 characters over the limit
        String expected = "Message exceeds 250 characters by 10, please reduce size.";
        String actual = SendingMessages.MessageValidator.validateMessageLength(message);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testMessageExceedsLimitBy1() {
        String message = "C".repeat(251); // 1 character over
        String expected = "Message exceeds 250 characters by 1, please reduce size.";
        String actual = SendingMessages.MessageValidator.validateMessageLength(message);

        Assert.assertEquals(expected, actual);
    }
    // ---------- RECIPIENT FORMAT TESTS ----------

    @Test
    public void testValidRecipientNumberFormat() {
        SendingMessages sm = new SendingMessages(null);  // We don't need Registration here
        String input = "+27123456789"; // Valid format
        boolean isValid = sm.checkRecipient(input);

        String expectedMessage = "Cell phone number successfully captured.";
        String actualMessage = isValid
                ? "Cell phone number successfully captured."
                : "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";

        Assert.assertTrue(isValid);
        Assert.assertEquals(actualMessage, expectedMessage);
    }

    @Test
    public void testRecipientMissingPlus() {
        SendingMessages sm = new SendingMessages(null);
        String input = "27123456789"; // Missing +
        boolean isValid = sm.checkRecipient(input);

        String expectedMessage = "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        String actualMessage = isValid
                ? "Cell phone number successfully captured."
                : "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";

        Assert.assertFalse(isValid);
        Assert.assertEquals(actualMessage, expectedMessage);
    }

    @Test
    public void testRecipientTooShort() {
        SendingMessages sm = new SendingMessages(null);
        String input = "+271234567"; // Only 9 digits
        boolean isValid = sm.checkRecipient(input);

        String expectedMessage = "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        String actualMessage = isValid
                ? "Cell phone number successfully captured."
                : "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";

        Assert.assertFalse(isValid);
        Assert.assertEquals(actualMessage, expectedMessage);
    }

    @Test
    public void testRecipientWithLetters() {
        SendingMessages sm = new SendingMessages(null);
        String input = "+27ABC456789"; // Contains letters
        boolean isValid = sm.checkRecipient(input);

        String expectedMessage = "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        String actualMessage = isValid
                ? "Cell phone number successfully captured."
                : "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";

        Assert.assertFalse(isValid);
        Assert.assertEquals(actualMessage, expectedMessage);
    }

    @Test
    public void testSingleMessageHashCorrectness() {
        MessageMethods message = new MessageMethods("0012345678", 0, "+27123456789", "Hi to night");
        String expectedHash = "00:0:HITONIGHT";
        String actualHash = message.createMessageHash();

        Assert.assertEquals(actualHash, expectedHash, "Hash does not match expected format for Test Case 1.");
    }

    @Test
    public void testMultipleMessageHashInLoop() {
        Object[][] testCases = {
            // messageID, messageNumber, content, expectedHash
            {"9912345678", 1, "Good morning", "99:1:GOODMORNING"},
            {"8823456789", 2, "Hello world", "88:2:HELLOWORLD"},
            {"7709876543", 3, "Let's go", "77:3:LET'SGO"},
            {"6611111111", 4, "Quick brown fox", "66:4:QUICKFOX"},
            {"5500000000", 5, "One", "55:5:ONEONE"},
            {"4400000000", 6, "", "44:6:"}, // Edge case: no words
            {"3300000000", 7, "Test", "33:7:TESTTEST"}, // Single word, used for both first and last
        };

        for (Object[] testCase : testCases) {
            String messageId = (String) testCase[0];
            int messageNumber = (int) testCase[1];
            String content = (String) testCase[2];
            String expectedHash = (String) testCase[3];

            MessageMethods message = new MessageMethods(messageId, messageNumber, "+27123456789", content);
            String actualHash = message.createMessageHash();

            Assert.assertEquals(actualHash, expectedHash,
                    String.format("Failed for messageID=%s, messageNumber=%d, content='%s'", messageId, messageNumber, content));
        }
    }

    @Test
    public void testGeneratedMessageIdFormatAndOutput() {
        SendingMessages sm = new SendingMessages(null);  // Registration not needed here

        // Redirect System.out to capture printed output
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(output));

        String generatedId = sm.generateAndLogMessageId();

        // Restore original System.out
        System.setOut(originalOut);

        String printedOutput = output.toString().trim();
        String expectedOutputPrefix = "Message ID generated: ";

        // Validate output format
        Assert.assertTrue(printedOutput.startsWith(expectedOutputPrefix), "Output should start with expected prefix");

        // Extract the actual ID from output
        String idInOutput = printedOutput.substring(expectedOutputPrefix.length());

        // Validate ID format (must be 10 digits)
        Assert.assertTrue(idInOutput.matches("\\d{10}"), "Generated ID should be 10 digits");

        // Validate that the returned ID matches the printed one
        Assert.assertEquals(generatedId, idInOutput, "Returned ID should match printed ID");
    }

    @Test
    public void testHandleSendMessageOption() {
        MessageMethods message = new MessageMethods("1234567890", 1, "+27123456789", "Hello World");
        String result = message.handleMessageOption(0);
        Assert.assertEquals(result, "Message successfully sent.");
    }

    @Test
    public void testHandleDisregardMessageOption() {
        MessageMethods message = new MessageMethods("1234567890", 2, "+27123456789", "Hi again");
        String result = message.handleMessageOption(1);
        Assert.assertEquals(result, "Press 0 to delete message.");
    }

    @Test
    public void testHandleStoreMessageOption() {
        MessageMethods message = new MessageMethods("1234567890", 3, "+27123456789", "Save this message");
        String result = message.handleMessageOption(2);
        Assert.assertEquals(result, "Message successfully stored.");
    }

    @Test
    public void testHandleInvalidMessageOption() {
        MessageMethods message = new MessageMethods("1234567890", 4, "+27123456789", "Invalid choice");
        String result = message.handleMessageOption(5);  // Invalid option
        Assert.assertEquals(result, "Action cancelled.");
    }
}
