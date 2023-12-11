package webSocket;

import chess.*;
import com.google.gson.*;
import dao.SQLDAO;
import dataAccess.DataAccessException;
import models.AuthToken;
import models.GameModel;

import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import responses.JoinGameResponse;
import webSocketCommands.*;
import webSocketServerMessages.*;
import webSocketMessages.userCommands.UserGameCommand;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Objects;

@WebSocket
public class WebSocketHandler {

    private final ConnectionManager connections = new ConnectionManager();
//    private final String username;

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws Exception {
        UserGameCommand userCommand = new Gson().fromJson(message, UserGameCommand.class);
        switch (userCommand.getCommandType()) {
            case JOIN_PLAYER -> joinPlayer(session, new Gson().fromJson(message, JoinGameCommand.class));
            case JOIN_OBSERVER -> joinObserver(session, new Gson().fromJson(message, JoinObserverCommand.class));
            case MAKE_MOVE -> {
                class PositionAdapter implements JsonDeserializer<ChessPosition> {

                    @Override
                    public ChessPosition deserialize(JsonElement jsonElement, Type type,
                                                     JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
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

    private void joinPlayer(Session session, JoinGameCommand command) throws Exception {
        var seshRemote = session.getRemote();
        AuthToken auth = null;
        SQLDAO dao = new SQLDAO();
        auth = dao.getAuthToken(command.getAuthString());
        int gameID = command.getGameID();

        if (auth.getAuthToken() == null) {
            seshRemote.sendString(new Gson().toJson(new ErrorMessage("Bad auth token.")));
        } else if (dao.getGame(gameID) == null) {
            seshRemote.sendString(new Gson().toJson(new ErrorMessage("Game doesn't exist.")));
        } else {
            boolean gameIsOver = false;
            if (connections.getGameIDToGameSession().get(gameID) != null) {
                gameIsOver = connections.gameOverQuery(gameID);
                if (gameIsOver) {
                    seshRemote.sendString(new Gson().toJson(new ErrorMessage("This game has already ended.")));
                }
            }
            if (!gameIsOver) {
                GameModel game;
                game = dao.getGame(gameID);
                if (command.getPlayerColor() == ChessGame.TeamColor.BLACK && game.getBlackUsername() == null) {
                    seshRemote.sendString(new Gson().toJson(new ErrorMessage("This game has already ended.")));

                } else if (command.getPlayerColor() == ChessGame.TeamColor.WHITE && game.getWhiteUsername() == null) {
                    seshRemote.sendString(new Gson().toJson(new ErrorMessage("Not your game.")));

                } else if (command.getPlayerColor() == ChessGame.TeamColor.BLACK && Objects.equals(game.getBlackUsername(), auth.getUsername())) {
                    connections.add(gameID, auth.getUsername(), session);
                    connections.reply(gameID, auth.getUsername(), new LoadGameMessage(dao.getGame(gameID).getGameString()));
                    connections.broadcastMinusOne(gameID, auth.getUsername(),
                            new NotificationMessage(auth.getUsername() + " has joined the game black."));

                } else if (command.getPlayerColor() == ChessGame.TeamColor.WHITE && Objects.equals(game.getWhiteUsername(), auth.getUsername())) {
                    connections.add(gameID, auth.getUsername(), session);
                    connections.reply(gameID, auth.getUsername(), new LoadGameMessage(dao.getGame(gameID).getGameString()));
                    connections.broadcastMinusOne(gameID, auth.getUsername(),
                            new NotificationMessage(auth.getUsername() + " has joined the game as white."));

                } else if (command.getPlayerColor() == ChessGame.TeamColor.WHITE && game.getWhiteUsername() != null) {
                    seshRemote.sendString(new Gson().toJson(new ErrorMessage("Color already taken.")));

                } else if (command.getPlayerColor() == ChessGame.TeamColor.BLACK && game.getBlackUsername() != null) {
                    seshRemote.sendString(new Gson().toJson(new ErrorMessage("Color already taken.")));

                } else {
                    new SQLDAO().claimSpot(gameID, auth.getAuthToken(), command.getPlayerColor().toString());
                    connections.add(gameID, auth.getUsername(), session);
                    connections.reply(gameID, auth.getUsername(), new LoadGameMessage(dao.getGame(gameID).getGameString()));
                    connections.broadcastMinusOne(gameID, auth.getUsername(),
                            new NotificationMessage(auth.getUsername() + " has joined the game as " + command.getPlayerColor().toString() + "."));
                }
            }
        }
    }

    private void joinObserver(Session session, JoinObserverCommand command) throws Exception {
        var seshRemote = session.getRemote();
        int gameID = command.getGameID();
        boolean gameIsOver = false;
        AuthToken auth = null;
        SQLDAO dao = new SQLDAO();
        auth = dao.getAuthToken(command.getAuthString());

        if (dao.getGame(gameID) == null) {
            seshRemote.sendString(new Gson().toJson(new ErrorMessage("Game doesn't exist.")));
        }
        if (connections.getGameIDToGameSession().get(gameID) != null) {
            gameIsOver = connections.gameOverQuery(gameID);
            if (gameIsOver) {
                seshRemote.sendString(new Gson().toJson(new ErrorMessage("This game has already ended.")));
            }
        }
        if (!gameIsOver) {
            connections.add(gameID, auth.getUsername(), session);
            connections.broadcastMinusOne(gameID, auth.getUsername(), new NotificationMessage(auth.getUsername() + " has started watching."));
            connections.reply(gameID, auth.getUsername(), new LoadGameMessage(new SQLDAO().getGame(gameID).getGameString()));
        }
    }

    private void makeMove(Session session, MakeMoveCommand command) throws Exception {
        var seshRemote = session.getRemote();
        int gameID = command.getGameID();
        SQLDAO dao = new SQLDAO();
        GameModel gameModel = dao.getGame(gameID);
        AuthToken auth = dao.getAuthToken(command.getAuthString());
        if (!Objects.equals(auth.getAuthToken(), gameModel.getWhiteUsername()) ||
                !Objects.equals(auth.getAuthToken(), gameModel.getBlackUsername())) {
            seshRemote.sendString(new Gson().toJson(new ErrorMessage("You are not a player in this game.")));
        } else {
            if (connections.gameOverQuery(gameID)) {
                seshRemote.sendString(new Gson().toJson(new ErrorMessage("This game has already ended.")));
            }
        }
//        connections.add(gameID, auth.getUsername(), session);

//        else {
//
//        }

        if (!connections.gameOverQuery(gameID)) {
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
                        try {
                            connections.broadcastAll(gameID, new NotificationMessage("Game has ended with " + pieceColor.toString() + " in checkmate."));
                        } catch (Exception e) {
                            System.out.print(e.getMessage());
                        }
                        connections.endGame(gameID);
                        //get out of rest of logic
                    }
                    if (gameModel.getGame().isInCheck(pieceColor)) {
                        try {
                            connections.broadcastMinusOne(gameID, auth.getUsername(), new NotificationMessage(pieceColor.toString() + "is in check."));
                        } catch (Exception e) {
                            System.out.print(e.getMessage());
                        }
                    }
                    try {
                        connections.broadcastAll(gameID, new LoadGameMessage(gameModel.getGameString()));
                    } catch (Exception e) {
                        System.out.print(e.getMessage());
                    }
                } else {
                    try {
                        connections.reply(gameID, auth.getUsername(), new ErrorMessage("Piece selected is not on your team"));//reply to user that the move was bad
                    } catch (Exception e) {
                        System.out.print(e.getMessage());
                    }
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
    }

    private void makeGameMove(GameModel game, Move move, String username) {
        if (game.getGame().validMoves(move.getStartPosition()).contains(move)) {
            try {
                game.getGame().makeMove(move);
            } catch (Exception e) {
                System.out.print(e.getMessage());
            }
            try {
                new SQLDAO().updateGame(game);
            } catch (Exception e) {
                System.out.print(e.getMessage());
            }
        } else {
            try {
                connections.reply(game.getGameID(), username, new ErrorMessage("Selected move is not a valid move."));
            } catch (Exception e) {
                System.out.print(e.getMessage());
            }
        }
    }

    private void leave(Session session, LeaveCommand command) {
        int gameID = command.getGameID();
//        GameModel game = null;
//
//        try {
//            game = new SQLDAO().getGame(gameID);
//        } catch (Exception ignored) {}

        if (!connections.gameOverQuery(gameID)) {
            AuthToken auth = null;
            try {
                auth = new SQLDAO().getAuthToken(command.getAuthString());
            } catch (Exception e) {
                System.out.print(e.getMessage());
            }
            connections.removePlayer(gameID, auth.getUsername(), session);
            try {
                connections.broadcastMinusOne(gameID, auth.getUsername(), new NotificationMessage(auth.getUsername() + " has disconnected from the game."));
            } catch (Exception e) {
                System.out.print(e.getMessage());
            }
        }
    }

    private void resign(Session session, ResignCommand command) {
        int gameID = command.getGameID();
        GameModel game = null;

        try {
            game = new SQLDAO().getGame(gameID);
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }

        if (!connections.gameOverQuery(gameID)) {
            AuthToken auth = null;
            try {
                auth = new SQLDAO().getAuthToken(command.getAuthString());
            } catch (Exception e) {
                System.out.print(e.getMessage());
            }

            if (Objects.equals(auth.getUsername(), game.getWhiteUsername()) || Objects.equals(auth.getUsername(), game.getBlackUsername())) {
                connections.endGame(gameID);
                try {
                    connections.broadcastMinusOne(gameID, auth.getUsername(), new NotificationMessage(auth.getUsername() + " resigned. Game has ended."));
                } catch (Exception e) {
                    System.out.print(e.getMessage());
                }
            }
        }
    }
}
