package DAO;

import Model.Account;

public interface AccountDAO<T> {
    Account createAccount(T t);
    Account login(T t);

    boolean usernameIsRegistered(String username);

    boolean idIsRegistered(int id);
}

