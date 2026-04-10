package com.mycompany.chatapp;

public class Login {

    private String username;
    private String password;
    private String cellNumber;
    private String name;
    private String surname;

    // 1
    public boolean checkUserName(String username) {
        return username.length() <= 5 && username.contains("_");
    }

    // 2
    public boolean checkPasswordComplexity(String password) {

        if (password.length() < 8) return false;

        boolean capital = false;
        boolean number = false;
        boolean special = false;

        for (int i = 0; i < password.length(); i++) {
            char ch = password.charAt(i);

            if (Character.isUpperCase(ch)) capital = true;
            else if (Character.isDigit(ch)) number = true;
            else if (!Character.isLetterOrDigit(ch)) special = true;
        }

        return capital && number && special;
    }

    // 3
    public boolean checkCellPhoneNumber(String cellNumber) {
        return cellNumber.matches("\\+27\\d{9}");
    }

    // 4
    public String registerUser(String name, String surname,
                               String username, String password,
                               String cellNumber) {

        if (!checkUserName(username)) {
            return "Username is not correctly formatted; must contain '_' and be ≤ 5 characters.";
        }

        if (!checkPasswordComplexity(password)) {
            return "Password must be 8+ chars with capital, number and special character.";
        }

        if (!checkCellPhoneNumber(cellNumber)) {
            return "Cell number must start with +27 and contain 9 digits.";
        }

        this.name = name;
        this.surname = surname;
        this.username = username;
        this.password = password;
        this.cellNumber = cellNumber;

        return "Account successfully created.";
    }

    // 5
    public boolean loginUser(String username, String password) {
        if (this.username == null) return false;
        return this.username.equals(username) && this.password.equals(password);
    }

    // 6
    public String returnLoginStatus(String username, String password) {

        if (loginUser(username, password)) {
            return "Welcome " + name + " " + surname + ", it is great to see you again.";
        } else {
            return "Username or password incorrect. Please try again.";
        }
    }
}