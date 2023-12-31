package main;


import dao.SQLDAO;
import dataAccess.DataAccessException;
import handlers.*;
import requests.CreateGameRequest;
import requests.JoinGameRequest;
import requests.LoginRequest;
import requests.RegisterRequest;
import spark.Request;
import spark.Response;
import spark.Spark;
import webSocket.WebSocketHandler;

public class Server {
    public static void main(String[] args) {
        new Server().run();
    }

    private void run() {

        try {
            new SQLDAO().configureDatabase();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        int port = 8080;
        Spark.port(port);
        Spark.externalStaticFileLocation("C:/Users/Spencer/Documents/Classes/2023 Fall/C S 240/NeuChess/web");

        Spark.webSocket("/connect", new WebSocketHandler());
        Spark.delete("/db",this::clearDatabase);
        Spark.post("/user", this::registerUser);
        Spark.post("/session", this::loginUser);
        Spark.delete("/session", this::logoutUser);
        Spark.get("/game", this::listGames);
        Spark.post("/game", this::createGame);
        Spark.put("/game", this::joinGame);

    }

    private Object clearDatabase(Request request, Response response) {
        response.type("application/json");
        return new ClearHandler().ClearHandler(request,response);
    }

    private Object registerUser(Request request, Response response) {
        response.type("application/json");
        return new RegisterHandler().RegisterHandler(request,response, RegisterRequest.class);
    }

    private Object loginUser(Request request, Response response) {
        response.type("application/json");
        return new LoginHandler().LoginHandler(request,response, LoginRequest.class);
    }

    private Object logoutUser(Request request, Response response) {
        response.type("application/json");
        return new LogoutHandler().LogoutHandler(request,response);
    }

    private Object listGames(Request request, Response response) {
        response.type("application/json");
        return new ListGamesHandler().ListGamesHandler(request,response);
    }
    private Object createGame(Request request, Response response) {
        response.type("application/json");
        return new CreateGameHandler().CreateGameHandler(request,response, CreateGameRequest.class);
    }
    private Object joinGame(Request request, Response response) {
        response.type("application/json");
        return new JoinGameHandler().JoinGameHandler(request,response, JoinGameRequest.class);
    }
}