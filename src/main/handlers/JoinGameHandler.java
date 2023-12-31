package handlers;

import com.google.gson.Gson;
import requests.JoinGameRequest;
import responses.JoinGameResponse;
import services.JoinGameService;
import spark.Request;
import spark.Response;

public class JoinGameHandler {
    public Object JoinGameHandler(Request request, Response response, Class<JoinGameRequest> klass) {
        String auth = request.headers("Authorization");
        JoinGameRequest bodyAsGson = new Gson().fromJson(request.body(),klass);
        JoinGameResponse result = new JoinGameService().joinGame(bodyAsGson.getGameID(), auth, bodyAsGson.getPlayerColor());

        String message = result.getMessage();
        if (message == null) {
            response.status(200);
        }
        else if (message.toLowerCase().contains("bad")) {
            response.status(400);
        }
        else if (message.toLowerCase().contains("unauthorized")) {
            response.status(401);
        }
        else if (message.toLowerCase().contains("already")) {
            response.status(403);
        }
        else if (message.toLowerCase().contains("database")) {
            response.status(500);
        }
        return new Gson().toJson(result);
    }
}
