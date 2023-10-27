package dao;

import dataAccess.DataAccessException;
import models.AuthToken;
import models.GameModel;
import models.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class MemoryDAO implements DataAcquisition {
    private static MemoryDAO instance = new MemoryDAO();
    Map<String, User> usernameToUsers = new HashMap<>();
    Map<Integer, GameModel> gameIDToGameModel = new HashMap<>();
    Map<String, AuthToken> authStrToAuthToken = new HashMap<>();
    public static MemoryDAO getInstance() {return instance;}



    @Override
    public void clearData() throws DataAccessException {
        usernameToUsers.clear();
        gameIDToGameModel.clear();
        authStrToAuthToken.clear();
    }

    @Override
    public int insertGame(GameModel gameModel) throws DataAccessException {
        gameIDToGameModel.put(gameModel.getGameID(),gameModel);
        return gameModel.getGameID();
    }

    public boolean findGame(Integer gameID) {
        return gameIDToGameModel.containsKey(gameID);
    }
    @Override
    public GameModel getGame(Integer gameID) throws DataAccessException {
        return gameIDToGameModel.get(gameID);
    }

    @Override
    public GameModel[] getAllGames(String authToken) throws DataAccessException {
        GameModel[] gameModelList = new GameModel[gameIDToGameModel.size()];
        int i = 0;
        for (var itr :gameIDToGameModel.keySet()) {
            gameModelList[i] = gameIDToGameModel.get(itr);
            i+=1;
        }
        return gameModelList;
    }

    @Override
    public void claimSpot(Integer gameID, String authToken, String color) throws DataAccessException {
        String username = authStrToAuthToken.get(authToken).getUsername();
        GameModel game = gameIDToGameModel.get(gameID);
        if (Objects.equals(color.toLowerCase(), "black")) {
            game.setBlackUsername(username);
        }
        else if (Objects.equals(color.toLowerCase(), "white")) {
            game.setWhiteUsername(username);
        } //else add as observer
    }

    @Override
    public void updateGame(GameModel gameModel) throws DataAccessException {
        gameIDToGameModel.replace(gameModel.getGameID(),gameModel);
    }

    @Override
    public void removeGame(Integer gameID) throws DataAccessException {
        gameIDToGameModel.remove(gameID);
    }

    @Override
    public User createUser(String username, User user) throws DataAccessException {
        usernameToUsers.put(username,user);
        return user;
    }

    public boolean findUser(String username) {
        return usernameToUsers.containsKey(username);
    }
    @Override
    public User getUser(String username) throws DataAccessException {
        return usernameToUsers.get(username);
    }

    @Override
    public void updateUser(String username) throws DataAccessException {
        //maybe this is used to change a password but I don't think I need that functionality
    }

    @Override
    public void deleteUser(String username) throws DataAccessException {
        usernameToUsers.remove(username);
    }

    @Override
    public String insertAuthToken(String username) throws DataAccessException {
        String uuid = String.valueOf(UUID.randomUUID());

        authStrToAuthToken.put(uuid,new AuthToken(uuid, username));
        return uuid;
    }

    @Override
    public AuthToken getAuthToken(String authToken) throws DataAccessException {
        return authStrToAuthToken.get(authToken);
    }

//    @Override
//    public void updateAuthToken() throws DataAccessException {
//
//    }

    @Override
    public void deleteAuthToken(String authToken) throws DataAccessException {
        if (authStrToAuthToken.get(authToken) == null) {
            throw new DataAccessException("unauthorized");
        }
        authStrToAuthToken.remove(authToken);
    }
    public boolean findAuthToken(String authToken) {
        return authStrToAuthToken.containsKey(authToken);
    }
}
