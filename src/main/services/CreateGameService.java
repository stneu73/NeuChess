package services;

import dao.MemoryDAO;
import dataAccess.DataAccessException;
import models.GameModel;
import requests.CreateGameRequest;
import responses.CreateGameResponse;

/**
 * Service to create a new chess game in the database
 */
public class CreateGameService {
    /**
     * creates a new game in the database
     */
    public CreateGameResponse newGame(String gameName, String authToken) {
        if (gameName.isEmpty()) {
            return new CreateGameResponse("Error: Bad Request");
        }
        if (!MemoryDAO.getInstance().findAuthToken(authToken)) {
            return new CreateGameResponse("Error: Unauthorized");
        }
        int gameID;
        try {
            gameID = MemoryDAO.getInstance().insertGame(new GameModel(gameName));
        } catch (DataAccessException e) {
            return new CreateGameResponse("Error: Couldn't Access Database");
        }
        return new CreateGameResponse(null, gameID);
    }
}
