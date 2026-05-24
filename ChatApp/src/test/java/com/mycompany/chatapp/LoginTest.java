package com.mycompany.chatapp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {

    Login login = new Login();

    @Test
    public void testValidUsername() {
        assertTrue(login.checkUserName("ab_c"));
    }

    @Test
    public void testInvalidUsername() {
        assertFalse(login.checkUserName("abcdef"));
    }

    @Test
    public void testValidPassword() {
        assertTrue(login.checkPasswordComplexity("Pass@123"));
    }

    @Test
    public void testInvalidPassword() {
        assertFalse(login.checkPasswordComplexity("password"));
    }

    @Test
    public void testValidCell() {
        assertEquals(
                "Cell phone number successfully captured.",
                login.checkCellPhoneNumber("0838968976")
        );
    }

    @Test
    public void testInvalidCell() {
        assertEquals(
                "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.",
                login.checkCellPhoneNumber("08575975889")
        );
    }

    @Test
    public void testLoginFail() {
        assertFalse(login.loginUser("a", "b"));
    }
}