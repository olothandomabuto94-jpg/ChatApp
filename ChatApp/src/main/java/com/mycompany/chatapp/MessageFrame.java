package com.mycompany.chatapp;

import javax.swing.*;

public class MessageFrame {

    public MessageFrame() {

        JFrame window = new JFrame("Send Message");
        window.setSize(400, 300);
        window.setLayout(null);
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setLocationRelativeTo(null);

        // user inputs
        JLabel recLbl = new JLabel("Recipient (+27...):");
        recLbl.setBounds(30, 30, 150, 25);
        window.add(recLbl);

        JTextField recField = new JTextField();
        recField.setBounds(180, 30, 170, 25);
        window.add(recField);

        JLabel msgLbl = new JLabel("Message:");
        msgLbl.setBounds(30, 70, 150, 25);
        window.add(msgLbl);

        JTextArea msgField = new JTextArea();
        msgField.setBounds(180, 70, 170, 80);
        window.add(msgField);

        // actions to send, store ro dsicard
        JButton sendBtn = new JButton("Send");
        sendBtn.setBounds(30, 180, 90, 30);
        window.add(sendBtn);

        JButton storeBtn = new JButton("Store");
        storeBtn.setBounds(140, 180, 90, 30);
        window.add(storeBtn);

        JButton discardBtn = new JButton("Discard");
        discardBtn.setBounds(250, 180, 90, 30);
        window.add(discardBtn);

        // output
        JLabel output = new JLabel("");
        output.setBounds(30, 220, 330, 25);
        window.add(output);

        // send message flow
        sendBtn.addActionListener(e -> {

            Message msg = new Message(recField.getText(), msgField.getText());

            String validation = msg.checkMessageLength();

            if (!validation.equals("Message ready to send.")) {
                output.setText(validation);
                return;
            }

            if (msg.checkRecipientCell() == 0) {
                output.setText("Invalid cell number.");
                return;
            }

            JOptionPane.showMessageDialog(
                    null,
                    msg.printSingleMessage()
            );

            output.setText(msg.sentMessage("Send"));
        });

        // massage storage
        storeBtn.addActionListener(e -> {

            Message msg = new Message(recField.getText(), msgField.getText());
            output.setText(msg.sentMessage("Store"));
        });

        // discard message
        discardBtn.addActionListener(e -> output.setText("Message discarded."));

        window.setVisible(true);
    }
}