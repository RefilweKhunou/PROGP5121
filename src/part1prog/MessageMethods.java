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
/**
 *
 * @author RC_Student_Lab
 */
public class MessageMethods {
    private String messageID;
    private int messageNumber;
    private String recipient;
    private String content;
    private String messageHash;

    // List to store all messages sent
    private static ArrayList<MessageMethods> sentMessages = new ArrayList<>();
    
    public MessageMethods(String messageId, int messageNumber, String recipient, String content) {
        this.messageID = messageId;
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.content = content;
        this.messageHash = createMessageHash();
        sentMessages.add(this);  // Add the new message to the sent messages list
    }

    // Validate message ID to ensure it's exactly 10 characters long
    public boolean checkMessageID() {
        return messageID.length() == 10;
    }

    // Create and return the message hash
    public String createMessageHash() {
        String[] words = content.split(" ");
        String firstWord = words.length > 0 ? words[0] : "";
        String lastWord = words.length > 1 ? words[words.length - 1] : firstWord;

        return messageID.substring(0, 2) + ":" + messageNumber + ":" + firstWord.toUpperCase() + lastWord.toUpperCase();
    }

    // Allow the user to choose if they want to send, store, or disregard the message
    public String sentMessage() {
        String[] options = {"Send Message", "Disregard Message", "Store Message for Later"};
        int optionChoice = JOptionPane.showOptionDialog(null, "What do you want to do with this message?",
                "Message Options", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        
        switch (optionChoice) {
            case 0:  // Send message
                return "Message Sent: " + this.toString();
            case 1:  // Disregard message
                return "Message Discarded";
            case 2:  // Store for later
                return "Message Stored for Later";
            default:
                return "Action cancelled";
        }
    }
    
    public String handleMessageOption(int optionChoice) {
        switch (optionChoice) {
            case 0:
                return "Message successfully sent.";
            case 1:
                return "Press 0 to delete message.";
            case 2:
                return "Message successfully stored.";
            default:
                return "Action cancelled.";
        }
    }

    // Return a string representation of the sent messages
    public static String printMessages() {
        if (sentMessages.isEmpty()) {
            return "No messages sent yet.";
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("Sent Messages:\n");
        for (MessageMethods msg : sentMessages) {
            sb.append(msg.toString()).append("\n");
        }
        return sb.toString();
    }

    // Return the total number of messages sent
    public static int returnTotalMessages() {
        return sentMessages.size();
    }

    @Override
    public String toString() {
        return "Message ID: " + messageID + "\nRecipient: " + recipient + "\nMessage: " + content + 
                "\nMessage Hash: " + messageHash;
    }

    // Main method for testing
    public static void main(String[] args) {
        MessageMethods message1 = new MessageMethods("1234567890", 1, "+12345678901", "Hello!");
        MessageMethods message2 = new MessageMethods("0987654321", 2, "+98765432101", "How are you?");

        // No need to call checkRecipientCell() here, it's already validated in Registration
        System.out.println(message1.checkMessageID());  
        System.out.println(message2.checkMessageID());  // Example validation for message ID
        System.out.println(message1.createMessageHash());
        System.out.println(message1.sentMessage());  
        System.out.println(printMessages());  
        System.out.println(returnTotalMessages());  
    }
}

