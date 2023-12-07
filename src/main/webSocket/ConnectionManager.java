package webSocket;

import org.eclipse.jetty.websocket.api.Session;
import webSocketMessages.serverMessages.ServerMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionManager {
    public final ConcurrentHashMap<Integer, GameSession> gameIDToGameSession = new ConcurrentHashMap<>();

    public void add(Integer gameID, String username, Session session) {
        gameIDToGameSession.get(gameID).addConnection(new Connection(username,session));
    }

    public void removePlayer(Integer gameID, String username, Session session) {
        gameIDToGameSession.get(gameID).removeConnection(new Connection(username,session));
    }

    public void broadcastMinusOne(Integer gameID, String excludeUsername, ServerMessage serverMessage) throws IOException {
        var removeList = new ArrayList<Connection>();
        for (var c : gameIDToGameSession.get(gameID).getConnections()) {
            if (c.session.isOpen()) {
                if (!c.username.equals(excludeUsername)) {
                    c.send(serverMessage.toString());
                }
            } else {
                removeList.add(c);
            }
        }
        // Clean up any connections that were left open.
        for (var c : removeList) {
            gameIDToGameSession.remove(c.username); //do the connections stay open until the game is over or someone leaves?
        }
    }

    public void broadcastAll(Integer gameID, ServerMessage serverMessage) {

    }
    public void reply(Integer gameID,String username, ServerMessage serverMessage) { //individual server response

    }

    public void endGame(Integer gameID) {
        gameIDToGameSession.get(gameID).setGameAsOver();
        gameIDToGameSession.get(gameID).clearConnections();
    }

    public boolean gameOverQuery(Integer gameID) {
        return gameIDToGameSession.get(gameID).getGameIsOver();
    }
}
