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
     * Constructor for ClearResponse. Initializes class variable message with the given response.
     * @param response given response to return to the user. Can be success or failure response.
     */
    public ClearResponse(String response) {
        this.message = response;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
