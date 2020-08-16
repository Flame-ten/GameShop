package epam.andrew.gameShop.dao.imp;

import epam.andrew.gameShop.connection.ConnectionPool;
import epam.andrew.gameShop.connection.ConnectionPoolException;
import epam.andrew.gameShop.dao.BaseDao;
import epam.andrew.gameShop.dao.Dao;
import epam.andrew.gameShop.dao.DaoException;
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

public class UserDao extends Dao implements BaseDao<User> {

    private static final Logger LOG = LoggerFactory.getLogger(UserDao.class);
    private static final String FIND_BY_ID = "SELECT * FROM user WHERE id = ?";
    private static final String UPDATE_USER = "UPDATE user SET name = ?, surname = ?," +
            "login = ?, password= ?, email = ?, phone = ?, gender = ?, country = ?, cash = ?, deleted = ?  WHERE id = ?";
    private static final String DELETE_USER = "DELETE FROM user WHERE id = ?";
    private static final String INSERT_USER = "INSERT INTO user VALUES (id,?,?,?,?,?,?,?,?,?,?,0)";
    private static final String ALL_USERS = "SELECT * FROM user WHERE deleted = 0 LIMIT ? OFFSET ?";
    private static final String DELETED = "SELECT count(*) FROM user WHERE deleted = 0";
    private static final String CANNOT_FIND_BY_ID = "Cannot find by id";
    private static final String CANNOT_GET_USER_LIST_BY_PARAMS = "Cannot get user list by params";
    private static final String SELECT_FROM_USER = "SELECT * FROM user WHERE ";
    private static final String AND = "' AND ";
    private static final String CANNOT_GET_ALL_USERS = "Cannot get all users";
    private static final String CANNOT_CREATE_USER_FROM_RESULT_SET = "Cannot create user from resultSet";
    private static final String CANNOT_CREATE_USER = "Cannot create user";
    private static final String CANNOT_UPDATE_USER = "Cannot update user";
    private static final String CANNOT_DELETE_USER = "Cannot delete user";
    private static final String CANNOT_GET_COUNT = "Cannot get count";

    private ConnectionPool connectionPool;
    private Connection connection;

    @Override
    public User findById(int id) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        User user = new User();
        try (PreparedStatement statement = connection.prepareStatement(FIND_BY_ID)) {
            statement.setInt(INDEX_1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                user = getObjectFromResultSet(resultSet);
            }
            resultSet.close();
        } catch (SQLException e) {
            LOG.error(CANNOT_FIND_BY_ID, e);
            throw new DaoException(CANNOT_FIND_BY_ID, e);
        } finally {
            connectionPool.returnConnection(connection);
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
            LOG.error(CANNOT_GET_USER_LIST_BY_PARAMS, e);
            throw new DaoException(CANNOT_GET_USER_LIST_BY_PARAMS, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return user;
    }

    private String createQuery(Map<String, String> parameters) {
        StringBuilder query = new StringBuilder(SELECT_FROM_USER);
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
    public List<User> getAll(int pageNumber, int pageSize) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        List<User> users = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(ALL_USERS)) {
            statement.setInt(INDEX_1, ((pageNumber - 1) * pageSize));
            statement.setInt(INDEX_2, pageSize);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                users.add(getObjectFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            LOG.error(CANNOT_GET_ALL_USERS, e);
            throw new DaoException(CANNOT_GET_ALL_USERS, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return users;
    }

    public User getObjectFromResultSet(ResultSet resultSet) throws DaoException {
        User user = new User();
        try {
            user.setId(resultSet.getInt(INDEX_1));
            user.setName(resultSet.getString(INDEX_2));
            user.setSurname(resultSet.getString(INDEX_3));
            user.setLogin(resultSet.getString(INDEX_4));
            user.setPassword(resultSet.getString(INDEX_5));
            user.setEmail(resultSet.getString(INDEX_6));
            user.setPhone(resultSet.getString(INDEX_7));
            user.setGender(resultSet.getString(INDEX_8));
            user.setCountry(resultSet.getString(INDEX_9));
            user.setCash(Money.of(CurrencyUnit.of(User.CURRENCY), resultSet.getBigDecimal(INDEX_10)));
        } catch (SQLException e) {
            LOG.error(CANNOT_CREATE_USER_FROM_RESULT_SET, e);
            throw new DaoException(CANNOT_CREATE_USER_FROM_RESULT_SET, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return user;
    }

    @Override
    public User create(User user) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(INSERT_USER,
                PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(INDEX_1, user.getName());
            statement.setString(INDEX_2, user.getSurname());
            statement.setString(INDEX_3, user.getPassword());
            statement.setString(INDEX_4, user.getLogin());
            statement.setString(INDEX_5, user.getEmail());
            statement.setString(INDEX_6, user.getPhone());
            statement.setString(INDEX_7, user.getGender());
            statement.setString(INDEX_8, user.getCountry());
            statement.setBigDecimal(INDEX_9, user.getCash().getAmount());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            while (resultSet.next()) {
                int id = resultSet.getInt(INDEX_1);
                user.setId(id);
            }
        } catch (SQLException e) {
            LOG.error(CANNOT_CREATE_USER, e);
            throw new DaoException(CANNOT_CREATE_USER, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return user;
    }

    @Override
    public void update(User user) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_USER)) {
            statement.setInt(INDEX_1, user.getId());
            statement.setString(INDEX_2, user.getName());
            statement.setString(INDEX_3, user.getSurname());
            statement.setString(INDEX_4, user.getLogin());
            statement.setString(INDEX_5, user.getPassword());
            statement.setString(INDEX_6, user.getEmail());
            statement.setString(INDEX_7, user.getPhone());
            statement.setString(INDEX_8, user.getGender());
            statement.setString(INDEX_9, user.getCountry());
            statement.setBigDecimal(INDEX_10, user.getCash().getAmount());
            statement.execute();
        } catch (SQLException e) {
            LOG.error(CANNOT_UPDATE_USER, e);
            throw new DaoException(CANNOT_UPDATE_USER, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public void delete(User user) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(DELETE_USER)) {
            statement.setInt(INDEX_1, user.getId());
            statement.execute();
        } catch (SQLException e) {
            LOG.error(CANNOT_DELETE_USER, e);
            throw new DaoException(CANNOT_DELETE_USER, e);
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
