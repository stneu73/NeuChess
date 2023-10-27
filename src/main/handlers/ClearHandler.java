package handlers;

import com.google.gson.Gson;
import responses.ClearResponse;
import services.ClearService;
import spark.Request;
import spark.Response;

public class ClearHandler {
    public Object ClearHandler(Request request, Response response) {
        //request json to request object (not for clear)
            //have to tell gson what to deserialize to if handler has a request
        ClearResponse myResponse = new ClearService().clearAll();//pass object to service
        //set status code based on response object
            // if statement looking at message
        //res.status(value)
        String message = myResponse.getMessage();
        if (message == null) {
            response.status(200);
        }
        else if (message.toLowerCase().contains("database")) {
            response.status(500);
        }
        return new Gson().toJson(myResponse);//response object to response json
        //make sure this is actually returned as a Json obj and not HTML
    }
}