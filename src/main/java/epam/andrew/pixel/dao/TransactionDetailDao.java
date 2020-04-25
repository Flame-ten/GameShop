package epam.andrew.pixel.dao;

import epam.andrew.pixel.connection.ConnectionPool;
import epam.andrew.pixel.connection.ConnectionPoolException;
import epam.andrew.pixel.entity.TransactionDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionDetailDao implements BaseDao<TransactionDetail> {

    private static final Logger LOG = LoggerFactory.getLogger(TransactionDetailDao.class);
    private static final String FIND_BY_ID = "SELECT * FROM transaction_detail WHERE id = ?";
    private static final String UPDATE_TRANS_DETAIL = "UPDATE transaction_detail SET user_name = ?, " +
            "amount = ?, payment = ?  WHERE id = ?";
    private static final String DELETE_TRANS_DETAIL = "DELETE FROM transaction_detail WHERE id = ?";
    private static final String INSERT_TRANS_DETAIL = "INSERT INTO transaction_detail VALUES (id,?,?,?,?,?)";
    private static final String ALL_TRANS_DETAILS = "SELECT id, user_name, amount," +
            "payment from transaction_detail";

    private ConnectionPool connectionPool;
    private Connection connection;

    @Override
    public TransactionDetail findById(int id) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        TransactionDetail transactionDetail = new TransactionDetail();
        try (PreparedStatement statement = connection.prepareStatement(FIND_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                transactionDetail = getObjectFromResultSet((resultSet));
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new DaoException("Cannot find by id", e);
        }
        return transactionDetail;
    }

    @Override
    public List<TransactionDetail> getAll(int pageNumber, int pageSize) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        List<TransactionDetail> transactionDetails = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(ALL_TRANS_DETAILS);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                transactionDetails.add(getObjectFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            LOG.error("cannot get all transaction details list", e);
            throw new DaoException("cannot get all transaction details list", e);
        }
        return transactionDetails;
    }

    public TransactionDetail getObjectFromResultSet(ResultSet resultSet) throws DaoException {
        TransactionDetail transactionDetail = new TransactionDetail();
        try {
            transactionDetail.setId(resultSet.getInt(1));
            transactionDetail.setUserName(resultSet.getString(2));
            transactionDetail.setAmount(resultSet.getDouble(3));
            transactionDetail.setPayment(resultSet.getDouble(4));
        } catch (SQLException e) {
            throw new DaoException("Cannot create transactionDetail from resultSet", e);
        }
        return transactionDetail;
    }

    @Override
    public TransactionDetail create(TransactionDetail transactionDetail) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(INSERT_TRANS_DETAIL,
                PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, transactionDetail.getUserName());
            statement.setDouble(2, transactionDetail.getAmount());
            statement.setDouble(3, transactionDetail.getPayment());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                transactionDetail.setId(id);
            }
        } catch (SQLException e) {
            throw new DaoException("Cannot create transactionDetail", e);
        }
        return transactionDetail;
    }

    @Override
    public void update(TransactionDetail transactionDetail) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_TRANS_DETAIL)) {
            statement.setString(1, transactionDetail.getUserName());
            statement.setDouble(2, transactionDetail.getAmount());
            statement.setDouble(3, transactionDetail.getPayment());
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException("Cannot update transactionDetail", e);
        }
    }

    @Override
    public void delete(TransactionDetail transactionDetail) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(DELETE_TRANS_DETAIL)) {
            statement.setInt(1, transactionDetail.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException("Cannot delete transactionDetail", e);
        }
    }
}
