package epam.andrew.pixel.dao;

import epam.andrew.pixel.connection.ConnectionPool;
import epam.andrew.pixel.connection.ConnectionPoolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;

public class DaoFactory implements AutoCloseable {
    public static final Logger LOG = LoggerFactory.getLogger(DaoFactory.class);
    private ConnectionPool connectionPool;
    private Connection connection = null;

    public DaoFactory() throws ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        try {
            connection = connectionPool.getConnection();
        } catch (ConnectionPoolException e) {
            LOG.error("Cannot get connection", e);
        }
    }

    public <T extends Dao> T getDao(Class<T> tClass) throws DaoException {
        T t;
        try {
            t = tClass.getDeclaredConstructor().newInstance();
            t.setConnection(connection);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new DaoException("Cannot create instance", e);
        }
        return t;
    }

    public void startTransaction() throws DaoException {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new DaoException("Cannot start transaction", e);
        }
    }

    public void commitTransaction() throws DaoException {
        try {
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            throw new DaoException("Cannot commit transaction", e);
        }
    }

    public void rollbackTransaction() throws DaoException {
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new DaoException("Cannot rollback transaction", e);
        }
    }

    @Override
    public void close() throws DaoException {
        try {
            connectionPool.closeConnection(connection);
        } catch (ConnectionPoolException e) {
            throw new DaoException("Cannot close connection", e);
        }
    }
}
