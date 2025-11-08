/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/EmptyTestNGTest.java to edit this template
 */
package part1prog;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author RC_Student_Lab
 */
public class ArrayMessageStorageNGTest {
    
    @BeforeMethod
    public void setUp() {
        // Reset storage before each test
        ArrayMessageStorage instance = getInstance();
        resetStorage(instance);

        // Add test messages
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

    private ArrayMessageStorage getInstance() {
        try {
            java.lang.reflect.Field instanceField = ArrayMessageStorage.class.getDeclaredField("instance");
            instanceField.setAccessible(true);
            return (ArrayMessageStorage) instanceField.get(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void resetStorage(ArrayMessageStorage instance) {
        try {
            java.lang.reflect.Field sentCountField = ArrayMessageStorage.class.getDeclaredField("sentCount");
            java.lang.reflect.Field disregardedCountField = ArrayMessageStorage.class.getDeclaredField("disregardedCount");
            java.lang.reflect.Field storedCountField = ArrayMessageStorage.class.getDeclaredField("storedCount");
            java.lang.reflect.Field messageCountField = ArrayMessageStorage.class.getDeclaredField("messageCount");
            sentCountField.setAccessible(true);
            disregardedCountField.setAccessible(true);
            storedCountField.setAccessible(true);
            messageCountField.setAccessible(true);

            sentCountField.setInt(instance, 0);
            disregardedCountField.setInt(instance, 0);
            storedCountField.setInt(instance, 0);
            messageCountField.setInt(instance, 0);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testSentMessagesArrayPopulated() {
        ArrayMessageStorage instance = getInstance();
        Assert.assertEquals(instance.sentMessages[0], "Did you get the cake?");
        Assert.assertEquals(instance.sentMessages[1], "It is dinner time!");
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
        Assert.assertEquals(instance.sentMessages[longestIndex], 
                            "Where are you? You are late!, I have asked you to be on time.");
    }

    @Test
    public void testSearchByMessageID() {
        ArrayMessageStorage instance = getInstance();
        String searchID = "002"; // Message 4
        int foundIndex = -1;
        for (int i = 0; i < instance.messageCount; i++) {
            if (searchID.equals(instance.messagesID[i])) {
                foundIndex = i;
                break;
            }
        }
        Assert.assertEquals(instance.sentMessages[foundIndex], "It is dinner time!");
    }

    @Test
    public void testSearchMessagesByRecipient() {
        ArrayMessageStorage instance = getInstance();
        String recipient = "+27838884567";
        StringBuilder messagesForRecipient = new StringBuilder();
        for (int i = 0; i < instance.sentCount; i++) {
            if (recipient.equals(instance.messageRecipients[i])) {
                messagesForRecipient.append(instance.sentMessages[i]).append("|");
            }
        }
        String[] results = messagesForRecipient.toString().split("\\|");
        Assert.assertEquals(results[0], "It is dinner time!");
        Assert.assertEquals(results[1], "Where are you? You are late!, I have asked you to be on time.");
        Assert.assertEquals(results[2], "Ok, I am leaving without you.");
    }

    @Test
    public void testDeleteMessageByHash() {
        ArrayMessageStorage instance = getInstance();
        String hashToDelete = "00:3:WHEREON"; // Test Message 2
        int foundIndex = -1;
        for (int i = 0; i < instance.messageCount; i++) {
            if (hashToDelete.equals(instance.messagesHash[i])) {
                foundIndex = i;
                break;
            }
        }

        // Simulate deletion
        for (int j = foundIndex; j < instance.messageCount - 1; j++) {
            instance.messagesID[j] = instance.messagesID[j + 1];
            instance.messagesHash[j] = instance.messagesHash[j + 1];
            instance.sentMessages[j] = instance.sentMessages[j + 1];
            instance.messageSenders[j] = instance.messageSenders[j + 1];
            instance.messageRecipients[j] = instance.messageRecipients[j + 1];
        }
        int lastIndex = instance.messageCount - 1;
        instance.messagesID[lastIndex] = null;
        instance.messagesHash[lastIndex] = null;
        instance.sentMessages[lastIndex] = null;
        instance.messageSenders[lastIndex] = null;
        instance.messageRecipients[lastIndex] = null;
        instance.messageCount--;
        instance.sentCount--;

        // Verify deletion
        for (int i = 0; i < instance.messageCount; i++) {
            Assert.assertNotEquals(instance.messagesHash[i], hashToDelete);
        }
    }

    @Test
    public void testDisplayFullReport() {
        ArrayMessageStorage instance = getInstance();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < instance.sentCount; i++) {
            sb.append("ID: ").append(instance.messagesID[i])
              .append("\nHash: ").append(instance.messagesHash[i])
              .append("\nFrom: ").append(instance.messageSenders[i])
              .append("\nTo: ").append(instance.messageRecipients[i])
              .append("\nMessage: ").append(instance.sentMessages[i])
              .append("\n\n");
        }

        String report = sb.toString();
        Assert.assertTrue(report.contains("Did you get the cake?"));
        Assert.assertTrue(report.contains("It is dinner time!"));
        Assert.assertTrue(report.contains("Where are you? You are late!, I have asked you to be on time."));
        Assert.assertTrue(report.contains("Ok, I am leaving without you."));
        Assert.assertTrue(report.contains("00:1:DIDCAKE"));
        Assert.assertTrue(report.contains("+27838884567"));
    }
}