package com.mycompany.chatapp;

import javax.swing.*;

public class MessageFrame {

    public MessageFrame(int messageNumber) {

        JDialog dialog = new JDialog((java.awt.Frame) null, "Send Message", true);
        dialog.setSize(440, 320);
        dialog.setLayout(null);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setLocationRelativeTo(null);

        JLabel heading = new JLabel("Message " + (messageNumber + 1));
        heading.setBounds(30, 15, 120, 25);
        dialog.add(heading);

        JLabel recLbl = new JLabel("Recipient:");
        recLbl.setBounds(30, 50, 120, 25);
        dialog.add(recLbl);

        JTextField recField = new JTextField();
        recField.setBounds(150, 50, 220, 25);
        dialog.add(recField);

        JLabel msgLbl = new JLabel("Message:");
        msgLbl.setBounds(30, 85, 120, 25);
        dialog.add(msgLbl);

        JTextArea msgArea = new JTextArea();
        msgArea.setLineWrap(true);
        msgArea.setWrapStyleWord(true);
        msgArea.setBounds(150, 85, 220, 90);
        dialog.add(msgArea);

        JLabel actionLbl = new JLabel("Choose action:");
        actionLbl.setBounds(30, 185, 120, 25);
        dialog.add(actionLbl);

        JButton sendBtn = new JButton("Send");
        sendBtn.setBounds(150, 185, 65, 30);
        dialog.add(sendBtn);

        JButton storeBtn = new JButton("Store");
        storeBtn.setBounds(220, 185, 65, 30);
        dialog.add(storeBtn);

        JButton discardBtn = new JButton("Discard");
        discardBtn.setBounds(290, 185, 80, 30);
        dialog.add(discardBtn);

        JLabel status = new JLabel("");
        status.setBounds(30, 230, 360, 25);
        dialog.add(status);

        sendBtn.addActionListener(e -> handleAction(
                dialog,
                messageNumber,
                recField.getText().trim(),
                msgArea.getText().trim(),
                "Send"
        ));

        storeBtn.addActionListener(e -> handleAction(
                dialog,
                messageNumber,
                recField.getText().trim(),
                msgArea.getText().trim(),
                "Store"
        ));

        discardBtn.addActionListener(e -> {
            Message msg = new Message(messageNumber, recField.getText().trim(), msgArea.getText().trim());
            JOptionPane.showMessageDialog(dialog, msg.SentMessage("Discard"));
            dialog.dispose();
        });

        dialog.setVisible(true);
    }

    private void handleAction(JDialog dialog, int messageNumber, String recipient, String text, String action) {

        if (recipient.isEmpty() || text.isEmpty()) {
            JOptionPane.showMessageDialog(dialog, "Please fill in both fields.");
            return;
        }

        Message msg = new Message(messageNumber, recipient, text);

        if (msg.checkRecipientCell() == 0) {
            JOptionPane.showMessageDialog(dialog, msg.checkRecipientCellMessage());
            return;
        }

        String lengthStatus = msg.checkMessageLength();
        if (!lengthStatus.equals("Message ready to send.")) {
            JOptionPane.showMessageDialog(dialog, lengthStatus);
            return;
        }

        if (action.equalsIgnoreCase("Send")) {
            JOptionPane.showMessageDialog(
                    dialog,
                    msg.printSingleMessage()
            );
            JOptionPane.showMessageDialog(
                    dialog,
                    msg.SentMessage("Send")
            );
            dialog.dispose();
        } else if (action.equalsIgnoreCase("Store")) {
            JOptionPane.showMessageDialog(
                    dialog,
                    msg.SentMessage("Store")
            );
            dialog.dispose();
        }
    }
}