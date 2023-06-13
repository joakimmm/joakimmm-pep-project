package Service;

import DAO.AccountDAO;
import DAO.MessageDAO;
import java.util.List;

public class Service {
    private final AccountDAO accountDAO;
    private final MessageDAO messageDAO;

    public Service(AccountDAO accountDAO, MessageDAO messageDAO) {
        this.accountDAO = accountDAO;
        this.messageDAO = messageDAO;
    }

    public boolean register(String username, String password) {
        
        return accountDAO.createAccount(username, password);
    }

    public String login(String username, String password) {
        
        return accountDAO.authenticate(username, password);
    }

    public boolean createMessage(String token, String text) {
        
        int accountId = accountDAO.getAccountIdByToken(token);
        return messageDAO.createMessage(accountId, text);
    }

    public List<String> getAllMessages() {
        
        return messageDAO.getAllMessages();
    }

    public String getMessageById(String messageId) {
        
        return messageDAO.getMessageById(messageId);
    }

    public boolean deleteMessageById(String messageId) {
        
        return messageDAO.deleteMessageById(messageId);
    }

    public boolean updateMessageById(String messageId, String text) {
        
        return messageDAO.updateMessageById(messageId, text);
    }

    public List<String> getMessagesByUser(String accountId) {
        
        return messageDAO.getMessagesByUser(accountId);
    }
}