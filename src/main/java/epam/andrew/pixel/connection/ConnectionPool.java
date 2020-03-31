package epam.andrew.pixel.connection;

import com.mysql.jdbc.Driver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionPool {
    private static final Logger LOG = LoggerFactory.getLogger(ConnectionPool.class);
    private static final String URL = "jdbc:mysql://localhost:3306/mydb?useUnicode=true&useSSL=true&" +
            "useJDBCCompliantTimezoneShift=true&allowPublicKeyRetrieval=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "515678";
    private static final String CANNOT_GET_CONNECTION = "can't get connection";


    public static Connection getConnection() throws ConnectionPoolException {
        try {
            DriverManager.registerDriver(new Driver());
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new ConnectionPoolException(CANNOT_GET_CONNECTION + e);
        }
    }

    public static class InstanceHolder {
        static ConnectionPool instance;

        public static void setInstance(ConnectionPool connectionPool) {
            instance = connectionPool;
        }
    }

    public static synchronized ConnectionPool getInstance() {
        return InstanceHolder.instance;
    }


}
