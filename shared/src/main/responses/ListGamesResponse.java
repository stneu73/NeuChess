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
    private String message;
    private String gamesPrint;
    private int[] gameIDs;

    /**
     * Constructor for ListGamesResponse
     * @param games list of games passed in by the DAO
     */
    public ListGamesResponse(LinkedList<GameModel> games) {
        this.games = games;
        this.message = null;
        this.gamesPrint = gamesToString();
        this.gameIDs = setGameIDs();
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

    private int[] setGameIDs() {
        int[] temp = new int[games.size()];
        int i = 0;
        for (var game:games) {

            temp[i] = game.getGameID();
            i++;
        }
        return temp;
    }
}