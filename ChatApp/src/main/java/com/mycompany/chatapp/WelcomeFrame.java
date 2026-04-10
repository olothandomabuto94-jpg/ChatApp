package com.mycompany.chatapp;

import javax.swing.*;

public class WelcomeFrame {

    public WelcomeFrame(String messageText) {

        JFrame window = new JFrame("Welcome");
        window.setSize(400, 200);
        window.setLayout(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);

        JLabel message = new JLabel("<html><center>" + messageText + "</center></html>");
        message.setBounds(50, 50, 300, 50);

        window.add(message);
        window.setVisible(true);
    }
}