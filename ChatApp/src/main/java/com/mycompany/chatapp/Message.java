package com.mycompany.chatapp;

import java.io.FileWriter;

public class Message {

    private String recipient;
    private String messageText;
    private String messageID;
    private String messageHash;

    private static int totalMessages = 0;

    public Message(String recipient, String messageText) {
        this.recipient = recipient;
        this.messageText = messageText;

        this.messageID = generateID();
        this.messageHash = createMessageHash();
    }

    private String generateID() {
        long id = (long)(Math.random() * 9_000_000_000L) + 1_000_000_000L;
        return String.valueOf(id);
    }

    public boolean checkMessageID() {
        return messageID.length() <= 10;
    }

    public String checkRecipientCell() {
        if (recipient != null && recipient.matches("\\+27\\d{9}")) {
            return "Cell phone number successfully captured.";
        }
        return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
    }

    public String checkMessageLength() {
        if (messageText.length() <= 250) {
            return "Message ready to send.";
        }

        return "Message exceeds 250 Characters by "
                + (messageText.length() - 250)
                + ", please reduce size.";
    }

    public String createMessageHash() {

        String[] words = messageText.trim().split("\\s+");

        String first = words[0];
        String last = words[words.length - 1];

        return (messageID.substring(0, 2)
                + ":" + (totalMessages + 1)
                + ":" + first + last).toUpperCase();
    }

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

    public void storeMessage() {

        String fileName = "stored_messages.json";

        try {
            java.io.File file = new java.io.File(fileName);

            boolean isNew = !file.exists();

            FileWriter writer = new FileWriter(file, true);

            if (isNew) {
                writer.write("[\n");
            }

            String json =
                    "  {\n" +
                    "    \"messageID\": \"" + messageID + "\",\n" +
                    "    \"recipient\": \"" + recipient + "\",\n" +
                    "    \"message\": \"" + messageText + "\",\n" +
                    "    \"hash\": \"" + messageHash + "\"\n" +
                    "  },\n";

            writer.write(json);
            writer.close();

        } catch (Exception e) {
            System.out.println("Error storing message: " + e.getMessage());
        }
    }

    public String printSingleMessage() {
        return "Message ID: " + messageID +
                "\nHash: " + messageHash +
                "\nRecipient: " + recipient +
                "\nMessage: " + messageText;
    }

    public static int returnTotalMessages() {
        return totalMessages;
    }
}