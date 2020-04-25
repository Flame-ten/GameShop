package epam.andrew.pixel.dao;

import epam.andrew.pixel.connection.ConnectionPool;
import epam.andrew.pixel.connection.ConnectionPoolException;
import epam.andrew.pixel.entity.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameDao implements BaseDao<Game> {
    private static final Logger LOG = LoggerFactory.getLogger(GameDao.class);
    private static final String FIND_BY_ID = "SELECT * FROM game WHERE id = ?";
    private static final String UPDATE_GAME = "UPDATE game SET name = ?, price = ?, discount = ? WHERE id = ?";
    private static final String DELETE_GAME = "DELETE FROM game WHERE id = ?";
    private static final String INSERT_GAME = "INSERT INTO game VALUES (id,?,?,?,?)";
    private static final String ALL_GAMES = "SELECT id, name, price , discount from game";
    private Connection connection;
    private ConnectionPool connectionPool;

    @Override
    public Game findById(int id) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        Game game = new Game();
        try (PreparedStatement statement = connection.prepareStatement(FIND_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                game = getObjectFromResultSet((resultSet));
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new DaoException("Cannot find by id", e);
        }
        return game;
    }

    public List<Game> getByParams(Map<String, String> params) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        List<Game> game = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(createQuery(params))) {
            while (resultSet.next()) {
                game.add(getObjectFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoException("cannot get game list by params", e);
        }
        return game;
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
    public List<Game> getAll(int pageNumber, int pageSize) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        List<Game> games = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(ALL_GAMES);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                games.add(getObjectFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            LOG.error("cannot get all games list", e);
            throw new DaoException("cannot get all games list", e);
        }
        return games;
    }

    public Game getObjectFromResultSet(ResultSet resultSet) throws DaoException {
        Game game = new Game();
        try {
            game.setId(resultSet.getInt(1));
            game.setName(resultSet.getString(2));
            game.setPrice(resultSet.getDouble(3));
            game.setDiscount(resultSet.getInt(4));
        } catch (SQLException e) {
            throw new DaoException("Cannot create game from resultSet", e);
        }
        return game;

    }

    @Override
    public Game create(Game game) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(INSERT_GAME,
                PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, game.getName());
            statement.setDouble(2, game.getPrice());
            statement.setInt(3, game.getDiscount());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                game.setId(id);
            }
        } catch (SQLException e) {
            throw new DaoException("Cannot create game", e);
        }
        return game;
    }

    @Override
    public void update(Game game) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_GAME)) {
            statement.setString(1, game.getName());
            statement.setDouble(2, game.getPrice());
            statement.setInt(3, game.getDiscount());
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException("Cannot update game", e);
        }
    }

    @Override
    public void delete(Game game) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(DELETE_GAME)) {
            statement.setInt(1, game.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException("Cannot delete game", e);
        }
    }
}
