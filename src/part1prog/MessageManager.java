/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package part1prog;

import javax.swing.JOptionPane;

/**
 *
 * @author RC_Student_Lab
 */
public class MessageManager {
    // Reference to arrays and count

    private String[] messagesID;
    private String[] messagesHash;
    private String[] sentMessages;
    private String[] disregardedMessages;
    private String[] storedMessages;
    private int sentCount;
    private int disregardedCount;
    private int storedCount;
    private int messageCount;

// Constructor to initialize data
    public MessageManager(String[] messagesID, String[] messagesHash,
            String[] sentMessages, String[] disregardedMessages, String[] storedMessages,
            int sentCount, int disregardedCount, int storedCount, int messageCount) {

        this.messagesID = messagesID;
        this.messagesHash = messagesHash;
        this.sentMessages = sentMessages;
        this.disregardedMessages = disregardedMessages;
        this.storedMessages = storedMessages;
        this.sentCount = sentCount;
        this.disregardedCount = disregardedCount;
        this.storedCount = storedCount;
        this.messageCount = messageCount;
    }

// Search by Message ID
    public void searchByMessageID() {
        String id = JOptionPane.showInputDialog("Enter Message ID:");
        if (id == null) return;

        for (int i = 0; i < messageCount; i++) {
            if (id.equals(messagesID[i])) {
                JOptionPane.showMessageDialog(null, "Found Message:\n" +
                        "ID: " + messagesID[i] + "\n" +
                        "Hash: " + messagesHash[i] + "\n" +
                        "Sent: " + sentMessages[i]);
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Message not found with ID: " + id);
    }

// Search by Message Hash
    public void searchByMessageHash() {
         String hash = JOptionPane.showInputDialog("Enter Message Hash:");
        if (hash == null) return;

        for (int i = 0; i < messageCount; i++) {
            if (hash.equals(messagesHash[i])) {
                JOptionPane.showMessageDialog(null, "Found Message:" 
                        + "ID: " + messagesID[i] + "\n" 
                        + "Sent: " + sentMessages[i]);
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Message not found with hash: " + hash);
    }

        
// === Display Sent Messages ===
    public void showSentMessages() {
        if (sentCount == 0) {
            JOptionPane.showMessageDialog(null, "No sent messages yet.");
            return;
        }

        StringBuilder sb = new StringBuilder("Sent Messages:\n");
        for (int i = 0; i < sentCount; i++) {
            sb.append((i + 1)).append(". ").append(sentMessages[i]).append("\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    // === Display Disregarded Messages ===
    public void showDisregardedMessages() {
        if (disregardedCount == 0) {
            JOptionPane.showMessageDialog(null, "No disregarded messages.");
            return;
        }

        StringBuilder sb = new StringBuilder("Disregarded Messages:\n");
        for (int i = 0; i < disregardedCount; i++) {
            sb.append((i + 1)).append(". ").append(disregardedMessages[i]).append("\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    // === Show All Messages ===
    public void showAllMessages() {
        StringBuilder sb = new StringBuilder("All Messages Summary:\n\n");

        sb.append("Sent Messages:\n");
        for (int i = 0; i < sentCount; i++) {
            sb.append("- ").append(sentMessages[i]).append("\n");
        }

        sb.append("\nDisregarded Messages:\n");
        for (int i = 0; i < disregardedCount; i++) {
            sb.append("- ").append(disregardedMessages[i]).append("\n");
        }

        sb.append("\nStored Messages:\n");
        for (int i = 0; i < storedCount; i++) {
            sb.append("- ").append(storedMessages[i]).append("\n");
        }

        JOptionPane.showMessageDialog(null, sb.toString());
    }

    // === Delete by ID ===
    public int deleteByMessageID() {
        String id = JOptionPane.showInputDialog("Enter Message ID to delete:");
        if (id == null) return messageCount;

        for (int i = 0; i < messageCount; i++) {
            if (id.equals(messagesID[i])) {
                int confirm = JOptionPane.showConfirmDialog(null,
                        "Delete message: " + sentMessages[i] + "?",
                        "Confirm", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    for (int j = i; j < messageCount - 1; j++) {
                        messagesID[j] = messagesID[j + 1];
                        messagesHash[j] = messagesHash[j + 1];
                        sentMessages[j] = sentMessages[j + 1];
                    }
                    messagesID[messageCount - 1] = null;
                    messagesHash[messageCount - 1] = null;
                    sentMessages[messageCount - 1] = null;
                    messageCount--;
                    JOptionPane.showMessageDialog(null, "Message deleted.");
                }
                return messageCount;
            }
        }
        JOptionPane.showMessageDialog(null, "Message ID not found.");
        return messageCount;
    }
}