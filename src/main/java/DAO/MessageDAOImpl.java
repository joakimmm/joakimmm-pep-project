package DAO;

import Model.Message;
import Util.ConnectionUtil;
import kotlin.coroutines.AbstractCoroutineContextElement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageDAOImpl implements MessageDAO<Message> {

    private final Connection connection = ConnectionUtil.getConnection();

    public MessageDAOImpl() {
        super();
    }

    @Override
    public Message createMessage(Message message) {
        try {
            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)";
            PreparedStatement ps = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, message.getPosted_by());
            ps.setString(2, message.getMessage_text());
            ps.setLong(3, message.getTime_posted_epoch());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                message.setMessage_id(rs.getInt("message_id"));
                return message;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Message> getAllMessages() {
        try {
            String sql = "SELECT * FROM message";
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<Message> messages = new ArrayList<>();
            while (rs.next()) {
                Message message = new Message();
                message.setMessage_id(rs.getInt("message_id"));
                message.setPosted_by(rs.getInt("posted_by"));
                message.setMessage_text(rs.getString("message_text"));
                message.setTime_posted_epoch(rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
            return messages;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Message getMessageById(int messageId) {
        try {
            String sql = "SELECT * FROM message WHERE message_id = ?";
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ps.setInt(1, messageId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Message message = new Message();
                message.setMessage_id(rs.getInt("message_id"));
                message.setPosted_by(rs.getInt("posted_by"));
                message.setMessage_text(rs.getString("message_text"));
                message.setTime_posted_epoch(rs.getLong("time_posted_epoch"));
                return message;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Message deleteMessageById(int messageId) {
        try{
            Message message = getMessageById(messageId);

            String sql = "DELETE FROM message WHERE message_id = ?";
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ps.setInt(1, messageId);
            ps.executeUpdate();

            return message;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Message updateMessageById(int messageId, String text) {
        try {
            String sql = "UPDATE message SET message_text = ? WHERE message_id = ?";
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ps.setString(1, text);
            ps.setInt(2, messageId);
            ps.executeUpdate();

            return getMessageById(messageId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Message> getMessagesByUserId(int accountId) {
        try{
            String sql = "SELECT * FROM message WHERE posted_by = ?";
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ps.setInt(1, accountId);
            ResultSet rs = ps.executeQuery();
            List<Message> messages = new ArrayList<>();
            while (rs.next()) {
                Message message = new Message();
                message.setMessage_id(rs.getInt("message_id"));
                message.setPosted_by(rs.getInt("posted_by"));
                message.setMessage_text(rs.getString("message_text"));
                message.setTime_posted_epoch(rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
            return messages;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}