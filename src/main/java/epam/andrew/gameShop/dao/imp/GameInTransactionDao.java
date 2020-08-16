package epam.andrew.gameShop.dao.imp;

import epam.andrew.gameShop.connection.ConnectionPool;
import epam.andrew.gameShop.connection.ConnectionPoolException;
import epam.andrew.gameShop.dao.BaseDao;
import epam.andrew.gameShop.dao.Dao;
import epam.andrew.gameShop.dao.DaoException;
import epam.andrew.gameShop.entity.GameInTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static epam.andrew.gameShop.util.Constant.INDEX_1;
import static epam.andrew.gameShop.util.Constant.INDEX_2;


public class GameInTransactionDao extends Dao implements BaseDao<GameInTransaction> {

    private static final Logger LOG = LoggerFactory.getLogger(GameInTransactionDao.class);
    private static final String FIND_BY_ID = "SELECT * FROM gameintransaction WHERE id = ?";
    private static final String UPDATE_GAME_IN_TRANS = "UPDATE gameintransaction SET amount = ?  WHERE id = ?";
    private static final String DELETE_GAME_IN_TRANS = "DELETE FROM gameintransaction WHERE id = ?";
    private static final String INSERT_GAME_IN_TRANS = "INSERT INTO gameintransaction VALUES (id,?,?,?,?)";
    private static final String ALL_GAMES_IN_TRANS = "SELECT * FROM gameintransaction WHERE deleted=0 LIMIT ? OFFSET ?";
    private static final String SELECT_FROM_WHERE = "SELECT * FROM gameintransaction WHERE ";
    private static final String AND = "' AND ";
    private static final String CANNOT_DELETE_GAME_IN_TRANS = "Cannot delete game in transaction";
    private static final String CANNOT_UPDATE_GAME_IN_TRANS = "Cannot update game in transaction";
    private static final String CANNOT_CREATE_GAME_IN_TRANS = "Cannot create game in transaction";
    private static final String CANNOT_CREATE_FROM_RESULT = "Cannot create game in transaction from resultSet";
    private static final String CANNOT_GET_ALL_GAMES_IN_TRANS = "Cannot get all games in transaction";
    private static final String CANNOT_GET_GAME_IN_TRANS_LIST_BY_PARAMS = "Cannot get game in transaction list by params";
    private static final String CANNOT_FIND_BY_ID = "Cannot find by id";

    private ConnectionPool connectionPool;
    private Connection connection;

    @Override
    public GameInTransaction findById(int id) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        GameInTransaction gameInTransaction = new GameInTransaction();
        try (PreparedStatement statement = connection.prepareStatement(FIND_BY_ID)) {
            statement.setInt(INDEX_1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                gameInTransaction = getObjectFromResultSet((resultSet));
            }
            resultSet.close();
        } catch (SQLException e) {
            LOG.error(CANNOT_FIND_BY_ID, e);
            throw new DaoException(CANNOT_FIND_BY_ID, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return gameInTransaction;
    }

    @Override
    public List<GameInTransaction> getAll(int pageNumber, int pageSize) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        List<GameInTransaction> gameInTransactions = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(ALL_GAMES_IN_TRANS);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                gameInTransactions.add(getObjectFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            LOG.error(CANNOT_GET_ALL_GAMES_IN_TRANS, e);
            throw new DaoException(CANNOT_GET_ALL_GAMES_IN_TRANS, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return gameInTransactions;
    }

    public GameInTransaction getObjectFromResultSet(ResultSet resultSet) throws DaoException {
        GameInTransaction gameInTransaction = new GameInTransaction();
        try {
            gameInTransaction.setId(resultSet.getInt(INDEX_1));
            gameInTransaction.setAmount(resultSet.getInt(INDEX_2));
        } catch (SQLException e) {
            LOG.error(CANNOT_CREATE_FROM_RESULT, e);
            throw new DaoException(CANNOT_CREATE_FROM_RESULT, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return gameInTransaction;
    }

    public List<GameInTransaction> getByParams(Map<String, String> params) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        List<GameInTransaction> gameInTransactions = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(createQuery(params))) {
            while (resultSet.next()) {
                gameInTransactions.add(getObjectFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            LOG.error(CANNOT_GET_GAME_IN_TRANS_LIST_BY_PARAMS, e);
            throw new DaoException(CANNOT_GET_GAME_IN_TRANS_LIST_BY_PARAMS, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return gameInTransactions;
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
    public GameInTransaction create(GameInTransaction gameInTransaction) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(INSERT_GAME_IN_TRANS,
                PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setInt(INDEX_1, gameInTransaction.getAmount());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            while (resultSet.next()) {
                int id = resultSet.getInt(INDEX_1);
                gameInTransaction.setId(id);
            }
        } catch (SQLException e) {
            LOG.error(CANNOT_CREATE_GAME_IN_TRANS, e);
            throw new DaoException(CANNOT_CREATE_GAME_IN_TRANS, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return gameInTransaction;
    }

    @Override
    public void update(GameInTransaction gameInTransaction) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_GAME_IN_TRANS)) {
            statement.setInt(INDEX_1, gameInTransaction.getAmount());
            statement.execute();
        } catch (SQLException e) {
            LOG.error(CANNOT_UPDATE_GAME_IN_TRANS, e);
            throw new DaoException(CANNOT_UPDATE_GAME_IN_TRANS, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public void delete(GameInTransaction gameInTransaction) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(DELETE_GAME_IN_TRANS)) {
            statement.setInt(INDEX_1, gameInTransaction.getId());
            statement.execute();
        } catch (SQLException e) {
            LOG.error(CANNOT_DELETE_GAME_IN_TRANS, e);
            throw new DaoException(CANNOT_DELETE_GAME_IN_TRANS, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }
}