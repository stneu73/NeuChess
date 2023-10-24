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
     * @param response given response to return to the user. Can be success or failure response.
     */
    public JoinGameResponse(String response) {
        this.message = response;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
