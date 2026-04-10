package com.mycompany.chatapp;

import javax.swing.*;

public class LoginFrame {

    public LoginFrame(Login login) {

        JFrame window = new JFrame("Login");
        window.setSize(350, 250);
        window.setLayout(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);

        JTextField userField = new JTextField();
        userField.setBounds(120, 50, 180, 25);

        JPasswordField passField = new JPasswordField();
        passField.setBounds(120, 90, 180, 25);

        JLabel message = new JLabel("");
        message.setBounds(30, 125, 280, 25);

        JButton loginBtn = new JButton("Login");
        loginBtn.setBounds(120, 160, 100, 30);

        window.add(new JLabel("Username:")).setBounds(30,50,100,25);
        window.add(new JLabel("Password:")).setBounds(30,90,100,25);

        window.add(userField);
        window.add(passField);
        window.add(message);
        window.add(loginBtn);

        loginBtn.addActionListener(e -> {

            String user = userField.getText();
            String pass = new String(passField.getPassword());

            String status = login.returnLoginStatus(user, pass);

            if (login.loginUser(user, pass)) {
                window.dispose();
                new WelcomeFrame(status);
            } else {
                message.setText(status);
            }
        });

        window.setVisible(true);
    }
}