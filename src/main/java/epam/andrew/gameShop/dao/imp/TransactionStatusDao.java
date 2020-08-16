package epam.andrew.gameShop.dao.imp;

import epam.andrew.gameShop.connection.ConnectionPool;
import epam.andrew.gameShop.connection.ConnectionPoolException;
import epam.andrew.gameShop.dao.BaseDao;
import epam.andrew.gameShop.dao.Dao;
import epam.andrew.gameShop.dao.DaoException;
import epam.andrew.gameShop.entity.TransactionStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static epam.andrew.gameShop.util.Constant.INDEX_1;
import static epam.andrew.gameShop.util.Constant.INDEX_2;

public class TransactionStatusDao extends Dao implements BaseDao<TransactionStatus> {
    private static final Logger LOG = LoggerFactory.getLogger(TransactionStatusDao.class);
    private static final String FIND_BY_ID = "SELECT * FROM status WHERE id = ?";
    private static final String UPDATE_STATUS = "UPDATE status SET name_en = ?, name_ru = ? WHERE id = ?";
    private static final String DELETE_STATUS = "DELETE FROM status WHERE id = ?";
    private static final String INSERT_STATUS = "INSERT INTO status VALUES (id,?,?,?)";
    private static final String ALL_STATUSES = "SELECT * FROM status WHERE deleted=0 LIMIT ? OFFSET ?";
    private static final String SELECT_ALL_BY_ID = "SELECT * FROM status ORDER BY id";
    private static final String CANNOT_DELETE_STATUS = "Cannot delete status";
    private static final String CANNOT_CREATE_STATUS = "Cannot create status";
    private static final String CANNOT_UPDATE_STATUS = "Cannot update status";
    private static final String CANNOT_CREATE_FROM_RESULT = "Cannot create status from resultSet";
    private static final String CANNOT_GET_ALL = "Cannot get all statuses list";
    private static final String CANNOT_FIND_BY_ID = "Cannot find by id";
    private ConnectionPool connectionPool;
    private Connection connection;

    @Override
    public TransactionStatus findById(int id) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        TransactionStatus transactionStatus = new TransactionStatus();
        try (PreparedStatement statement = connection.prepareStatement(FIND_BY_ID)) {
            statement.setInt(INDEX_1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                transactionStatus = getObjectFromResultSet((resultSet));
            }
            resultSet.close();
        } catch (SQLException e) {
            LOG.error(CANNOT_FIND_BY_ID, e);
            throw new DaoException(CANNOT_FIND_BY_ID, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return transactionStatus;
    }

    @Override
    public List<TransactionStatus> getAll(int pageNumber, int pageSize) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        List<TransactionStatus> transactionStatuses = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(ALL_STATUSES)) {
            statement.setInt(INDEX_1, ((pageNumber - 1) * pageSize));
            statement.setInt(INDEX_2, pageSize);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                transactionStatuses.add(getObjectFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            LOG.error(CANNOT_GET_ALL, e);
            throw new DaoException(CANNOT_GET_ALL, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return transactionStatuses;
    }

    public List<TransactionStatus> getAll() throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        List<TransactionStatus> statuses = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_BY_ID);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                statuses.add(getObjectFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            LOG.error(CANNOT_GET_ALL, e);
            throw new DaoException(CANNOT_GET_ALL, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return statuses;
    }

    public TransactionStatus getObjectFromResultSet(ResultSet resultSet) throws DaoException {
        TransactionStatus transactionStatus = new TransactionStatus();
        try {
            transactionStatus.setId(resultSet.getInt(INDEX_1));
            transactionStatus.setName(resultSet.getString(INDEX_2));
        } catch (SQLException e) {
            LOG.error(CANNOT_CREATE_FROM_RESULT, e);
            throw new DaoException(CANNOT_CREATE_FROM_RESULT, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return transactionStatus;
    }

    @Override
    public TransactionStatus create(TransactionStatus transactionStatus) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(INSERT_STATUS,
                PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(INDEX_1, transactionStatus.getName());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            while (resultSet.next()) {
                int id = resultSet.getInt(INDEX_1);
                transactionStatus.setId(id);
            }
        } catch (SQLException e) {
            LOG.error(CANNOT_CREATE_STATUS, e);
            throw new DaoException(CANNOT_CREATE_STATUS, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return transactionStatus;
    }

    @Override
    public void update(TransactionStatus transactionStatus) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_STATUS)) {
            statement.setString(INDEX_1, transactionStatus.getName());
            statement.execute();
        } catch (SQLException e) {
            LOG.error(CANNOT_UPDATE_STATUS, e);
            throw new DaoException(CANNOT_UPDATE_STATUS, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public void delete(TransactionStatus transactionStatus) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(DELETE_STATUS)) {
            statement.setInt(INDEX_1, transactionStatus.getId());
            statement.execute();
        } catch (SQLException e) {
            LOG.error(CANNOT_DELETE_STATUS, e);
            throw new DaoException(CANNOT_DELETE_STATUS, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }
}
