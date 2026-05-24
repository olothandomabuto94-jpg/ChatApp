package com.mycompany.chatapp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    @Test
    public void testMessageID() {
        Message msg = new Message(0, "+27718693002", "Hi Mike, can you join us for dinner tonight");
        assertTrue(msg.checkMessageID());
    }

    @Test
    public void testValidMessageLength() {
        Message msg = new Message(0, "+27718693002", "Hi Mike, can you join us for dinner tonight");
        assertEquals("Message ready to send.", msg.checkMessageLength());
    }

    @Test
    public void testInvalidMessageLength() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 251; i++) {
            sb.append("a");
        }

        Message msg = new Message(1, "+27718693002", sb.toString());
        assertEquals("Message exceeds 250 characters by 1, please reduce size.", msg.checkMessageLength());
    }

    @Test
    public void testValidRecipient() {
        Message msg = new Message(0, "+27718693002", "Hi Mike, can you join us for dinner tonight");
        assertEquals(1, msg.checkRecipientCell());
        assertEquals("Cell phone number successfully captured.", msg.checkRecipientCellMessage());
    }

    @Test
    public void testInvalidRecipient() {
        Message msg = new Message(1, "08575975889", "Hi Keegan, did you receive the payment?");
        assertEquals(0, msg.checkRecipientCell());
        assertEquals(
                "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.",
                msg.checkRecipientCellMessage()
        );
    }

    @Test
    public void testMessageHash() {
        Message msg = new Message(0, "+27718693002", "Hi Mike, can you join us for dinner tonight");
        assertEquals("00:0:HITONIGHT", msg.createMessageHash());
    }
}