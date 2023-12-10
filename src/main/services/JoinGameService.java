package services;

import dao.SQLDAO;
import dataAccess.DataAccessException;
import models.GameModel;
import responses.JoinGameResponse;

/**
 * Adds the user to a game
 */
public class JoinGameService {
    /**
     * adds a user to a game
     * @param gameID the number of the game ID to be joined
     * @param authToken the name of the user joining the game
     * @param color the team color the user will be joining the game as
     */
    public JoinGameResponse joinGame(int gameID, String authToken, String color) {
        SQLDAO dao = new SQLDAO();
        try {
            if (!dao.findAuthToken(authToken)) {
                return new JoinGameResponse("Error: Unauthorized");
            }
        } catch (DataAccessException e) {
            return new JoinGameResponse("Error: Couldn't Access Database");
        }
        try {
            if (!dao.findGame(gameID)) {
                return new JoinGameResponse("Error: Bad Request");
            }
        } catch (DataAccessException e) {
            return new JoinGameResponse("Error: Couldn't Access Database");
        }
        GameModel game;
        try {
            game = dao.getGame(gameID);//MemoryDAO.getInstance().getGame(gameID);
        } catch (DataAccessException e) {
            return new JoinGameResponse("Error: Couldn't Access Database");
        }
        if (color != null) {
            if (color.equalsIgnoreCase("white")) {
                if (game.getWhiteUsername() != null) {
                    return new JoinGameResponse("Error: Color Already Taken");
                }
            }
            if (color.equalsIgnoreCase("black")) {
                if (game.getBlackUsername() != null) {
                    return new JoinGameResponse("Error: Color Already Taken");
                }
            }
            try {
                dao.claimSpot(gameID, authToken, color);//MemoryDAO.getInstance().claimSpot(gameID, authToken, color);
            } catch (DataAccessException e) {
                return new JoinGameResponse("Error: Couldn't Access Database");
            }
        }
        return new JoinGameResponse(null);
    }
}