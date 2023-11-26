package models;

/**
 * Stores the information for AuthToken and the related Username
 */
public class AuthToken {
    /**
     * contains the authToken as a string
     */
    String authToken;
    /**
     * contains the username as a string
     */
    String username;
    public AuthToken(String authToken, String username) {
        this.authToken = authToken;
        this.username = username;
    }

    public String getAuthToken() {
        return this.authToken;
    }

    public String getUsername() {
        return this.username;
    }
}
