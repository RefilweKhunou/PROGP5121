/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package part1prog;

import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.Random;
import org.json.JSONArray;
import org.json.JSONObject;

public class SendingMessages {

    private Registration register;
    private ArrayList<Message> messages = new ArrayList<>(); // List to store sent messages
    private ArrayList<Message> pendingMessages = new ArrayList<>(); // List to store messages for later
    private int messageNumber = 1;

    public SendingMessages(Registration register) {
        this.register = register;
    }

    public void showMenu() {
        boolean running = true;
        while (running) {
            String[] options = {"Send Messages", "Show Recently Sent Messages", "Send Pending Messages", "Quit"};
            int choice = JOptionPane.showOptionDialog(null, "Select an option", "QuickChat Menu",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

            switch (choice) {
                case 0: // Send Messages
                    sendMessages(); // Call the method to send messages
                    break;
                case 1: // Show Recently Sent Messages
                    showRecentMessages(); // Show the messages sent so far
                    break;
                case 2: // Send Pending Messages
                    sendPendingMessages(); // Send the stored messages
                    break;
                case 3: // Quit
                    JOptionPane.showMessageDialog(null, "Goodbye!");
                    running = false; // Exit the loop and end the application
                    break;
            }
        }
    }

    public static class MessageValidator {

        public static String validateMessageLength(String messageContent) {
            if (messageContent.length() > 250) {
                int excessLength = messageContent.length() - 250;
                return "Message exceeds 250 characters by " + excessLength + ", please reduce size.";
            } else {
                return "Message ready to send.";
            }
        }
    }

    public void sendMessages() {
        boolean continueSending = true;

        while (continueSending) {
            String recipient = JOptionPane.showInputDialog("Enter recipient's cellphone number (with international code, e.g., +27123456789):\nOr click Cancel to return to menu.");
            if (recipient == null) {
                break; // If the user cancels, return to menu
            }

            if (!checkRecipient(recipient)) {
                JOptionPane.showMessageDialog(null, "Invalid recipient number format.\nPlease use format: +27123456789 (country code + 10 digits)");
                continue;
            }

            String messageContent = JOptionPane.showInputDialog("Enter message " + messageNumber + " (Max 250 characters):");
            if (messageContent == null) {
                break; // If the user cancels, return to menu
            }

            // Check if the message exceeds 250 characters
            if (messageContent.length() > 250) {
                int excessLength = messageContent.length() - 250;
                JOptionPane.showMessageDialog(null, "Message exceeds 250 characters by " + excessLength + ", please reduce size.");
                continue; // Skip this message and allow the user to enter again
            }

            // Generate a unique 10-digit message ID
            String messageId = generateMessageId();

            // Create the Message object
            Message newMessage = new Message(messageId, messageNumber, recipient, messageContent);

            // Ask the user what they want to do with the message
            String[] options = {"Send Message", "Disregard Message", "Store Message to Send Later"};
            int optionChoice = JOptionPane.showOptionDialog(null, "What do you want to do with this message?\n\n" + newMessage.toString(),
                    "Message Options", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

            switch (optionChoice) {
                case 0: // Send Message
                    messages.add(newMessage); // Store the message as sent
                    JOptionPane.showMessageDialog(null, "Message successfully sent!\n" + newMessage.toString());
                    messageNumber++;
                    break;
                case 1: // Disregard Message
                    JOptionPane.showMessageDialog(null, "Message disregarded.\n" + newMessage.toString());
                    break;
                case 2: // Store Message to Send Later
                    pendingMessages.add(newMessage); // Store the message for later
                    JOptionPane.showMessageDialog(null, "Message successfully stored for later!\n" + newMessage.toString());
                    messageNumber++;
                    break;
            }

            // Ask if user wants to send another message
            int continueChoice = JOptionPane.showConfirmDialog(null,
                    "Do you want to send another message?", "Continue Sending",
                    JOptionPane.YES_NO_OPTION);

            continueSending = (continueChoice == JOptionPane.YES_OPTION);
        }
    }

    public void showRecentMessages() {
        // Show the recently sent messages
        if (messages.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No messages sent yet.");
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Recently Sent Messages:\n\n");
            for (Message msg : messages) {
                sb.append(msg).append("\n\n");
            }
            JOptionPane.showMessageDialog(null, sb.toString());
        }
    }

    public void sendPendingMessages() {
        // Send the stored pending messages
        if (pendingMessages.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No pending messages to send.");
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Sending Pending Messages:\n\n");
            for (Message msg : pendingMessages) {
                messages.add(msg); // Move from pending to sent
                sb.append(msg).append("\n\n");
            }
            pendingMessages.clear(); // Clear the pending messages after sending
            JOptionPane.showMessageDialog(null, sb.toString() + "All pending messages have been sent!");
        }
    }

    // Validate recipient number (should be 12 digits including the international code, e.g., +27123456789)
    public boolean checkRecipient(String phoneNumber) {
        return phoneNumber.matches("\\+\\d{11}"); // + followed by 11 digits (country code + 10 digit number)
    }

    // Generate a unique 10-digit message ID
    public String generateMessageId() {
        Random rand = new Random();
        return String.format("%010d", rand.nextInt(1000000000)); // Random 10-digit number
    }

    public String generateAndLogMessageId() {
        String id = generateMessageId();
        System.out.println("Message ID generated: " + id);
        return id;
    }

    // Message class with ID, recipient, content, and hash
    public static class Message {

        private String messageId;
        private int messageNumber;
        private String recipient;
        private String content;
        private String messageHash;

        public Message(String messageId, int messageNumber, String recipient, String content) {
            this.messageId = messageId;
            this.messageNumber = messageNumber;
            this.recipient = recipient;
            this.content = content;
            this.messageHash = generateMessageHash();
        }

        // Generate message hash
        public String generateMessageHash() {
            String[] words = content.split(" ");
            String firstWord = words.length > 0 ? words[0] : "";
            String lastWord = words.length > 1 ? words[words.length - 1] : firstWord;

            return messageId.substring(0, 2) + ":" + messageNumber + ":" + firstWord.toUpperCase() + lastWord.toUpperCase();
        }

        @Override
        public String toString() {
            return "Message #" + messageNumber
                    + "\nMessage ID: " + messageId
                    + "\nRecipient: " + recipient
                    + "\nMessage: " + content
                    + "\nMessage Hash: " + messageHash;
        }
    }
}
