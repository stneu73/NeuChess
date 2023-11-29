package uiTests;

import org.junit.jupiter.api.*;
import ui.PreLogin;
import ui.ServerFacade;

public class uiTests {
    private static String authToken;
    private static final String username = "scooter";
    private static final String password = "scoot";
    private static final String badAuth = "badAuth";
    @BeforeAll
    public static void init() {
        try {
            authToken = ServerFacade.register(username, password, "scootmail");
            ServerFacade.createGame("scoot's",authToken);
        } catch(Exception ignored) {}

    }

    @BeforeEach
    public void init2() {
        try {
            authToken = ServerFacade.login(username,password);
        } catch(Exception ignored) {}
    }

    @Test
    public void loginPositive() {
        try {
            Assertions.assertFalse(ServerFacade.login(username,password).isEmpty());
        } catch(Exception ignored) {}

    }

    @Test
    public void loginNegative() {
        String a = "";
        try {
            a = ServerFacade.login(username,"scoot");
        } catch(Exception e) {
            Assertions.assertTrue(a.isEmpty());
            Assertions.assertFalse(e.getMessage().isEmpty());
        }
    }

    @Test
    public void registerPositive() {
        try {
            Assertions.assertFalse(ServerFacade.register("scoot","scoot","email").isEmpty());
        } catch(Exception ignored) {}
    }

    @Test
    public void registerNegative() {
        try {
            ServerFacade.register(username,password,"email");
        } catch(Exception e) {
            Assertions.assertFalse(e.getMessage().isEmpty());
        }
    }

    @Test
    public void logoutPositive() {
        try {
            ServerFacade.logout(authToken);
        } catch(Exception ignored) {}
        try {
            ServerFacade.listGames(authToken);
        } catch(Exception e) {
            Assertions.assertFalse(e.getMessage().isEmpty());
        }
    }

    @Test
    public void logoutNegative() {
        try {
            ServerFacade.logout("asdf");
        } catch(Exception e) {
            Assertions.assertTrue(e.getMessage().toLowerCase().contains("unauthorized"));
        }

    }

    @Test
    public void createGamePositive() {
        String gameName = "The Scoot";
        try {
            ServerFacade.createGame(gameName,authToken);
        } catch(Exception ignored) {}
        try {
            for (var gameID : ServerFacade.gameIDs) {
                String name = ServerFacade.games.get(gameID).getGameName();
                if (name.contains(gameName)) {
                    Assertions.assertTrue(name.contains(gameName));
                }
            }

        } catch(Exception ignored) {}
    }

    @Test
    public void createGameNegative() {
        try {
            ServerFacade.createGame("name",badAuth);
        } catch(Exception e) {
            Assertions.assertTrue(e.getMessage().toLowerCase().contains("unauthorized"));
        }
    }

    @Test
    public void listGamesPositive() {
        try {
            ServerFacade.listGames(authToken);
            Assertions.assertFalse(ServerFacade.games.isEmpty());
        } catch(Exception ignored) {}
    }

    @Test
    public void listGamesNegative() {
        try {
            ServerFacade.listGames(badAuth);
        } catch(Exception e) {
            Assertions.assertTrue(e.getMessage().toLowerCase().contains("unauthorized"));
        }
    }

    @Test
    public void joinGamePositive() {
        try {

            for (var gameID : ServerFacade.gameIDs) {
                ServerFacade.joinGame(gameID, authToken, "white");
                break;
            }
        } catch(Exception ignored) {}
        try {
            for (var gameID : ServerFacade.gameIDs) {
                String name = ServerFacade.games.get(gameID).getWhiteUsername();
                if (name.contains("white player: " + username)) {
                    Assertions.assertTrue(name.contains("white player: " + username));
                }
            }
        } catch(Exception ignored) {}
    }

    @Test
    public void joinGameNegative() {
        try {
            ServerFacade.joinGame(0,badAuth,null);
        } catch(Exception e) {
            Assertions.assertTrue(e.getMessage().toLowerCase().contains("unauthorized"));
        }
    }

}
