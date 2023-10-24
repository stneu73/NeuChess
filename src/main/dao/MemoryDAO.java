package dao;

import chess.ChessGame;
import dataAccess.DataAccessException;
import models.AuthToken;
import models.GameModel;
import models.User;

import java.util.Map;

public class MemoryDAO implements DataAcquisition {
    Map<String, User> usernameToUsers;
    Map<Integer, GameModel> gameIDToGameModel;
    Map<String, AuthToken> authStrToAuthToken;
    @Override
    public void clearData() throws DataAccessException {

    }

    @Override
    public void insertGame() throws DataAccessException {

    }

    @Override
    public ChessGame findGame() throws DataAccessException {
        return null;
    }

    @Override
    public ChessGame[] findAllGames() throws DataAccessException {
        return new ChessGame[0];
    }

    @Override
    public void claimSpot() throws DataAccessException {

    }

    @Override
    public void updateGame() throws DataAccessException {

    }

    @Override
    public void removeGame() throws DataAccessException {

    }

    @Override
    public void createUser() throws DataAccessException {

    }

    @Override
    public User getUser() throws DataAccessException {
        return null;
    }

    @Override
    public void updateUser() throws DataAccessException {

    }

    @Override
    public void deleteUser() throws DataAccessException {

    }

    @Override
    public void createAuthToken() throws DataAccessException {

    }

    @Override
    public AuthToken getAuthToken() throws DataAccessException {
        return null;
    }

    @Override
    public void updateAuthToken() throws DataAccessException {

    }

    @Override
    public void deleteAuthToken() throws DataAccessException {

    }
}
