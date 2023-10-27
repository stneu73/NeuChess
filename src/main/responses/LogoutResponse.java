package responses;
/**
 * Response for logging out of a session
 */
public class LogoutResponse {
    /**
     * contains success or error message
     */
    String message;

    public LogoutResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
