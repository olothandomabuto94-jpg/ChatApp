package com.mycompany.chatapp;

import java.util.ArrayList;

public class Message {

    private String messageID;
    private String recipient;
    private String message;
    private String messageHash;

    private static int totalMessages = 0;
    private static ArrayList<String> sentMessages = new ArrayList<>();

    public Message(String recipient, String message) {
        this.messageID = String.format("%02d", totalMessages);
        this.recipient = recipient;
        this.message = message;
        this.messageHash = createMessageHash();
    }

    // massage ID check
    public boolean checkMessageID() {
        return messageID.length() <= 10;
    }

    // recipient number check
    public int checkRecipientCell() {

        if (recipient.matches("^\\+27\\d{9}$")) {
            return 1;
        }

        return 0;
    }

    // checks message length is <= 250 characters
    public String checkMessageLength() {

        if (message.length() <= 250) {
            return "Message ready to send.";
        }

        int extra = message.length() - 250;

        return "Message exceeds 250 characters by "
                + extra
                + ", please reduce size.";
    }

    // Hash
    public String createMessageHash() {

        String[] words = message.trim().split("\\s+");

        String firstTwo =
                words[0].substring(0, 2);

        String lastWord =
                words[words.length - 1];

        return messageID
                + ":"
                + totalMessages
                + ":"
                + (firstTwo + lastWord).toUpperCase();
    }

    // send, store or discard
    public String sentMessage(String option) {

        if (option.equals("Send")) {

            totalMessages++;

            sentMessages.add(printSingleMessage());

            return "Message sent.";
        }

        if (option.equals("Store")) {

            storeMessage();

            return "Message stored.";
        }

        return "Message discarded.";
    }

    // Display one
    public String printSingleMessage() {

        return "MessageID: " + messageID
                + "\nMessage Hash: " + messageHash
                + "\nRecipient: " + recipient
                + "\nMessage: " + message;
    }

    // Display all
    public static String printMessages() {

        String result = "";

        for (String msg : sentMessages) {
            result += msg + "\n\n";
        }

        return result;
    }

    // Total
    public static int returnTotalMessages() {
        return totalMessages;
    }

    // JSON Storage
    public void storeMessage() {

        String json =
                "{"
                + "\"messageID\":\"" + messageID + "\","
                + "\"recipient\":\"" + recipient + "\","
                + "\"message\":\"" + message + "\""
                + "}";

        System.out.println(json);
    }
}