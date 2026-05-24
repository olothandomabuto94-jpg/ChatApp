package com.mycompany.chatapp;

import javax.swing.*;

public class WelcomeFrame {

    private int maxMessages;
    private int messagesEntered = 0;
    private JButton sendButton;

    public WelcomeFrame() {

        maxMessages = askForMessageCount();

        JFrame window = new JFrame("QuickChat");
        window.setSize(420, 320);
        window.setLayout(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);

        JLabel title = new JLabel("Welcome to QuickChat.");
        title.setBounds(130, 20, 200, 25);
        window.add(title);

        JLabel menuLbl = new JLabel("Choose an option:");
        menuLbl.setBounds(150, 50, 150, 25);
        window.add(menuLbl);

        sendButton = new JButton("1. Send Messages");
        sendButton.setBounds(100, 90, 220, 30);
        window.add(sendButton);

        JButton recentButton = new JButton("2. Show recently sent messages");
        recentButton.setBounds(100, 135, 220, 30);
        window.add(recentButton);

        JButton quitButton = new JButton("3. Quit");
        quitButton.setBounds(100, 180, 220, 30);
        window.add(quitButton);

        sendButton.addActionListener(e -> {

            if (messagesEntered >= maxMessages) {
                JOptionPane.showMessageDialog(
                        window,
                        "Total number of messages sent: " + Message.returnTotalMessages()
                );
                sendButton.setEnabled(false);
                return;
            }

            new MessageFrame(messagesEntered);
            messagesEntered++;

            if (messagesEntered >= maxMessages) {
                JOptionPane.showMessageDialog(
                        window,
                        "Total number of messages sent: " + Message.returnTotalMessages()
                );
                sendButton.setEnabled(false);
            }
        });

        recentButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(window, "Coming Soon.");
        });

        quitButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(
                    window,
                    "Total number of messages sent: " + Message.returnTotalMessages()
            );
            window.dispose();
            System.exit(0);
        });

        window.setVisible(true);
    }

    private int askForMessageCount() {
        while (true) {
            String input = JOptionPane.showInputDialog(
                    null,
                    "How many messages do you want to send?"
            );

            if (input == null) {
                System.exit(0);
            }

            try {
                int count = Integer.parseInt(input.trim());
                if (count > 0) {
                    return count;
                }
            } catch (Exception ignored) {
            }

            JOptionPane.showMessageDialog(
                    null,
                    "Please enter a valid number greater than 0."
            );
        }
    }
}