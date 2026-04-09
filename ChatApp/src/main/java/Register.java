import javax.swing.*;
import java.awt.event.*;

public class Register {

    public Register(Login login) {

        JFrame window = new JFrame("Create Account");
        window.setSize(420, 380);
        window.setLayout(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);

        // Labels
        JLabel nameLbl = new JLabel("First Name:");
        nameLbl.setBounds(30, 20, 120, 25);
        window.add(nameLbl);

        JLabel surnameLbl = new JLabel("Surname:");
        surnameLbl.setBounds(30, 60, 120, 25);
        window.add(surnameLbl);

        JLabel userLbl = new JLabel("Username:");
        userLbl.setBounds(30, 100, 120, 25);
        window.add(userLbl);

        JLabel passLbl = new JLabel("Password:");
        passLbl.setBounds(30, 140, 120, 25);
        window.add(passLbl);

        JLabel cellLbl = new JLabel("Cell Number:");
        cellLbl.setBounds(30, 180, 120, 25);
        window.add(cellLbl);

        // Inputs
        JTextField nameField = new JTextField();
        nameField.setBounds(150, 20, 200, 25);
        window.add(nameField);

        JTextField surnameField = new JTextField();
        surnameField.setBounds(150, 60, 200, 25);
        window.add(surnameField);

        JTextField userField = new JTextField();
        userField.setBounds(150, 100, 200, 25);
        window.add(userField);

        JPasswordField passField = new JPasswordField();
        passField.setBounds(150, 140, 200, 25);
        window.add(passField);

        JTextField cellField = new JTextField();
        cellField.setBounds(150, 180, 200, 25);
        window.add(cellField);

        // Message
        JLabel feedback = new JLabel("");
        feedback.setBounds(30, 220, 340, 40);
        window.add(feedback);

        // Button
        JButton registerBtn = new JButton("Register");
        registerBtn.setBounds(150, 280, 120, 30);
        window.add(registerBtn);

        // Button action
        registerBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String name = nameField.getText();
                String surname = surnameField.getText();
                String username = userField.getText();
                String password = new String(passField.getPassword());
                String cell = cellField.getText();

                String result = login.registerUser(name, surname, username, password, cell);

                feedback.setText("<html>" + result + "</html>");

                if (result.contains("successfully")) {
                    window.dispose();
                    new LoginFrame(login);
                }
            }
        });

        window.setVisible(true);
    }
}