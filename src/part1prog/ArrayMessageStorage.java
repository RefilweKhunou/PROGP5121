/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package part1prog;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author RC_Student_Lab
 */
public class ArrayMessageStorage {

    static int getLongestMessageIndex() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    static Object report() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    static void addStored(String string) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    int max_array_size = 50;
    String[] sentMessages = new String[max_array_size];
    String[] messagesHash = new String[max_array_size];
    String[] messagesID = new String[max_array_size];
    String[] messageSenders = new String[max_array_size];
String[] messageRecipients = new String[max_array_size];

String[] disregardedMessages = new String[max_array_size];
    String[] storedMessages = new String[max_array_size]; // read from JSON

    int sentCount = 0;
    int disregardedCount = 0;
    int storedCount = 0;
    int messageCount = 0;
    
    private static ArrayMessageStorage instance = new ArrayMessageStorage();
    
    private ArrayMessageStorage() {}

    public static ArrayMessageStorage get() {
        return instance;
    }
    
    // === Static methods to add messages and details ===
    public static void addSentMessage(String message, String sender, String recipient) {
        if (get().sentCount < get().max_array_size) {
        int i = get().sentCount;
        get().sentMessages[i] = message;
        get().messageSenders[i] = sender;
        get().messageRecipients[i] = recipient;

        // Increment sentCount and messageCount after adding the message
        get().sentCount++;
        get().messageCount++; // Assuming you need this for global tracking
    } else {
        JOptionPane.showMessageDialog(null, "Sent messages storage is full!");
    }
    }

