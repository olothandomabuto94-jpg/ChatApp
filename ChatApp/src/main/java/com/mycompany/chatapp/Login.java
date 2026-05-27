package com.mycompany.chatapp;

public class Login {

    private String username;
    private String password;
    private String cellNumber;
    private String name;
    private String surname;

    // Quick rule check: username must be short and include underscore (feels like a simple handle system)
    public boolean checkUserName(String username) {
        return username.length() <= 5 && username.contains("_");
    }

    // Password rules: we force a bit of "real-world chaos protection" here
    public boolean checkPasswordComplexity(String password) {

        if (password.length() < 8) return false;

        boolean capital = false;
        boolean number = false;
        boolean special = false;

        // Walk through every character like we're inspecting a security checkpoint
        for (char ch : password.toCharArray()) {
            if (Character.isUpperCase(ch)) capital = true;
            else if (Character.isDigit(ch)) number = true;
            else if (!Character.isLetterOrDigit(ch)) special = true;
        }

        return capital && number && special;
    }

    // Phone numbers get “normalized” into international format here (+27 logic)
    public String checkCellPhoneNumber(String cellNumber) {

        if (cellNumber.startsWith("0") && cellNumber.length() == 10) {
            cellNumber = "+27" + cellNumber.substring(1);
        }

        // Final sanity check: must match SA-style +27 format
        if (cellNumber.matches("\\+27\\d{9}")) {
            return "Cell phone number successfully captured.";
        }

        return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
    }

    // Big moment: user registration pipeline
    public String registerUser(String name, String surname,
            String username, String password,
            String cellNumber) {

        // Step 1: username validation gate
        if (!checkUserName(username)) {
            return "Username must contain _ and be 5 characters or less.";
        }

        // Step 2: password security gate
        if (!checkPasswordComplexity(password)) {
            return "Password must be 8+ chars with capital, number and special character.";
        }

        // Step 3: phone validation (we reuse logic so nothing slips through cracks)
        String cellStatus = checkCellPhoneNumber(cellNumber);
        if (!cellStatus.equals("Cell phone number successfully captured.")) {
            return cellStatus;
        }

        // Normalize again just in case (defensive coding vibes)
        if (cellNumber.startsWith("0") && cellNumber.length() == 10) {
            cellNumber = "+27" + cellNumber.substring(1);
        }

        // Store identity into this object like saving a profile
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.password = password;
        this.cellNumber = cellNumber;

        return "Account successfully created.";
    }

    // Simple authentication check: does input match stored credentials?
    public boolean loginUser(String username, String password) {

        if (this.username == null) return false;

        return this.username.equals(username)
                && this.password.equals(password);
    }

    // Friendly login response depending on success or failure
    public String returnLoginStatus(String username, String password) {

        if (loginUser(username, password)) {
            return "Welcome " + name + " " + surname + ", it is great to see you again.";
        }

        return "Username or password incorrect. Please try again.";
    }
}