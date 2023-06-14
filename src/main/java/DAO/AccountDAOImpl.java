package DAO;
import java.util.HashMap;
import java.util.Map;

public class AccountDAOImpl implements AccountDAO {

    private final Map<String, String> accounts;
    private int nextAccountId;
    
    public AccountDAOImpl() {
        this.accounts = new HashMap<>();
        this.nextAccountId = 1;
    }

    @Override
    public boolean createAccount(String username, String password) {
        if (accounts.containsKey(username) ||password.length() <= 4 ) {
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
                return generateToken(); 
            }
        }
        return null; 
    }

    @Override
    public int getAccountIdByToken(String token) {
        
        return nextAccountId++;
    }

    
    private String generateToken() {
        
        return "random_token";
    }
}