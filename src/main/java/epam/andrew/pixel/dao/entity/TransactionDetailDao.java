package epam.andrew.pixel.dao.entity;

import epam.andrew.pixel.DaoException;
import epam.andrew.pixel.model.TransactionDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionDetailDao extends Dao implements EntityDao<TransactionDetail> {
    private static final Logger LOG = LoggerFactory.getLogger(TransactionDetailDao.class);
    private static final String FIND_BY_ID = "SELECT * FROM transactionDetail WHERE id = ?";
    private static final String UPDATE_TRANS_DETAIL = "UPDATE transactionDetail SET `user name` = ?, `game name` = ?," +
            "amount = ?, payment = ?, date = ?  WHERE id = ?";
    private static final String DELETE_TRANS_DETAIL = "DELETE FROM transactionDetail WHERE id = ?";
    private static final String INSERT_TRANS_DETAIL = "INSERT INTO transactionDetail VALUES (id,?,?,?,?,?,?,?)";
    private static final String ALL_TRANSACTIONS = "SELECT id, `user name`, `game name`, amount," +
            "payment, date ,time, `language id`  from transactionDetail";

    @Override
    public TransactionDetail findById(int id) throws DaoException {
        TransactionDetail transactionDetail = new TransactionDetail();
        try (PreparedStatement statement = getConnection().prepareStatement(FIND_BY_ID)) {
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
    public List<TransactionDetail> getAll() throws DaoException {
        List<TransactionDetail> transactionDetails = new ArrayList<>();
        try (PreparedStatement statement = getConnection().prepareStatement(ALL_TRANSACTIONS);
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
            transactionDetail.setGameName(resultSet.getString(3));
            transactionDetail.setAmount(resultSet.getDouble(4));
            transactionDetail.setPayment(resultSet.getDouble(5));
            transactionDetail.setDate(resultSet.getDate(6));
            transactionDetail.setTime(resultSet.getTime(7));
        } catch (SQLException e) {
            throw new DaoException("Cannot create transactionDetail from resultSet", e);
        }
        return transactionDetail;
    }

    @Override
    public TransactionDetail create(TransactionDetail transactionDetail) throws DaoException {
        try (PreparedStatement statement = getConnection().prepareStatement(INSERT_TRANS_DETAIL,
                PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, transactionDetail.getUserName());
            statement.setString(2, transactionDetail.getGameName());
            statement.setDouble(3, transactionDetail.getAmount());
            statement.setDouble(4, transactionDetail.getPayment());
            statement.setDate(5, transactionDetail.getDate());
            statement.setTime(6, transactionDetail.getTime());
        } catch (SQLException e) {
            throw new DaoException("Cannot create transactionDetail", e);
        }
        return transactionDetail;
    }

    @Override
    public void update(TransactionDetail transactionDetail) throws DaoException {
        try (PreparedStatement statement = getConnection().prepareStatement(UPDATE_TRANS_DETAIL)) {
            statement.setString(1, transactionDetail.getUserName());
            statement.setString(2, transactionDetail.getGameName());
            statement.setDouble(3, transactionDetail.getAmount());
            statement.setDouble(4, transactionDetail.getPayment());
            statement.setDate(5, transactionDetail.getDate());
            statement.setTime(6, transactionDetail.getTime());
        } catch (SQLException e) {
            throw new DaoException("Cannot update transactionDetail", e);
        }
    }

    @Override
    public void delete(TransactionDetail transactionDetail) throws DaoException {
        try (PreparedStatement statement = getConnection().prepareStatement(DELETE_TRANS_DETAIL)) {
            statement.setInt(1, transactionDetail.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException("Cannot delete transactionDetail", e);
        }
    }
}
