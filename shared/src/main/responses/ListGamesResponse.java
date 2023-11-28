package responses;

import models.GameModel;

import java.util.LinkedList;

/**
 * Contains all the information for all the games to be listed out to be given to the client
 */
public class ListGamesResponse {
    /**
     * contains a list of all the games from the database
     */
    LinkedList<GameModel> games;
    /**
     * contains message to be returned to the user
     */
    String message;
    String gamesPrint;

    /**
     * Constructor for ListGamesResponse
     * @param games list of games passed in by the DAO
     */
    public ListGamesResponse(LinkedList<GameModel> games) {
        this.games = games;
        this.message = null;
        this.gamesPrint = gamesToString();

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

    private String gamesToString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < games.size(); i++) {
            s.append(i+1).append(". ").append(games.get(i).gameToStringPrint()).append("\n");
        }
        return s.toString();
    }
}