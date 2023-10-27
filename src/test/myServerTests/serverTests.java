package myServerTests;

import org.junit.jupiter.api.*;
import responses.*;
import services.*;

public class serverTests {
    private String existingAuth;
    @BeforeEach
    public void init() {
        new ClearService().clearAll();
        String username = "TestUsername";
        String password = "password123";
        String email = "email";
        new RegisterService().registerUser(username, password,email);
        existingAuth = new LoginService().login(username,password).getAuthToken();
    }

    @Test
    public void clearTest() {
        ClearResponse result = new ClearService().clearAll();
        Assertions.assertNull(result.getMessage(), "Database Clear returned non null value for message");
    }
    @Test
    public void registerPositive() {

        String username = "TestUsername1";
        String password = "password123";
        String email = "email";
        RegisterResponse response = new RegisterService().registerUser(username, password,email);
        Assertions.assertEquals(username,response.getUsername());
    }

    @Test
    public void registerNegative() {
        String username = "TestUsername1";
        String password = null;
        String email = "email";
        RegisterResponse response = new RegisterService().registerUser(username, password, email);
        Assertions.assertTrue(response.getMessage().toLowerCase().contains("bad request"));//Equals(username,response.getUsername());
    }

    @Test
    public void createGamePositive() {
        CreateGameResponse response = new CreateGameService().newGame("theGame", existingAuth);
        Assertions.assertNull(response.getMessage(), "");
    }

    @Test
    public void createGameNegative() {
        CreateGameResponse response = new CreateGameService().newGame("",existingAuth);
        Assertions.assertTrue(response.getMessage().toLowerCase().contains("error: bad request"));
    }

    @Test
    public void joinGamePositive() {
        int gameID = new CreateGameService().newGame("theGame", existingAuth).getGameID();
        JoinGameResponse response = new JoinGameService().joinGame(gameID, existingAuth,"WHITE");
        Assertions.assertNull(response.getMessage());

    }

    @Test
    public void joinGameNegative() {
        int gameID = 0;
        JoinGameResponse response = new JoinGameService().joinGame(gameID, existingAuth,"WHITE");
        Assertions.assertTrue(response.getMessage().toLowerCase().contains("error: bad request"));
    }

    @Test
    public void ListGamePositive() {
        ListGamesResponse response = new ListGameService().listGames(existingAuth);
        Assertions.assertNull(response.getMessage());
    }

    @Test
    public void ListGamesNegative() {
        ListGamesResponse response = new ListGameService().listGames("invalid auth");
        Assertions.assertTrue(response.getMessage().toLowerCase().contains("error: unauthorized"));
    }

    @Test
    public void LoginPositive() {
        LoginResponse response = new LoginService().login("TestUsername", "password123");
        Assertions.assertNull(response.getMessage());
    }

    @Test
    public void LoginNegative() {
        LoginResponse response = new LoginService().login("TestUsername", "password12");
        Assertions.assertTrue(response.getMessage().toLowerCase().contains("error"));
    }

    @Test
    public void LogoutPositive() {
        LogoutResponse response = new LogoutService().logout(existingAuth);
        Assertions.assertNull(response.getMessage());
    }

    @Test
    public void LogoutNegative() {
        LogoutResponse response = new LogoutService().logout("invalid auth");
        Assertions.assertTrue(response.getMessage().toLowerCase().contains("error"));
    }
}
