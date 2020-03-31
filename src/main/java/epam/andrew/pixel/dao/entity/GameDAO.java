package epam.andrew.pixel.dao.entity;

import epam.andrew.pixel.DaoException;
import epam.andrew.pixel.model.Game;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GameDAO extends BaseDao<Game> {
    private static final String GAME = "Company";
    private static final String FIND_BY_ID = "SELECT * FROM game WHERE id = ?";
    private static final String UPDATE_GAME = "UPDATE game SET name = ? WHERE id = ?";
    private static final String UPDATE_USERS_AVATAR = "UPDATE game SET avatar_id= ?  WHERE id = ?";
    private static final String DELETE_GAME = "DELETE FROM game WHERE id = ?";
    private static final String INSERT_GAME = "INSERT INTO game VALUES (id,?,?,?,?,?,?,?,?,NULL)";


    @Override
    public Game findById(int id) throws DaoException {
        Game game = new Game();
        try (PreparedStatement statement = getConnection().prepareStatement(FIND_BY_ID)) {
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


    @Override
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
    public Game create(Game game) throws DaoException {
        try (PreparedStatement statement = getConnection().prepareStatement(INSERT_GAME,
                PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, game.getName());
            statement.setDouble(2, game.getPrice());
            statement.setInt(3, game.getDiscount());
        } catch (SQLException e) {
            throw new DaoException("Cannot create game", e);
        }
        return game;
    }

    @Override
    public void update(Game game) throws DaoException {
        try (PreparedStatement statement = getConnection().prepareStatement(UPDATE_GAME)) {
            statement.setString(1, game.getName());
            statement.setDouble(2, game.getPrice());
            statement.setInt(3, game.getDiscount());
        } catch (SQLException e) {
            throw new DaoException("Cannot update game", e);
        }
    }

    @Override
    public void delete(Game game) throws DaoException {
        try (PreparedStatement statement = getConnection().prepareStatement(DELETE_GAME)) {
            statement.setInt(1, game.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException("Cannot delete game", e);
        }
    }

    @Override
    protected String getTableName() {
        return GAME;
    }
}
