package DAO;

import Model.Account;

import java.sql.SQLException;

public interface AccountDAO<T> {
    Account createAccount(T t);
    Account login(T t);

    boolean usernameIsRegistered(String username);

    boolean idIsRegistered(int id);
}

