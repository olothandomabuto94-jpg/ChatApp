package com.mycompany.chatapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    // Reset all static state and clear JSON file before each test
    @BeforeEach
    public void setUp() {
        Message.resetAll();
        new File("stored_messages.json").delete();
    }

    // --- Existing tests (Part 1/2) ---

    @Test
    public void testValidRecipient() {
        Message msg = new Message(1, "+27718693002",
                "Hi Mike, can you join us for dinner tonight");
        assertEquals("Cell phone number successfully captured.", msg.checkRecipientCell());
    }

    @Test
    public void testInvalidRecipient() {
        Message msg = new Message(2, "08575975889",
                "Hi Keegan, did you receive the payment?");
        assertEquals("Invalid cell number.", msg.checkRecipientCell());
    }

    @Test
    public void testMessageLengthValid() {
        Message msg = new Message(3, "+27838968976", "Short message");
        assertEquals("Message ready to send.", msg.checkMessageLength());
    }

    @Test
    public void testMessageHash() {
        Message msg = new Message(4, "+27838968976", "Hi Mike dinner tonight");
        String hash = msg.createMessageHash(4);
        assertNotNull(hash);
        assertTrue(hash.contains(":"));
    }

    @Test
    public void testSendMessage() {
        Message msg = new Message(5, "+27838968976", "Hello");
        assertEquals("Message sent.", msg.sentMessage("Send"));
    }

    // --- Part 3 tests ---

    // Populate all 5 test messages as per the spec, then check sent array
    // contains messages 1 and 4 (the ones flagged "Sent")
    @Test
    public void testSentMessagesArrayCorrectlyPopulated() {

        Message msg1 = new Message(1, "+27834557896", "Did you get the cake?");
        msg1.sentMessage("Send");

        Message msg2 = new Message(2, "+27838884567",
                "Where are you? You are late! I have asked you to be on time.");
        msg2.sentMessage("Store");

        Message msg3 = new Message(3, "+27834484567", "Yohoooo, I am at your gate.");
        msg3.sentMessage("Discard");

        Message msg4 = new Message(4, "0838884567", "It is dinner time!");
        msg4.sentMessage("Send");

        Message msg5 = new Message(5, "+27838884567", "Ok, I am leaving without you.");
        msg5.sentMessage("Store");

        String sentJoined = String.join(" ", Message.getSentMessages());

        assertTrue(sentJoined.contains("Did you get the cake?"));
        assertTrue(sentJoined.contains("It is dinner time!"));
    }

    // Longest message across all stored entries should be message 2
    @Test
    public void testDisplayLongestMessage() {

        Message msg1 = new Message(1, "+27834557896", "Did you get the cake?");
        msg1.sentMessage("Send");

        Message msg2 = new Message(2, "+27838884567",
                "Where are you? You are late! I have asked you to be on time.");
        msg2.sentMessage("Store");

        Message msg3 = new Message(3, "+27834484567", "Yohoooo, I am at your gate.");
        msg3.sentMessage("Discard");

        Message msg4 = new Message(4, "0838884567", "It is dinner time!");
        msg4.sentMessage("Send");

        assertEquals(
                "Where are you? You are late! I have asked you to be on time.",
                Message.displayLongestMessage()
        );
    }

    // Search by message ID — message 4 (0838884567 → normalized to +27838884567)
    @Test
    public void testSearchByMessageID() {

        Message msg4 = new Message(4, "0838884567", "It is dinner time!");
        msg4.sentMessage("Send");

        // Get the generated ID for this message
        String id = Message.getMessageIDs().get(0);

        String result = Message.searchByMessageID(id);

        assertTrue(result.contains("It is dinner time!"));
    }

    // Search by recipient +27838884567 should return messages 2 and 5
    @Test
    public void testSearchByRecipient() {

        Message msg1 = new Message(1, "+27834557896", "Did you get the cake?");
        msg1.sentMessage("Send");

        Message msg2 = new Message(2, "+27838884567",
                "Where are you? You are late! I have asked you to be on time.");
        msg2.sentMessage("Store");

        Message msg3 = new Message(3, "+27834484567", "Yohoooo, I am at your gate.");
        msg3.sentMessage("Discard");

        Message msg4 = new Message(4, "0838884567", "It is dinner time!");
        msg4.sentMessage("Send");

        Message msg5 = new Message(5, "+27838884567", "Ok, I am leaving without you.");
        msg5.sentMessage("Store");

        String result = Message.searchByRecipient("+27838884567");

        assertTrue(result.contains(
                "Where are you? You are late! I have asked you to be on time."));
        assertTrue(result.contains("Ok, I am leaving without you."));
    }

    // Delete message 2 by its hash — confirm success message returned
    @Test
    public void testDeleteMessageByHash() {

        Message msg2 = new Message(2, "+27838884567",
                "Where are you? You are late! I have asked you to be on time.");
        msg2.sentMessage("Store");

        // Get the actual hash that was generated for this message
        String hash = Message.getMessageHashes().get(0);

        String result = Message.deleteMessageByHash(hash);

        assertTrue(result.contains(
                "Where are you? You are late! I have asked you to be on time."));
        assertTrue(result.contains("successfully deleted"));
    }

    // Report should contain hash, recipient, and message for all stored entries
    @Test
    public void testDisplayReport() {

        Message msg1 = new Message(1, "+27834557896", "Did you get the cake?");
        msg1.sentMessage("Send");

        Message msg2 = new Message(2, "+27838884567",
                "Where are you? You are late! I have asked you to be on time.");
        msg2.sentMessage("Store");

        String report = Message.displayReport();

        assertTrue(report.contains("Hash:"));
        assertTrue(report.contains("Recipient:"));
        assertTrue(report.contains("Message:"));
    }
}