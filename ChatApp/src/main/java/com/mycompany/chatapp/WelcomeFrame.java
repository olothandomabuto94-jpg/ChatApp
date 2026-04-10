package com.mycompany.chatapp;

import javax.swing.*;

public class WelcomeFrame {

    public WelcomeFrame(String message) {

        JFrame window = new JFrame("Welcome");
        window.setSize(400, 200);
        window.setLayout(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);

        JLabel label = new JLabel("<html><center>" + message + "</center></html>");
        label.setBounds(50, 50, 300, 50);
        window.add(label);

        window.setVisible(true);
    }
}