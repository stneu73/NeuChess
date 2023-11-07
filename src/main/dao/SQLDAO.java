package dao;

import dataAccess.DataAccessException;
import dataAccess.Database;
import models.AuthToken;
import models.GameModel;
import models.User;

import java.sql.SQLException;
import java.util.Objects;
import java.util.UUID;

public class SQLDAO implements DataAcquisition {
    public void configureDatabase() throws DataAccessException {
        try (var conn = new Database().getConnection()) {
//            var createDbStatement = conn.prepareStatement("CREATE DATABASE IF NOT EXISTS chess");
//            createDbStatement.executeUpdate();

//            conn.setCatalog("chess");

            var createUserTable = """
                CREATE TABLE IF NOT EXISTS user (
                    id INT NOT NULL AUTO_INCREMENT,
                    username VARCHAR(255) NOT NULL,
                    password VARCHAR(255) NOT NULL,
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
                preparedStatement.setString(5,gameModel.getGame().gameToString()); //TODO fix this to be a serialized version of the board state

                preparedStatement.executeUpdate();
                //TODO: get the generated keys? what is that really?
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
            try (var preparedStatement = conn.prepareStatement("SELECT id FROM games WHERE id =" + gameID + ";")) {
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
                    return new GameModel(result.getInt("id"), result.getString("whiteUsername"),result.getString("blackUsername"),result.getString("gameName"),result.getString("gameBoard"));
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("fail");
        }
    }

    @Override
    public GameModel[] getAllGames(String authToken) throws DataAccessException {
        GameModel[] gameList;
        try (var conn = new Database().getConnection()) {
            conn.setCatalog("chess");
            try (var preparedStatement = conn.prepareStatement("SELECT * FROM games;")) {// id, whiteUsername, blackUsername, gameName, gameBoard FROM games WHERE id=?;")) {
                try (var result = preparedStatement.executeQuery()) {
                    gameList = new GameModel[result.getFetchSize()];
                    int i = 0;
                    while (result.next()) {
                        gameList[i] = new GameModel(result.getInt("id"), result.getString("whiteUsername"), result.getString("blackUsername"), result.getString("gameName"), result.getString("gameBoard"));
                        i++;
                    }
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("fail");
        }
        return gameList;
    }

    @Override
    public void claimSpot(Integer gameID, String username, String color) throws DataAccessException {
        try (var conn = new Database().getConnection()) {
            conn.setCatalog("chess");
            try (var preparedStatement = conn.prepareStatement("SELECT id, "+color.toLowerCase()+"Username FROM games WHERE id =?;")) {
                preparedStatement.setInt(1,gameID);
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
            try (var preparedStatement = conn.prepareStatement("INSERT INTO user (username, password, email) VALUES (?,?,?);")) {
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
        boolean flag = false;
        try (var conn = new Database().getConnection()) {
            conn.setCatalog("chess");
            try (var preparedStatement = conn.prepareStatement("SELECT username FROM user WHERE username=" + username + ";")) {
                if (Objects.equals(preparedStatement.executeQuery().getString("username"), username)) {
                    flag = true;
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("fail");
        }
        return flag;
    }

    @Override
    public User getUser(String username) throws DataAccessException {
        try (var conn = new Database().getConnection()) {
            conn.setCatalog("chess");
            try (var preparedStatement = conn.prepareStatement("SELECT username, password, email FROM user WHERE username =?;")) {
                preparedStatement.setString(1,username);
                try (var result = preparedStatement.executeQuery()) {
                    return new User(result.getString("username"),result.getString("password"),result.getString("email"));
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("fail");
        }
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
        boolean flag = false;
        try (var conn = new Database().getConnection()) {
            conn.setCatalog("chess");
            try (var preparedStatement = conn.prepareStatement("SELECT authToken FROM auth WHERE authToken =" + authToken + ";")) {
                if (Objects.equals(preparedStatement.executeQuery().getString("authToken"), authToken)) {
                    flag = true;
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("fail");
        }
        return flag;
    }
}
