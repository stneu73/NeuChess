package dao;

import dataAccess.DataAccessException;
import models.AuthToken;
import models.GameModel;
import models.User;

/**
 * Interface that manages reading and writing to the database
 */

public interface DataAcquisition {
    /**
     * Clears all data stored in the database
     * @throws DataAccessException If there is an error in accessing data this is thrown
     */
    void clearData() throws DataAccessException;

    //game data

    /**
     * creates a game in the database
     * @throws DataAccessException If there is an error in accessing data this is thrown
     */
    int insertGame(GameModel gameModel) throws DataAccessException;

    boolean findGame(Integer gameID);

    /**
     * Receives a game ID or name and then finds the game in the database
     * @return returns the chess game specified to be found
     * @throws DataAccessException If there is an error in accessing data this is thrown
     */
    GameModel getGame(Integer gameID) throws DataAccessException;

    /**
     * Goes into the database to find all the games that are currently being run or have been finished
     * @return returns a list of all the games in the database
     * @throws DataAccessException If there is an error in accessing data this is thrown
     */
    GameModel[] getAllGames(String authToken)throws DataAccessException;

    /**
     * Sets one of the users as one of the teams in the chess team
     * @throws DataAccessException If there is an error in accessing data this is thrown
     */
    void claimSpot(Integer gameID, String username, String color) throws DataAccessException;

    void addWatcher(Integer gameID, String authToken) throws DataAccessException;

    /**
     * changes the state of a game in the database based on user action
     * @throws DataAccessException If there is an error in accessing data this is thrown
     */
    void updateGame(GameModel gameModel) throws DataAccessException;

    /**
     * deletes a game from the database
     * @throws DataAccessException If there is an error in accessing data this is thrown
     */
    void removeGame(Integer gameID) throws DataAccessException;

    //User data

    /**
     * creates a user in the database
     *
     * @return
     * @throws DataAccessException If there is an error in accessing data this is thrown
     */
    User createUser(String username, User user) throws DataAccessException;

    boolean findUser(String username);

    /**
     * finds a user in the database
     * @return returns the specified user
     * @throws DataAccessException If there is an error in accessing data this is thrown
     */
    User getUser(String username) throws DataAccessException;

    /**
     * changes a user data in the database
     * @throws DataAccessException If there is an error in accessing data this is thrown
     */
    void updateUser(String username) throws DataAccessException;

    /**
     * removes a user from the database
     * @throws DataAccessException If there is an error in accessing data this is thrown
     */
    void deleteUser(String username) throws DataAccessException;

    //auth Token

    /**
     * inserts an authToken in the database
     * @throws DataAccessException If there is an error in accessing data this is thrown
     */
//    void insertAuthToken(String uuid, AuthToken authToken) throws DataAccessException;

    String generateAuthToken(String username) throws DataAccessException;

    /**
     * finds and returns an auth token from the database
     * @return returns something of type AuthToken
     * @throws DataAccessException If there is an error in accessing data this is thrown
     */
    AuthToken getAuthToken(String authToken) throws DataAccessException;

    /**
     * removes an AtuhToken from the database
     * @throws DataAccessException If there is an error in accessing data this is thrown
     */
    void deleteAuthToken(String authToken) throws DataAccessException;

    boolean findAuthToken(String authToken);
}
