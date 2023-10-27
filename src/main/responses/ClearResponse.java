package responses;

/**
 *Reads in response from the server to return to the user on success or failure of the clear request.
 */
public class ClearResponse {
    /**
     * Contains response message when attempting to join a game.
     */
    String message;
    /**
     * Parameterized constructor for ClearResponse. Initializes class variable message with the given response.
     * @param response given response to return to the user. Can be success or failure response.
     */

    public ClearResponse(String response) {
        this.message = response;
    }

    /**
     * Constructor for Clear response. Message is initialized to null.
     */

    public ClearResponse() {
        this.message = null;
    };
    public String getMessage() {
        return message;
    }
}
