package epam.andrew.pixel.dao;

import epam.andrew.pixel.connection.ConnectionPool;
import epam.andrew.pixel.connection.ConnectionPoolException;
import epam.andrew.pixel.entity.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PublisherDao implements BaseDao<Publisher> {
    private static final Logger LOG = LoggerFactory.getLogger(PublisherDao.class);
    private static final String FIND_BY_ID = "SELECT * FROM publisher WHERE id = ?";
    private static final String UPDATE_COMPANY = "UPDATE publisher SET name = ?, address = ?, phone = ?, " +
            "email = ?, country = ? WHERE id = ?";
    private static final String DELETE_COMPANY = "DELETE FROM publisher WHERE id = ?";
    private static final String INSERT_COMPANY = "INSERT INTO publisher VALUES (id,?,?,?,?,?)";
    private static final String ALL_COMPANY = "SELECT id, name, address, phone, email, country from publisher";
    private ConnectionPool connectionPool;
    private Connection connection;

    @Override
    public Publisher findById(int id) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        Publisher publisher = new Publisher();
        try (PreparedStatement statement = connection.prepareStatement(FIND_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                publisher = getObjectFromResultSet((resultSet));
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new DaoException("Cannot find by id", e);
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
            throw new DaoException("cannot get publisher list by params", e);
        }
        return publisher;
    }

    private String createQuery(Map<String, String> parameters) {
        StringBuilder query = new StringBuilder("SELECT * FROM game WHERE ");
        for (Map.Entry<String, String> parameter : parameters.entrySet()) {
            if (parameters.size() == 1) {
                query.append(parameter.getKey()).append("='").append(parameter.getValue()).append("'");
                return query.toString();
            } else {
                query.append(parameter.getKey()).append("='").append(parameter.getValue()).append("' AND ");
            }
        }
        return query.substring(0, query.length() - 5);
    }

    @Override
    public List<Publisher> getAll(int pageNumber, int pageSize) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        List<Publisher> publishers = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(ALL_COMPANY);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                publishers.add(getObjectFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            LOG.error("cannot get all publishers list", e);
            throw new DaoException("cannot get all publishers list", e);
        }
        return publishers;
    }

    public Publisher getObjectFromResultSet(ResultSet resultSet) throws DaoException {
        Publisher publisher = new Publisher();
        try {
            publisher.setId(resultSet.getInt(1));
            publisher.setName(resultSet.getString(2));
            publisher.setAddress(resultSet.getString(3));
            publisher.setPhone(resultSet.getInt(4));
            publisher.setEmail(resultSet.getString(5));
            publisher.setCountry(resultSet.getString(6));
        } catch (SQLException e) {
            throw new DaoException("Cannot create company from resultSet", e);
        }
        return publisher;
    }

    @Override
    public Publisher create(Publisher publisher) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(INSERT_COMPANY,
                PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, publisher.getName());
            statement.setString(2, publisher.getAddress());
            statement.setInt(3, publisher.getPhone());
            statement.setString(4, publisher.getEmail());
            statement.setString(5, publisher.getCountry());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                publisher.setId(id);
            }
        } catch (SQLException e) {
            throw new DaoException("Cannot create publisher", e);
        }
        return publisher;
    }

    @Override
    public void update(Publisher publisher) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_COMPANY)) {
            statement.setString(1, publisher.getName());
            statement.setString(2, publisher.getAddress());
            statement.setInt(3, publisher.getPhone());
            statement.setString(4, publisher.getEmail());
            statement.setString(5, publisher.getCountry());
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException("Cannot update publisher", e);
        }
    }

    @Override
    public void delete(Publisher publisher) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(DELETE_COMPANY)) {
            statement.setInt(1, publisher.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException("Cannot delete publisher", e);
        }
    }
}

