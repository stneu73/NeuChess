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
}
