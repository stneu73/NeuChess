package responses;
/**
 * Contains all the information for all the games to be listed out to be given to the client
 */
public class ListGamesResponse {
    /**
     * has the username of the white player for one of the games
     */
    String whiteUsername;
    /**
     * has the username of the black player for one of the games
     */
    String blackUsername;
    /**
     * has the name of one game
     */
    String gameName;
    /**
     * contains a list of all the games from the database
     */
    String[] games;

    public ListGamesResponse(String whiteUsername, String blackUsername, String gameName, String[] games) {
        this.whiteUsername = whiteUsername;
        this.blackUsername = blackUsername;
        this.gameName = gameName;
        this.games = games;
    }
}
