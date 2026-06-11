package com.mycompany.chatapp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class Message {

    private String recipient;
    private String messageText;
    private String messageID;
    private String messageHash;

    private static int totalMessages = 0;

    // Part 1/2 array: sent messages (full formatted strings)
    private static ArrayList<String> messages = new ArrayList<>();

    // Part 3 arrays
    private static ArrayList<String> disregardedMessages = new ArrayList<>();
    private static ArrayList<String> storedMessages      = new ArrayList<>();
    private static ArrayList<String> messageHashes       = new ArrayList<>();
    private static ArrayList<String> messageIDs          = new ArrayList<>();

    // Core message object creation
    public Message(int id, String recipient, String messageText) {

        if (recipient != null && recipient.startsWith("0") && recipient.length() == 10) {
            recipient = "+27" + recipient.substring(1);
        }

        this.recipient   = recipient;
        this.messageText = messageText;
        this.messageID   = generateID();
        this.messageHash = createMessageHash(id);
    }

    // Random 10-digit ID
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

    // Validates message length
    public String checkMessageLength() {
        if (messageText != null && messageText.length() <= 250) {
            return "Message ready to send.";
        }
        return "Message too long.";
    }

    // Hash: first two digits of ID + position + first word + last word (uppercased)
    public String createMessageHash(int id) {
        String[] words = messageText.trim().split("\\s+");
        String first = words.length > 0 ? words[0] : "";
        String last  = words.length > 1 ? words[words.length - 1] : first;
        return (messageID.substring(0, 2) + ":" + id + ":" + first + last).toUpperCase();
    }

    // Handles send / store / discard and populates all relevant arrays
    public String sentMessage(String action) {

        if (action == null) {
            disregardedMessages.add(messageText);
            return "Message discarded.";
        }

        if (action.equalsIgnoreCase("Send")) {
            totalMessages++;
            messages.add(printSingleMessage());
            messageHashes.add(messageHash);
            messageIDs.add(messageID);
            storeMessage();
            return "Message sent.";
        }

        if (action.equalsIgnoreCase("Store")) {
            messageHashes.add(messageHash);
            messageIDs.add(messageID);
            storeMessage();
            return "Message stored.";
        }

        // Discard
        disregardedMessages.add(messageText);
        return "Message discarded.";
    }

    // Writes message to JSON file
    public void storeMessage() {
        try (FileWriter writer = new FileWriter("stored_messages.json", true)) {
            writer.write(
                "{ \"id\":\"" + messageID
                + "\", \"recipient\":\"" + recipient
                + "\", \"message\":\"" + messageText
                + "\", \"hash\":\"" + messageHash
                + "\" }\n"
            );
        } catch (Exception ignored) {}
    }

    // Reads stored_messages.json and populates storedMessages array
    public static void loadStoredMessages() {
        storedMessages.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader("stored_messages.json"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    storedMessages.add(line);
                }
            }
        } catch (Exception ignored) {}
    }

    // --- Part 3 feature methods ---

    // a. Display sender (ID) and recipient of all stored messages
    public static String displayStoredSenderRecipient() {
        loadStoredMessages();
        if (storedMessages.isEmpty()) return "No stored messages found.";

        StringBuilder sb = new StringBuilder();
        for (String entry : storedMessages) {
            String id        = extractJsonField(entry, "id");
            String recipient = extractJsonField(entry, "recipient");
            sb.append("ID: ").append(id)
              .append(" | Recipient: ").append(recipient)
              .append("\n");
        }
        return sb.toString().trim();
    }

    // b. Display the longest stored message
    public static String displayLongestMessage() {
        loadStoredMessages();
        if (storedMessages.isEmpty()) return "No stored messages found.";

        String longest = "";
        for (String entry : storedMessages) {
            String text = extractJsonField(entry, "message");
            if (text.length() > longest.length()) {
                longest = text;
            }
        }
        return longest.isEmpty() ? "No messages found." : longest;
    }

    // c. Search by message ID — return recipient and message
    public static String searchByMessageID(String searchID) {
        loadStoredMessages();
        for (String entry : storedMessages) {
            String id = extractJsonField(entry, "id");
            if (id.equals(searchID)) {
                String recipient = extractJsonField(entry, "recipient");
                String message   = extractJsonField(entry, "message");
                return "Recipient: " + recipient + "\nMessage: " + message;
            }
        }
        return "Message ID not found.";
    }

    // d. Search all messages for a particular recipient
    public static String searchByRecipient(String searchRecipient) {
        loadStoredMessages();
        StringBuilder sb = new StringBuilder();
        for (String entry : storedMessages) {
            String recipient = extractJsonField(entry, "recipient");
            if (recipient.equals(searchRecipient)) {
                String message = extractJsonField(entry, "message");
                sb.append(message).append("\n");
            }
        }
        return sb.length() == 0 ? "No messages found for that recipient." : sb.toString().trim();
    }

    // e. Delete a message by its hash
    public static String deleteMessageByHash(String hash) {
        loadStoredMessages();
        String deletedMessage = null;

        for (String entry : storedMessages) {
            String entryHash = extractJsonField(entry, "hash");
            if (entryHash.equalsIgnoreCase(hash)) {
                deletedMessage = extractJsonField(entry, "message");
                storedMessages.remove(entry);
                break;
            }
        }

        if (deletedMessage == null) return "Hash not found.";

        // Rewrite file without the deleted entry
        try (FileWriter writer = new FileWriter("stored_messages.json", false)) {
            for (String entry : storedMessages) {
                writer.write(entry + "\n");
            }
        } catch (Exception ignored) {}

        return "Message: \"" + deletedMessage + "\" successfully deleted.";
    }

    // f. Full report of all stored messages
    public static String displayReport() {
        loadStoredMessages();
        if (storedMessages.isEmpty()) return "No stored messages found.";

        StringBuilder sb = new StringBuilder("=== Message Report ===\n\n");
        for (String entry : storedMessages) {
            sb.append("Hash:      ").append(extractJsonField(entry, "hash")).append("\n");
            sb.append("Recipient: ").append(extractJsonField(entry, "recipient")).append("\n");
            sb.append("Message:   ").append(extractJsonField(entry, "message")).append("\n");
            sb.append("---------------------\n");
        }
        return sb.toString().trim();
    }

    // Helper: pulls a value out of a simple JSON line by key name
    private static String extractJsonField(String json, String key) {
        String search = "\"" + key + "\":\"";
        int start = json.indexOf(search);
        if (start == -1) return "";
        start += search.length();
        int end = json.indexOf("\"", start);
        if (end == -1) return "";
        return json.substring(start, end);
    }

    // Pretty-print for a single message
    public String printSingleMessage() {
        return "Message ID: " + messageID
             + "\nHash: "      + messageHash
             + "\nRecipient: " + recipient
             + "\nMessage: "   + messageText;
    }

    // Returns all sent messages during this session
    public static String printMessages() {
        if (messages.isEmpty()) return "No messages sent.";
        return String.join("\n\n", messages);
    }

    // Getters for test access
    public static ArrayList<String> getSentMessages()       { return messages; }
    public static ArrayList<String> getDisregardedMessages(){ return disregardedMessages; }
    public static ArrayList<String> getStoredMessages()     { return storedMessages; }
    public static ArrayList<String> getMessageHashes()      { return messageHashes; }
    public static ArrayList<String> getMessageIDs()         { return messageIDs; }

    public static int returnTotalMessages() { return totalMessages; }

    // Resets all static state — useful for unit tests
    public static void resetAll() {
        totalMessages = 0;
        messages.clear();
        disregardedMessages.clear();
        storedMessages.clear();
        messageHashes.clear();
        messageIDs.clear();
    }
}