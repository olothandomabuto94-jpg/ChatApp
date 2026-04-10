package com.mycompany.chatapp;

import javax.swing.*;
import java.awt.event.*;

public class RegisterFrame {

    public RegisterFrame(Login login) {

        JFrame window = new JFrame("Register");
        window.setSize(420, 380);
        window.setLayout(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);

        // Labels + fields aligned properly

        JLabel nameLbl = new JLabel("First Name:");
        nameLbl.setBounds(30, 20, 120, 25);
        window.add(nameLbl);

        JTextField nameField = new JTextField();
        nameField.setBounds(160, 20, 200, 25);
        window.add(nameField);

        JLabel surnameLbl = new JLabel("Surname:");
        surnameLbl.setBounds(30, 60, 120, 25);
        window.add(surnameLbl);

        JTextField surnameField = new JTextField();
        surnameField.setBounds(160, 60, 200, 25);
        window.add(surnameField);

        JLabel userLbl = new JLabel("Username:");
        userLbl.setBounds(30, 100, 120, 25);
        window.add(userLbl);

        JTextField userField = new JTextField();
        userField.setBounds(160, 100, 200, 25);
        window.add(userField);

        JLabel passLbl = new JLabel("Password:");
        passLbl.setBounds(30, 140, 120, 25);
        window.add(passLbl);

        JPasswordField passField = new JPasswordField();
        passField.setBounds(160, 140, 200, 25);
        window.add(passField);

        JLabel cellLbl = new JLabel("Cell Number:");
        cellLbl.setBounds(30, 180, 120, 25);
        window.add(cellLbl);

        JTextField cellField = new JTextField();
        cellField.setBounds(160, 180, 200, 25);
        window.add(cellField);

        JLabel feedback = new JLabel("");
        feedback.setBounds(30, 220, 350, 40);
        window.add(feedback);

        JButton btn = new JButton("Register");
        btn.setBounds(160, 280, 120, 30);
        window.add(btn);

        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String result = login.registerUser(
                        nameField.getText(),
                        surnameField.getText(),
                        userField.getText(),
                        new String(passField.getPassword()),
                        cellField.getText()
                );

                feedback.setText("<html>" + result + "</html>");

                if (result.equals("Account successfully created.")) {
                    window.dispose();
                    new LoginFrame(login);
                }
            }
        });

        window.setVisible(true);
    }
}