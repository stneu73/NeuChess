package handlers;

import com.google.gson.Gson;
import requests.LoginRequest;
import responses.LoginResponse;
import services.LoginService;
import spark.Request;
import spark.Response;

public class LoginHandler {

    public Object LoginHandler(Request request, Response response, Class<LoginRequest> klass) {
        LoginRequest bodyAsGson = new Gson().fromJson(request.body(), klass);
        LoginResponse result = new LoginService().login(bodyAsGson.getUsername(), bodyAsGson.getPassword());

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
