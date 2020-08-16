package epam.andrew.gameShop.connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


public class ConnectionPool {
    private static final Logger LOG = LoggerFactory.getLogger(ConnectionPool.class);
    private static final String PROPERTY_FILE_NAME = "database.properties";
    private static final String URL = "url";
    private static final String USER = "user";
    private static final String PASSWORD = "password";
    private static final String DRIVER = "driver";
    private static final String CONNECTIONS_LIMIT = "connections";
    private static final String CANNOT_GET_CONNECTION = "Cannot get connection";
    private static final String CANNOT_LOAD_DB_PROPERTIES = "Cannot load data base properties";
    private static final String CANNOT_LOAD_JDBC_DRIVER = "Cannot to load JDBC driver.";
    private static final String CANNOT_INIT_CONNECTIONS = "Cannot to init connections.";
    private static final String CANNOT_CLOSE_CONNECTIONS = "Cannot to close connections.";

    private static volatile ConnectionPool currentConnectionPool;

    private final List<Connection> allConnections;
    private final BlockingQueue<Connection> freeConnections;
    private String driverName;
    private String user;
    private String password;
    private String url;
    private int poolSize;

    private ConnectionPool() throws ConnectionPoolException {
        loadProperties();
        loadJdbcDriver();
        allConnections = new ArrayList<>(poolSize);
        initConnections();
        freeConnections = new ArrayBlockingQueue<>(poolSize);
        freeConnections.addAll(allConnections);
    }

    public static ConnectionPool getInstance() throws ConnectionPoolException {
        ConnectionPool connectionPool = currentConnectionPool;
        if (connectionPool == null) {
            synchronized (ConnectionPool.class) {
                connectionPool = currentConnectionPool;
                if (connectionPool == null) {
                    connectionPool = currentConnectionPool = new ConnectionPool();
                }
            }
        }
        return connectionPool;
    }

    public Connection getConnection() throws ConnectionPoolException {
        try {
            return freeConnections.take();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            LOG.error(CANNOT_GET_CONNECTION, e);
            throw new ConnectionPoolException(CANNOT_GET_CONNECTION, e);
        }
    }

    public void returnConnection(Connection connection) {
        if ((freeConnections.size() < poolSize) && (connection != null)) {
            freeConnections.add(connection);
        }
    }

    public void close() throws ConnectionPoolException {
        for (Connection connection : allConnections) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOG.error(CANNOT_CLOSE_CONNECTIONS, e);
                throw new ConnectionPoolException(CANNOT_CLOSE_CONNECTIONS, e);
            }
        }
    }

    private void initConnections() throws ConnectionPoolException {
        for (int i = 0; i < poolSize; i++) {
            try {
                allConnections.add(DriverManager.getConnection(url, user, password));
            } catch (SQLException e) {
                LOG.error(CANNOT_INIT_CONNECTIONS, e);
                throw new ConnectionPoolException(CANNOT_INIT_CONNECTIONS, e);
            }
        }
    }

    private void loadJdbcDriver() throws ConnectionPoolException {
        try {
            Driver driver = (Driver) Class.forName(driverName).getDeclaredConstructor().newInstance();
            DriverManager.registerDriver(driver);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | SQLException | NoSuchMethodException | InvocationTargetException e) {
            LOG.error(CANNOT_LOAD_JDBC_DRIVER, e);
            throw new ConnectionPoolException(CANNOT_LOAD_JDBC_DRIVER, e);
        }
    }

    private void loadProperties() throws ConnectionPoolException {
        try (InputStream connectionPoolProperties =
                     ConnectionPool.class.getClassLoader().getResourceAsStream(PROPERTY_FILE_NAME)) {
            if (connectionPoolProperties != null) {
                Properties properties = new Properties();
                properties.load(connectionPoolProperties);
                driverName = properties.getProperty(DRIVER);
                user = properties.getProperty(USER);
                password = properties.getProperty(PASSWORD);
                url = properties.getProperty(URL);
                poolSize = Integer.parseInt(properties.getProperty(CONNECTIONS_LIMIT));
            }
        } catch (IOException e) {
            LOG.error(CANNOT_LOAD_DB_PROPERTIES, e);
            throw new ConnectionPoolException(CANNOT_LOAD_DB_PROPERTIES, e);
        }
    }
}
