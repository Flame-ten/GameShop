package epam.andrew.pixel.dao;

import epam.andrew.pixel.connection.ConnectionPool;
import epam.andrew.pixel.connection.ConnectionPoolException;
import epam.andrew.pixel.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserDao implements BaseDao<User> {
    private static final Logger LOG = LoggerFactory.getLogger(UserDao.class);
    private static final String FIND_BY_ID = "SELECT * FROM user WHERE id = ?";
    private static final String UPDATE_USER = "UPDATE user SET name = ? , surname = ? ," +
            "password= ? , email = ? , login = ? , gender = ? , country = ?, position = ? WHERE id = ?";
    private static final String DELETE_USER = "DELETE FROM user WHERE id = ?";
    private static final String INSERT_USER = "INSERT INTO user VALUES (id,?,?,?,?,?,?,?,?,?)";
    private static final String ALL_USERS = "SELECT id, name, surname , password , email, login , gender, " +
            "country, position from user";
    private ConnectionPool connectionPool;
    private Connection connection;

    @Override
    public User findById(int id) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        User user = new User();
        try (PreparedStatement statement = connection.prepareStatement(FIND_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                user = getObjectFromResultSet(resultSet);
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new DaoException("Cannot find by id", e);
        }
        return user;
    }

    public List<User> getByParams(Map<String, String> params) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        List<User> user = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(createQuery(params))) {
            while (resultSet.next()) {
                user.add(getObjectFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoException("cannot get user list by params", e);
        }
        return user;
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
    public List<User> getAll(int pageNumber, int pageSize) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        List<User> users = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(ALL_USERS);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                users.add(getObjectFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            LOG.error("cannot get all users list", e);
            throw new DaoException("cannot get all users list", e);
        }
        return users;
    }

    public User getObjectFromResultSet(ResultSet resultSet) throws DaoException {
        User user = new User();
        try {
            user.setId(resultSet.getInt(1));
            user.setName(resultSet.getString(2));
            user.setSurname(resultSet.getString(3));
            user.setLogin(resultSet.getString(4));
            user.setPassword(resultSet.getString(5));
            user.setEmail(resultSet.getString(6));
            user.setPhone(resultSet.getInt(7));
            user.setGender(resultSet.getString(8));
            user.setCountry(resultSet.getString(9));
            user.setPosition((resultSet.getString(10)));
        } catch (SQLException e) {
            throw new DaoException("Cannot create user from resultSet", e);
        }
        return user;
    }

    @Override
    public User create(User user) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(INSERT_USER,
                PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getSurname());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getLogin());
            statement.setString(5, user.getEmail());
            statement.setInt(6, user.getPhone());
            statement.setString(7, user.getGender());
            statement.setString(8, user.getCountry());
            statement.setString(9, user.getPosition());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                user.setId(id);
            }
        } catch (SQLException e) {
            throw new DaoException("Cannot create user", e);
        }
        return user;
    }

    @Override
    public void update(User user) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_USER)) {
            statement.setInt(1, user.getId());
            statement.setString(2, user.getName());
            statement.setString(3, user.getSurname());
            statement.setString(4, user.getLogin());
            statement.setString(5, user.getPassword());
            statement.setString(6, user.getEmail());
            statement.setInt(7, user.getPhone());
            statement.setString(8, user.getGender());
            statement.setString(9, user.getCountry());
            statement.setString(10, user.getPosition());
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException("Cannot update user", e);
        }
    }

    @Override
    public void delete(User user) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(DELETE_USER)) {
            statement.setInt(1, user.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException("Cannot delete user", e);
        }
    }

}
