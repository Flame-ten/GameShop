package epam.andrew.pixel.dao;

import epam.andrew.pixel.connection.ConnectionPool;
import epam.andrew.pixel.connection.ConnectionPoolException;
import epam.andrew.pixel.entity.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionDao implements BaseDao<Transaction> {

    private static final Logger LOG = LoggerFactory.getLogger(TransactionDao.class);
    private static final String FIND_BY_ID = "SELECT * FROM transaction WHERE id = ?";
    private static final String UPDATE_TRANSACTION = "UPDATE transaction SET date = ?, " +
            " time = ? WHERE id = ?";
    private static final String DELETE_TRANSACTION = "DELETE FROM transaction WHERE id = ?";
    private static final String INSERT_TRANSACTION = "INSERT INTO transaction VALUES (id,?,?,?,?)";
    private static final String ALL_TRANSACTIONS = "SELECT date , time  from transaction";
    private ConnectionPool connectionPool;
    private Connection connection;

    @Override
    public Transaction findById(int id) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        Transaction transaction = new Transaction();
        try (PreparedStatement statement = connection.prepareStatement(FIND_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                transaction = getObjectFromResultSet((resultSet));
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new DaoException("Cannot find by id", e);
        }
        return transaction;
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
            LOG.error("cannot get all transactions list", e);
            throw new DaoException("cannot get all transactions list", e);
        }
        return transactions;
    }

    public Transaction getObjectFromResultSet(ResultSet resultSet) throws DaoException {
        Transaction transaction = new Transaction();
        try {
            transaction.setId(resultSet.getInt(1));
            transaction.setDate(resultSet.getDate(2));
            transaction.setTime(resultSet.getTime(3));
        } catch (SQLException e) {
            throw new DaoException("Cannot create transaction from resultSet", e);
        }
        return transaction;
    }

    @Override
    public Transaction create(Transaction transaction) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(INSERT_TRANSACTION,
                PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setDate(1, transaction.getDate());
            statement.setTime(2, transaction.getTime());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                transaction.setId(id);
            }
        } catch (SQLException e) {
            throw new DaoException("Cannot create transaction", e);
        }
        return transaction;
    }

    @Override
    public void update(Transaction transaction) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_TRANSACTION)) {
            statement.setDate(1, transaction.getDate());
            statement.setTime(2, transaction.getTime());
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException("Cannot update transaction", e);
        }
    }

    @Override
    public void delete(Transaction transaction) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(DELETE_TRANSACTION)) {
            statement.setInt(1, transaction.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException("Cannot delete transaction", e);
        }
    }
}
