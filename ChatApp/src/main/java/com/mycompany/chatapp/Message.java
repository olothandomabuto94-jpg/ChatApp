package com.mycompany.chatapp;

import java.io.FileWriter;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Message {

    private int messageNumber;
    private String recipient;
    private String messageText;
    private String messageID;
    private String messageHash;

    private static int totalMessages = 0;
    private static ArrayList<String> messages = new ArrayList<>();
    private static ArrayList<String> storedMessagesJson = new ArrayList<>();

    public Message(int messageNumber, String recipient, String messageText) {

        if (recipient != null && recipient.startsWith("0") && recipient.length() == 10) {
            recipient = "+27" + recipient.substring(1);
        }

        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;

        this.messageID = generateID();
        this.messageHash = createMessageHash();
    }

    private String generateID() {
        int randomPart = (int) (Math.random() * 100000000);
        return "00" + String.format("%08d", randomPart);
    }

    public boolean checkMessageID() {
        return messageID.length() <= 10;
    }

    public int checkRecipientCell() {
        if (recipient != null && recipient.matches("\\+27\\d{9}")) {
            return 1;
        }
        return 0;
    }

    public String checkRecipientCellMessage() {
        if (checkRecipientCell() == 1) {
            return "Cell phone number successfully captured.";
        }

        return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
    }

    public String checkMessageLength() {

        if (messageText != null && messageText.length() <= 250) {
            return "Message ready to send.";
        }

        int extra = messageText == null ? 250 : messageText.length() - 250;
        return "Message exceeds 250 characters by " + extra + ", please reduce size.";
    }

    public String createMessageHash() {

        String[] words = messageText.trim().split("\\s+");
        String firstWord = words[0].replaceAll("[^A-Za-z0-9]", "");
        String lastWord = words[words.length - 1].replaceAll("[^A-Za-z0-9]", "");

        return (messageID.substring(0, 2)
                + ":" + messageNumber
                + ":" + firstWord + lastWord).toUpperCase();
    }

    public String SentMessage() {
        String choice = JOptionPane.showInputDialog(
                "Choose one:\n1. Send\n2. Store\n3. Discard"
        );

        if (choice == null) {
            return "Message discarded.";
        }

        return SentMessage(choice);
    }

    public String SentMessage(String action) {

        if (action.equalsIgnoreCase("Send")) {
            totalMessages++;
            messages.add(printSingleMessage());
            return "Message sent.";
        }

        if (action.equalsIgnoreCase("Store")) {
            storeMessage();
            return "Message stored.";
        }

        return "Message discarded.";
    }

    public String sentMessage(String action) {
        return SentMessage(action);
    }

    public void storeMessage() {

        storedMessagesJson.add(
                "  {\n" +
                "    \"messageNumber\": " + messageNumber + ",\n" +
                "    \"messageID\": \"" + escapeJson(messageID) + "\",\n" +
                "    \"messageHash\": \"" + escapeJson(messageHash) + "\",\n" +
                "    \"recipient\": \"" + escapeJson(recipient) + "\",\n" +
                "    \"message\": \"" + escapeJson(messageText) + "\"\n" +
                "  }"
        );

        try (FileWriter writer = new FileWriter("stored_messages.json")) {
            writer.write("[\n");

            for (int i = 0; i < storedMessagesJson.size(); i++) {
                writer.write(storedMessagesJson.get(i));
                if (i < storedMessagesJson.size() - 1) {
                    writer.write(",\n");
                } else {
                    writer.write("\n");
                }
            }

            writer.write("]\n");
        } catch (Exception e) {
        }
    }

    private String escapeJson(String value) {
        if (value == null) {
            return "";
        }
        return value.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    public String printSingleMessage() {
        return "MessageID: " + messageID
                + "\nMessage Hash: " + messageHash
                + "\nRecipient: " + recipient
                + "\nMessage: " + messageText;
    }

    public static String printMessages() {
        if (messages.isEmpty()) {
            return "No messages sent.";
        }

        return String.join("\n\n", messages);
    }

    public static int returnTotalMessages() {
        return totalMessages;
    }
}