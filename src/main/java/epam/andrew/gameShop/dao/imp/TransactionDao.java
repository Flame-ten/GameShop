package epam.andrew.gameShop.dao.imp;

import epam.andrew.gameShop.connection.ConnectionPool;
import epam.andrew.gameShop.connection.ConnectionPoolException;
import epam.andrew.gameShop.dao.BaseDao;
import epam.andrew.gameShop.dao.Dao;
import epam.andrew.gameShop.dao.DaoException;
import epam.andrew.gameShop.entity.Transaction;
import epam.andrew.gameShop.entity.User;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static epam.andrew.gameShop.util.Constant.*;

public class TransactionDao extends Dao implements BaseDao<Transaction> {

    private static final Logger LOG = LoggerFactory.getLogger(TransactionDao.class);
    private static final String FIND_BY_ID = "SELECT * FROM transaction WHERE id = ?";
    private static final String UPDATE_TRANSACTION = "UPDATE transaction SET price = ? , date = ?, " +
            " time = ? WHERE id = ?";
    private static final String DELETE_TRANSACTION = "DELETE FROM transaction WHERE id = ?";
    private static final String INSERT_TRANSACTION = "INSERT INTO transaction VALUES (id,?,?,?,?,?,?)";
    private static final String ALL_TRANSACTIONS = "SELECT * FROM transaction WHERE deleted=0 LIMIT ? OFFSET ?";
    private static final String DELETED = "SELECT count(*) FROM transaction WHERE deleted = 0";
    private static final String SELECT_ALL_BY_ID = "SELECT * FROM transaction ORDER BY id";
    private static final String SELECT_FROM_TRANSACTION = "SELECT * FROM transaction WHERE ";
    private static final String AND = "' AND ";
    private static final String CANNOT_GET_COUNT = "Cannot get count";
    private static final String CANNOT_DELETE_TRANSACTION = "Cannot delete transaction";
    private static final String CANNOT_UPDATE_TRANSACTION = "Cannot update transaction";
    private static final String CANNOT_CREATE_TRANSACTION = "Cannot create transaction";
    private static final String CANNOT_CREATE_FROM_RESULT = "Cannot create transaction from resultSet";
    private static final String CANNOT_GET_ALL_TRANSACTIONS = "Cannot get all transactions list";
    private static final String CANNOT_FIND_BY_ID = "Cannot find by id";
    private static final String CANNOT_GET_TRANSACTIONS_LIST_BY_PARAMS = "Cannot get transactions list by params";
    private ConnectionPool connectionPool;
    private Connection connection;

    @Override
    public Transaction findById(int id) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        Transaction transaction = new Transaction();
        try (PreparedStatement statement = connection.prepareStatement(FIND_BY_ID)) {
            statement.setInt(INDEX_1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                transaction = getObjectFromResultSet((resultSet));
            }
            resultSet.close();
        } catch (SQLException e) {
            LOG.error(CANNOT_FIND_BY_ID, e);
            throw new DaoException(CANNOT_FIND_BY_ID, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return transaction;
    }

    public List<Transaction> getByParams(Map<String, String> params) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        List<Transaction> transactions = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(createQuery(params))) {
            while (resultSet.next()) {
                transactions.add(getObjectFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            LOG.error(CANNOT_GET_TRANSACTIONS_LIST_BY_PARAMS, e);
            throw new DaoException(CANNOT_GET_TRANSACTIONS_LIST_BY_PARAMS, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return transactions;
    }

    private String createQuery(Map<String, String> parameters) {
        StringBuilder query = new StringBuilder(SELECT_FROM_TRANSACTION);
        for (Map.Entry<String, String> parameter : parameters.entrySet()) {
            if (parameters.size() == 1) {
                query.append(parameter.getKey()).append("='").append(parameter.getValue()).append("'");
                return query.toString();
            } else {
                query.append(parameter.getKey()).append("='").append(parameter.getValue()).append(AND);
            }
        }
        return query.substring(0, query.length() - 5);
    }

    @Override
    public List<Transaction> getAll(int pageNumber, int pageSize) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        List<Transaction> transactions = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(ALL_TRANSACTIONS);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                transactions.add(getObjectFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            LOG.error(CANNOT_GET_ALL_TRANSACTIONS, e);
            throw new DaoException(CANNOT_GET_ALL_TRANSACTIONS, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return transactions;
    }

    public List<Transaction> getAll() throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        List<Transaction> transactions = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_BY_ID);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                transactions.add(getObjectFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            LOG.error(CANNOT_GET_ALL_TRANSACTIONS, e);
            throw new DaoException(CANNOT_GET_TRANSACTIONS_LIST_BY_PARAMS, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return transactions;
    }

    public Transaction getObjectFromResultSet(ResultSet resultSet) throws DaoException {
        Transaction transaction = new Transaction();
        try {
            transaction.setId(resultSet.getInt(INDEX_1));
            transaction.setPrice(Money.of(CurrencyUnit.of(User.CURRENCY), resultSet.getBigDecimal(INDEX_2)));
            transaction.setDate(resultSet.getDate(INDEX_3));
            transaction.setTime(resultSet.getTime(INDEX_4));
        } catch (SQLException e) {
            LOG.error(CANNOT_CREATE_FROM_RESULT, e);
            throw new DaoException(CANNOT_CREATE_FROM_RESULT, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return transaction;
    }

    @Override
    public Transaction create(Transaction transaction) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(INSERT_TRANSACTION,
                PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setBigDecimal(INDEX_1, transaction.getPrice().getAmount());
            statement.setDate(INDEX_2, transaction.getDate());
            statement.setTime(INDEX_3, transaction.getTime());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            while (resultSet.next()) {
                int id = resultSet.getInt(INDEX_1);
                transaction.setId(id);
            }
        } catch (SQLException e) {
            LOG.error(CANNOT_CREATE_TRANSACTION, e);
            throw new DaoException(CANNOT_CREATE_TRANSACTION, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return transaction;
    }

    @Override
    public void update(Transaction transaction) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_TRANSACTION)) {
            statement.setBigDecimal(INDEX_1, transaction.getPrice().getAmount());
            statement.setDate(INDEX_2, transaction.getDate());
            statement.setTime(INDEX_3, transaction.getTime());
            statement.execute();
        } catch (SQLException e) {
            LOG.error(CANNOT_UPDATE_TRANSACTION, e);
            throw new DaoException(CANNOT_UPDATE_TRANSACTION, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public void delete(Transaction transaction) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(DELETE_TRANSACTION)) {
            statement.setInt(INDEX_1, transaction.getId());
            statement.execute();
        } catch (SQLException e) {
            LOG.error(CANNOT_DELETE_TRANSACTION, e);
            throw new DaoException(CANNOT_DELETE_TRANSACTION, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    public int getNotDeletedCount() throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        int count = 0;
        try (Statement statement = getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(DELETED);
            while (resultSet.next()) {
                count = resultSet.getInt(INDEX_1);
            }
            resultSet.close();
        } catch (SQLException e) {
            LOG.error(CANNOT_GET_COUNT, e);
            throw new DaoException(CANNOT_GET_COUNT, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return count;
    }
}
