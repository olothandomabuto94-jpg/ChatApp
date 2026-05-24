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

    public Message(int id, String recipient, String messageText) {

        if (recipient != null && recipient.startsWith("0") && recipient.length() == 10) {
            recipient = "+27" + recipient.substring(1);
        }

        this.recipient = recipient;
        this.messageText = messageText;
        this.messageID = generateID();

        this.messageHash = createMessageHash(id);
    }

    private String generateID() {
        long id = (long) (Math.random() * 9000000000L) + 1000000000L;
        return String.valueOf(id);
    }

    public String checkRecipientCell() {

        if (recipient != null && recipient.matches("\\+27\\d{9}")) {
            return "Cell phone number successfully captured.";
        }

        return "Invalid cell number.";
    }

    public String checkMessageLength() {

        if (messageText != null && messageText.length() <= 250) {
            return "Message ready to send.";
        }

        return "Message too long.";
    }

    public String createMessageHash(int id) {

        String[] words = messageText.trim().split("\\s+");

        String first = words.length > 0 ? words[0] : "";
        String last = words.length > 1 ? words[words.length - 1] : first;

        return (messageID.substring(0, 2)
                + ":" + id
                + ":" + first + last).toUpperCase();
    }

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

    public String printSingleMessage() {

        return "Message ID: " + messageID
                + "\nHash: " + messageHash
                + "\nRecipient: " + recipient
                + "\nMessage: " + messageText;
    }

    public static String printMessages() {

        if (messages.isEmpty()) return "No messages sent.";

        return String.join("\n\n", messages);
    }

    public static int returnTotalMessages() {
        return totalMessages;
    }
}