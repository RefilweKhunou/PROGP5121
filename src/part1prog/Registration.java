/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package part1prog;

import javax.swing.JOptionPane;

/**
 *
 * @author RC_Student_lab
 */
public class Registration {

    String name;
    String surname;
    String username;
    String password;
    String cellphoneNumber;
    

    public void userInput() {

        name = JOptionPane.showInputDialog("Please enter your name: ");
        surname = JOptionPane.showInputDialog("Please enter your surname: ");

        do {
            username = JOptionPane.showInputDialog("Please enter your username: ");
        } while (!checkUsername(username));

        do {
            password = JOptionPane.showInputDialog("Please enter your password: ");
        } while (!checkPassword(password));

        // ask once for cellphone number until valid:
        do {
            cellphoneNumber = JOptionPane.showInputDialog("Please enter your cellphone number: ");

            if (checkCellphoneNumber(cellphoneNumber)) {
                JOptionPane.showMessageDialog(null, "Login successful!");
            } else {
                // invalid cellphone: login failed
                JOptionPane.showMessageDialog(null, "Login failed. Cellphone number incorrect.");
            }
        } while (!checkCellphoneNumber(cellphoneNumber)); // repeat until valid
    }

    public boolean checkUsername(String username) {
        if (username.contains("_") && username.length() <= 5) {
            JOptionPane.showMessageDialog(null, "Welcome " + name + " " + surname + " it is great to see you.");
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Your username did not meet the required conditions.");
            return false;
        }
    }//end of checkUsername method 

    public boolean checkPassword(String password) {
        boolean hasMinLength = password.length() >= 8;
        boolean hasDigit = password.matches(".*\\d.*");
        boolean hasUppercase = password.matches(".*[A-Z].*");
        boolean hasSpecialCharacters = password.matches(".*[!@#$%&^*()_-].*");

        if (hasMinLength && hasDigit && hasUppercase && hasSpecialCharacters) {
            JOptionPane.showMessageDialog(null, "Password is successfully captured");
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Password does not meet the complexity requirements.");
            return false;
        }
    }
    //end of check of password

    public boolean checkCellphoneNumber(String phoneNumber) {
        boolean countryCode = phoneNumber.contains("+27");
        boolean hasDigit = phoneNumber.matches(".*\\d{9}.*");
        boolean hasMinLength = phoneNumber.length() == 12;

        if (countryCode && hasDigit && hasMinLength) {
            JOptionPane.showMessageDialog(null, "Phone number has been successfully captured");
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Phone number did not meet conditions, try again");
            return false;
        }
    }

    public String registerUser() {
        if (!checkUsername(username)) {
            return "The username is incorrectly formatted.";
        }

        if (!checkPassword(password)) {
            return "The password does not meet the complexity requirements.";
        }

        // If both username and password are valid:
        return "User registered successfully.";
    }

    public boolean loginUser() {
        String loginUsername = JOptionPane.showInputDialog("Please enter your username to login: ");
        String loginPassword = JOptionPane.showInputDialog("Please enter your password to login: ");

        if (loginUsername.equals(this.username) && loginPassword.equals(this.password)) {
            JOptionPane.showMessageDialog(null, "Login successful!");
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Login failed. Username or password incorrect.");
            return false;
        }
    }
    public String getUsername() {
        return username;
    }

}
