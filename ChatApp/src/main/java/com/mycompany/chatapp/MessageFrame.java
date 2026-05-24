package com.mycompany.chatapp;

import javax.swing.*;

public class MessageFrame {

    private int maxMessages;
    private int sentCount = 0;

    public MessageFrame() {

        String input = JOptionPane.showInputDialog(
                "How many messages do you want to send?"
        );

        try {
            maxMessages = Integer.parseInt(input);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Invalid input. Defaulting to 1.");
            maxMessages = 1;
        }

        menu();
    }

    private void menu() {

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

                    JOptionPane.showMessageDialog(
                            null,
                            "Coming Soon."
                    );
                    break;

                case "3":

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

                    JOptionPane.showMessageDialog(
                            null,
                            "Please enter 1, 2 or 3."
                    );
            }
        }
    }

    private void sendMessages() {

        while (sentCount < maxMessages) {

            String recipient = JOptionPane.showInputDialog(
                    "Recipient (+27 or 0xxxxxxxxx):"
            );

            String text = JOptionPane.showInputDialog(
                    "Message (max 250 chars):"
            );

            // FIXED constructor match
            Message msg = new Message(sentCount + 1, recipient, text);

            if (!msg.checkRecipientCell()
                    .equals("Cell phone number successfully captured.")) {

                JOptionPane.showMessageDialog(null, msg.checkRecipientCell());
                continue;
            }

            if (!msg.checkMessageLength()
                    .equals("Message ready to send.")) {

                JOptionPane.showMessageDialog(null, msg.checkMessageLength());
                continue;
            }

            JOptionPane.showMessageDialog(
                    null,
                    msg.printSingleMessage()
            );

            String action = JOptionPane.showInputDialog(
                    "Send / Store / Discard"
            );

            if (action == null) return;

            JOptionPane.showMessageDialog(
                    null,
                    msg.sentMessage(action)
            );

            if (action.equalsIgnoreCase("Send")) {
                sentCount++;
            }

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