package com.mycompany.chatapp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    @Test
    public void testValidRecipient() {

        Message msg = new Message(1, "+27718693002",
                "Hi Mike, can you join us for dinner tonight");

        assertEquals(
                "Cell phone number successfully captured.",
                msg.checkRecipientCell()
        );
    }

    @Test
    public void testInvalidRecipient() {

        Message msg = new Message(2, "08575975889",
                "Hi Keegan, did you receive the payment?");

        assertEquals(
                "Invalid cell number.",
                msg.checkRecipientCell()
        );
    }

    @Test
    public void testMessageLengthValid() {

        Message msg = new Message(3, "+27838968976",
                "Short message");

        assertEquals(
                "Message ready to send.",
                msg.checkMessageLength()
        );
    }

    @Test
    public void testMessageHash() {

        Message msg = new Message(4, "+27838968976",
                "Hi Mike dinner tonight");

        String hash = msg.createMessageHash(4);

        assertNotNull(hash);
        assertTrue(hash.contains(":"));
    }

    @Test
    public void testSendMessage() {

        Message msg = new Message(5, "+27838968976",
                "Hello");

        assertEquals(
                "Message sent.",
                msg.sentMessage("Send")
        );
    }
}