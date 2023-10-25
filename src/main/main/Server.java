package main;


import handlers.*;
import requests.CreateGameRequest;
import requests.LoginRequest;
import spark.Request;
import spark.Response;
import spark.Spark;

public class Server {
    public static void main(String[] args) { new Server().run(); }

    private void run() {

        int port = 8080;
        Spark.port(port);
        Spark.externalStaticFileLocation("C:/Users/Spencer/Documents/Classes/2023 Fall/C S 240/NeuChess/web");

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
//        Class<LoginRequest> klass = new Class<LoginRequest>;
        return new RegisterHandler().RegisterHandler(request,response,klass);
    }

    private Object loginUser(Request request, Response response) {
        response.type("application/json");
        return new LoginHandler().LoginHandler(request,response,klass);
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
        return new CreateGameHandler().CreateGameHandler(request,response,klass);
    }
    private Object joinGame(Request request, Response response) {
        response.type("application/json");
        return new JoinGameHandler().JoinGameHandler(request,response,klass);
    }
}