package epam.andrew.gameShop.dao;

import java.sql.Connection;

public abstract class Dao {
    private Connection connection;

    public Dao() {
    }

    public Connection getConnection() {
        return connection;
    }

    void setConnection(Connection connection) {
        this.connection = connection;
    }

}
