package webSocketCommands;

import chess.Move;
import models.User;
import webSocketMessages.userCommands.UserGameCommand;

public class MakeMoveCommand extends UserGameCommand {
    private final Integer gameID;
    private final Move move;

    public MakeMoveCommand(String authToken, Integer gameID, Move move) {
        super(authToken);
        this.gameID = gameID;
        this.move = move;
    }

    public Move getMove() {
        return move;
    }

    public Integer getGameID() {
        return gameID;
    }
}
