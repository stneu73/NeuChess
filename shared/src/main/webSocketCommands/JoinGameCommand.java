package webSocketCommands;

import chess.ChessGame;
import webSocketMessages.userCommands.UserGameCommand;

public class JoinGameCommand extends UserGameCommand {
    private final Integer gameID;
    private final ChessGame.TeamColor playerColor;


    public JoinGameCommand(String authToken, Integer gameID, ChessGame.TeamColor playerColor) {
        super(authToken);
        this.playerColor = playerColor;
        this.gameID = gameID;
        this.commandType = CommandType.JOIN_PLAYER;
    }

    public Integer getGameID() {
        return gameID;
    }

    public ChessGame.TeamColor getPlayerColor() {
        return playerColor;
    }
}
