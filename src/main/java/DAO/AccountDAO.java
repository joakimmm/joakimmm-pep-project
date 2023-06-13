package DAO;

public interface AccountDAO {
    boolean createAccount(String username, String password);

    String authenticate(String username, String password);

    int getAccountIdByToken(String token);
}

