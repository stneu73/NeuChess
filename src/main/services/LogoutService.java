package services;

import dao.MemoryDAO;
import dataAccess.DataAccessException;
import org.eclipse.jetty.util.log.Log;
import responses.LogoutResponse;

/**
 * logs a user out of the current session
 */
public class LogoutService {
    /**
     * logs the current user out of the current session
     */
    public LogoutResponse logout(String authToken) {
        if (!MemoryDAO.getInstance().findAuthToken(authToken)) {
            return new LogoutResponse("Error: Unauthorized");
        }
        try {
            MemoryDAO.getInstance().deleteAuthToken(authToken);
        } catch (DataAccessException e) {
            return new LogoutResponse("Error: Couldn't Access DataBase");
        }
        return new LogoutResponse(null);
    }
}
