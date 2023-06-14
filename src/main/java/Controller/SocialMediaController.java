package Controller;

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
        app.get("/accounts/{message_id}/messages", this::getMessagesByUserHandler);

        return app;
    }

    private void registerHandler(Context context) {
        String username = context.formParam("username");
        String password = context.formParam("password");

        if (username == null || password == null) {
            context.status(400).result("Username and password are required");
            return;
        }

        boolean success = service.register(username, password);

        if (success) {
            context.status(201).json(service.register(username,password));
            
        } else {
            context.status(400).result("Username already exists");
        }
    }

    private void loginHandler(Context context) {
        String username = context.formParam("username");
        String password = context.formParam("password");

        if (username == null || password == null) {
            context.status(400).result("Username and password are required");
            return;
        }

        String token = service.login(username, password);

        if (token != null) {
            context.status(200).result(token);
        } else {
            context.status(401).result("Invalid username or password");
        }
    }

    private void createMessageHandler(Context context) {
        String token = context.header("Authorization");
        String text = context.body();

        if (token == null || text == null) {
            context.status(400).result("Authorization token and message text are required");
            return;
        }

        boolean success = service.createMessage(token, text);

        if (success) {
            context.status(200).json("Message created");
        } else {
            context.status(401).result("Invalid token");
        }
    }

    private void getAllMessagesHandler(Context context) {
        List<String> messages = service.getAllMessages();

        context.json(messages);
    }

    private void getMessageByIdHandler(Context context) {
        String messageId = context.pathParam("message_id");

        String message = service.getMessageById(messageId);

        if (message != null) {
            context.json(message);
        } else {
            context.status(404).result("Message not found");
        }
    }

    private void deleteMessageByIdHandler(Context context) {
        String messageId = context.pathParam("message_id");

        boolean success = service.deleteMessageById(messageId);

        if (success) {
            context.status(204).result("Message deleted");
        } else {
            context.status(404).result("Message not found");
        }
    }

    private void updateMessageByIdHandler(Context context) {
        String messageId = context.pathParam("message_id");
        String newText = context.formParam("text");

        if (newText == null) {
            context.status(400).result("New message text is required");
            return;
        }

        boolean success = service.updateMessageById(messageId, newText);

        if (success) {
            context.status(200).result("Message updated");
        } else {
            context.status(404).result("Message not found");
        }
    }

    private void getMessagesByUserHandler(Context context) {
        String accountId = context.pathParam("account_id");

        List<String> messages = service.getMessagesByUser(accountId);

        if (messages != null) {
            context.json(messages);
        } else {
            context.status(404).result("Account not found");
        }
    }
}