package webSocket;

import java.util.HashSet;
import java.util.Objects;

import org.eclipse.jetty.websocket.api.Session;
public class GameSession {
    private boolean gameIsOver = false;

    private HashSet<Connection> connections = new HashSet<>();

    public GameSession() {

    }

    public void addConnection(Connection conn) {
        connections.add(conn);
    }

    public void removeConnection(Connection conn) {
        connections.remove(conn);
    }

    public void clearConnections() {
        connections.clear();
    }

    public HashSet<Connection> getConnections() {
        return this.connections;
    }

    public boolean getGameIsOver() {
        return this.gameIsOver;
    }

    public void setGameAsOver() {
        gameIsOver = true;
    }
}
