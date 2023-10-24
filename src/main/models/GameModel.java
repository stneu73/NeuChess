package models;

import chess.ChessGame;

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
    int getGameID() {
        return 0;
    }
    String getWhiteUsername() {
        return null;
    }
    String getBlackUsername() {
        return null;
    }
    String getGameName() {
        return null;
    }
    ChessGame getGame() {
        return null;
    }
}
