package responses;

import models.GameModel;

/**
 * Contains all the information for all the games to be listed out to be given to the client
 */
public class ListGamesResponse {
    /**
     * contains a list of all the games from the database
     */
    GameModel[] games;
    String message;

    public ListGamesResponse(GameModel[] games) {
        this.games = games;
        this.message = null;
    }
    public ListGamesResponse(String message) {
        this.games = null;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}