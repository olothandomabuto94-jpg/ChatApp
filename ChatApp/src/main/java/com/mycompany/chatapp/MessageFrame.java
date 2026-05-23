package com.mycompany.chatapp;

import javax.swing.*;

public class MessageFrame {

    public MessageFrame() {

        JFrame window = new JFrame("QuickChat");
        window.setSize(400, 300);
        window.setLayout(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);

        JLabel recLbl = new JLabel("Recipient (+27...):");
        recLbl.setBounds(30, 30, 150, 25);
        window.add(recLbl);

        JTextField recField = new JTextField();
        recField.setBounds(180, 30, 180, 25);
        window.add(recField);

        JLabel msgLbl = new JLabel("Message:");
        msgLbl.setBounds(30, 70, 150, 25);
        window.add(msgLbl);

        JTextField msgField = new JTextField();
        msgField.setBounds(180, 70, 180, 25);
        window.add(msgField);

        JButton sendBtn = new JButton("Send");
        sendBtn.setBounds(150, 120, 100, 30);
        window.add(sendBtn);

        JTextArea output = new JTextArea();
        output.setBounds(30, 170, 330, 70);
        output.setEditable(false);
        window.add(output);

        sendBtn.addActionListener(e -> {

            String recipient = recField.getText();
            String message = msgField.getText();

            if (message.length() > 250) {
                output.setText("Message too long (max 250 chars)");
                return;
            }

            Message msg = new Message(recipient, message);

            output.setText(msg.getMessageDetails());
        });

        window.setVisible(true);
    }
}