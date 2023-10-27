package requests;

/**
 * Request from the client to join a game
 * contains the color requested and the gameID to be joined
 */
public class JoinGameRequest {
    /**
     * holds the color requested
     */
    String playerColor;
    /**
     * holds the gameID number
     */
    int gameID;

    public JoinGameRequest(int gameID, String color) {
        this.gameID = gameID;
        this.playerColor = color;
    }
    public int getGameID() {
        return gameID;
    }

    public String getPlayerColor() {
        return playerColor;
    }
}
