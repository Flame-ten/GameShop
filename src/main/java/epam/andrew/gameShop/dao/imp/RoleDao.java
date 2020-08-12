package epam.andrew.gameShop.dao.imp;

import epam.andrew.gameShop.connection.ConnectionPool;
import epam.andrew.gameShop.connection.ConnectionPoolException;
import epam.andrew.gameShop.dao.BaseDao;
import epam.andrew.gameShop.dao.Dao;
import epam.andrew.gameShop.dao.DaoException;
import epam.andrew.gameShop.entity.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static epam.andrew.gameShop.util.Constant.INDEX_1;
import static epam.andrew.gameShop.util.Constant.INDEX_2;

public class RoleDao extends Dao implements BaseDao<Role> {
    private static final Logger LOG = LoggerFactory.getLogger(RoleDao.class);
    private static final String FIND_BY_ID = "SELECT * FROM role WHERE id = ?";
    private static final String UPDATE_ROLE = "UPDATE role SET name = ? WHERE id = ?";
    private static final String DELETE_ROLE = "DELETE FROM role WHERE id = ?";
    private static final String INSERT_ROLE = "INSERT INTO role VALUES (id,?,?,?)";
    private static final String ALL_ROLE = "SELECT * FROM role WHERE deleted=0 LIMIT ? OFFSET ?";
    private static final String CANNOT_DELETE_ROLE = "Cannot delete role";
    private static final String CANNOT_UPDATE_ROLE = "Cannot update role";
    private static final String CANNOT_CREATE_ROLE = "Cannot create role";
    private static final String CANNOT_CREATE_FROM_RESULT = "Cannot create role from resultSet";
    private static final String CANNOT_GET_ALL = "Cannot get all roles";
    private static final String CANNOT_FIND_BY_ID = "Cannot find by id";
    private Connection connection;
    private ConnectionPool connectionPool;

    @Override
    public Role findById(int id) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        Role role = new Role();
        try (PreparedStatement statement = connection.prepareStatement(FIND_BY_ID)) {
            statement.setInt(INDEX_1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                role = getObjectFromResultSet((resultSet));
            }
            resultSet.close();
        } catch (SQLException e) {
            LOG.error(CANNOT_FIND_BY_ID, e);
            throw new DaoException(CANNOT_FIND_BY_ID, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return role;
    }

    @Override
    public List<Role> getAll(int pageNumber, int pageSize) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        List<Role> roles = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(ALL_ROLE);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                roles.add(getObjectFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            LOG.error(CANNOT_GET_ALL, e);
            throw new DaoException(CANNOT_GET_ALL, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return roles;
    }

    public Role getObjectFromResultSet(ResultSet resultSet) throws DaoException {
        Role role = new Role();
        try {
            role.setId(resultSet.getInt(INDEX_1));
            role.setName(resultSet.getString(INDEX_2));
        } catch (SQLException e) {
            LOG.error(CANNOT_CREATE_FROM_RESULT, e);
            throw new DaoException(CANNOT_CREATE_FROM_RESULT, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return role;

    }

    @Override
    public Role create(Role role) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(INSERT_ROLE,
                PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(INDEX_1, role.getName());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            while (resultSet.next()) {
                int id = resultSet.getInt(INDEX_1);
                role.setId(id);
            }
        } catch (SQLException e) {
            LOG.error(CANNOT_CREATE_ROLE, e);
            throw new DaoException(CANNOT_CREATE_ROLE, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return role;
    }

    @Override
    public void update(Role role) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_ROLE)) {
            statement.setString(INDEX_1, role.getName());
            statement.execute();
        } catch (SQLException e) {
            LOG.error(CANNOT_UPDATE_ROLE, e);
            throw new DaoException(CANNOT_UPDATE_ROLE, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public void delete(Role role) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(DELETE_ROLE)) {
            statement.setInt(INDEX_1, role.getId());
            statement.execute();
        } catch (SQLException e) {
            LOG.error(CANNOT_DELETE_ROLE, e);
            throw new DaoException(CANNOT_DELETE_ROLE, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }
}
