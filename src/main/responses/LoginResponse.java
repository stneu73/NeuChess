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
    public LoginResponse(String username, String authToken,String message) {
        this.username = username;
        this.message = message;
        this.authToken = authToken;
    }

    public LoginResponse(String message) {
        this.username = null;
        this.message = message;
        this.authToken = null;
    }

    public String getMessage() {
        return this.message;
    }

    public String getAuthToken() {
        return authToken;
    }
}