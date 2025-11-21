package part1prog;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.io.FileReader;
import java.io.IOException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import javax.swing.JOptionPane;

/**
 *
 * @author RC_Student_Lab
 */
public class JsonMessageLoader {

    // Load stored messages from JSON file into ArrayMessageStorage
    public static void loadStoredMessagesFromJson(String filePath) {
        try (FileReader reader = new FileReader(filePath)) {
            JSONTokener tokener = new JSONTokener(reader);
            JSONObject obj = new JSONObject(tokener);
            
            // Get the stored messages array from JSON
            JSONArray messagesArray = obj.getJSONArray("storedMessages");

            // Add each message from the JSON array into ArrayMessageStorage
            for (int i = 0; i < messagesArray.length(); i++) {
                String message = messagesArray.getString(i);
                ArrayMessageStorage.addStoredMessage(message);
            }

            // Show success message with the number of stored messages loaded
            JOptionPane.showMessageDialog(null, "Stored messages loaded: " + messagesArray.length());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading JSON file: " + e.getMessage());
        }
    }

}