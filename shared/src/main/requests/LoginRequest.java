package requests;

/**
 * Represents a request from the client
 * contains the username and password
 */
public class LoginRequest {
    /**
     * holds the username of the request
     */
    String username;
    /**
     * holds the password of the request
     */
    String password;

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
}
