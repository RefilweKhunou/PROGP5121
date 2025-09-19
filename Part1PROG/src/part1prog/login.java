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
class login {

    Registration register;

    public login(Registration register) {
        this.register = register;
    }

    public void log() {
        Scanner scan = new Scanner(System.in);
        String loginUsername;
        String loginPassword;

        do{
        System.out.println("Enter Username");
        loginUsername = scan.nextLine();

        System.out.println("Enter password");
        loginPassword = scan.nextLine();}while(!checkUserDetails(loginUsername,loginPassword));
    } // end of log method

    public boolean checkUserDetails(String username, String password) {
        if (username.equals(register.username) && password.equals(register.password)) {
            System.out.println("Welcome" + register.name);
            return false;

        } else {
            System.out.println("incorrect details");
            return false;
        }
    }
}
