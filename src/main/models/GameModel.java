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

    private transient ChessGame game;

    public GameModel(String gameName) {
        this.gameName = gameName;
        this.game = new Game();
        Random rand = new Random();
        this.gameID = rand.nextInt(200000);
        this.whiteUsername = null;
        this.blackUsername = null;
    }

    public GameModel(int gameID, String whiteUsername, String blackUsername, String gameName, String gameBoard) {
        this.gameID = gameID;
        this.whiteUsername = whiteUsername;
        this.blackUsername = blackUsername;
        this.gameName = gameName;
        this.game = new Game(gameBoard);

    }

    public int getGameID() {
        return gameID;
    }
    public String getWhiteUsername() {
        return this.whiteUsername;
    }
    public void setWhiteUsername(String whiteUsername) {
        this.whiteUsername = whiteUsername;
    }
    public String getBlackUsername() {
        return this.blackUsername;
    }
    public void setBlackUsername(String blackUsername) {
        this.blackUsername = blackUsername;
    }
    public String getGameName() { return gameName;}
    public ChessGame getGame() {
        return this.game;
    }

}
