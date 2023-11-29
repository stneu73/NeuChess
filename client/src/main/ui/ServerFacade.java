package ui;

import com.google.gson.Gson;
import models.GameModel;
import responses.ListGamesResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class ServerFacade {

    private static final String USER_URL = "/user";
    private static final String GAME_URL = "/game";
    private static final String SESSION_URL = "/session";
    private static final String SERVER_URL = "http://localhost:8080";

    public static HashMap<Integer, GameModel> games = new HashMap<>();
    public static HashSet<Integer> gameIDs = new HashSet<>();

    public static String login(String username, String password) throws Exception {
        URI uri = new URI(SERVER_URL + SESSION_URL);
        HttpURLConnection http = (HttpURLConnection) uri.toURL().openConnection();
        http.setRequestMethod("POST");
        http.setDoOutput(true);
        http.addRequestProperty("Content-Type", "application/json");
        var body = Map.of("username", username, "password", password);
        try (var outputStream = http.getOutputStream()) {
            var jsonBody = new Gson().toJson(body);
            outputStream.write(jsonBody.getBytes());
        }

        http.connect();

        int responseCode = http.getResponseCode();
        if (isSuccessful(responseCode)) {
            try (InputStream respBody = http.getInputStream()) {
                InputStreamReader reader = new InputStreamReader(respBody);
                var json = new Gson().fromJson(reader, Map.class);
                return (String) json.get("authToken");

            } catch (IOException e) {
                throw new Exception();
            }
        } else {
            try (InputStream respBody = http.getErrorStream()) {
                InputStreamReader reader = new InputStreamReader(respBody);
                var json = new Gson().fromJson(reader, Map.class);
                throw new Exception((String) json.get("message"));
            }
        }
    }

    public static String register(String username, String password, String email) throws Exception {
        URI uri = new URI(SERVER_URL+USER_URL);
        HttpURLConnection http = (HttpURLConnection) uri.toURL().openConnection();
        http.setRequestMethod("POST");
        http.setDoOutput(true);
        http.addRequestProperty("Content-Type","application/json");
        var body = Map.of("username",username,"password",password,"email",email);
        try (var outputStream = http.getOutputStream()) {
            var jsonBody = new Gson().toJson(body);
            outputStream.write(jsonBody.getBytes());
        }
        http.connect();

        int responseCode = http.getResponseCode();
        if (isSuccessful(responseCode)) {
            try (InputStream respBody = http.getInputStream()) {
                InputStreamReader reader = new InputStreamReader(respBody);
                var json = new Gson().fromJson(reader, Map.class);
                return (String) json.get("authToken");

            } catch (IOException e) {
                throw new Exception();
            }
        } else {
            try (InputStream respBody = http.getErrorStream()) {
                InputStreamReader reader = new InputStreamReader(respBody);
                var json = new Gson().fromJson(reader, Map.class);
                throw new Exception((String) json.get("message"));
            }
        }
    }

    public static void logout(String authToken) throws Exception {
        URI uri = new URI(SERVER_URL+SESSION_URL);
        HttpURLConnection http = (HttpURLConnection) uri.toURL().openConnection();
        http.setRequestMethod("DELETE");
        http.setDoOutput(true);
        http.addRequestProperty("Authorization",authToken);

        http.connect();

        int responseCode = http.getResponseCode();
        if (!isSuccessful(responseCode)) {
            try (InputStream respBody = http.getErrorStream()) {
                InputStreamReader reader = new InputStreamReader(respBody);
                var json = new Gson().fromJson(reader, Map.class);
                throw new Exception((String) json.get("message"));
            }
        }


    }

    public static void createGame(String gameName, String authToken) throws Exception {
        URI uri = new URI(SERVER_URL+GAME_URL);
        HttpURLConnection http = (HttpURLConnection) uri.toURL().openConnection();
        http.setRequestMethod("POST");
        http.setDoOutput(true);
        http.addRequestProperty("Authorization",authToken);
        http.addRequestProperty("Content-Type","application/json");
        var body = Map.of( "gameName",gameName);
        try (var outputStream = http.getOutputStream()) {
            var jsonBody = new Gson().toJson(body);
            outputStream.write(jsonBody.getBytes());
        }

        http.connect();

        int responseCode = http.getResponseCode();
        if (!isSuccessful(responseCode)) {
            try (InputStream respBody = http.getErrorStream()) {
                InputStreamReader reader = new InputStreamReader(respBody);
                var json = new Gson().fromJson(reader, Map.class);
                throw new Exception((String) json.get("message"));
            }
        }
    }

    public static void listGames(String authToken) throws Exception {
        URI uri = new URI(SERVER_URL+GAME_URL);
        HttpURLConnection http = (HttpURLConnection) uri.toURL().openConnection();
        http.setRequestMethod("GET");
        http.setDoOutput(true);
        http.addRequestProperty("Authorization",authToken);

        http.connect();

        int responseCode = http.getResponseCode();
        if (isSuccessful(responseCode)) {
            games.clear();
            try (InputStream respBody = http.getInputStream()) {
                InputStreamReader reader = new InputStreamReader(respBody);
                ListGamesResponse json = new Gson().fromJson(reader, ListGamesResponse.class);
                for (var game : json.getGames()) {
                    games.put(game.getGameID(),game);
                    gameIDs.add(game.getGameID());
                }
                if (games.isEmpty()) {
                    System.out.print("\nNo Games\n\n");
                    gameIDs.clear();
                } else {
                    int i = 0;
                    StringBuilder s = new StringBuilder();
                    for (var gameID : gameIDs) {
                        i++;
                        GameModel game = games.get(gameID);
//                        System.out.print(i+". Game Name: " + game.getGameName() + "\n   Game ID: " + gameID +
//                                "\n   White Player: " + game.getWhiteUsername() + "\n   Black Player: " +
//                                game.getBlackUsername() + "\n\n");

                        s.append(i).append(". Game Name: ").append(game.getGameName()).append("\n");
                        s.append("   Game ID: ").append(gameID).append("\n");
                        s.append("   ").append("White Player: ");
                        if (game.getWhiteUsername() == null) {
                            s.append("\n");
                        } else {
                            s.append(game.getWhiteUsername()).append("\n");
                        }
                        s.append("   Black Player: ");
                        if (game.getBlackUsername() == null) {
                            s.append("\n\n");
                        } else {
                            s.append(game.getBlackUsername()).append("\n\n");
                        }

                    }
                    System.out.print(s);

                }
            } catch (IOException e) {
                throw new Exception();
            }
        } else {
            try (InputStream respBody = http.getErrorStream()) {
                InputStreamReader reader = new InputStreamReader(respBody);
                var json = new Gson().fromJson(reader, Map.class);
                throw new Exception((String) json.get("message"));
            }
        }
    }

    public static void joinGame(int gameID, String authToken, String color) throws Exception {
        URI uri = new URI(SERVER_URL + GAME_URL);
        HttpURLConnection http = (HttpURLConnection) uri.toURL().openConnection();
        http.setRequestMethod("PUT");
        http.setDoOutput(true);
        http.addRequestProperty("Content-Type", "application/json");
        http.addRequestProperty("Authorization", authToken);

        if (color == null) {
            var body = Map.of("gameID", gameID);
            try (var outputStream = http.getOutputStream()) {
                var jsonBody = new Gson().toJson(body);
                outputStream.write(jsonBody.getBytes());
            }
        } else {
            var body = Map.of("gameID", gameID, "playerColor", color);
            try (var outputStream = http.getOutputStream()) {
                var jsonBody = new Gson().toJson(body);
                outputStream.write(jsonBody.getBytes());
            }
        }

        http.connect();

        int responseCode = http.getResponseCode();
        if (!isSuccessful(responseCode)) {
            try (InputStream respBody = http.getErrorStream()) {
                InputStreamReader reader = new InputStreamReader(respBody);
                var json = new Gson().fromJson(reader, Map.class);
                throw new Exception((String) json.get("message"));
            }
        }
    }

    private static boolean isSuccessful(int status) {
        return status == 200;
    }
}
