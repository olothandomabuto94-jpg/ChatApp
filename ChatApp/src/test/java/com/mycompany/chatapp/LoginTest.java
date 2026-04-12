package com.mycompany.chatapp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {

    Login login = new Login();

    // ---------------- USERNAME ----------------
    @Test
    public void testValidUsername() {
        assertTrue(login.checkUserName("kyl_1"));
    }

    @Test
    public void testInvalidUsername() {
        assertFalse(login.checkUserName("kyle!!!!"));
    }

    // ---------------- PASSWORD ----------------
    @Test
    public void testValidPassword() {
        assertTrue(login.checkPasswordComplexity("Ch&sec@ke99!"));
    }

    @Test
    public void testInvalidPassword() {
        assertFalse(login.checkPasswordComplexity("password"));
    }

    // ---------------- CELL NUMBER ----------------
    @Test
    public void testValidCell() {
        assertTrue(login.checkCellPhoneNumber("+27838968976"));
    }

    @Test
    public void testInvalidCell() {
        assertFalse(login.checkCellPhoneNumber("08966553"));
    }
    
    @Test
    public void testCellAutoConvert() {
    assertTrue(login.checkCellPhoneNumber("0838968976"));
    }

    // ---------------- LOGIN ----------------
    @Test
    public void testLoginFailBeforeRegister() {
        assertFalse(login.loginUser("abc", "1234"));
    }
}