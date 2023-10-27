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
    /**
     * contains message to be returned to the user
     */
    String message;

    /**
     * Constructor for ListGamesResponse
     * @param games list of games passed in by the DAO
     */
    public ListGamesResponse(GameModel[] games) {
        this.games = games;
        this.message = null;
    }
    /**
     * Constructor for ListGamesResponse
     * @param message error message to be given to the user
     */
    public ListGamesResponse(String message) {
        this.games = null;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}