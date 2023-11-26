package dao;

import dataAccess.DataAccessException;
import dataAccess.Database;
import models.AuthToken;
import models.GameModel;
import models.User;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.UUID;

public class SQLDAO implements DataAcquisition {
    public void configureDatabase() throws DataAccessException {
        try (var conn = new Database().getConnection()) {

            var createUserTable = """
                CREATE TABLE IF NOT EXISTS user (
                    id INT NOT NULL AUTO_INCREMENT,
                    username VARCHAR(255) NOT NULL,
                    pass VARCHAR(255) NOT NULL,
                    email VARCHAR(255) NOT NULL,
                    PRIMARY KEY(id)
                );""";

            try (var createTableStatement = conn.prepareStatement(createUserTable)) {
                createTableStatement.executeUpdate();
            }
            var createAuthTable = """
                CREATE TABLE IF NOT EXISTS auth (
                    authToken VARCHAR(255) NOT NULL,
                    username VARCHAR(255) NOT NULL,
                    PRIMARY KEY(authToken)
                );""";
            try (var createTableStatement = conn.prepareStatement(createAuthTable)) {
                createTableStatement.executeUpdate();
            }

            var createGameTable = """
                Create Table IF NOT EXISTS games (
                    id INT NOT NULL,
                    whiteUsername VARCHAR(255),
                    blackUsername VARCHAR(255),
                    gameName VARCHAR(255) NOT NULL,
                    gameBoard VARCHAR(255) NOT NULL,
                    PRIMARY KEY(id)
                );""";

            try (var createTableStatement = conn.prepareStatement(createGameTable)) {
                createTableStatement.executeUpdate();
            }

        } catch (SQLException e) {
            throw new DataAccessException("fail");
        }
    }

    @Override
    public void clearData() throws DataAccessException {
        try (var conn = new Database().getConnection()) {
            conn.setCatalog("chess");
            var dropUserTable = "DROP TABLE user;";
            try (var dropTableStatement = conn.prepareStatement(dropUserTable)) {
                dropTableStatement.executeUpdate();
            }
            var dropAuthTable = "DROP TABLE auth;";
            try (var dropTableStatement = conn.prepareStatement(dropAuthTable)) {
                dropTableStatement.executeUpdate();
            }
            var dropGamesTable = "DROP TABLE games;";
            try (var dropTableStatement = conn.prepareStatement(dropGamesTable)) {
                dropTableStatement.executeUpdate();
            }

        } catch (SQLException e) {
            throw new DataAccessException("fail");
        }

        try {
            configureDatabase();
        } catch (DataAccessException e) {
            throw new DataAccessException("fail");
        }

    }

    @Override
    public int insertGame(GameModel gameModel) throws DataAccessException {
        try (var conn = new Database().getConnection()) {
            conn.setCatalog("chess");
            try (var preparedStatement = conn.prepareStatement("INSERT INTO games (id, whiteUsername, blackUsername, gameName, gameBoard) VALUES (?,?,?,?,?);")) {
                preparedStatement.setInt(1,gameModel.getGameID());
                preparedStatement.setString(2,gameModel.getWhiteUsername());
                preparedStatement.setString(3,gameModel.getBlackUsername());
                preparedStatement.setString(4,gameModel.getGameName());
                preparedStatement.setString(5,gameModel.getGameToString());

                preparedStatement.executeUpdate();
            }

        } catch (SQLException e) {
            throw new DataAccessException("fail");
        }
        return gameModel.getGameID();
    }

