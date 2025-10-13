/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package part1prog;


import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.Random;


/**
 *
 * @author RC_Student_lab
 */
public class login {

    private Registration register;

    public login(Registration register) {
        this.register = register;
    }

    public void log() {
        // Login loop with validation
        boolean loggedIn = false;
        while (!loggedIn) {
            loggedIn = register.loginUser(); // This validates login
        }

        // Show Welcome Message
        JOptionPane.showMessageDialog(null, "Hi " + register.username + ", Welcome to QuickChat!");

        // Ask user for the number of messages they want to enter
        String input = JOptionPane.showInputDialog("How many messages would you like to send?");
        int messageCount = 0;
        try {
            messageCount = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Please enter a valid number.");
            return;
        }

        // Show Menu
        Registration reg = new Registration(); // Your registration object
SendingMessages sender = new SendingMessages(reg);
sender.showMenu();
    }

    public static void main(String[] args) {
        // Sample code to run the login and registration process
        Registration register = new Registration();
        register.userInput();
        login app = new login(register);
        app.log();
    }
}