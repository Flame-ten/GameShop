package epam.andrew.gameShop.dao.imp;

import epam.andrew.gameShop.connection.ConnectionPool;
import epam.andrew.gameShop.connection.ConnectionPoolException;
import epam.andrew.gameShop.dao.BaseDao;
import epam.andrew.gameShop.dao.Dao;
import epam.andrew.gameShop.dao.DaoException;
import epam.andrew.gameShop.entity.Genre;
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

public class GenreDao extends Dao implements BaseDao<Genre> {

    private static final Logger LOG = LoggerFactory.getLogger(Genre.class);
    private static final String FIND_BY_ID = "SELECT * FROM genre WHERE id = ?";
    private static final String UPDATE_GENRE = "UPDATE genre SET info = ? WHERE id = ?";
    private static final String DELETE_GENRE = "DELETE FROM genre WHERE id = ?";
    private static final String CREATE_GENRE = "INSERT INTO genre VALUES (id,?,?,?)";
    private static final String ALL_GENRES = "SELECT * FROM genre WHERE deleted=0 LIMIT ? OFFSET ?";
    private static final String SELECT_ALL_BY_ID = "SELECT * FROM genre ORDER BY id";
    private static final String CANNOT_DELETE_GENRE = "Cannot delete genre";
    private static final String CANNOT_UPDATE_GENRE = "Cannot update genre";
    private static final String CANNOT_CREATE_GENRE = "Cannot create genre";
    private static final String CANNOT_CREATE_FROM_RESULT = "Cannot create genre from resultSet";
    private static final String CANNOT_GET_ALL_GENRES_LIST = "Cannot get all genres list";
    private static final String CANNOT_FIND_BY_ID = "Cannot find by id";
    private static final String CANNOT_GET_ALL_GENRES = "Cannot get all genres";
    private ConnectionPool connectionPool;
    private Connection connection;

    @Override
    public Genre findById(int id) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        Genre genre = new Genre();
        try (PreparedStatement statement = connection.prepareStatement(FIND_BY_ID)) {
            statement.setInt(INDEX_1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                genre = getObjectFromResultSet((resultSet));
            }
            resultSet.close();
        } catch (SQLException e) {
            LOG.error(CANNOT_FIND_BY_ID, e);
            throw new DaoException(CANNOT_FIND_BY_ID, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return genre;
    }

    @Override
    public List<Genre> getAll(int pageNumber, int pageSize) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        List<Genre> genres = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(ALL_GENRES);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                genres.add(getObjectFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            LOG.error(CANNOT_GET_ALL_GENRES_LIST, e);
            throw new DaoException(CANNOT_GET_ALL_GENRES_LIST, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return genres;
    }

    public List<Genre> getAll() throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        List<Genre> genres = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_BY_ID);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                genres.add(getObjectFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            LOG.error(CANNOT_GET_ALL_GENRES, e);
            throw new DaoException(CANNOT_GET_ALL_GENRES, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return genres;
    }

    public Genre getObjectFromResultSet(ResultSet resultSet) throws DaoException {
        Genre genre = new Genre();
        try {
            genre.setId(resultSet.getInt(INDEX_1));
            genre.setInfo(resultSet.getString(INDEX_2));
        } catch (SQLException e) {
            LOG.error(CANNOT_CREATE_FROM_RESULT, e);
            throw new DaoException(CANNOT_CREATE_FROM_RESULT, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return genre;
    }

    @Override
    public Genre create(Genre genre) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(CREATE_GENRE,
                PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(INDEX_1, genre.getInfo());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            while (resultSet.next()) {
                int id = resultSet.getInt(INDEX_1);
                genre.setId(id);
            }
        } catch (SQLException e) {
            LOG.error(CANNOT_CREATE_GENRE, e);
            throw new DaoException(CANNOT_CREATE_GENRE, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return genre;
    }

    @Override
    public void update(Genre genre) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_GENRE)) {
            statement.setString(INDEX_1, genre.getInfo());
            statement.execute();
        } catch (SQLException e) {
            LOG.error(CANNOT_UPDATE_GENRE, e);
            throw new DaoException(CANNOT_UPDATE_GENRE, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public void delete(Genre genre) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(DELETE_GENRE)) {
            statement.setInt(INDEX_1, genre.getId());
            statement.execute();
        } catch (SQLException e) {
            LOG.error(CANNOT_DELETE_GENRE, e);
            throw new DaoException(CANNOT_DELETE_GENRE, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }
}
