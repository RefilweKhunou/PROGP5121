/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/EmptyTestNGTest.java to edit this template
 */
package part1prog;

import java.lang.reflect.Field;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.io.FileReader;
import java.io.IOException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.util.ArrayList;

/**
 *
 * @author RC_Student_Lab
 */
public class ArrayMessageStorageNGTest {
    
    @BeforeMethod
    public void setUp() {
        ArrayMessageStorage instance = getInstance();
        resetStorage(instance);

        // Add initial test messages
        ArrayMessageStorage.addSentMessage("Did you get the cake?", "Dev", "+27830000001");
        ArrayMessageStorage.addMessageID("001");
        ArrayMessageStorage.addMessageHash("00:1:DIDCAKE");

        ArrayMessageStorage.addSentMessage("It is dinner time!", "Dev", "+27838884567");
        ArrayMessageStorage.addMessageID("002");
        ArrayMessageStorage.addMessageHash("00:2:ITTIME");

        ArrayMessageStorage.addSentMessage("Where are you? You are late!, I have asked you to be on time.", "Dev", "+27838884567");
        ArrayMessageStorage.addMessageID("003");
        ArrayMessageStorage.addMessageHash("00:3:WHEREON");

        ArrayMessageStorage.addSentMessage("Ok, I am leaving without you.", "Dev", "+27838884567");
        ArrayMessageStorage.addMessageID("004");
        ArrayMessageStorage.addMessageHash("00:4:OKYOU");
    }

    // Helper method to get instance of ArrayMessageStorage
    private ArrayMessageStorage getInstance() {
        try {
            Field instanceField = ArrayMessageStorage.class.getDeclaredField("instance");
            instanceField.setAccessible(true);
            return (ArrayMessageStorage) instanceField.get(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Helper method to reset storage
    private void resetStorage(ArrayMessageStorage instance) {
        try {
            String[] fields = {"sentCount", "disregardedCount", "storedCount", "messageCount"};
            for (String fName : fields) {
                Field field = ArrayMessageStorage.class.getDeclaredField(fName);
                field.setAccessible(true);
                field.setInt(instance, 0);
            }
            // Clear message arrays
            String[][] arrays = {
                instance.sentMessages,
                instance.messageSenders,
                instance.messageRecipients,
                instance.messagesHash,
                instance.messagesID,
                instance.storedMessages,
                instance.disregardedMessages
            };
            for (String[] arr : arrays) {
                for (int i = 0; i < arr.length; i++) arr[i] = null;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testAddSentMessage() {
        ArrayMessageStorage.addSentMessage("Hello", "Alice", "Bob");
        Assert.assertEquals("Hello", ArrayMessageStorage.get().sentMessages[ArrayMessageStorage.get().sentCount - 1]);
    }

    @Test
    public void testDeleteMessageByHash() {
        // Add a message
    ArrayMessageStorage.addSentMessage("Hello", "Alice", "Bob");
    ArrayMessageStorage.addMessageHash("00:5:HELLO");

    // Ensure message is added
    Assert.assertEquals("Hello", ArrayMessageStorage.get().sentMessages[0]);
    Assert.assertEquals("00:5:HELLO", ArrayMessageStorage.get().messagesHash[0]);

    // Delete the message using existing method
    ArrayMessageStorage.deleteByMessageHash("00:5:HELLO");

    // Assert that the message is removed
    Assert.assertNull(ArrayMessageStorage.get().sentMessages[0]);
    Assert.assertNull(ArrayMessageStorage.get().messagesHash[0]);
    Assert.assertNull(ArrayMessageStorage.get().messageSenders[0]);
    Assert.assertNull(ArrayMessageStorage.get().messageRecipients[0]);
}

    @Test
    public void testSentMessagesArrayPopulated() {
        ArrayMessageStorage instance = getInstance();
        Assert.assertEquals("Did you get the cake?", instance.sentMessages[0]);
        Assert.assertEquals("It is dinner time!", instance.sentMessages[1]);
    }

    @Test
    public void testDisplayLongestMessage() {
        ArrayMessageStorage instance = getInstance();
        int longestIndex = -1;
        int maxLength = 0;
        for (int i = 0; i < instance.sentCount; i++) {
            if (instance.sentMessages[i].length() > maxLength) {
                maxLength = instance.sentMessages[i].length();
                longestIndex = i;
            }
        }
        Assert.assertEquals("Where are you? You are late!, I have asked you to be on time.", instance.sentMessages[longestIndex]);
    }

    @Test
    public void testSearchByMessageID() {
        ArrayMessageStorage instance = getInstance();
        String searchID = "002";
        int foundIndex = -1;
        for (int i = 0; i < instance.messageCount; i++) {
            if (searchID.equals(instance.messagesID[i])) {
                foundIndex = i;
                break;
            }
        }
        Assert.assertEquals("It is dinner time!", instance.sentMessages[foundIndex]);
    }

    @Test
    public void testSearchMessagesByRecipient() {
        ArrayMessageStorage instance = getInstance();
        String recipient = "+27838884567";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < instance.sentCount; i++) {
            if (recipient.equals(instance.messageRecipients[i])) sb.append(instance.sentMessages[i]).append("|");
        }
        String[] results = sb.toString().split("\\|");
        Assert.assertEquals("It is dinner time!", results[0]);
        Assert.assertEquals("Where are you? You are late!, I have asked you to be on time.", results[1]);
        Assert.assertEquals("Ok, I am leaving without you.", results[2]);
    }

    @Test
    public void testDeleteByHashMethod() {
        ArrayMessageStorage.deleteByMessageHash("00:3:WHEREON");

    // Get instance to check arrays
    ArrayMessageStorage instance = getInstance();

    // Verify that the message hash no longer exists
    for (int i = 0; i < instance.messageCount; i++) {
        Assert.assertNotEquals(instance.messagesHash[i], "00:3:WHEREON");
    }

    // Optionally, also check that sentMessages and related arrays shifted properly
    for (int i = 0; i < instance.sentCount; i++) {
        Assert.assertNotEquals(instance.sentMessages[i], "Where are you? You are late!, I have asked you to be on time.");
    }
    }

    @Test
    public void testDisplayFullReport() {
        ArrayMessageStorage instance = getInstance();
        String report = ArrayMessageStorage.displayAllArrays();
        Assert.assertTrue(report.contains("Did you get the cake?"));
        Assert.assertTrue(report.contains("It is dinner time!"));
        Assert.assertTrue(report.contains("Where are you? You are late!, I have asked you to be on time."));
        Assert.assertTrue(report.contains("Ok, I am leaving without you."));
        Assert.assertTrue(report.contains("00:1:DIDCAKE"));
        Assert.assertTrue(report.contains("+27838884567"));
    }

    @Test
    public void testLoadStoredMessagesFromJSON() throws IOException {
        // Prepare JSON file path
    String filepath = "test_messages.json";

    // Use the existing method to load stored messages
    JsonMessageLoader.loadStoredMessagesFromJson(filepath);

    // Verify that messages were loaded correctly
    ArrayMessageStorage instance = getInstance();
    Assert.assertEquals("JSON message 1", instance.storedMessages[0]);
    Assert.assertEquals("JSON message 2", instance.storedMessages[1]);
    Assert.assertEquals(2, instance.storedCount);
    }
}