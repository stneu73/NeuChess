package webSocketCommands;

import models.User;
import webSocketMessages.userCommands.UserGameCommand;

public class JoinObserverCommand extends UserGameCommand {
    private final Integer gameID;


    public JoinObserverCommand(String authToken, Integer gameID) {
        super(authToken);
        this.gameID = gameID;
    }

    public Integer getGameID() {
        return gameID;
    }
}
