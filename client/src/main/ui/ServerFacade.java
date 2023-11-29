package ui;

import com.google.gson.Gson;
import responses.ListGamesResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.Map;
//import exception.ResponseException;

public class ServerFacade {

    private static final String USER_URL = "/user";
    private static final String GAME_URL = "/game";
    private static final String SESSION_URL = "/session";
    private static final String SERVER_URL = "http://localhost:8080";

    public static int[] gameIDs;

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

    public static String listGames(String authToken) throws Exception {
        URI uri = new URI(SERVER_URL+GAME_URL);
        HttpURLConnection http = (HttpURLConnection) uri.toURL().openConnection();
        http.setRequestMethod("GET");
        http.setDoOutput(true);
        http.addRequestProperty("Authorization",authToken);

        http.connect();

        int responseCode = http.getResponseCode();
        if (isSuccessful(responseCode)) {
            try (InputStream respBody = http.getInputStream()) {
                InputStreamReader reader = new InputStreamReader(respBody);
                ListGamesResponse json = new Gson().fromJson(reader, ListGamesResponse.class);
                gameIDs = json.getGameIDs();
                if (gameIDs.length == 0) {
                    return "\nNo Games\n\n";
                } else {
                    return json.gamesToString();
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
