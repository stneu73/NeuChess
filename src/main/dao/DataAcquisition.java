package dao;

import chess.ChessGame;
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
    void insertGame() throws DataAccessException;

    /**
     * Receives a game ID or name and then finds the game in the database
     * @return returns the chess game specified to be found
     * @throws DataAccessException If there is an error in accessing data this is thrown
     */
    ChessGame findGame() throws DataAccessException;

    /**
     * Goes into the database to find all the games that are currently being run or have been finished
     * @return returns a list of all the games in the database
     * @throws DataAccessException If there is an error in accessing data this is thrown
     */
    ChessGame[] findAllGames()throws DataAccessException;

    /**
     * Sets one of the users as one of the teams in the chess team
     * @throws DataAccessException If there is an error in accessing data this is thrown
     */
    void claimSpot() throws DataAccessException;

    /**
     * changes the state of a game in the database based on user action
     * @throws DataAccessException If there is an error in accessing data this is thrown
     */
    void updateGame() throws DataAccessException;

    /**
     * deletes a game from the database
     * @throws DataAccessException If there is an error in accessing data this is thrown
     */
    void removeGame() throws DataAccessException;

    //User data

    /**
     * creates a user in the database
     * @throws DataAccessException If there is an error in accessing data this is thrown
     */
    void createUser() throws DataAccessException;

    /**
     * finds a user in the database
     * @return returns the specified user
     * @throws DataAccessException If there is an error in accessing data this is thrown
     */
    User getUser() throws DataAccessException;

    /**
     * changes a user data in the database
     * @throws DataAccessException If there is an error in accessing data this is thrown
     */
    void updateUser() throws DataAccessException;

    /**
     * removes a user from the database
     * @throws DataAccessException If there is an error in accessing data this is thrown
     */
    void deleteUser() throws DataAccessException;

    //auth Token

    /**
     * inserts an authToken in the database
     * @throws DataAccessException If there is an error in accessing data this is thrown
     */
    void createAuthToken() throws DataAccessException;

    /**
     * finds and returns an auth token from the database
     * @return returns something of type AuthToken
     * @throws DataAccessException If there is an error in accessing data this is thrown
     */
    AuthToken getAuthToken() throws DataAccessException;

    /**
     * changes an authToken in the database
     * @throws DataAccessException If there is an error in accessing data this is thrown
     */
    void updateAuthToken() throws DataAccessException;

    /**
     * removes an AtuhToken from the database
     * @throws DataAccessException If there is an error in accessing data this is thrown
     */
    void deleteAuthToken() throws DataAccessException;

}
