package epam.andrew.gameShop.connection;

import com.mysql.cj.jdbc.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionPool {
    private static final String URL = "jdbc:mysql://localhost:3306/mydb?serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "515678";

    public static Connection getConnection() {
        try {
            DriverManager.registerDriver(new Driver());
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database" + e);
        }
    }

}
