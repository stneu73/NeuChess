package ui;

import com.google.gson.Gson;
import webSocketMessages.userCommands.UserGameCommand;

import javax.websocket.*;
import java.net.URI;

public class WSClient extends Endpoint {
    public Session session;

    public WSClient() throws Exception {
        URI uri = new URI("ws://localhost:8080/connect");
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        this.session = container.connectToServer(this, uri);

        this.session.addMessageHandler(new MessageHandler.Whole<String>() {
            public void onMessage(String message) { //I want this to call something in gameplay that will the display the message as I like, depending on the message type sent.
                System.out.println(message);
            }
        });
    }

    public void send(UserGameCommand msg) throws Exception {
        var json = new Gson().toJson(msg);
        this.session.getBasicRemote().sendText(json);
    }
    public void onOpen(Session session, EndpointConfig endpointConfig) {
    }
}
