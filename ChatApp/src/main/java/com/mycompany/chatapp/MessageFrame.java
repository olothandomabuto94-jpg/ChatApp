package com.mycompany.chatapp;

import javax.swing.*;

public class MessageFrame {

    private int maxMessages;
    private int sentCount = 0;

    public MessageFrame() {

        // Keep asking until we get a valid number (cancel = default to 1)
        while (true) {
            String input = JOptionPane.showInputDialog(
                    "How many messages do you want to send?"
            );

            // Cancel on this prompt — default to 1 and continue
            if (input == null) {
                JOptionPane.showMessageDialog(null, "Defaulting to 1 message.");
                maxMessages = 1;
                break;
            }

            try {
                maxMessages = Integer.parseInt(input.trim());
                if (maxMessages > 0) break;
                JOptionPane.showMessageDialog(null, "Please enter a number greater than 0.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter a number.");
            }
        }

        menu();
    }

    private void menu() {

        while (true) {

            String option = JOptionPane.showInputDialog(
                    "QuickChat Menu\n\n"
                    + "1. Send Messages\n"
                    + "2. Show Recently Sent Messages\n"
                    + "3. Quit\n"
                    + "4. Stored Messages"
            );

            // Cancel on main menu — loop back (don't exit)
            if (option == null) {
                continue;
            }

            switch (option.trim()) {

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
                            Message.printMessages()
                    );
                    break;

                case "3":
                    JOptionPane.showMessageDialog(null, "Goodbye.");
                    JOptionPane.showMessageDialog(
                            null,
                            "Total messages sent: " + Message.returnTotalMessages()
                    );
                    System.exit(0);
                    break;

                case "4":
                    storedMessagesMenu();
                    break;

                default:
                    JOptionPane.showMessageDialog(null, "Please enter 1, 2, 3 or 4.");
            }
        }
    }

    private void storedMessagesMenu() {

        while (true) {

            String option = JOptionPane.showInputDialog(
                    "Stored Messages Menu\n\n"
                    + "a. Display sender and recipient of all stored messages\n"
                    + "b. Display the longest stored message\n"
                    + "c. Search for a message by ID\n"
                    + "d. Search messages by recipient\n"
                    + "e. Delete a message by hash\n"
                    + "f. Display full report\n"
                    + "x. Back to main menu"
            );

            // Cancel or x — go back to main menu
            if (option == null || option.equalsIgnoreCase("x")) return;

            switch (option.toLowerCase().trim()) {

                case "a":
                    JOptionPane.showMessageDialog(
                            null,
                            Message.displayStoredSenderRecipient()
                    );
                    break;

                case "b":
                    JOptionPane.showMessageDialog(
                            null,
                            "Longest message:\n\n" + Message.displayLongestMessage()
                    );
                    break;

                case "c":
                    String searchID = JOptionPane.showInputDialog("Enter Message ID:");
                    // Cancel — go back to stored messages menu
                    if (searchID == null) break;
                    if (!searchID.trim().isEmpty()) {
                        JOptionPane.showMessageDialog(
                                null,
                                Message.searchByMessageID(searchID.trim())
                        );
                    }
                    break;

                case "d":
                    String searchRecipient = JOptionPane.showInputDialog(
                            "Enter recipient number (+27xxxxxxxxx):"
                    );
                    // Cancel — go back to stored messages menu
                    if (searchRecipient == null) break;
                    if (!searchRecipient.trim().isEmpty()) {
                        JOptionPane.showMessageDialog(
                                null,
                                Message.searchByRecipient(searchRecipient.trim())
                        );
                    }
                    break;

                case "e":
                    String hash = JOptionPane.showInputDialog("Enter message hash to delete:");
                    // Cancel — go back to stored messages menu
                    if (hash == null) break;
                    if (!hash.trim().isEmpty()) {
                        JOptionPane.showMessageDialog(
                                null,
                                Message.deleteMessageByHash(hash.trim())
                        );
                    }
                    break;

                case "f":
                    JOptionPane.showMessageDialog(
                            null,
                            Message.displayReport()
                    );
                    break;

                default:
                    JOptionPane.showMessageDialog(null, "Please enter a, b, c, d, e, f or x.");
            }
        }
    }

    private void sendMessages() {

        while (sentCount < maxMessages) {

            String recipient = JOptionPane.showInputDialog(
                    "Recipient (+27 or 0xxxxxxxxx):"
            );
            // Cancel — go back to main menu
            if (recipient == null) return;

            String text = JOptionPane.showInputDialog(
                    "Message (max 250 chars):"
            );
            // Cancel — go back to main menu
            if (text == null) return;

            Message msg = new Message(sentCount + 1, recipient, text);

            String recipientCheck = msg.checkRecipientCell();
            if (!recipientCheck.equals("Cell phone number successfully captured.")) {
                JOptionPane.showMessageDialog(null, recipientCheck);
                continue;
            }

            String lengthCheck = msg.checkMessageLength();
            if (!lengthCheck.equals("Message ready to send.")) {
                JOptionPane.showMessageDialog(null, lengthCheck);
                continue;
            }

            JOptionPane.showMessageDialog(null, msg.printSingleMessage());

            String action = JOptionPane.showInputDialog("Send / Store / Discard");
            // Cancel on action — go back to main menu
            if (action == null) return;

            JOptionPane.showMessageDialog(null, msg.sentMessage(action));

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