package com.mycompany.chatapp;

public class Login {

    private String username;
    private String password;
    private String cellNumber;
    private String name;
    private String surname;

    public boolean checkUserName(String username) {
        return username.length() <= 5 && username.contains("_");
    }

    public boolean checkPasswordComplexity(String password) {

        if (password.length() < 8) return false;

        boolean capital = false;
        boolean number = false;
        boolean special = false;

        for (char ch : password.toCharArray()) {
            if (Character.isUpperCase(ch)) capital = true;
            else if (Character.isDigit(ch)) number = true;
            else if (!Character.isLetterOrDigit(ch)) special = true;
        }

        return capital && number && special;
    }

    public String checkCellPhoneNumber(String cellNumber) {

        if (cellNumber.startsWith("0") && cellNumber.length() == 10) {
            cellNumber = "+27" + cellNumber.substring(1);
        }

        if (cellNumber.matches("\\+27\\d{9}")) {
            return "Cell phone number successfully captured.";
        }

        return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
    }

    public String registerUser(String name, String surname,
            String username, String password,
            String cellNumber) {

        if (!checkUserName(username)) {
            return "Username must contain _ and be 5 characters or less.";
        }

        if (!checkPasswordComplexity(password)) {
            return "Password must be 8+ chars with capital, number and special character.";
        }

        String cellStatus = checkCellPhoneNumber(cellNumber);
        if (!cellStatus.equals("Cell phone number successfully captured.")) {
            return cellStatus;
        }

        if (cellNumber.startsWith("0") && cellNumber.length() == 10) {
            cellNumber = "+27" + cellNumber.substring(1);
        }

        this.name = name;
        this.surname = surname;
        this.username = username;
        this.password = password;
        this.cellNumber = cellNumber;

        return "Account successfully created.";
    }

    public boolean loginUser(String username, String password) {

        if (this.username == null) return false;

        return this.username.equals(username)
                && this.password.equals(password);
    }

    public String returnLoginStatus(String username, String password) {

        if (loginUser(username, password)) {
            return "Welcome " + name + " " + surname + ", it is great to see you again.";
        }

        return "Username or password incorrect. Please try again.";
    }
}