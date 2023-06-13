package DAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageDAOImpl implements MessageDAO {

    private final Map<String, String> messages;

    public MessageDAOImpl() {
        this.messages = new HashMap<>();
    }

    @Override
    public boolean createMessage(int accountId, String text) {
        String messageId = generateMessageId();
        messages.put(messageId, text);
        return true; // Message created successfully
    }

    @Override
    public List<String> getAllMessages() {
        return new ArrayList<>(messages.values());
    }

    @Override
    public String getMessageById(String messageId) {
        return messages.get(messageId);
    }

    @Override
    public boolean deleteMessageById(String messageId) {
        if (messages.containsKey(messageId)) {
            messages.remove(messageId);
            return true; // Message deleted successfully
        }
        return false; // Message not found
    }

    @Override
    public boolean updateMessageById(String messageId, String text) {
        if (messages.containsKey(messageId)) {
            messages.put(messageId, text);
            return true; // Message updated successfully
        }
        return false; // Message not found
    }

    @Override
    public List<String> getMessagesByUser(String accountId) {
        // Not implemented in this example
        return new ArrayList<>();
    }

    // Helper method to generate a unique message ID
    private String generateMessageId() {
        // Not implemented in this example
        return "message_id";
    }
}