    public static void addDisregardedMessage(String message) {
    if (get().disregardedCount < get().max_array_size) {
        int i = get().disregardedCount;
        get().disregardedMessages[i] = message;
        
        // Increment the count after adding the message
        get().disregardedCount++;
    } else {
        JOptionPane.showMessageDialog(null, "Disregarded messages storage is full!");
    }
}

public static void addStoredMessage(String message) {
    if (get().storedCount < get().max_array_size) {
        int i = get().storedCount;
        get().storedMessages[i] = message;
        
        // Increment the count after adding the message
        get().storedCount++;
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
        for (int i = 0; i < get().sentCount; i++) {
            sb.append("From: ").append(get().messageSenders[i])
              .append(" -> To: ").append(get().messageRecipients[i])
              .append("\nMessage: ").append(get().sentMessages[i])
              .append("\nHash: ").append(get().messagesHash[i])
              .append("\nID: ").append(get().messagesID[i])
              .append("\n\n");
        }

        sb.append("Disregarded Messages:\n");
        for (int i = 0; i < get().disregardedCount; i++) {
            sb.append(get().disregardedMessages[i]).append("\n");
        }

        sb.append("Stored Messages:\n");
        for (int i = 0; i < get().storedCount; i++) {
            sb.append(get().storedMessages[i]).append("\n");
        }

        return sb.toString();
    }

    // === a. Show sender and recipient of all sent messages ===
    public static void showSendersAndRecipients() {
        StringBuilder sb = new StringBuilder("Sent Messages with Sender/Recipient:\n");
        for (int i = 0; i < get().sentCount; i++) {
            sb.append("From: ").append(get().messageSenders[i])
              .append(" -> To: ").append(get().messageRecipients[i])
              .append("\nMessage: ").append(get().sentMessages[i])
              .append("\n\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    // === b. Display the longest sent message ===
    public static void showLongestSentMessage() {
        int longestIndex = -1;
        int maxLength = 0;

        for (int i = 0; i < get().sentCount; i++) {
            if (get().sentMessages[i] != null && get().sentMessages[i].length() > maxLength) {
                maxLength = get().sentMessages[i].length();
                longestIndex = i;
            }
        }

        if (longestIndex != -1) {
            JOptionPane.showMessageDialog(null, "Longest Sent Message:\nFrom: " 
                + get().messageSenders[longestIndex]
                + " -> To: " + get().messageRecipients[longestIndex]
                + "\nMessage: " + get().sentMessages[longestIndex]);
        } else {
            JOptionPane.showMessageDialog(null, "No sent messages found.");
        }
    }

    // === c. Search message by ID and show recipient/message ===
    public static void searchByMessageIDShowRecipient() {
        String id = JOptionPane.showInputDialog("Enter Message ID to search:");
        if (id == null || id.trim().isEmpty()) return;  // If user cancels or enters empty ID

        for (int i = 0; i < get().messageCount; i++) {
            if (id.equals(get().messagesID[i])) {
                JOptionPane.showMessageDialog(null, "Message ID: " + id
                    + "\nRecipient: " + get().messageRecipients[i]
                    + "\nMessage: " + get().sentMessages[i]);
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Message ID not found.");
    }

    // === d. Search all messages sent to a particular recipient ===
    public static void searchMessagesByRecipient(String recipient) {
        StringBuilder sb = new StringBuilder("Messages sent to " + recipient + ":\n");
        boolean found = false;

        for (int i = 0; i < get().sentCount; i++) {
            if (recipient.equals(get().messageRecipients[i])) {
                sb.append("From: ").append(get().messageSenders[i])
                  .append("\nMessage: ").append(get().sentMessages[i])
                  .append("\n\n");
                found = true;
            }
        }
        if (!found) sb.append("No messages found.");
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    // === e. Delete a message using message hash ===
    public static void deleteByMessageHash(String hash) {
        // Search through all messages
    for (int i = 0; i < get().messageCount; i++) {

        // If the hash matches
        if (hash.equals(get().messagesHash[i])) {

            int confirm = JOptionPane.showConfirmDialog(
                null,
                "Delete message: " + get().sentMessages[i] + "?",
                "Confirm",
                JOptionPane.YES_NO_OPTION
            );

            if (confirm != JOptionPane.YES_OPTION) {
                return; // user canceled
            }

            // SHIFT LEFT all message-related arrays
            for (int j = i; j < get().messageCount - 1; j++) {
                get().sentMessages[j] = get().sentMessages[j + 1];
                get().messageSenders[j] = get().messageSenders[j + 1];
                get().messageRecipients[j] = get().messageRecipients[j + 1];
                get().messagesHash[j] = get().messagesHash[j + 1];
                get().messagesID[j] = get().messagesID[j + 1];
            }

            // Nullify the very last slot
            int last = get().messageCount - 1;
            get().sentMessages[last] = null;
            get().messageSenders[last] = null;
            get().messageRecipients[last] = null;
            get().messagesHash[last] = null;
            get().messagesID[last] = null;

            // Reduce total messages
            get().messageCount--;

            // Only reduce sentCount if the deleted message was actually sent
            if (i < get().sentCount) {
                get().sentCount--;
            }

            JOptionPane.showMessageDialog(null, "Message deleted successfully.");
            return;
        }
    }

    // If no match found
    JOptionPane.showMessageDialog(null, "Message hash not found.");
}

    // === f. Display full report of all sent messages ===
    public static void displayFullReport() {
        StringBuilder sb = new StringBuilder("Full Sent Messages Report:\n");
        for (int i = 0; i < get().sentCount; i++) {
            sb.append("ID: ").append(get().messagesID[i])
              .append("\nHash: ").append(get().messagesHash[i])
              .append("\nFrom: ").append(get().messageSenders[i])
              .append("\nTo: ").append(get().messageRecipients[i])
              .append("\nMessage: ").append(get().sentMessages[i])
              .append("\n\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    // --- Load Messages from JSON ---
    public static void loadFromJSON(String filepath) throws IOException {
        FileReader reader = new FileReader(filepath);
        JSONTokener tok = new JSONTokener(reader);
        JSONObject obj = new JSONObject(tok);
        JSONArray arr = obj.getJSONArray("storedMessages");

        for (int i = 0; i < arr.length(); i++) {
            addStoredMessage(arr.getString(i));
        }
    }

    // --- Search Messages by Recipient ---
    public static String[] searchByRecipient(String rec) {
        ArrayList<String> result = new ArrayList<>();

        for (int i = 0; i < get().sentCount; i++) {
            if (rec.equals(get().messageRecipients[i])) {
                result.add(get().sentMessages[i]);
            }
        }
        return result.toArray(new String[0]);
    }
}