package responses;
/**
 * Response to login request giving back the authentication token, username, and message, or error message
 */
public class LoginResponse {
    /**
     * contains the username of the attempted login
     */
    String username;
    /**
     * contains success or error message
     */
    String message;
    /**
     * contains the authentication token from the database
     */
    String authToken;
}