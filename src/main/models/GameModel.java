package models;

import chess.ChessGame;
import chess.Game;

import java.util.Random;

/**
 * Stores information structure of a chess game
 */
public class GameModel {
    /**
     * contains the game ID as an int
     */
    int gameID;
    /**
     * contains the username of the white player as a string
     */
    String whiteUsername;
    /**
     * contains the username of the black player as a string
     */
    String blackUsername;
    /**
     * contains the name of the game as a string
     */
    String gameName;
    String[] observerUsernames; //TODO: figure out how to store observer usernames
    private transient ChessGame game;

    public GameModel(String gameName) {
        this.gameName = gameName;
        this.game = new Game();
        Random rand = new Random();
        this.gameID = rand.nextInt(200000);
        this.whiteUsername = null;
        this.blackUsername = null;
    }
    public int getGameID() {
        return gameID;
    }
    public String getWhiteUsername() {
        return this.whiteUsername;
    }
    public String getBlackUsername() {
        return this.blackUsername;
    }
    public String getGameName() {
        return this.gameName;
    }
    public ChessGame getGame() {
        return this.game;
    }
    public void addObserver(String username) {
//        observerUsernames[observerUsernames.length] = username;
    }

    public void setBlackUsername(String blackUsername) {
        this.blackUsername = blackUsername;
    }

    public void setWhiteUsername(String whiteUsername) {
        this.whiteUsername = whiteUsername;
    }
}
