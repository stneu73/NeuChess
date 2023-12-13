package ui;

import chess.ChessGame;
import chess.Game;
import com.google.gson.Gson;
import models.GameModel;
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
    private final int gameID;

    public WSClient(int gameID) throws Exception {
        URI uri = new URI("ws://localhost:8080/connect");
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        this.session = container.connectToServer(this, uri);
        this.gameID = gameID;

        this.session.addMessageHandler(new MessageHandler.Whole<String>() {
            public void onMessage(String message) {
                ServerMessage serverMessage = new Gson().fromJson(message, ServerMessage.class);
                switch (serverMessage.getServerMessageType()) {
                    case NOTIFICATION -> System.out.print(new Gson().fromJson(message, NotificationMessage.class).getMessage());
                    case ERROR -> System.out.print(new Gson().fromJson(message, ErrorMessage.class).getErrorMessage());
                    case LOAD_GAME -> {
                        String gameString = new Gson().fromJson(message, LoadGameMessage.class).getGame();
                        Gameplay.redrawChessBoard(gameString);
                        GameModel gameModel = ServerFacade.games.get(gameID);
                        Game game = new Game(gameString);
                        if (game.isInCheckmate(ChessGame.TeamColor.WHITE)) {
                            System.out.print(gameModel.getWhiteUsername() + " is in checkmate.");
                        } else if (game.isInCheck(ChessGame.TeamColor.WHITE)) {
                            System.out.print(gameModel.getWhiteUsername() + " is in check.");
                        }
                        if (game.isInCheckmate(ChessGame.TeamColor.BLACK)) {
                            System.out.print(gameModel.getBlackUsername() + " is in checkmate.");
                        } else if (game.isInCheck(ChessGame.TeamColor.BLACK)) {
                            System.out.print(gameModel.getBlackUsername() + " is in check.");
                        }

                    }
                }
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
