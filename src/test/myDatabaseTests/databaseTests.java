package myDatabaseTests;

import dataAccess.DataAccessException;
import dataAccess.Database;
import org.junit.jupiter.api.*;
import dao.SQLDAO;
import models.*;

import java.sql.SQLException;

public class databaseTests {
    String username = "testUsername";
    String password = "testPassword";
    String email = "testEmail";
    String authToken;
    int gameID = 0;

    @BeforeAll
    public static void init() {
        try {
            new SQLDAO().configureDatabase();
        } catch (DataAccessException ignored) {}


    }
    @BeforeEach
    public void init2() {
        try {
            new SQLDAO().clearData();
        } catch (DataAccessException ignored) {}
        try (var conn = new Database().getConnection()) {
            conn.setCatalog("chess");
            try (var preparedStatement = conn.prepareStatement("INSERT INTO user (username, pass, email) VALUES (?,?,?);")) {
                preparedStatement.setString(1,username);
                preparedStatement.setString(2,password);
                preparedStatement.setString(3,email);

                preparedStatement.executeUpdate();
            }

        } catch (SQLException | DataAccessException ignored) {}
        try {
            authToken = new SQLDAO().generateAuthToken(username);
        } catch (DataAccessException ignored) {}
        GameModel model = new GameModel("test");
        gameID = model.getGameID();
        try {
            new SQLDAO().insertGame(model);
        } catch (DataAccessException ignored) {}
    }

    @Test
    public void configDatabasePositive() {

        try {
            new SQLDAO().clearData();
            new SQLDAO().configureDatabase();
        } catch (DataAccessException ignore) {}
        try (var conn = new Database().getConnection()) {
            conn.setCatalog("chess");
            try (var preparedStatement = conn.prepareStatement("SHOW Tables;")) {
                var result = preparedStatement.executeQuery();
                int i = 0;
                while (result.next()) {
                    i++;
                }
                Assertions.assertEquals(3,i,"Incorrect number of tables");
            }
        } catch (SQLException | DataAccessException ignore) {}
    }

    @Test
    public void configDatabasePositive2() {
        try {
            new SQLDAO().clearData();
            new SQLDAO().configureDatabase();
        } catch (DataAccessException ignore) {}
        try {
            Assertions.assertFalse(new SQLDAO().findUser(username),"Cannot be queried");
        } catch (DataAccessException ignore) {}

    }

    @Test
    public void clearDataPositive() {
        try {
            new SQLDAO().clearData();
        } catch (DataAccessException ignored) {}

        try (var conn = new Database().getConnection()) {
            conn.setCatalog("chess");
            try (var preparedStatement = conn.prepareStatement("SELECT * FROM auth;")) {
                Assertions.assertFalse(preparedStatement.executeQuery().next(),"auth table didn't return empty.");
            }
            try (var preparedStatement = conn.prepareStatement("SELECT * FROM games;")) {
                Assertions.assertFalse(preparedStatement.executeQuery().next(),"games table didn't return empty.");
            }
            try (var preparedStatement = conn.prepareStatement("SELECT * FROM user;")) {
                Assertions.assertFalse(preparedStatement.executeQuery().next(),"user table didn't return empty.");
            }
        } catch (DataAccessException | SQLException ignored) {}
    }

    @Test
    public void insertGamePositive() {
        try (var conn = new Database().getConnection()) {
            conn.setCatalog("chess");
            try (var preparedStatement = conn.prepareStatement("SELECT * FROM games;")) {
                var result = preparedStatement.executeQuery();
                Assertions.assertTrue(result.next(),"games table returned empty.");
                Assertions.assertEquals(result.getString("gameName"),"test","Game name returned wrong");
            }
        } catch (DataAccessException | SQLException ignored) {}
    }

    @Test
    public void insertGameNegative() {
        var fakeBoard = new char[64];
        GameModel model = new GameModel(12,"","","", String.valueOf(fakeBoard));
        try {
            new SQLDAO().insertGame(model);
        } catch (DataAccessException d) {
            Assertions.assertEquals(d.getMessage(),"fail", "Database should have returned an exception");
        }

    }

    @Test
    public void findGamePositive() {
        int idOfGame = 0;
        try (var conn = new Database().getConnection()) {
            conn.setCatalog("chess");
            try (var preparedStatement = conn.prepareStatement("SELECT id, gameName FROM games WHERE gameName=\"test\";")) {
                var result = preparedStatement.executeQuery();
                result.next();
                idOfGame = result.getInt("id");
            }
        } catch (DataAccessException | SQLException ignored) {}
        try {
            Assertions.assertTrue(new SQLDAO().findGame(idOfGame));
        } catch (DataAccessException ignored) {}
    }

    @Test
    public void findGameNegative() {
        try {
            Assertions.assertFalse(new SQLDAO().findGame(1000000000),"Found game that doesn't exist");
        } catch (DataAccessException ignored) {}
    }

    @Test
    public void getGamePositive() {
        int idOfGame = 0;
        try (var conn = new Database().getConnection()) {
            conn.setCatalog("chess");
            try (var preparedStatement = conn.prepareStatement("SELECT id, gameName FROM games WHERE gameName=\"test\";")) {
                var result = preparedStatement.executeQuery();
                result.next();
                idOfGame = result.getInt("id");
            }
        } catch (DataAccessException | SQLException ignored) {}
        try {
            Assertions.assertEquals(new SQLDAO().getGame(idOfGame).getGameName(),"test","Database did not return the correct game");
        } catch (DataAccessException ignored) {}


    }

    @Test
    public void getGameNegative() {
        try {
            new SQLDAO().getGame(1000000000);
        } catch (DataAccessException e) {
            Assertions.assertEquals(e.getMessage(),"fail", "DataAccessException was not thrown");
        }
    }

