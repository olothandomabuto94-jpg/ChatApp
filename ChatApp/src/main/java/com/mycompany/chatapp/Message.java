package com.mycompany.chatapp;

import java.io.FileWriter;
import java.util.ArrayList;

public class Message {

    private String recipient;
    private String messageText;
    private String messageID;
    private String messageHash;

    private static int totalMessages = 0;
    private static ArrayList<String> messages = new ArrayList<>();

    // Core message object creation (everything gets built here)
    public Message(int id, String recipient, String messageText) {

        // Normalize South African numbers into +27 format
        if (recipient != null && recipient.startsWith("0") && recipient.length() == 10) {
            recipient = "+27" + recipient.substring(1);
        }

        this.recipient = recipient;
        this.messageText = messageText;
        this.messageID = generateID();

        // Hash is generated immediately for integrity tracking
        this.messageHash = createMessageHash(id);
    }

    // Random ID generator (simulates unique message identity)
    private String generateID() {
        long id = (long) (Math.random() * 9000000000L) + 1000000000L;
        return String.valueOf(id);
    }

    // Validates recipient format
    public String checkRecipientCell() {

        if (recipient != null && recipient.matches("\\+27\\d{9}")) {
            return "Cell phone number successfully captured.";
        }

        return "Invalid cell number.";
    }

    // Ensures message is not too long for system constraints
    public String checkMessageLength() {

        if (messageText != null && messageText.length() <= 250) {
            return "Message ready to send.";
        }

        return "Message too long.";
    }

    // Hash creation logic (first + last word + id influence)
    public String createMessageHash(int id) {

        String[] words = messageText.trim().split("\\s+");

        String first = words.length > 0 ? words[0] : "";
        String last = words.length > 1 ? words[words.length - 1] : first;

        return (messageID.substring(0, 2)
                + ":" + id
                + ":" + first + last).toUpperCase();
    }

    // Handles what happens after user chooses action
    public String sentMessage(String action) {

        if (action == null) return "Message discarded.";

        if (action.equalsIgnoreCase("Send")) {
            totalMessages++;
            messages.add(printSingleMessage());
            storeMessage();
            return "Message sent.";
        }

        if (action.equalsIgnoreCase("Store")) {
            storeMessage();
            return "Message stored.";
        }

        return "Message discarded.";
    }

    // Saves message to file for persistence (basic JSON-ish storage)
    public void storeMessage() {

        try (FileWriter writer = new FileWriter("stored_messages.json", true)) {

            writer.write(
                    "{ \"id\":\"" + messageID
                            + "\", \"recipient\":\"" + recipient
                            + "\", \"message\":\"" + messageText
                            + "\", \"hash\":\"" + messageHash
                            + "\" }\n"
            );

        } catch (Exception ignored) {
        }
    }

    // Pretty-print for UI display
    public String printSingleMessage() {

        return "Message ID: " + messageID
                + "\nHash: " + messageHash
                + "\nRecipient: " + recipient
                + "\nMessage: " + messageText;
    }

    // Returns all messages sent during runtime
    public static String printMessages() {

        if (messages.isEmpty()) return "No messages sent.";

        return String.join("\n\n", messages);
    }

    // Tracks total sent messages across app session
    public static int returnTotalMessages() {
        return totalMessages;
    }
}