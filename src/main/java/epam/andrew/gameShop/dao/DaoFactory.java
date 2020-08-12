package epam.andrew.gameShop.dao;

import epam.andrew.gameShop.connection.ConnectionPool;
import epam.andrew.gameShop.connection.ConnectionPoolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;

public class DaoFactory implements AutoCloseable {
    public static final Logger LOG = LoggerFactory.getLogger(DaoFactory.class);
    private static final String CANNOT_GET_CONNECTION = "Cannot get connection";
    private static final String CANNOT_CLOSE_CONNECTION = "Cannot close connection";
    private static final String CANNOT_START_TRANSACTION = "Cannot start transaction";
    private static final String CANNOT_COMMIT_TRANSACTION = "Cannot get transaction";
    private static final String CANNOT_ROLLBACK_TRANSACTION = "Cannot get transaction";
    private static final String CANNOT_MAKE_NEW_INSTANCE_OF_DAO = "Cannot make new instance of Dao";
    private final ConnectionPool connectionPool;
    private Connection connection = null;

    public DaoFactory() throws ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        try {
            connection = connectionPool.getConnection();
        } catch (ConnectionPoolException e) {
            LOG.error(CANNOT_GET_CONNECTION, e);
        }
    }


    public void startTransaction() throws DaoException {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            LOG.error(CANNOT_START_TRANSACTION, e);
            throw new DaoException(CANNOT_START_TRANSACTION, e);
        }
    }

    public void commitTransaction() throws DaoException {
        try {
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            LOG.error(CANNOT_COMMIT_TRANSACTION, e);
            throw new DaoException(CANNOT_COMMIT_TRANSACTION, e);
        }
    }

    public void rollbackTransaction() throws DaoException {
        try {
            connection.rollback();
        } catch (SQLException e) {
            LOG.error(CANNOT_ROLLBACK_TRANSACTION, e);
            throw new DaoException(CANNOT_ROLLBACK_TRANSACTION, e);
        }
    }

    @Override
    public void close() throws DaoException {
        try {
            connectionPool.close();
        } catch (ConnectionPoolException e) {
            LOG.error(CANNOT_CLOSE_CONNECTION, e);
            throw new DaoException(CANNOT_CLOSE_CONNECTION, e);
        }
    }

    public <T extends Dao> T getDao(Class<T> clazz) throws DaoException {
        T t;
        try {
            t = clazz.getDeclaredConstructor().newInstance();
            t.setConnection(connection);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            LOG.error(CANNOT_MAKE_NEW_INSTANCE_OF_DAO, e);
            throw new DaoException(CANNOT_MAKE_NEW_INSTANCE_OF_DAO, e);
        }
        return t;
    }

}
