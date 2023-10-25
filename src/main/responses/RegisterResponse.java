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
     * contains the password of the associated as a string
     */
    String password;
    /**
     * contains the authentication token from the database as a string
     */
    String authToken;
    /**
     * contains success or error message
     */
    String message;

    public RegisterResponse(String username, String password, String authToken, String message) {
        this.message = message;
        this.authToken = authToken;
        this.password = password;
        this.username = username;
    }
}
