package Service;

import DAO.AccountDAOImpl;
import DAO.MessageDAOImpl;
import Model.Account;
import Model.Message;

import java.util.List;

public class Service {
    private final AccountDAOImpl accountDAO;
    private final MessageDAOImpl messageDAO;

    public Service(AccountDAOImpl accountDAO, MessageDAOImpl messageDAO) {
        this.accountDAO = accountDAO;
        this.messageDAO = messageDAO;
    }

    public Service() {
        this.accountDAO = new AccountDAOImpl();
        this.messageDAO = new MessageDAOImpl();
    }

    public Account register(Account account) {
        if (account.getUsername() == null ||
                account.getPassword().length() < 4 ||
                account.getUsername().length() == 0 ||
                accountDAO.usernameIsRegistered(account.getUsername())
        ) {
            return null;
        }
        return this.accountDAO.createAccount(account);
    }

    public Account authenticate(Account account) {

        return this.accountDAO.login(account);
    }

    public Message createMessage(Message message) {
        if (message.getMessage_text() == null ||
                message.getMessage_text().length() == 0 ||
                message.getMessage_text().length() >= 255 ||
                !accountDAO.idIsRegistered(message.getPosted_by())) {
            return null;
        }
        return this.messageDAO.createMessage(message);
    }

    public List<Message> getAllMessages() {

        return this.messageDAO.getAllMessages();
    }

    public Message getMessageById(int messageId) {

        return this.messageDAO.getMessageById(messageId);
    }

    public Message deleteMessageById(int messageId) {

        return this.messageDAO.deleteMessageById(messageId);
    }

    public Message updateMessageById(int messageId, String text) {
        if(text == null ||
                text.length() == 0 ||
                text.length() >= 255 ||
                getMessageById(messageId) == null ) {
            return null;
        }
        return this.messageDAO.updateMessageById(messageId, text);
    }

    public List<Message> getMessagesByUserId(int accountId) {

        return messageDAO.getMessagesByUserId(accountId);
    }
}