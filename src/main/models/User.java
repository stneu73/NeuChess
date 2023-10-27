package models;

/**
 * Stores information structure of a user
 */
public class User {
    /**
     * contains the username as a string
     */
    String username;
    /**
     * contains the password as a string
     */
    String password;
    /**
     * contains the email as a string
     */
    String email;
    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
    public String getUsername() {
        return this.username;
    }
    public String getPassword() {
        return this.password;
    }
}
