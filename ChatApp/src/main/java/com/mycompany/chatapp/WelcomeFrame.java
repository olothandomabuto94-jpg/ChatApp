package com.mycompany.chatapp;

import javax.swing.*;

public class WelcomeFrame {

    // Tracks how many messages the user is allowed to send this session
    private int totalAllowed = 0;

    // Tracks how many messages have actually been sent
    private int sentCount = 0;

    public WelcomeFrame(String message) {

        // window for QuickChat dasjboard
        JFrame window = new JFrame("QuickChat");
        window.setSize(420, 300);
        window.setLayout(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);

        // Welcome label
        JLabel label = new JLabel("Welcome to QuickChat.");
        label.setBounds(120, 20, 200, 30);
        window.add(label);

        // Asks user how many messages they want to send
        String input = JOptionPane.showInputDialog(
                "How many messages do you want to send?"
        );

        // Converts input to integer
        try {
            totalAllowed = Integer.parseInt(input);
        } catch (Exception e) {
            totalAllowed = 0;
        }

        // BUTTON 1
        JButton sendBtn = new JButton("1) Send Messages");
        sendBtn.setBounds(100, 70, 200, 30);
        window.add(sendBtn);

        // BUTTON 2
        JButton recentBtn = new JButton("2) Show Recently Sent");
        recentBtn.setBounds(100, 120, 200, 30);
        window.add(recentBtn);

        // BUTTON 3
        JButton quitBtn = new JButton("3) Quit");
        quitBtn.setBounds(100, 170, 200, 30);
        window.add(quitBtn);

        //Send Messages button
        sendBtn.addActionListener(e -> {

            // Only allow sending if user has not exceeded limit
            if (sentCount < totalAllowed) {

                // Open the message input window
                new MessageFrame();

                // Increment how many messages were initiated
                sentCount++;

                // If user reaches limit, show total sent messages
                if (sentCount == totalAllowed) {

                    JOptionPane.showMessageDialog(
                            null,
                            "Total messages sent: "
                                    + Message.returnTotalMessages()
                    );
                }

            } else {

                // Prevents user from sending more than allowed amount
                JOptionPane.showMessageDialog(
                        null,
                        "You already entered your set number of messages."
                );
            }
        });

        // Shows recently sent messages (placeholder feature)
        recentBtn.addActionListener(e -> {

            JOptionPane.showMessageDialog(
                    null,
                    "Coming Soon."
            );
        });

        // ACTION: Quit application completely
        quitBtn.addActionListener(e -> {
            System.exit(0);
        });

        // Make the window visible
        window.setVisible(true);
    }
}