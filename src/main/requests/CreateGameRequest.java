package requests;

/**
 * request from client to create a game with the stored game name
 */
public class CreateGameRequest {
    /**
     * stores the name of a game
     */
    String gameName;

    public CreateGameRequest(String gameName) {
        this.gameName = gameName;
    }
    public String getGameName() {
        return gameName;
    }
}