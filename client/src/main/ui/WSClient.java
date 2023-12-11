package ui;

import com.google.gson.Gson;
import webSocketCommands.JoinGameCommand;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.UserGameCommand;
import webSocketServerMessages.ErrorMessage;
import webSocketServerMessages.LoadGameMessage;
import webSocketServerMessages.NotificationMessage;

import javax.websocket.*;
import java.net.URI;

public class WSClient extends Endpoint {
    public Session session;

    public WSClient() throws Exception {
        URI uri = new URI("ws://localhost:8080/connect");
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        this.session = container.connectToServer(this, uri);

        this.session.addMessageHandler(new MessageHandler.Whole<String>() {
            public void onMessage(String message) {
                ServerMessage serverMessage = new Gson().fromJson(message, ServerMessage.class);
                switch (serverMessage.getServerMessageType()) {
                    case NOTIFICATION -> System.out.print(new Gson().fromJson(message, NotificationMessage.class).getMessage());
                    case ERROR -> System.out.print(new Gson().fromJson(message, ErrorMessage.class).getErrorMessage());
                    case LOAD_GAME -> Gameplay.redrawChessBoard(new Gson().fromJson(message, LoadGameMessage.class).getGame());
                }

            }
        });
    }

    public void send(UserGameCommand msg) throws Exception {
        var json = new Gson().toJson(msg);
//        switch (type) {
//            case JOIN_PLAYER -> json = new Gson().toJson()
//            case JOIN_OBSERVER -> {}
//            case RESIGN -> {}
//            case MAKE_MOVE -> {}
//            case LEAVE -> {}
//        }
        this.session.getBasicRemote().sendText(json);
    }
    public void onOpen(Session session, EndpointConfig endpointConfig) {
    }
}
