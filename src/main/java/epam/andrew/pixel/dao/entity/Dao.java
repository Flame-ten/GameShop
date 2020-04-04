package epam.andrew.pixel.dao.entity;

import java.sql.Connection;

public abstract class Dao {
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

}

