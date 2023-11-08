package services;

import dao.MemoryDAO;
import dao.SQLDAO;
import dataAccess.DataAccessException;
import responses.LogoutResponse;

/**
 * logs a user out of the current session
 */
public class LogoutService {
    /**
     * logs the current user out of the current session
     */
    public LogoutResponse logout(String authToken) {
        SQLDAO dao = new SQLDAO();
        try {
            if (!dao.findAuthToken(authToken)) {//!MemoryDAO.getInstance().findAuthToken(authToken)) {
                return new LogoutResponse("Error: Unauthorized");
            }
        } catch (DataAccessException e) {
            return new LogoutResponse("Error: Couldn't Access DataBase");
        }
        try {
            dao.deleteAuthToken(authToken);//MemoryDAO.getInstance().deleteAuthToken(authToken);
        } catch (DataAccessException e) {
            return new LogoutResponse("Error: Couldn't Access DataBase");
        }
        return new LogoutResponse(null);
    }
}
