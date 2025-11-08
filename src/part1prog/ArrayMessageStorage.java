/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package part1prog;

import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author RC_Student_Lab
 */
public class ArrayMessageStorage {

    int max_array_size = 50;
    String[] sentMessages = new String[max_array_size];
    String[] disregardedMessages = new String[max_array_size];
    String[] storedMessages = new String[max_array_size]; // read from JSON
    String[] messagesHash = new String[max_array_size];
    String[] messagesID = new String[max_array_size];
    String[] messageSenders = new String[max_array_size];
String[] messageRecipients = new String[max_array_size];

    int sentCount = 0;
    int disregardedCount = 0;
    int storedCount = 0;
    int messageCount = 0;
    
    private static ArrayMessageStorage instance = new ArrayMessageStorage();
    
    // === Static methods to add messages and details ===
    public static void addSentMessage(String message, String sender, String recipient) {
        if (instance.sentCount < instance.max_array_size) {
            instance.sentMessages[instance.sentCount] = message;
            instance.messageSenders[instance.sentCount] = sender;
            instance.messageRecipients[instance.sentCount] = recipient;
            instance.sentCount++;
            instance.messageCount++;
        } else {
            JOptionPane.showMessageDialog(null, "Sent messages storage is full!");
        }
    }

    public static void addDisregardedMessage(String message) {
        if (instance.disregardedCount < instance.max_array_size) {
            instance.disregardedMessages[instance.disregardedCount++] = message;
        } else {
            JOptionPane.showMessageDialog(null, "Disregarded messages storage is full!");
        }
    }

    public static void addStoredMessage(String message) {
        if (instance.storedCount < instance.max_array_size) {
            instance.storedMessages[instance.storedCount++] = message;
        } else {
            JOptionPane.showMessageDialog(null, "Stored messages storage is full!");
        }
    }

    public static void addMessageHash(String hash) {
        if (instance.messageCount < instance.max_array_size) {
            instance.messagesHash[instance.messageCount++] = hash;
        } else {
            JOptionPane.showMessageDialog(null, "Message hash storage is full!");
        }
    }

    public static void addMessageID(String id) {
        if (instance.messageCount < instance.max_array_size) {
            instance.messagesID[instance.messageCount++] = id;
        } else {
            JOptionPane.showMessageDialog(null, "Message ID storage is full!");
        }
    }

    // === Display all arrays with sender/recipient ===
    public static String displayAllArrays() {
        StringBuilder sb = new StringBuilder();
        sb.append("Sent Messages:\n");
        for (int i = 0; i < instance.sentCount; i++) {
            sb.append("From: ").append(instance.messageSenders[i])
              .append(" -> To: ").append(instance.messageRecipients[i])
              .append("\nMessage: ").append(instance.sentMessages[i])
              .append("\nHash: ").append(instance.messagesHash[i])
              .append("\nID: ").append(instance.messagesID[i])
              .append("\n\n");
        }

        sb.append("Disregarded Messages:\n");
        for (int i = 0; i < instance.disregardedCount; i++) {
            sb.append(instance.disregardedMessages[i]).append("\n");
        }

        sb.append("Stored Messages:\n");
        for (int i = 0; i < instance.storedCount; i++) {
            sb.append(instance.storedMessages[i]).append("\n");
        }

        return sb.toString();
    }

    // === a. Show sender and recipient of all sent messages ===
    public static void showSendersAndRecipients() {
        StringBuilder sb = new StringBuilder("Sent Messages with Sender/Recipient:\n");
        for (int i = 0; i < instance.sentCount; i++) {
            sb.append("From: ").append(instance.messageSenders[i])
              .append(" -> To: ").append(instance.messageRecipients[i])
              .append("\nMessage: ").append(instance.sentMessages[i])
              .append("\n\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    // === b. Display the longest sent message ===
    public static void showLongestSentMessage() {
        int longestIndex = -1;
        int maxLength = 0;
        for (int i = 0; i < instance.sentCount; i++) {
            if (instance.sentMessages[i].length() > maxLength) {
                maxLength = instance.sentMessages[i].length();
                longestIndex = i;
            }
        }
        if (longestIndex != -1) {
            JOptionPane.showMessageDialog(null, "Longest Sent Message:\nFrom: " 
                + instance.messageSenders[longestIndex]
                + " -> To: " + instance.messageRecipients[longestIndex]
                + "\nMessage: " + instance.sentMessages[longestIndex]);
        } else {
            JOptionPane.showMessageDialog(null, "No sent messages found.");
        }
    }

    // === c. Search message by ID and show recipient/message ===
    public static void searchByMessageIDShowRecipient() {
        String id = JOptionPane.showInputDialog("Enter Message ID to search:");
        if (id == null) return;
        for (int i = 0; i < instance.messageCount; i++) {
            if (id.equals(instance.messagesID[i])) {
                JOptionPane.showMessageDialog(null, "Message ID: " + id
                    + "\nRecipient: " + instance.messageRecipients[i]
                    + "\nMessage: " + instance.sentMessages[i]);
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Message ID not found.");
    }

    // === d. Search all messages sent to a particular recipient ===
    public static void searchMessagesByRecipient(String recipient) {
        StringBuilder sb = new StringBuilder("Messages sent to " + recipient + ":\n");
        boolean found = false;
        for (int i = 0; i < instance.sentCount; i++) {
            if (recipient.equals(instance.messageRecipients[i])) {
                sb.append("From: ").append(instance.messageSenders[i])
                  .append("\nMessage: ").append(instance.sentMessages[i])
                  .append("\n\n");
                found = true;
            }
        }
        if (!found) sb.append("No messages found.");
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    // === e. Delete a message using message hash ===
    public static void deleteByMessageHash(String hash) {
        for (int i = 0; i < instance.messageCount; i++) {
            if (hash.equals(instance.messagesHash[i])) {
                int confirm = JOptionPane.showConfirmDialog(null,
                    "Delete message: " + instance.sentMessages[i] + "?",
                    "Confirm", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    for (int j = i; j < instance.messageCount - 1; j++) {
                        instance.messagesID[j] = instance.messagesID[j+1];
                        instance.messagesHash[j] = instance.messagesHash[j+1];
                        instance.sentMessages[j] = instance.sentMessages[j+1];
                        instance.messageSenders[j] = instance.messageSenders[j+1];
                        instance.messageRecipients[j] = instance.messageRecipients[j+1];
                    }
                    instance.messagesID[instance.messageCount-1] = null;
                    instance.messagesHash[instance.messageCount-1] = null;
                    instance.sentMessages[instance.messageCount-1] = null;
                    instance.messageSenders[instance.messageCount-1] = null;
                    instance.messageRecipients[instance.messageCount-1] = null;
                    instance.messageCount--;
                    instance.sentCount--;
                    JOptionPane.showMessageDialog(null, "Message deleted.");
                }
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Message hash not found.");
    }

    // === f. Display full report of all sent messages ===
    public static void displayFullReport() {
        StringBuilder sb = new StringBuilder("Full Sent Messages Report:\n");
        for (int i = 0; i < instance.sentCount; i++) {
            sb.append("ID: ").append(instance.messagesID[i])
              .append("\nHash: ").append(instance.messagesHash[i])
              .append("\nFrom: ").append(instance.messageSenders[i])
              .append("\nTo: ").append(instance.messageRecipients[i])
              .append("\nMessage: ").append(instance.sentMessages[i])
              .append("\n\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }
}