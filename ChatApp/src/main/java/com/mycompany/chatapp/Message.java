package com.mycompany.chatapp;

import java.io.FileWriter;
import java.io.IOException;

public class Message {

    private String recipient;
    private String messageText;
    private String messageID;
    private String messageHash;

    private static int totalMessages = 0;

    // construct
    public Message(String recipient, String messageText) {
        this.recipient = recipient;
        this.messageText = messageText;

        this.messageID = generateID();
        this.messageHash = createMessageHash();
    }

    // ID generation simple random 10-digit
    private String generateID() {
        long id = (long)(Math.random() * 9_000_000_000L) + 1_000_000_000L;
        return String.valueOf(id);
    }

    // validate message length
    public String checkMessageLength() {
        if (messageText.length() <= 250) {
            return "Message ready to send.";
        }
        return "Message exceeds 250 characters by "
                + (messageText.length() - 250)
                + ", please reduce size.";
    }

    // validate recipient (+27 format expected from GUI)
    public int checkRecipientCell() {
        if (recipient != null && recipient.matches("\\+27\\d{9}")) {
            return 1;
        }
        return 0;
    }

    // hash creation
    public String createMessageHash() {
        String first = messageText.split("\\s+")[0];
        String last = messageText.split("\\s+")[messageText.split("\\s+").length - 1];

        return (messageID.substring(0, 2)
                + ":" + (totalMessages + 1)
                + ":" + first + last).toUpperCase();
    }

    // send, store, discard handler
    public String sentMessage(String action) {

        if (action.equalsIgnoreCase("Send")) {
            totalMessages++;
            return "Message sent.";
        }

        if (action.equalsIgnoreCase("Store")) {
            storeMessage();
            return "Message stored.";
        }

        return "Message discarded.";
    }

    // JSON storage
    public void storeMessage() {

        String json =
                "{\n" +
                "  \"messageID\": \"" + messageID + "\",\n" +
                "  \"recipient\": \"" + recipient + "\",\n" +
                "  \"message\": \"" + messageText + "\",\n" +
                "  \"hash\": \"" + messageHash + "\"\n" +
                "}\n";

        try (FileWriter writer = new FileWriter("stored_messages.json", true)) {
            writer.write(json);
            writer.write(",\n");
        } catch (IOException e) {
            System.out.println("Error storing message: " + e.getMessage());
        }
    }

    // display message
    public String printSingleMessage() {
        return "Message ID: " + messageID +
                "\nHash: " + messageHash +
                "\nRecipient: " + recipient +
                "\nMessage: " + messageText;
    }

    // total sent messages
    public static int returnTotalMessages() {
        return totalMessages;
    }
}