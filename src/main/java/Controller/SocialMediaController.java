package Controller;

import Model.Message;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

import Service.Service;
import Model.Account;


public class SocialMediaController {
    private final Service service;

    public SocialMediaController() {
        this.service = new Service();
    }

    public SocialMediaController(Service service) {
        this.service = service;
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     *
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();

        app.post("/register", this::registerHandler);
        app.post("/login", this::loginHandler);
        app.post("/messages", this::createMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageByIdHandler);
        app.patch("/messages/{message_id}", this::updateMessageByIdHandler);
        app.get("/accounts/{account_id}/messages", this::getMessagesByUserIdHandler);

        return app;
    }

    private void registerHandler(Context context) {
        Account account = context.bodyAsClass(Account.class);

        account = this.service.register(account);
        if (account != null) {
            context.status(200).json(account);
        } else {
            context.status(400);
        }
    }

    private void loginHandler(Context context) {
        Account account = context.bodyAsClass(Account.class);

        account = this.service.authenticate(account);
        if (account != null) {
            context.status(200).json(account);
        } else {
            context.status(401);
        }
    }

    private void createMessageHandler(Context context) {
        Message message = context.bodyAsClass(Message.class);

        message = this.service.createMessage(message);
        if (message != null) {
            context.status(200).json(message);
        } else {
            context.status(400);
        }
    }

    private void getAllMessagesHandler(Context context) {
        List<Message> messages = service.getAllMessages();
        context.status(200).json(messages);
    }

    private void getMessageByIdHandler(Context context) {
        int messageId = Integer.parseInt(context.pathParam("message_id"));
        Message message = service.getMessageById(messageId);
        if (message == null) {
            context.status(200);
        } else {
            context.status(200).json(message);
        }
    }

    private void deleteMessageByIdHandler(Context context) {
        int messageId = Integer.parseInt(context.pathParam("message_id"));
        Message message = service.deleteMessageById(messageId);
        if (message == null) {
            context.status(200);
        } else {
            context.status(200).json(message);
        }
    }

    private void updateMessageByIdHandler(Context context) {
        int messageId = Integer.parseInt(context.pathParam("message_id"));
        String newText = context.bodyAsClass(Message.class).getMessage_text();
        Message message = service.updateMessageById(messageId, newText);

        if (message != null) {
            context.status(200).json(message);
        } else {
            context.status(400);
        }
    }

    private void getMessagesByUserIdHandler(Context context) {
        int accountId = Integer.parseInt(context.pathParam("account_id"));

        List<Message> messages = service.getMessagesByUserId(accountId);

        context.status(200).json(messages);
    }
}