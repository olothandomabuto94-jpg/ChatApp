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
        return false;
    }
    
    public String userReg(String name, String surname, String username, String password, String cellNo) {
        return "";
    }
    
    public boolean login(String username, String password) {
        return false;
    }
    
    public String returnLogin(String username, String password) {
        return "";
    }
}