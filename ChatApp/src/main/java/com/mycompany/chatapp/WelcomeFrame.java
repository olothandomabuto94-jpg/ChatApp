package com.mycompany.chatapp;

import javax.swing.*;

public class WelcomeFrame {

    public WelcomeFrame(String message) {

        // Simple welcome screen after login success
        JFrame window = new JFrame("Welcome");
        window.setSize(400, 200);
        window.setLayout(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);

        // Centered message display (HTML used for simple formatting)
        JLabel label = new JLabel("<html><center>" + message + "</center></html>");
        label.setBounds(50, 50, 300, 50);
        window.add(label);

        // Continue button moves user into messaging system
        JButton continueBtn = new JButton("Continue");
        continueBtn.setBounds(140, 120, 120, 30);
        window.add(continueBtn);

        continueBtn.addActionListener(e -> {
            window.dispose();
            new MessageFrame();
        });

        window.setVisible(true);
    }
}