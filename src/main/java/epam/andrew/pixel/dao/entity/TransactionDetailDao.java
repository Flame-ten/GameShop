package epam.andrew.pixel.dao.entity;

import epam.andrew.pixel.DaoException;
import epam.andrew.pixel.model.TransactionDetail;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionDetailDao extends BaseDao<TransactionDetail> {
    private static final String TRANS_DETAIL = "TransactionDetail";
    private static final String FIND_BY_ID = "SELECT * FROM transactionDetail WHERE id = ?";
    private static final String UPDATE_TRANS_DETAIL = "UPDATE transactionDetail SET `user name` = ?, `game name` = ?," +
            "amount = ?, payment = ?, date = ?  WHERE id = ?";
    private static final String DELETE_TRANS_DETAIL = "DELETE FROM transactionDetail WHERE id = ?";
    private static final String INSERT_TRANS_DETAIL = "INSERT INTO transactionDetail VALUES (id,?,?,?,?,?,?,?,?,NULL)";

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

    @Override
    protected String getTableName() {
        return TRANS_DETAIL;
    }
}
