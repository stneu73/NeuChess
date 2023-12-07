package webSocket;

import chess.*;
import com.google.gson.*;
import dao.SQLDAO;
import models.AuthToken;
import models.GameModel;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.Session;
import webSocketCommands.*;
import webSocketServerMessages.*;
import webSocketMessages.userCommands.UserGameCommand;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Objects;

public class WebSocketHandler {

    private final ConnectionManager connections = new ConnectionManager();

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException {
        UserGameCommand userCommand = new Gson().fromJson(message, UserGameCommand.class);
        switch (userCommand.getCommandType()) {
            case JOIN_PLAYER -> joinPlayer(session, new Gson().fromJson(message, JoinGameCommand.class));
            case JOIN_OBSERVER -> joinObserver(session, new Gson().fromJson(message, JoinObserverCommand.class));
            case MAKE_MOVE -> {
                class PositionAdapter implements JsonDeserializer<ChessPosition> {

                    @Override
                    public ChessPosition deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                        return jsonDeserializationContext.deserialize(jsonElement,Position.class);
                    }
                }
                GsonBuilder gson = new GsonBuilder();
                gson.registerTypeAdapter(ChessPosition.class, new PositionAdapter());
                makeMove(session, gson.create().fromJson(message, MakeMoveCommand.class));
            }
            case LEAVE -> leave(session, new Gson().fromJson(message, LeaveCommand.class));
            case RESIGN -> resign(session, new Gson().fromJson(message, ResignCommand.class));
        }
    }

    private void joinPlayer(Session session, JoinGameCommand command) {
        int gameID = command.getGameID();
        if (connections.gameOverQuery(gameID)) {
            //send message back that the game is over
        } else {
            AuthToken auth = null;
            try {
                auth = new SQLDAO().getAuthToken(command.getAuthString());
            } catch (Exception ignored) {}
            try {
                new SQLDAO().claimSpot(gameID, auth.getAuthToken(), command.getPlayerColor().toString());
            } catch (Exception ignore) {}
            connections.add(gameID, auth.getUsername(), session);
            try {
                connections.broadcastAll(gameID, new LoadGameMessage(new SQLDAO().getGame(gameID).getGameToString()));
            } catch (Exception ignored) {}
        }
    }

    private void joinObserver(Session session, JoinObserverCommand command) {
        int gameID = command.getGameID();
        if (connections.gameOverQuery(gameID)) {
            connections.reply(gameID, command.getAuthString(), new ErrorMessage("Game is already over."));
        } else {
            AuthToken auth = null;
            try {
                auth = new SQLDAO().getAuthToken(command.getAuthString());
            } catch (Exception ignored) {}

            connections.add(gameID, auth.getUsername(), session);

            try {
                connections.broadcastMinusOne(gameID, auth.getUsername(), new NotificationMessage(auth.getUsername() + " has started watching."));
            } catch (Exception ignored) {}

            try {
                connections.reply(gameID, auth.getUsername(), new LoadGameMessage(new SQLDAO().getGame(gameID).getGameToString()));
            } catch (Exception ignored) {}
        }
    }

    private void makeMove(Session session, MakeMoveCommand command) {
        int gameID = command.getGameID();
        GameModel gameModel = null;
        try {
            gameModel = new SQLDAO().getGame(gameID);
        } catch (Exception ignored) {}

        if (!connections.gameOverQuery(gameID)) {
            AuthToken auth = null;
            try {
                auth = new SQLDAO().getAuthToken(command.getAuthString());
            } catch (Exception ignored) {}

            if (gameModel.getGame().getBoard().getPiece(command.getMove().getStartPosition()) != null) {

                ChessGame.TeamColor pieceColor = gameModel.getGame().getBoard().getPiece(command.getMove().getStartPosition()).getTeamColor();

                if (Objects.equals(auth.getUsername(), gameModel.getWhiteUsername()) && pieceColor == ChessGame.TeamColor.WHITE ||
                        Objects.equals(auth.getUsername(), gameModel.getBlackUsername()) && pieceColor == ChessGame.TeamColor.BLACK) {

                    makeGameMove(gameModel, command.getMove(), auth.getUsername());

                    switch(pieceColor) {
                        case WHITE -> pieceColor = ChessGame.TeamColor.BLACK;
                        case BLACK -> pieceColor = ChessGame.TeamColor.WHITE;
                        }
                    if (gameModel.getGame().isInCheckmate(pieceColor)) {
                        connections.broadcastAll(gameID,new NotificationMessage("Game has ended with " + pieceColor.toString() + " in checkmate."));
                        connections.endGame(gameID);
                        //get out of rest of logic
                    }
                    if (gameModel.getGame().isInCheck(pieceColor)) {
                        try {
                            connections.broadcastMinusOne(gameID, auth.getUsername(), new NotificationMessage(pieceColor.toString() + "is in check."));
                        } catch (Exception ignore) {}
                    }

                    connections.broadcastAll(gameID, new LoadGameMessage(gameModel.getGame().gameToString()));
                } else {
                    connections.reply(gameID, auth.getUsername(), new ErrorMessage("Piece selected is not on your team"));//reply to user that the move was bad
                }
            }
        }
        //get the game from database
        //validate the piece and mover match color
        //get move info
        //make the move
            //notifications
        //update database
        //check for check or checkmate
            //notification
        //send it all back TODO: what all am I sending back?
    }

    private void makeGameMove(GameModel game, Move move, String username) {
        if (game.getGame().validMoves(move.getStartPosition()).contains(move)) {
            try {
                game.getGame().makeMove(move);
            } catch (Exception ignored) {}
            try {
                new SQLDAO().updateGame(game);
            } catch(Exception ignored) {}
        } else {
            connections.reply(game.getGameID(), username, new ErrorMessage("Selected move is not a valid move."));
        }
    }

    private void leave(Session session, LeaveCommand command) {
        int gameID = command.getGameID();
        GameModel game = null;

        try {
            game = new SQLDAO().getGame(gameID);
        } catch (Exception ignored) {}

        if (!connections.gameOverQuery(gameID)) {
            AuthToken auth = null;
            try {
                auth = new SQLDAO().getAuthToken(command.getAuthString());
            } catch (Exception ignored) {}
            connections.removePlayer(gameID, auth.getUsername(), session);
            try {
                connections.broadcastMinusOne(gameID, auth.getUsername(), new NotificationMessage(auth.getUsername() + " has disconnected from the game."));
            } catch (Exception ignored) {}
        }
    }

    private void resign(Session session, ResignCommand command) {
        int gameID = command.getGameID();
        GameModel game = null;

        try {
            game = new SQLDAO().getGame(gameID);
        } catch (Exception ignored) {}

        if (!connections.gameOverQuery(gameID)) {
            AuthToken auth = null;
            try {
                auth = new SQLDAO().getAuthToken(command.getAuthString());
            } catch (Exception ignored) {
            }

            if (Objects.equals(auth.getUsername(), game.getWhiteUsername()) || Objects.equals(auth.getUsername(), game.getBlackUsername())) {
                connections.endGame(gameID);
                try {
                    connections.broadcastMinusOne(gameID, auth.getUsername(), new NotificationMessage(auth.getUsername() + " resigned. Game has ended."));
                } catch (Exception ignored) {}
            }
        }
    }
}
