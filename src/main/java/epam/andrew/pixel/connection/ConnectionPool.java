package epam.andrew.pixel.connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool {
    private static final Logger LOG = LoggerFactory.getLogger(ConnectionPool.class);
    private static final String URL = "url";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String DRIVER = "driver";
    private static final String CONNECTIONS_LIMIT = "connections";
    private static final String CANNOT_GET_CONNECTION = "cannot get connection";
    private static final String CANNOT_LOAD_DB_PROPERTIES = "cannot load data base properties";

    private String url;
    private String username;
    private String password;
    private String driver;
    private int connectionsLimit;

    private BlockingQueue<Connection> freeConnections = null;
    private BlockingQueue<Connection> usedConnections = null;

    private ConnectionPool() throws ConnectionPoolException {
        Properties properties = new Properties();

        try {
            InputStream is = this.getClass().getClassLoader().getResourceAsStream("database.properties");
            properties.load(is);
        } catch (IOException e) {
            throw new ConnectionPoolException(CANNOT_LOAD_DB_PROPERTIES, e);
        }

        if (!properties.isEmpty()) {
            setDriver(properties.getProperty(DRIVER));
            setUrl(properties.getProperty(URL));
            setUsername(properties.getProperty(USERNAME));
            setPassword(properties.getProperty(PASSWORD));
            setConnectionsLimit(Integer.parseInt(properties.getProperty(CONNECTIONS_LIMIT)));
        } else {
            LOG.error(CANNOT_LOAD_DB_PROPERTIES);
        }

        if (freeConnections == null) {
            freeConnections = new ArrayBlockingQueue<>(connectionsLimit);
        }
        if (usedConnections == null) {
            usedConnections = new ArrayBlockingQueue<>(connectionsLimit);
        }
        try {
            Class.forName(driver);
            for (int i = 0; i < connectionsLimit; i++) {
                Connection connection = DriverManager.getConnection(url, username, password);
                freeConnections.put(connection);
            }
        } catch (InterruptedException | ClassNotFoundException | SQLException e) {
            throw new ConnectionPoolException("Initialize error", e);
        }
    }

    public synchronized Connection getConnection() throws ConnectionPoolException {
        Connection currentConnection;
        try {
            currentConnection = freeConnections.take();
            usedConnections.put(currentConnection);
        } catch (InterruptedException e) {
            throw new ConnectionPoolException(CANNOT_GET_CONNECTION, e);
        }
        return currentConnection;
    }

    public synchronized void closeConnection(Connection connection) throws ConnectionPoolException {
        try {
            usedConnections.remove(connection);
            freeConnections.put(connection);
        } catch (InterruptedException e) {
            throw new ConnectionPoolException(CANNOT_GET_CONNECTION, e);
        }
    }

    private void closeAllConnectionsInQueue(BlockingQueue<Connection> connections) throws ConnectionPoolException {
        for (Connection connection : connections) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new ConnectionPoolException(CANNOT_GET_CONNECTION, e);
            }
        }
    }

    public void close() throws ConnectionPoolException {
        closeAllConnectionsInQueue(freeConnections);
        closeAllConnectionsInQueue(usedConnections);
    }

    private static volatile ConnectionPool instance;

    public static ConnectionPool getInstance() throws ConnectionPoolException {
        ConnectionPool localInstance = instance;
        if (localInstance == null) {
            synchronized (ConnectionPool.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ConnectionPool();
                }
            }
        }
        return localInstance;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public int getConnectionsLimit() {
        return connectionsLimit;
    }

    public void setConnectionsLimit(int connectionsLimit) {
        this.connectionsLimit = connectionsLimit;
    }
}