    @Test
    public void getAllGamesPositive() {
        GameModel model1 = new GameModel("test1");
        GameModel model2 = new GameModel("test2");
        try {
            new SQLDAO().insertGame(model1);
            new SQLDAO().insertGame(model2);
        } catch (DataAccessException ignored) {}
        try {
            Assertions.assertEquals(new SQLDAO().getAllGames().size(),3,"Returned wrong number of games");
        } catch (DataAccessException ignored) {}
    }

    @Test
    public void getAllGamesPositiveEmptyList() {
        try {
            new SQLDAO().clearData();
        } catch (DataAccessException ignore) {}
        try {
            Assertions.assertTrue(new SQLDAO().getAllGames().isEmpty(),"getAllGames did not return an empty list");
        } catch (DataAccessException ignore) {}
    }

    @Test
    public void claimSpotPositive() {
        try {
            new SQLDAO().claimSpot(gameID,authToken,"WHITE");
        } catch (DataAccessException ignored) {}
        try (var conn = new Database().getConnection()) {
            conn.setCatalog("chess");
            try (var preparedStatement = conn.prepareStatement("SELECT id, whiteUsername FROM games WHERE id="+gameID+";")) {
                var result = preparedStatement.executeQuery();
                result.next();
                Assertions.assertEquals(result.getString("whiteUsername"),username);
            }
        } catch (DataAccessException | SQLException ignored) {}

    }

    @Test
    public void claimSpotNegative () {
        try {
            new SQLDAO().claimSpot(1000000000,authToken,"WHITE");
        } catch (DataAccessException e) {
            Assertions.assertEquals(e.getMessage(),"fail", "DataAccessException was not thrown");
        }
    }

    @Test
    public void createUserPositive() {
        String testUsername = "tester";
        User user = new User(testUsername, "pass123", "muhemail");
        try {
            new SQLDAO().createUser(testUsername, user);
        } catch (DataAccessException ignore) {}
        try (var conn = new Database().getConnection()) {
            conn.setCatalog("chess");
            try (var preparedStatement = conn.prepareStatement("SELECT username FROM user WHERE username="+testUsername+";")) {
                var result = preparedStatement.executeQuery();
                result.next();
                Assertions.assertEquals(result.getString("username"),testUsername);
            }
        } catch (DataAccessException | SQLException ignored) {}

    }

    @Test
    public void createUserNegative() {
        User user = new User("","pass123","email");
        try {
            new SQLDAO().createUser(user.getUsername(),user);
        } catch (DataAccessException e) {
            Assertions.assertEquals(e.getMessage(),"fail", "DataAccessException was not thrown");
        }
    }

    @Test
    public void findUserPositive() {
        try {
            Assertions.assertTrue(new SQLDAO().findUser(username), "Database did not find existing user");
        } catch (DataAccessException ignore) {}
    }

    @Test
    public void findUserNegative() {
        try {
            Assertions.assertFalse(new SQLDAO().findUser("user"),"Database found non-existent user");
        } catch (DataAccessException ignore) {}
    }

    @Test
    public void getUserPositive() {
        try {
            Assertions.assertEquals(new SQLDAO().getUser(username).getUsername(),username,"Did not retrieve specified user");
        } catch (DataAccessException ignore) {}
    }

    @Test
    public void getUserNegative() {
        try {
            Assertions.assertNull(new SQLDAO().getUser("DNE"),"Retrieved non-existent user");
        } catch (DataAccessException ignore) {}
    }

    @Test
    public void generateAuthTokenPositive() {

        try {
            new SQLDAO().generateAuthToken("tester");
        } catch (DataAccessException ignore) {}
        try (var conn = new Database().getConnection()) {
            conn.setCatalog("chess");
            try (var preparedStatement = conn.prepareStatement("SELECT FROM auth WHERE username=?")) {
                preparedStatement.setString(1, "tester");
                var result = preparedStatement.executeQuery();
                result.next();
                Assertions.assertFalse(result.getString("authToken").isEmpty(),"Did not return an auth token");
            }
        } catch (DataAccessException | SQLException ignore) {}
    }

    @Test
    public void generateAuthTokenNegative() {
        try {
            new SQLDAO().generateAuthToken("");
        } catch (DataAccessException e) {
            Assertions.assertEquals("fail",e.getMessage(),"Generated AuthToken for an empty username");
        }
    }

    @Test
    public void deleteAuthTokenPositive() {
        try {
            new SQLDAO().deleteAuthToken(authToken);
        } catch (DataAccessException ignore) {}
        try (var conn = new Database().getConnection()) {
            conn.setCatalog("chess");
            try (var preparedStatement = conn.prepareStatement("SELECT authToken FROM auth WHERE authToken =\"" + authToken + "\";")) {
                Assertions.assertFalse(preparedStatement.executeQuery().next(),"AuthToken was found in the database");
            }
        } catch (DataAccessException | SQLException ignore) {}
    }

    @Test
    public void deleteAuthTokenNegative() {
        try {
            new SQLDAO().deleteAuthToken("asdfasdf");
        } catch (DataAccessException e) {
            Assertions.assertEquals("unauthorized",e.getMessage(),"Deleted auth token without proper authorization");
        }
    }

    @Test
    public void findAuthTokenPositive() {
        try {
            Assertions.assertTrue(new SQLDAO().findAuthToken(authToken));
        } catch (DataAccessException ignore) {}
    }

    @Test
    public void findAuthTokenNegative() {
        try {
            Assertions.assertFalse(new SQLDAO().findAuthToken("asdf"));
        } catch (DataAccessException ignore) {}

    }


}
