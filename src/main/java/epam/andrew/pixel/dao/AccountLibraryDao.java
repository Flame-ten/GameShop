package epam.andrew.pixel.dao;

import epam.andrew.pixel.entity.AccountLibrary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountLibraryDao extends Dao implements EntityDao<AccountLibrary> {

    private static final Logger LOG = LoggerFactory.getLogger(AccountLibraryDao.class);
    private static final String FIND_BY_ID = "SELECT * FROM accountlibrary WHERE `user id` = ?";
    private static final String UPDATE_LIBRARY = "UPDATE accountlibrary SET `user name` = ?, `game name` = ?" +
            " WHERE `user id` = ?";
    private static final String DELETE_LIBRARY = "DELETE FROM accountlibrary WHERE `user id` = ?";
    private static final String INSERT_LIBRARY = "INSERT INTO accountlibrary VALUES (`user id`,?,?,?)";
    private static final String ALL_LIBRARIES = "SELECT `user id`, `user name` , `game name`  from accountlibrary";

    @Override
    public AccountLibrary findById(int id) throws DaoException {
        AccountLibrary library = new AccountLibrary();
        try (PreparedStatement statement = getConnection().prepareStatement(FIND_BY_ID)) {
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

    public AccountLibrary getObjectFromResultSet(ResultSet resultSet) throws DaoException {
        AccountLibrary library = new AccountLibrary();
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
    public List<AccountLibrary> getAll() throws DaoException {
        List<AccountLibrary> libraries = new ArrayList<>();
        try (PreparedStatement statement = getConnection().prepareStatement(ALL_LIBRARIES);
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
    public AccountLibrary create(AccountLibrary accountLibrary) throws DaoException {
        try (PreparedStatement statement = getConnection().prepareStatement(INSERT_LIBRARY,
                PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, accountLibrary.getUsername());
            statement.setString(2, accountLibrary.getGameName());
        } catch (SQLException e) {
            throw new DaoException("Cannot create library", e);
        }
        return accountLibrary;
    }

    @Override
    public void update(AccountLibrary accountLibrary) throws DaoException {
        try (PreparedStatement statement = getConnection().prepareStatement(UPDATE_LIBRARY)) {
            statement.setString(1, accountLibrary.getUsername());
            statement.setString(2, accountLibrary.getGameName());
        } catch (SQLException e) {
            throw new DaoException("Cannot update library", e);
        }
    }

    @Override
    public void delete(AccountLibrary accountLibrary) throws DaoException {
        try (PreparedStatement statement = getConnection().prepareStatement(DELETE_LIBRARY)) {
            statement.setInt(1, accountLibrary.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException("Cannot delete library", e);
        }
    }
}
