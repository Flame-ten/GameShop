package epam.andrew.pixel.dao;

import epam.andrew.pixel.connection.ConnectionPool;
import epam.andrew.pixel.connection.ConnectionPoolException;
import epam.andrew.pixel.entity.Genre;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GenreDao implements BaseDao<Genre> {

    private static final Logger LOG = LoggerFactory.getLogger(Genre.class);
    private static final String FIND_BY_ID = "SELECT * FROM genre WHERE id = ?";
    private static final String UPDATE_GENRE = "UPDATE genre SET info = ? WHERE id = ?";
    private static final String DELETE_GENRE = "DELETE FROM genre WHERE id = ?";
    private static final String INSERT_GENRE = "INSERT INTO genre VALUES (id,?,?)";
    private static final String ALL_GENRES = "SELECT info  from genre";
    private ConnectionPool connectionPool;
    private Connection connection;

    @Override
    public Genre findById(int id) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        Genre genre = new Genre();
        try (PreparedStatement statement = connection.prepareStatement(FIND_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                genre = getObjectFromResultSet((resultSet));
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new DaoException("Cannot find by id", e);
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
            LOG.error("cannot get all genres list", e);
            throw new DaoException("cannot get all genres list", e);
        }
        return genres;
    }

    public Genre getObjectFromResultSet(ResultSet resultSet) throws DaoException {
        Genre genre = new Genre();
        try {
            genre.setId(resultSet.getInt(1));
            genre.setInfo(resultSet.getString(2));
        } catch (SQLException e) {
            throw new DaoException("Cannot create genre from resultSet", e);
        }
        return genre;
    }

    @Override
    public Genre create(Genre genre) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(INSERT_GENRE,
                PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, genre.getInfo());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                genre.setId(id);
            }
        } catch (SQLException e) {
            throw new DaoException("Cannot create genre", e);
        }
        return genre;
    }

    @Override
    public void update(Genre genre) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_GENRE)) {
            statement.setString(1, genre.getInfo());
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException("Cannot update genre", e);
        }
    }

    @Override
    public void delete(Genre genre) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(DELETE_GENRE)) {
            statement.setInt(1, genre.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException("Cannot delete genre", e);
        }
    }
}
