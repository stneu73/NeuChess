package services;

import dao.MemoryDAO;
import dataAccess.DataAccessException;
import responses.ClearResponse;

/**
 * Stores functions used for testing the database
 */
public class ClearService {
    /**
     * deletes everything in the database
     */
    public ClearResponse clearAll() {
        try {
            MemoryDAO.getInstance().clearData();
        } catch (DataAccessException e) {
            return new ClearResponse("Error: Couldn't Access Database");
        }
        return new ClearResponse();
    }
}
