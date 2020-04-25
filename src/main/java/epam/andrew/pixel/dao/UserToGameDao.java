package epam.andrew.pixel.dao;

import epam.andrew.pixel.connection.ConnectionPool;
import epam.andrew.pixel.connection.ConnectionPoolException;
import epam.andrew.pixel.entity.UserToGame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserToGameDao implements BaseDao<UserToGame> {

    private static final Logger LOG = LoggerFactory.getLogger(UserToGameDao.class);
    private static final String FIND_BY_ID = "SELECT * FROM user_to_game WHERE id = ?";
    private static final String UPDATE_LIBRARY = "UPDATE user_to_game SET user_name = ?, game_name = ?" +
            " WHERE id = ?";
    private static final String DELETE_LIBRARY = "DELETE FROM user_to_game WHERE id = ?";
    private static final String INSERT_LIBRARY = "INSERT INTO user_to_game VALUES (id,?,?,?,?)";
    private static final String ALL_LIBRARIES = "SELECT id, user_name, game_name  from user_to_game";
    private ConnectionPool connectionPool;
    private Connection connection;

    @Override
    public UserToGame findById(int id) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        UserToGame library = new UserToGame();
        try (PreparedStatement statement = connection.prepareStatement(FIND_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                library = getObjectFromResultSet((resultSet));
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new DaoException("Cannot find by id", e);
        }
        return library;
    }

    public UserToGame getObjectFromResultSet(ResultSet resultSet) throws DaoException {
        UserToGame library = new UserToGame();
        try {
            library.setId(resultSet.getInt(1));
            library.setUsername(resultSet.getString(2));
            library.setGameName(resultSet.getString(3));
        } catch (SQLException e) {
            throw new DaoException("Cannot create library from resultSet", e);
        }
        return library;
    }

    @Override
    public List<UserToGame> getAll(int pageNumber, int pageSize) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        List<UserToGame> libraries = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(ALL_LIBRARIES);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                libraries.add(getObjectFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            LOG.error("cannot get all libraries list", e);
            throw new DaoException("cannot get all libraries list", e);
        }
        return libraries;
    }

    @Override
    public UserToGame create(UserToGame userToGame) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(INSERT_LIBRARY,
                PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, userToGame.getUsername());
            statement.setString(2, userToGame.getGameName());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                userToGame.setId(id);
            }
        } catch (SQLException e) {
            throw new DaoException("Cannot create library", e);
        }
        return userToGame;
    }

    @Override
    public void update(UserToGame userToGame) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_LIBRARY)) {
            statement.setString(1, userToGame.getUsername());
            statement.setString(2, userToGame.getGameName());
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException("Cannot update library", e);
        }
    }

    @Override
    public void delete(UserToGame userToGame) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(DELETE_LIBRARY)) {
            statement.setInt(1, userToGame.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException("Cannot delete library", e);
        }
    }
}