    @Override
    public boolean findGame(Integer gameID) throws DataAccessException {
        boolean flag = false;
        try (var conn = new Database().getConnection()) {
            conn.setCatalog("chess");
            try (var preparedStatement = conn.prepareStatement("SELECT id FROM games WHERE id =\"" + gameID + "\";")) {
                if (preparedStatement.executeQuery().next()) {
                    flag = true;
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("fail");
        }
        return flag;
    }

    @Override
    public GameModel getGame(Integer gameID) throws DataAccessException {
        try (var conn = new Database().getConnection()) {
            conn.setCatalog("chess");
            try (var preparedStatement = conn.prepareStatement("SELECT id, whiteUsername, blackUsername, gameName, gameBoard FROM games WHERE id =?;")) {
                preparedStatement.setInt(1,gameID);
                try (var result = preparedStatement.executeQuery()) {
                    if (result.next()) {
                        int theID = result.getInt("id");
                        String whteUser = result.getString("whiteUsername");
                        String blckUser = result.getString("blackUsername");
                        String gameyName = result.getString("gameName");
                        String gamey = result.getString("gameBoard");
                        return new GameModel(theID,whteUser,blckUser,gameyName,gamey);
                    }

                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("fail");
        }
        return null;
    }

    @Override
    public LinkedList<GameModel> getAllGames() throws DataAccessException {
        LinkedList<GameModel> gameList = new LinkedList<>();
        try (var conn = new Database().getConnection()) {
            conn.setCatalog("chess");
            try (var preparedStatement = conn.prepareStatement("SELECT * FROM games;")) {// id, whiteUsername, blackUsername, gameName, gameBoard FROM games WHERE id=?;")) {
                try (var result = preparedStatement.executeQuery()) {
                    while (result.next()) {
                        int theID = result.getInt("id");
                        String whteUser = result.getString("whiteUsername");
                        String blckUser = result.getString("blackUsername");
                        String gameyName = result.getString("gameName");
                        String gamey = result.getString("gameBoard");
                        gameList.add(new GameModel(theID,whteUser,blckUser,gameyName,gamey));
                    }
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("fail");
        }
        return gameList;
    }

    @Override
    public void claimSpot(Integer gameID, String authToken, String color) throws DataAccessException {
        String username = null;
        try (var conn = new Database().getConnection()) {
            conn.setCatalog("chess");
            try (var preparedStatement = conn.prepareStatement("SELECT authToken, username FROM auth WHERE authToken=?;")) {
                preparedStatement.setString(1,authToken);
                try (var result = preparedStatement.executeQuery()) {
                    if (result.next()) {
                        username = result.getString("username");
                    }
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("fail");
        }
        try (var conn = new Database().getConnection()) {
            conn.setCatalog("chess");
            try (var preparedStatement = conn.prepareStatement("UPDATE games SET "+ color.toLowerCase() +"Username =? WHERE id=?;")) {
                preparedStatement.setString(1,username);
                preparedStatement.setInt(2,gameID);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataAccessException("fail");
        }
    }

    @Override
    public void addWatcher(Integer gameID, String authToken) throws DataAccessException {

    }

    @Override
    public void updateGame(GameModel gameModel) throws DataAccessException {

    }

    @Override
    public void removeGame(Integer gameID) throws DataAccessException {

    }

    @Override
    public User createUser(String username, User user) throws DataAccessException {
        try (var conn = new Database().getConnection()) {
            conn.setCatalog("chess");
            try (var preparedStatement = conn.prepareStatement("INSERT INTO user (username, pass, email) VALUES (?,?,?);")) {
                preparedStatement.setString(1,user.getUsername());
                preparedStatement.setString(2,user.getPassword());
                preparedStatement.setString(3,user.getEmail());

                preparedStatement.executeUpdate();
            }

        } catch (SQLException e) {
            throw new DataAccessException("fail");
        }
        return user;
    }

    @Override
    public boolean findUser(String username) throws DataAccessException {
        try (var conn = new Database().getConnection()) {
            conn.setCatalog("chess");
            try (var preparedStatement = conn.prepareStatement("SELECT username FROM user WHERE username=\"" + username + "\";")) {
                return preparedStatement.executeQuery().next();
            }
        } catch (SQLException e) {
            throw new DataAccessException("fail");
        }
    }

    @Override
    public User getUser(String username) throws DataAccessException {
        try (var conn = new Database().getConnection()) {
            conn.setCatalog("chess");
            try (var preparedStatement = conn.prepareStatement("SELECT username, pass, email FROM user WHERE username =?;")) {
                preparedStatement.setString(1,username);
                try (var result = preparedStatement.executeQuery()) {
                    if (result.next()) {
                        String name = result.getString("username");
                        String pass = result.getString("pass");
                        String mail = result.getString("email");
                        return new User(name,pass,mail);
                    }

                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("fail");
        }
        return null;
    }

    @Override
    public void updateUser(String username) throws DataAccessException {

    }

    @Override
    public void deleteUser(String username) throws DataAccessException {

    }

    @Override
    public String generateAuthToken(String username) throws DataAccessException {
        String uuid = String.valueOf(UUID.randomUUID());
        try (var conn = new Database().getConnection()) {
            conn.setCatalog("chess");
            try (var preparedStatement = conn.prepareStatement("INSERT INTO auth (authToken, username) VALUES (?,?);")) {
                preparedStatement.setString(1,uuid);
                preparedStatement.setString(2,username);

                preparedStatement.executeUpdate();
            }

        } catch (SQLException e) {
            throw new DataAccessException("fail");
        }
        return uuid;
    }

    @Override
    public AuthToken getAuthToken(String authToken) throws DataAccessException {
//        try (var conn = new Database().getConnection()) {
//            conn.setCatalog("chess");
//            try (var preparedStatement = conn.prepareStatement("SELECT FROM auth WHERE authToken=?")) {
//                preparedStatement.setString(1,authToken);
//
//                preparedStatement.executeQuery();
//            }
//
//        } catch (SQLException e) {
//            throw new DataAccessException("fail");
//        }
        return null;
    }

    @Override
    public void deleteAuthToken(String authToken) throws DataAccessException {
        if (!findAuthToken(authToken)) {
            throw new DataAccessException("unauthorized");
        }
        try (var conn = new Database().getConnection()) {
            conn.setCatalog("chess");
            try (var preparedStatement = conn.prepareStatement("DELETE FROM auth WHERE authToken=?;")) {
                preparedStatement.setString(1,authToken);

                preparedStatement.executeUpdate();
            }

        } catch (SQLException e) {
            throw new DataAccessException("fail");
        }
    }

    @Override
    public boolean findAuthToken(String authToken) throws DataAccessException {
        try (var conn = new Database().getConnection()) {
            conn.setCatalog("chess");
            try (var preparedStatement = conn.prepareStatement("SELECT authToken FROM auth WHERE authToken =\"" + authToken + "\";")) {
                return preparedStatement.executeQuery().next();
            }
        } catch (SQLException e) {
            throw new DataAccessException("fail");
        }
    }
}
