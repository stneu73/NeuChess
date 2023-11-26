package services;

import dao.SQLDAO;
import dataAccess.DataAccessException;
import models.GameModel;
import responses.CreateGameResponse;

/**
 * Service to create a new chess game in the database
 */
public class CreateGameService {
    /**
     * creates a new game in the database
     */
    public CreateGameResponse newGame(String gameName, String authToken) {
        SQLDAO dao = new SQLDAO();
        if (gameName == null || gameName.isEmpty()) {
            return new CreateGameResponse("Error: Bad Request");
        }
        try {
            if (!dao.findAuthToken(authToken)) {
                return new CreateGameResponse("Error: Unauthorized");
            }
        } catch (DataAccessException e) {
            return new CreateGameResponse("Error: Couldn't Access Database");
        }
        int gameID;
        try {
            gameID = dao.insertGame(new GameModel(gameName));//MemoryDAO.getInstance().insertGame(new GameModel(gameName));
        } catch (DataAccessException e) {
            return new CreateGameResponse("Error: Couldn't Access Database");
        }
        return new CreateGameResponse(null, gameID);
    }
}
