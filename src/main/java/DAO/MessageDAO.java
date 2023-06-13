package DAO;
import java.util.List;

public interface MessageDAO {
    boolean createMessage(int accountId, String text);

    List<String> getAllMessages();

    String getMessageById(String messageId);

    boolean deleteMessageById(String messageId);

    boolean updateMessageById(String messageId, String text);

    List<String> getMessagesByUser(String accountId);
}