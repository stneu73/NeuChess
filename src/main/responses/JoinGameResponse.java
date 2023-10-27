package responses;

/**
 * Reads in response from the server to return to the user on success or failure of the JoinGame request.
 */
public class JoinGameResponse {
    /**
     * Contains response message when attempting to join a game.
     */
    private String message;

    /**
     * Constructor for JoinGameResponse. Initializes class variable message with the given response.
     * @param message given response to return to the user. Can be success or failure response.
     */
    public JoinGameResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }


}
