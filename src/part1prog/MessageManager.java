/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package part1prog;

import java.io.IOException;
import javax.swing.JOptionPane;

/**
 *
 * @author RC_Student_Lab
 */
public class MessageManager {

    // === Add Message ===
    public static void addMessage(String message, String sender, String recipient, String id, String hash) {
        // Use ArrayMessageStorage methods to add the message
        ArrayMessageStorage.addSentMessage(message, sender, recipient);
        ArrayMessageStorage.addMessageID(id);
        ArrayMessageStorage.addMessageHash(hash);
    }

    // === Display the longest sent message ===
    public static void displayLongestMessage() {
        int index = ArrayMessageStorage.getLongestMessageIndex();
        if (index != -1) {
            JOptionPane.showMessageDialog(null, "Longest Message:\nFrom: "
                + ArrayMessageStorage.get().messageSenders[index]
                + " -> To: " + ArrayMessageStorage.get().messageRecipients[index]
                + "\nMessage: " + ArrayMessageStorage.get().sentMessages[index]);
        } else {
            JOptionPane.showMessageDialog(null, "No messages found.");
        }
    }
    
    // === Compute index of longest sent message (ADDED HERE PER YOUR REQUEST) ===
    public static int getLongestMessageIndex() {
        int longest = -1;
        int max = 0;

        for (int i = 0; i < ArrayMessageStorage.get().sentCount; i++) {
            String msg = ArrayMessageStorage.get().sentMessages[i];
            if (msg != null && msg.length() > max) {
                max = msg.length();
                longest = i;
            }
        }
        return longest;
    }

    // === Search messages by recipient ===
    public static void searchMessagesByRecipient(String recipient) {
        String[] messages = ArrayMessageStorage.searchByRecipient(recipient);
        if (messages.length > 0) {
            JOptionPane.showMessageDialog(null, String.join("\n", messages));
        } else {
            JOptionPane.showMessageDialog(null, "No messages found.");
        }
    }

    // === Delete message by hash ===
    public static void deleteMessageByHash(String hash) {
        ArrayMessageStorage.deleteByMessageHash(hash);
    }

    // === Load messages from JSON file ===
    public static void loadMessagesFromJson(String filepath) {
        try {
            ArrayMessageStorage.loadFromJSON(filepath);
            JOptionPane.showMessageDialog(null, "Messages loaded successfully.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error loading messages: " + e.getMessage());
        }
    }

    // === Display full report of all messages ===
    public static void displayFullReport() {
        JOptionPane.showMessageDialog(null, ArrayMessageStorage.report());
    }

    // === Search message by Message ID ===
    public static void searchByMessageID() {
        String id = JOptionPane.showInputDialog("Enter Message ID to search:");
        if (id == null) return;

        for (int i = 0; i < ArrayMessageStorage.get().messageCount; i++) {
            if (id.equals(ArrayMessageStorage.get().messagesID[i])) {
                JOptionPane.showMessageDialog(null, "Found Message:\n" +
                        "ID: " + ArrayMessageStorage.get().messagesID[i] + "\n" +
                        "Hash: " + ArrayMessageStorage.get().messagesHash[i] + "\n" +
                        "Sent: " + ArrayMessageStorage.get().sentMessages[i]);
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Message not found with ID: " + id);
    }

    // === Search by Message Hash ===
    public static void searchByMessageHash() {
        String hash = JOptionPane.showInputDialog("Enter Message Hash:");
        if (hash == null) return;

        for (int i = 0; i < ArrayMessageStorage.get().messageCount; i++) {
            if (hash.equals(ArrayMessageStorage.get().messagesHash[i])) {
                JOptionPane.showMessageDialog(null, "Found Message:" 
                        + "\nID: " + ArrayMessageStorage.get().messagesID[i] + "\n" 
                        + "Sent: " + ArrayMessageStorage.get().sentMessages[i]);
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Message not found with hash: " + hash);
    }

    // === Show Sent Messages ===
    public static void showSentMessages() {
        if (ArrayMessageStorage.get().sentCount == 0) {
            JOptionPane.showMessageDialog(null, "No sent messages yet.");
            return;
        }

        StringBuilder sb = new StringBuilder("Sent Messages:\n");
        for (int i = 0; i < ArrayMessageStorage.get().sentCount; i++) {
            sb.append((i + 1)).append(". ").append(ArrayMessageStorage.get().sentMessages[i]).append("\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    // === Show Disregarded Messages ===
    public static void showDisregardedMessages() {
        if (ArrayMessageStorage.get().disregardedCount == 0) {
            JOptionPane.showMessageDialog(null, "No disregarded messages.");
            return;
        }

        StringBuilder sb = new StringBuilder("Disregarded Messages:\n");
        for (int i = 0; i < ArrayMessageStorage.get().disregardedCount; i++) {
            sb.append((i + 1)).append(". ").append(ArrayMessageStorage.get().disregardedMessages[i]).append("\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    // === Show All Messages ===
    public static void showAllMessages() {
        StringBuilder sb = new StringBuilder("All Messages Summary:\n\n");

        sb.append("Sent Messages:\n");
        for (int i = 0; i < ArrayMessageStorage.get().sentCount; i++) {
            sb.append("- ").append(ArrayMessageStorage.get().sentMessages[i]).append("\n");
        }

        sb.append("\nDisregarded Messages:\n");
        for (int i = 0; i < ArrayMessageStorage.get().disregardedCount; i++) {
            sb.append("- ").append(ArrayMessageStorage.get().disregardedMessages[i]).append("\n");
        }

        sb.append("\nStored Messages:\n");
        for (int i = 0; i < ArrayMessageStorage.get().storedCount; i++) {
            sb.append("- ").append(ArrayMessageStorage.get().storedMessages[i]).append("\n");
        }

        JOptionPane.showMessageDialog(null, sb.toString());
    }

    // === Delete by ID ===
    public static void deleteByMessageID() {
        String id = JOptionPane.showInputDialog("Enter Message ID to delete:");
        if (id == null) return;

        for (int i = 0; i < ArrayMessageStorage.get().messageCount; i++) {
            if (id.equals(ArrayMessageStorage.get().messagesID[i])) {
                int confirm = JOptionPane.showConfirmDialog(null,
                        "Delete message: " + ArrayMessageStorage.get().sentMessages[i] + "?",
                        "Confirm", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    // Shift arrays left after deleting the message
                    for (int j = i; j < ArrayMessageStorage.get().messageCount - 1; j++) {
                        ArrayMessageStorage.get().messagesID[j] = ArrayMessageStorage.get().messagesID[j + 1];
                        ArrayMessageStorage.get().messagesHash[j] = ArrayMessageStorage.get().messagesHash[j + 1];
                        ArrayMessageStorage.get().sentMessages[j] = ArrayMessageStorage.get().sentMessages[j + 1];
                    }
                    int last = ArrayMessageStorage.get().messageCount - 1;
                    ArrayMessageStorage.get().messagesID[last] = null;
                    ArrayMessageStorage.get().messagesHash[last] = null;
                    ArrayMessageStorage.get().sentMessages[last] = null;

                    ArrayMessageStorage.get().messageCount--;
                    JOptionPane.showMessageDialog(null, "Message deleted.");
                }
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Message ID not found.");
    }
}