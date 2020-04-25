package epam.andrew.pixel.dao;

import epam.andrew.pixel.connection.ConnectionPool;
import epam.andrew.pixel.connection.ConnectionPoolException;
import epam.andrew.pixel.entity.OrderStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderStatusDao implements BaseDao<OrderStatus> {
    private static final Logger LOG = LoggerFactory.getLogger(OrderStatusDao.class);
    private static final String FIND_BY_ID = "SELECT * FROM status WHERE id = ?";
    private static final String UPDATE_STATUS = "UPDATE status SET info = ? WHERE id = ?";
    private static final String DELETE_STATUS = "DELETE FROM status WHERE id = ?";
    private static final String INSERT_STATUS = "INSERT INTO status VALUES (id,?,?)";
    private static final String ALL_STATUSES = "SELECT info  from status";
    private ConnectionPool connectionPool;
    private Connection connection;

    @Override
    public OrderStatus findById(int id) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        OrderStatus orderStatus = new OrderStatus();
        try (PreparedStatement statement = connection.prepareStatement(FIND_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                orderStatus = getObjectFromResultSet((resultSet));
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new DaoException("Cannot find by id", e);
        }
        return orderStatus;
    }

    @Override
    public List<OrderStatus> getAll(int pageNumber, int pageSize) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        List<OrderStatus> orderStatuses = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(ALL_STATUSES);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                orderStatuses.add(getObjectFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            LOG.error("cannot get all statuses list", e);
            throw new DaoException("cannot get all statuses list", e);
        }
        return orderStatuses;
    }

    public OrderStatus getObjectFromResultSet(ResultSet resultSet) throws DaoException {
        OrderStatus orderStatus = new OrderStatus();
        try {
            orderStatus.setId(resultSet.getInt(1));
            orderStatus.setInfo(resultSet.getString(2));
        } catch (SQLException e) {
            throw new DaoException("Cannot create status from resultSet", e);
        }
        return orderStatus;
    }

    @Override
    public OrderStatus create(OrderStatus orderStatus) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(INSERT_STATUS,
                PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, orderStatus.getInfo());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                orderStatus.setId(id);
            }
        } catch (SQLException e) {
            throw new DaoException("Cannot create status", e);
        }
        return orderStatus;
    }

    @Override
    public void update(OrderStatus orderStatus) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_STATUS)) {
            statement.setString(1, orderStatus.getInfo());
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException("Cannot update status", e);
        }
    }

    @Override
    public void delete(OrderStatus orderStatus) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(DELETE_STATUS)) {
            statement.setInt(1, orderStatus.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException("Cannot delete status", e);
        }
    }
}
