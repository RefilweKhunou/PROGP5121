/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/EmptyTestNGTest.java to edit this template
 */
package part1prog;

import org.testng.Assert;
import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import part1prog.Registration;

/**
 *
 * @author RC_Student_lab
 */
public class Part1PROGNGTest {

    Registration registertest = new Registration();

    @Test
    public void validUseernameTest() {
        Assert.assertTrue(registertest.checkUsername("Re_f!"));
    }

    /**
     * Test of checkUsername method, of class Method.
     */
    @Test
    public void UsernameWithoutHashTest() {
        Assert.assertFalse(registertest.checkUsername("Refilwe"));

    }

    @Test
    public void ShortUsernameTest() {
        Assert.assertFalse(registertest.checkUsername("Fifi"));
    }

    @Test
    public void ValidPasswordTest() {
        // valid password: >=8 chars, has digit, uppercase, special char
        Assert.assertTrue(registertest.checkPassword("Fifi@190Kagiso"));
    }

    @Test
    public void PasswordTooShortTest() {
        // too short
        Assert.assertFalse(registertest.checkPassword("Fifi"));
    }

    @Test
    public void PasswordMissingDigitTest() {
        // missing digit
        Assert.assertFalse(registertest.checkPassword("RefilweK"));
    }

    @Test
    public void PasswordMissingUppercaseTest() {
        // missing uppercase
        Assert.assertFalse(registertest.checkPassword("fifi_khu!"));
    }

    
// ---------- CELLPHONE NUMBER TESTS ----------
    @Test
    public void ValidCellphoneNumberTest() {
        // correct: +27 + 9 digits (total length 12)
        Assert.assertTrue(registertest.checkCellphoneNumber("+27711402910"));
    }

    @Test
    public void MissingCountryCodeTest() {
        // missing +27
        Assert.assertFalse(registertest.checkCellphoneNumber("0711402910"));
    }

    @Test
    public void TooShortCellphoneNumberTest() {
        // only 8 digits after country code
        Assert.assertFalse(registertest.checkCellphoneNumber("+2771402910"));
    }
}

