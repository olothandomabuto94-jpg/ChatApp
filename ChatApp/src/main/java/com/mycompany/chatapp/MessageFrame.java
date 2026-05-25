package com.mycompany.chatapp;

import javax.swing.*;

public class MessageFrame {

    private int maxMessages;
    private int sentCount = 0;

    public MessageFrame() {

        // Ask user how many messages they want to send in this session
        String input = JOptionPane.showInputDialog(
                "How many messages do you want to send?"
        );

        try {
            maxMessages = Integer.parseInt(input);
        } catch (Exception e) {
            // If user types nonsense, we don’t crash the whole app
            JOptionPane.showMessageDialog(null, "Invalid input. Defaulting to 1.");
            maxMessages = 1;
        }

        menu();
    }

    private void menu() {

        // Main loop that keeps the app alive until user quits
        while (true) {

            String option = JOptionPane.showInputDialog(
                    "QuickChat Menu\n\n"
                    + "1. Send Messages\n"
                    + "2. Show Recently Sent Messages\n"
                    + "3. Quit"
            );

            if (option == null) return;

            switch (option) {

                case "1":

                    // Stop user if they've already reached their message quota
                    if (sentCount >= maxMessages) {
                        JOptionPane.showMessageDialog(
                                null,
                                "Message limit reached (" + maxMessages + ")."
                        );
                        break;
                    }

                    sendMessages();
                    break;

                case "2":

                    // Feature placeholder (as per assignment spec)
                    JOptionPane.showMessageDialog(
                            null,
                            "Coming Soon."
                    );
                    break;

                case "3":

                    // Exit flow + summary
                    JOptionPane.showMessageDialog(
                            null,
                            "Goodbye."
                    );

                    JOptionPane.showMessageDialog(
                            null,
                            "Total messages sent: " + Message.returnTotalMessages()
                    );

                    System.exit(0);
                    break;

                default:

                    // Handles invalid menu input
                    JOptionPane.showMessageDialog(
                            null,
                            "Please enter 1, 2 or 3."
                    );
            }
        }
    }

    private void sendMessages() {

        // Loop until we hit the max message limit
        while (sentCount < maxMessages) {

            String recipient = JOptionPane.showInputDialog(
                    "Recipient (+27 or 0xxxxxxxxx):"
            );

            String text = JOptionPane.showInputDialog(
                    "Message (max 250 chars):"
            );

            // FIX: constructor now matches Message(int, String, String)
            Message msg = new Message(sentCount + 1, recipient, text);

            // Validate recipient format before proceeding
            String recipientCheck = msg.checkRecipientCell();
            if (!recipientCheck.equals("Cell phone number successfully captured.")) {
                JOptionPane.showMessageDialog(null, recipientCheck);
                continue;
            }

            // Validate message length constraint
            String lengthCheck = msg.checkMessageLength();
            if (!lengthCheck.equals("Message ready to send.")) {
                JOptionPane.showMessageDialog(null, lengthCheck);
                continue;
            }

            // Show final formatted message preview
            JOptionPane.showMessageDialog(
                    null,
                    msg.printSingleMessage()
            );

            // Ask user what to do with the message
            String action = JOptionPane.showInputDialog(
                    "Send / Store / Discard"
            );

            if (action == null) continue;

            // Execute chosen action
            JOptionPane.showMessageDialog(
                    null,
                    msg.sentMessage(action)
            );

            // Only count as “sent” if actually sent
            if (action.equalsIgnoreCase("Send")) {
                sentCount++;
            }

            // Stop once quota is reached
            if (sentCount >= maxMessages) {
                JOptionPane.showMessageDialog(
                        null,
                        "All messages complete.\nTotal sent: " + Message.returnTotalMessages()
                );
                break;
            }
        }
    }
}