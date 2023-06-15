package DAO;
import Model.Message;

import java.util.List;

public interface MessageDAO<T> {
    Message createMessage(T t);

    List<Message> getAllMessages();

    Message getMessageById(int messageId);

    Message deleteMessageById(int messageId);

    Message updateMessageById(int messageId, String text);

    List<Message> getMessagesByUserId(int accountId);
}