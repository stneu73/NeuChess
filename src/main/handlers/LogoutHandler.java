package handlers;

import com.google.gson.Gson;
import responses.LogoutResponse;
import services.LogoutService;
import spark.Request;
import spark.Response;

public class LogoutHandler {
    public Object LogoutHandler(Request request, Response response) {
        String auth = request.headers("Authorization");
        LogoutResponse result = new LogoutService().logout(auth);

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
