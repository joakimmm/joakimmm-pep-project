package DAO;
import java.util.HashMap;
import java.util.Map;

public class AccountDAOImpl implements AccountDAO {

    private final Map<String, String> accounts;

    public AccountDAOImpl() {
        this.accounts = new HashMap<>();
    }

    @Override
    public boolean createAccount(String username, String password) {
        if (accounts.containsKey(username)) {
            return false; 
        }
        if (password.length() <= 4){
            return false;
        }
            
        accounts.put(username, password);
        return true; 
    }

    @Override
    public String authenticate(String username, String password) {
        if (accounts.containsKey(username)) {
            String storedPassword = accounts.get(username);
            if (storedPassword.equals(password)) {
                return generateToken(); // Authentication successful
            }
        }
        return null; // Authentication failed
    }

    @Override
    public int getAccountIdByToken(String token) {
        
        return 0;
    }

    
    private String generateToken() {
        
        return "random_token";
    }
}