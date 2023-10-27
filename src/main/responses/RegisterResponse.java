package responses;
/**
 * Response to a request to make a new user in the database
 */
public class RegisterResponse {
    /**
     * contains the username that was just created
     */
    String username;
    /**
     * contains the authentication token from the database as a string
     */
    String authToken;
    /**
     * contains success or error message
     */
    String message;

    public RegisterResponse(String username, String authToken, String message) {
        this.message = message;
        this.authToken = authToken;
        this.username = username;
    }

    public RegisterResponse(String message) {
        this.message = message;
        this.authToken = null;
        this.username = null; // should this be the duplicate username?
    }

    public String getMessage() {
        return this.message;
    }
    public String getUsername() {
        return username;
    }
}
