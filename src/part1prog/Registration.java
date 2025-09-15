/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package part1prog;

import java.util.Scanner;

/**
 *
 * @author RC_Student_lab
 */
public class Registration {
    Scanner scan = new Scanner(System.in);
    String name;
    String surname;
    String username;
    String password;
    String cellphoneNumber;

    public void userInput() {

        System.out.println("Please enter your name: ");
        name = scan.nextLine();

        System.out.println("Please enter your surname: ");
        surname = scan.nextLine();

        do {
            System.out.println("Please enter your username: ");
            username = scan.nextLine();
        } while (!checkUsername(username));

        do {
            System.out.println("Please enter your password: ");
            password = scan.nextLine();
        } while (!checkPassword(password));

        // ask once for cellphone number until valid:
        do {
            System.out.println("Please enter your cellphone number: ");
            cellphoneNumber = scan.nextLine();

            if (checkCellphoneNumber(cellphoneNumber)) {
                // valid cellphone: login successful
                System.out.println("Login successful!");
            } else {
                // invalid cellphone: login failed
                System.out.println("Login failed. Cellphone number incorrect.");
            }

        } while (!checkCellphoneNumber(cellphoneNumber)); // repeat until valid

    } //end of userInput()

    public boolean checkUsername(String username) {
        if (username.contains("_") && username.length() <= 5) {
            System.out.println("Welcome " + name + " " + surname + " it is great to see you.");
            return true;
        } else {
            System.out.println("Your username did not meet the following conditions");
            return false;
        }
    } //end of checkUsername method 

    public boolean checkPassword(String password) {
        //check password length
        boolean hasMinLength = password.length() >= 8;

        boolean hasDigit = password.matches(".*\\d.*");

        //check password has uppercase
        boolean hasUppercase = password.matches(".*[A-Z].*");

        //check if password has special chacater
        boolean hasSpecialCharacters = password.matches(".*[!@#$%&^*()_-].*");

        if (hasMinLength && hasDigit && hasUppercase && hasSpecialCharacters) {
            System.out.println("Password is successfully captured");
            return true;
        } else {
            System.out.println("Your password is not correctly formatted, please ensure that the "
                    + "password contains at least eight characters, a capital letter, a number, and a special character.");
            return false;
        }
    }
    //end of check of password

    public boolean checkCellphoneNumber(String phoneNumber) {
        //contains country code
        boolean countryCode = phoneNumber.contains("+27");

        //contains 9 digits
        boolean hasDigit = phoneNumber.matches(".*\\d{9}.*");

        boolean hasMinLength = phoneNumber.length() == 12;

        if (countryCode && hasDigit && hasMinLength) {
            System.out.println("Phone number has been successfully captured");
            return true;
        } else {
            System.out.println("Phone number did not meet conditions, try again");
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
        System.out.println("Please enter your username to login: ");
        String loginUsername = scan.nextLine();

        System.out.println("Please enter your password to login: ");
        String loginPassword = scan.nextLine();

        if (loginUsername.equals(this.username) && loginPassword.equals(this.password)) {
            System.out.println("Login successful!");
            return true;
        } else {
            System.out.println("Login failed. Username or password incorrect.");
            return false;
        }
    }

    public String returnLoginStatus() {
        boolean loginSuccessful = loginUser();
        if (loginSuccessful) {
            return "Login was successful.";
        } else {
            return "Login failed. Username or password incorrect.";
        }
    }
}
