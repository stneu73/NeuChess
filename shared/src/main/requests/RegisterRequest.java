package requests;
/**
 * Request from client to make a new user
 * contains the username, password, and email as strings
 */
public class RegisterRequest {
    /**
     * stores the username as a string
     */
    String username;
    /**
     * stores the password as a string
     */
    String password;
    /**
     * stores the email as a string
     */
    String email;

    public RegisterRequest(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}
