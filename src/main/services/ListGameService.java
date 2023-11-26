package services;

import dao.SQLDAO;
import dataAccess.DataAccessException;
import responses.ListGamesResponse;

/**
 * Lists and packages the games to be sent to the client
 */
public class ListGameService {
    /**
     * Converts a list of game models to be transferred and then read by the client
     * @return the list of games
     */
    public ListGamesResponse listGames(String authToken) {
        SQLDAO dao = new SQLDAO();
        try {
            if (!dao.findAuthToken(authToken)) {
                return new ListGamesResponse("Error: Unauthorized");
            }
        } catch (DataAccessException e) {
            return new ListGamesResponse("Error: Couldn't Access Database");
        }
        try {
            return new ListGamesResponse(dao.getAllGames());//MemoryDAO.getInstance().getAllGames(authToken));
        } catch (DataAccessException e) {
            return new ListGamesResponse("Error: Couldn't Access Database");
        }
    }
}
