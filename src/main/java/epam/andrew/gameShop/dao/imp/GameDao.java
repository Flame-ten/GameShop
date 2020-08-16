package epam.andrew.gameShop.dao.imp;

import epam.andrew.gameShop.connection.ConnectionPool;
import epam.andrew.gameShop.connection.ConnectionPoolException;
import epam.andrew.gameShop.dao.BaseDao;
import epam.andrew.gameShop.dao.Dao;
import epam.andrew.gameShop.dao.DaoException;
import epam.andrew.gameShop.entity.Game;
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

public class GameDao extends Dao implements BaseDao<Game> {
    private static final Logger LOG = LoggerFactory.getLogger(GameDao.class);
    private static final String FIND_BY_ID = "SELECT * FROM game WHERE id = ?";
    private static final String UPDATE_GAME = "UPDATE game SET name = ?, amount = ? , price = ?, description_en = ?, description_ru = ?, deleted = ? WHERE id = ?";
    private static final String DELETE_GAME = "DELETE FROM game WHERE id = ?";
    private static final String INSERT_GAME = "INSERT INTO game VALUES (id,?,?,?,?,?,?,?,?,0)";
    private static final String ALL_GAMES = "SELECT * FROM game WHERE deleted=0 LIMIT ? OFFSET ?";
    private static final String SELECT_FROM_GAME = "SELECT count(*) FROM game WHERE deleted = 0";
    private static final String SELECT_ALL_BY_ID = "SELECT * FROM game ORDER BY id";
    private static final String ADD_GAME_TO_USER = "INSERT INTO library VALUES (?,?)";
    private static final String SELECT_FROM_WHERE = "SELECT * FROM game WHERE ";
    private static final String AND = "' AND ";
    private static final String CANNOT_ADD_GAME_IN_LIBRARY = "Cannot add game in library";
    private static final String CANNOT_GET_COUNT = "Cannot get count";
    private static final String CANNOT_DELETE_GAME = "Cannot delete game";
    private static final String CANNOT_UPDATE_GAME = "Cannot update game";
    private static final String CANNOT_CREATE_GAME = "Cannot create game";
    private static final String CANNOT_CREATE_FROM_RESULT = "Cannot create game from resultSet";
    private static final String CANNOT_GET_ALL_GAMES = "Cannot get all games";
    private static final String CANNOT_GET_GAME_LIST_BY_PARAMS = "Cannot get game list by params";
    private static final String CANNOT_FIND_BY_ID = "Cannot find by id";

    private Connection connection;
    private ConnectionPool connectionPool;

    @Override
    public Game findById(int id) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        Game game = new Game();
        try (PreparedStatement statement = connection.prepareStatement(FIND_BY_ID)) {
            statement.setInt(INDEX_1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                game = getObjectFromResultSet((resultSet));
            }
            resultSet.close();
        } catch (SQLException e) {
            LOG.error(CANNOT_FIND_BY_ID, e);
            throw new DaoException(CANNOT_FIND_BY_ID, e);
        } finally {
            connectionPool.returnConnection(connection);
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
            LOG.error(CANNOT_GET_GAME_LIST_BY_PARAMS, e);
            throw new DaoException(CANNOT_GET_GAME_LIST_BY_PARAMS, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return game;
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
            LOG.error(CANNOT_GET_ALL_GAMES, e);
            throw new DaoException(CANNOT_GET_ALL_GAMES, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return games;
    }

    public List<Game> getAll() throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        List<Game> games = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_BY_ID);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                games.add(getObjectFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            LOG.error(CANNOT_GET_ALL_GAMES, e);
            throw new DaoException(CANNOT_GET_ALL_GAMES, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return games;
    }

    public Game getObjectFromResultSet(ResultSet resultSet) throws DaoException {
        Game game = new Game();
        try {
            game.setId(resultSet.getInt(INDEX_1));
            game.setName(resultSet.getString(INDEX_2));
            game.setAmount(resultSet.getInt(INDEX_3));
            game.setDescription(resultSet.getString(INDEX_4));
            game.setPrice(Money.of(CurrencyUnit.of(User.CURRENCY), resultSet.getBigDecimal(INDEX_5)));
        } catch (SQLException e) {
            LOG.error(CANNOT_CREATE_FROM_RESULT, e);
            throw new DaoException(CANNOT_CREATE_FROM_RESULT, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return game;

    }

    @Override
    public Game create(Game game) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(INSERT_GAME,
                PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(INDEX_1, game.getName());
            statement.setBigDecimal(INDEX_2, game.getPrice().getAmount());
            statement.setInt(INDEX_3, game.getAmount());
            statement.setString(INDEX_4, game.getDescription());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            while (resultSet.next()) {
                int id = resultSet.getInt(INDEX_1);
                game.setId(id);
            }
        } catch (SQLException e) {
            LOG.error(CANNOT_CREATE_GAME, e);
            throw new DaoException(CANNOT_CREATE_GAME, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return game;
    }

    @Override
    public void update(Game game) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_GAME)) {
            statement.setString(INDEX_1, game.getName());
            statement.setBigDecimal(INDEX_2, game.getPrice().getAmount());
            statement.setInt(INDEX_3, game.getAmount());
            statement.setString(INDEX_4, game.getDescription());
            statement.execute();
        } catch (SQLException e) {
            LOG.error(CANNOT_UPDATE_GAME, e);
            throw new DaoException(CANNOT_UPDATE_GAME, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public void delete(Game game) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(DELETE_GAME)) {
            statement.setInt(INDEX_1, game.getId());
            statement.execute();
        } catch (SQLException e) {
            LOG.error(CANNOT_DELETE_GAME, e);
            throw new DaoException(CANNOT_DELETE_GAME, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    public int getNotDeletedCount() throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        int count = 0;
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SELECT_FROM_GAME);
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

    public void addGameToLibrary(Game game, User user) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement statement = getConnection().prepareStatement(ADD_GAME_TO_USER)) {
            statement.setInt(INDEX_1, user.getId());
            statement.setInt(INDEX_2, game.getId());
            statement.execute();
        } catch (SQLException e) {
            LOG.error(CANNOT_ADD_GAME_IN_LIBRARY, e);
            throw new DaoException(CANNOT_ADD_GAME_IN_LIBRARY, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }
}
