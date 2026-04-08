public class Login {
    private String username;
    private String password;
    private String cellNo;
    private String name;
    private String surname;
    
    public boolean checkUserName(String username) {
        if (username.length() > 5) {
            return false;
        }
        if (!username.contains("_")) {
            return false;
        }
        return true;
    }
    
    public boolean checkPassword(String password) {
        if (password.length() < 8) {
            return false;
        }
        
        boolean capital = false;
        boolean number = false;
        boolean special = false;
        
        for (int i = 0; i < password.length(); i++) {
            char ch = password.charAt(i);
            
            if (Character.isUpperCase(ch)) {
                capital = true;
            } else if (Character.isDigit(ch)) {
                number = true;
            } else if (!Character.isLetterOrDigit(ch)) {
                special = true;
            }
        }
        return capital && number && special;
    }
    
    public boolean checkCellNo(String cellNo) {
        return cellNo.matches("\\+27\\d(9)");
    }
    
    public String userReg(String name, String surname, String username, String password, String cellNo) {
        if (!checkUserName(username)) {
            return "Username is not coreectly formatted. Please ensure that the username contains an underscore and is  ot longer than five letters.";
        }
        if (!checkPassword(password)) {
            return "Password is not formatted correctly. Please ensure your password contains a capital letter, a number, a special character and is at least 8 characters in lenght.";
        }
        if (!checkCellNo(cellNo)) {
            return "Please ensure that your cellphone number is the correct lenght and caontains the international code (+27)";
        }
        
        this.cellNo = cellNo;
        this.name = name;
        this.password = password;
        this.surname = surname;
        this.username = username;
        
        return "Account creation is successful!";
    }
    
    public boolean login(String username, String password) {
        return false;
    }
    
    public String returnLogin(String username, String password) {
        return "";
    }
}