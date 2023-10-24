package responses;
/**
 * Reads in the information from the server sent back to the client after attempting to create a new game
 */
public class CreateGameResponse {
    /**
     * contains the gameID to be given to the user
     */
    private int gameID;
    /**
     * Contains response message when attempting to create a game.
     */
    String message;

    /**
     * Constructor for CrateGameResponse
     * @param message the message to be given to the client
     * @param gameID the number of the game made
     */
    public CreateGameResponse(String message, int gameID) {

    }


}