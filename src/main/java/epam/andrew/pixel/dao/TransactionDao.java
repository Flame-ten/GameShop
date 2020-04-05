package epam.andrew.pixel.dao;

import epam.andrew.pixel.entity.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionDao extends Dao implements EntityDao<Transaction> {

    private static final Logger LOG = LoggerFactory.getLogger(TransactionDao.class);
    private static final String FIND_BY_ID = "SELECT * FROM transaction WHERE id = ?";
    private static final String UPDATE_TRANSACTION = "UPDATE transaction SET payment = ?, date = ?, " +
            " time = ? WHERE id = ?";
    private static final String DELETE_TRANSACTION = "DELETE FROM transaction WHERE id = ?";
    private static final String INSERT_TRANSACTION = "INSERT INTO transaction VALUES (id,?,?,?)";
    private static final String ALL_TRANSACTIONS = "SELECT id, payment , date , time  from transaction";

    @Override
    public Transaction findById(int id) throws DaoException {
        Transaction transaction = new Transaction();
        try (PreparedStatement statement = getConnection().prepareStatement(FIND_BY_ID)) {
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
    public List<Transaction> getAll() throws DaoException {
        List<Transaction> transactions = new ArrayList<>();
        try (PreparedStatement statement = getConnection().prepareStatement(ALL_TRANSACTIONS);
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
            transaction.setPayment(resultSet.getDouble(2));
            transaction.setDate(resultSet.getDate(3));
            transaction.setTime(resultSet.getTime(4));
        } catch (SQLException e) {
            throw new DaoException("Cannot create transaction from resultSet", e);
        }
        return transaction;
    }

    @Override
    public Transaction create(Transaction transaction) throws DaoException {
        try (PreparedStatement statement = getConnection().prepareStatement(INSERT_TRANSACTION,
                PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setDouble(1, transaction.getPayment());
            statement.setDate(2, transaction.getDate());
            statement.setTime(3, transaction.getTime());
        } catch (SQLException e) {
            throw new DaoException("Cannot create transaction", e);
        }
        return transaction;
    }

    @Override
    public void update(Transaction transaction) throws DaoException {
        try (PreparedStatement statement = getConnection().prepareStatement(UPDATE_TRANSACTION)) {
            statement.setDouble(1, transaction.getPayment());
            statement.setDate(2, transaction.getDate());
            statement.setTime(3, transaction.getTime());
        } catch (SQLException e) {
            throw new DaoException("Cannot update transaction", e);
        }
    }

    @Override
    public void delete(Transaction transaction) throws DaoException {
        try (PreparedStatement statement = getConnection().prepareStatement(DELETE_TRANSACTION)) {
            statement.setInt(1, transaction.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException("Cannot delete transaction", e);
        }
    }
}
