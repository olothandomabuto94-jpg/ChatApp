package com.mycompany.chatapp;

public class Main {

    // Entry point: everything starts here, like opening a gate into the system
    public static void main(String[] args) {

        Login login = new Login();

        // Start at registration screen first (no access without identity)
        new RegisterFrame(login);
    }
}