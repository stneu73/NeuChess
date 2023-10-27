package handlers;

import com.google.gson.Gson;
import services.ListGameService;
import spark.Request;
import spark.Response;

public class ListGamesHandler {
    public Object ListGamesHandler(Request request, Response response) {
        var auth = request.headers("Authorization");
        var result = new ListGameService().listGames(auth);

        String message = result.getMessage();
        if (message == null) {
            response.status(200);
        }
        else if (message.toLowerCase().contains("unauthorized")) {
            response.status(401);
        }
        else if (message.toLowerCase().contains("database")) {
            response.status(500);
        }


        return new Gson().toJson(result);
    }
}
