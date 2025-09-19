/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package part1prog;

/**
 *
 * @author RC_Student_lab
 */
public class Part1PROG {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Registration register= new Registration();
        register.userInput();
        
        login login = new login(register);
        login.log();
        
    }
    
}
