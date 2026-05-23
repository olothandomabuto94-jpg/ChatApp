package com.mycompany.chatapp;

public class Message {

    private String recipient;
    private String messageText;
    private String messageID;
    private String messageHash;

    private static int messageCount = 0;

    public Message(String recipient, String messageText) {
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageID = generateID();
        this.messageHash = createHash();
        messageCount++;
    }

    private String generateID() {
        return String.valueOf((int)(Math.random() * 9000) + 1000);
    }

    private String createHash() {
        String[] words = messageText.trim().split("\\s+");

        String first = words.length > 0 ? words[0] : "";
        String last = words.length > 0 ? words[words.length - 1] : "";

        return (messageID + ":" + messageCount + ":" + first + last).toUpperCase();
    }

    public String getMessageDetails() {
        return "ID: " + messageID +
               "\nHash: " + messageHash +
               "\nTo: " + recipient +
               "\nMessage: " + messageText;
    }

    public static int getTotalMessages() {
        return messageCount;
    }
}