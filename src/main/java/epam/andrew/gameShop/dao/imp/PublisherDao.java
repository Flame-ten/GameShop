package epam.andrew.gameShop.dao.imp;

import epam.andrew.gameShop.connection.ConnectionPool;
import epam.andrew.gameShop.connection.ConnectionPoolException;
import epam.andrew.gameShop.dao.BaseDao;
import epam.andrew.gameShop.dao.Dao;
import epam.andrew.gameShop.dao.DaoException;
import epam.andrew.gameShop.entity.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static epam.andrew.gameShop.util.Constant.*;

public class PublisherDao extends Dao implements BaseDao<Publisher> {
    private static final Logger LOG = LoggerFactory.getLogger(PublisherDao.class);
    private static final String FIND_BY_ID = "SELECT * FROM publisher WHERE id = ?";
    private static final String UPDATE_PUBLISHER = "UPDATE publisher SET name = ?, address = ?, phone = ?, " +
            "email = ?, country = ?, deleted = ? WHERE id = ?";
    private static final String DELETE_PUBLISHER = "DELETE FROM publisher WHERE id = ?";
    private static final String INSERT_PUBLISHER = "INSERT INTO publisher VALUES (id,?,?,?,?,?,?,?)";
    private static final String ALL_PUBLISHERS = "SELECT * FROM publisher WHERE deleted=0 LIMIT ? OFFSET ?";
    private static final String DELETED = "SELECT count(*) FROM publisher WHERE deleted = 0";
    private static final String SELECT_ALL_BY_ID = "SELECT * FROM publisher ORDER BY id";
    private static final String SELECT_FROM_WHERE = "SELECT * FROM publisher WHERE ";
    private static final String AND = "' AND ";
    private static final String CANNOT_GET_COUNT = "Cannot get count";
    private static final String CANNOT_DELETE_PUBLISHER = "Cannot delete publisher";
    private static final String CANNOT_UPDATE_PUBLISHER = "Cannot update publisher";
    private static final String CANNOT_CREATE_PUBLISHER = "Cannot create publisher";
    private static final String CANNOT_CREATE_FROM_RESULT = "Cannot create publisher from resultSet";
    private static final String CANNOT_GET_ALL_PUBLISHERS_LIST = "Cannot get all publishers list";
    private static final String CANNOT_GET_PUBLISHER_LIST_BY_PARAMS = "Cannot get publisher list by params";
    private static final String CANNOT_FIND_BY_ID = "Cannot find by id";
    private ConnectionPool connectionPool;
    private Connection connection;

    @Override
    public Publisher findById(int id) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        Publisher publisher = new Publisher();
        try (PreparedStatement statement = connection.prepareStatement(FIND_BY_ID)) {
            statement.setInt(INDEX_1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                publisher = getObjectFromResultSet((resultSet));
            }
            resultSet.close();
        } catch (SQLException e) {
            LOG.error(CANNOT_FIND_BY_ID, e);
            throw new DaoException(CANNOT_FIND_BY_ID, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return publisher;
    }

    public List<Publisher> getByParams(Map<String, String> params) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        List<Publisher> publisher = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(createQuery(params))) {
            while (resultSet.next()) {
                publisher.add(getObjectFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            LOG.error(CANNOT_GET_PUBLISHER_LIST_BY_PARAMS, e);
            throw new DaoException(CANNOT_GET_PUBLISHER_LIST_BY_PARAMS, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return publisher;
    }

    private String createQuery(Map<String, String> parameters) {
        StringBuilder query = new StringBuilder(SELECT_FROM_WHERE);
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
    public List<Publisher> getAll(int pageNumber, int pageSize) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        List<Publisher> publishers = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(ALL_PUBLISHERS);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                publishers.add(getObjectFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            LOG.error(CANNOT_GET_ALL_PUBLISHERS_LIST, e);
            throw new DaoException(CANNOT_GET_ALL_PUBLISHERS_LIST, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return publishers;
    }

    public List<Publisher> getAll() throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        List<Publisher> publishers = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_BY_ID);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                publishers.add(getObjectFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            LOG.error(CANNOT_GET_ALL_PUBLISHERS_LIST, e);
            throw new DaoException(CANNOT_GET_ALL_PUBLISHERS_LIST, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return publishers;
    }

    public Publisher getObjectFromResultSet(ResultSet resultSet) throws DaoException {
        Publisher publisher = new Publisher();
        try {
            publisher.setId(resultSet.getInt(INDEX_1));
            publisher.setName(resultSet.getString(INDEX_2));
            publisher.setAddress(resultSet.getString(INDEX_3));
            publisher.setPhone(resultSet.getString(INDEX_4));
            publisher.setEmail(resultSet.getString(INDEX_5));
            publisher.setCountry(resultSet.getString(INDEX_6));
        } catch (SQLException e) {
            LOG.error(CANNOT_CREATE_FROM_RESULT, e);
            throw new DaoException(CANNOT_CREATE_FROM_RESULT, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return publisher;
    }

    @Override
    public Publisher create(Publisher publisher) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(INSERT_PUBLISHER,
                PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(INDEX_1, publisher.getName());
            statement.setString(INDEX_2, publisher.getAddress());
            statement.setString(INDEX_3, publisher.getPhone());
            statement.setString(INDEX_4, publisher.getEmail());
            statement.setString(INDEX_5, publisher.getCountry());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            while (resultSet.next()) {
                int id = resultSet.getInt(INDEX_1);
                publisher.setId(id);
            }
        } catch (SQLException e) {
            LOG.error(CANNOT_CREATE_PUBLISHER, e);
            throw new DaoException(CANNOT_CREATE_PUBLISHER, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return publisher;
    }

    @Override
    public void update(Publisher publisher) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_PUBLISHER)) {
            statement.setString(INDEX_1, publisher.getName());
            statement.setString(INDEX_2, publisher.getAddress());
            statement.setString(INDEX_3, publisher.getPhone());
            statement.setString(INDEX_4, publisher.getEmail());
            statement.setString(INDEX_5, publisher.getCountry());
            statement.execute();
        } catch (SQLException e) {
            LOG.error(CANNOT_UPDATE_PUBLISHER, e);
            throw new DaoException(CANNOT_UPDATE_PUBLISHER, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public void delete(Publisher publisher) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(DELETE_PUBLISHER)) {
            statement.setInt(INDEX_1, publisher.getId());
            statement.execute();
        } catch (SQLException e) {
            LOG.error(CANNOT_DELETE_PUBLISHER, e);
            throw new DaoException(CANNOT_DELETE_PUBLISHER, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    public int getNotDeletedCount() throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        int count = 0;
        try (Statement statement = connection.createStatement()) {
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

