package services;

import chess.ChessGame;
import dao.MemoryDAO;
import dataAccess.DataAccessException;
import models.GameModel;
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
        if (!MemoryDAO.getInstance().findAuthToken(authToken)) {
            return new ListGamesResponse("Error: Unauthorized");
        }
        try {
            return new ListGamesResponse(MemoryDAO.getInstance().getAllGames(authToken));
        } catch (DataAccessException e) {
            return new ListGamesResponse("Error: Couldn't Access Database");
        }
    }
}
