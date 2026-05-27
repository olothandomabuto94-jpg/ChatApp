package com.mycompany.chatapp;

import javax.swing.*;

public class LoginFrame {

    public LoginFrame(Login login) {

        // Basic login window setup (old school Swing vibes, no fancy frameworks here)
        JFrame window = new JFrame("Login");
        window.setSize(350, 250);
        window.setLayout(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);

        // Username label + input field pairing
        JLabel userLbl = new JLabel("Username:");
        userLbl.setBounds(30, 50, 100, 25);
        window.add(userLbl);

        JTextField userField = new JTextField();
        userField.setBounds(120, 50, 180, 25);
        window.add(userField);

        // Password section (kept hidden because chaos should be encrypted 😌)
        JLabel passLbl = new JLabel("Password:");
        passLbl.setBounds(30, 90, 100, 25);
        window.add(passLbl);

        JPasswordField passField = new JPasswordField();
        passField.setBounds(120, 90, 180, 25);
        window.add(passField);

        // Message label for login feedback (errors, success, etc.)
        JLabel msg = new JLabel("");
        msg.setBounds(30, 130, 300, 25);
        window.add(msg);

        // Login button triggers authentication flow
        JButton btn = new JButton("Login");
        btn.setBounds(120, 160, 100, 30);
        window.add(btn);

        btn.addActionListener(e -> {

            // Pull typed input from UI fields
            String u = userField.getText();
            String p = new String(passField.getPassword());

            // If credentials match, move into the app flow
            if (login.loginUser(u, p)) {

                window.dispose();

                JOptionPane.showMessageDialog(
                        null,
                        "Welcome to QuickChat."
                );

                // Jump into message system (core feature)
                new MessageFrame();

            } else {
                // Otherwise show why login failed
                msg.setText(login.returnLoginStatus(u, p));
            }
        });

        window.setVisible(true);
    }
}