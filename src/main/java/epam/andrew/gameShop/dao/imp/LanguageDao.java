package epam.andrew.gameShop.dao.imp;

import epam.andrew.gameShop.connection.ConnectionPool;
import epam.andrew.gameShop.connection.ConnectionPoolException;
import epam.andrew.gameShop.dao.BaseDao;
import epam.andrew.gameShop.dao.Dao;
import epam.andrew.gameShop.dao.DaoException;
import epam.andrew.gameShop.entity.Language;
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

public class LanguageDao extends Dao implements BaseDao<Language> {
    private static final Logger LOG = LoggerFactory.getLogger(LanguageDao.class);
    private static final String FIND_BY_ID = "SELECT * FROM language WHERE id = ?";
    private static final String UPDATE_LANGUAGE = "UPDATE language SET language = ? WHERE id = ?";
    private static final String DELETE_LANGUAGE = "DELETE FROM language WHERE id = ?";
    private static final String INSERT_LANGUAGE = "INSERT INTO language VALUES (id,?,?)";
    private static final String ALL_LANGUAGES = "SELECT * FROM language WHERE deleted=0 LIMIT ? OFFSET ?";
    private static final String CANNOT_DELETE_LANGUAGE = "Cannot delete language";
    private static final String CANNOT_UPDATE_LANGUAGE = "Cannot update language";
    private static final String CANNOT_CREATE_LANGUAGE = "Cannot create language";
    private static final String CANNOT_CREATE_FROM_RESULT = "Cannot create language from resultSet";
    private static final String CANNOT_GET_ALL = "Cannot get all languages";
    private static final String CANNOT_FIND_BY_ID = "Cannot find by id";
    private Connection connection;
    private ConnectionPool connectionPool;

    @Override
    public Language findById(int id) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        Language language = new Language();
        try (PreparedStatement statement = connection.prepareStatement(FIND_BY_ID)) {
            statement.setInt(INDEX_1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                language = getObjectFromResultSet((resultSet));
            }
            resultSet.close();
        } catch (SQLException e) {
            LOG.error(CANNOT_FIND_BY_ID, e);
            throw new DaoException(CANNOT_FIND_BY_ID, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return language;
    }

    @Override
    public List<Language> getAll(int pageNumber, int pageSize) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        List<Language> languages = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(ALL_LANGUAGES);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                languages.add(getObjectFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            LOG.error(CANNOT_GET_ALL, e);
            throw new DaoException(CANNOT_GET_ALL, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return languages;
    }

    public Language getObjectFromResultSet(ResultSet resultSet) throws DaoException {
        Language language = new Language();
        try {
            language.setId(resultSet.getInt(INDEX_1));
            language.setLanguage(resultSet.getString(INDEX_2));
        } catch (SQLException e) {
            LOG.error(CANNOT_CREATE_FROM_RESULT, e);
            throw new DaoException(CANNOT_CREATE_FROM_RESULT, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return language;

    }

    @Override
    public Language create(Language language) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(INSERT_LANGUAGE,
                PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(INDEX_1, language.getLanguage());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            while (resultSet.next()) {
                int id = resultSet.getInt(INDEX_1);
                language.setId(id);
            }
        } catch (SQLException e) {
            LOG.error(CANNOT_CREATE_LANGUAGE, e);
            throw new DaoException(CANNOT_CREATE_LANGUAGE, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return language;
    }

    @Override
    public void update(Language language) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_LANGUAGE)) {
            statement.setString(INDEX_1, language.getLanguage());
            statement.execute();
        } catch (SQLException e) {
            LOG.error(CANNOT_UPDATE_LANGUAGE, e);
            throw new DaoException(CANNOT_UPDATE_LANGUAGE, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public void delete(Language language) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(DELETE_LANGUAGE)) {
            statement.setInt(INDEX_1, language.getId());
            statement.execute();
        } catch (SQLException e) {
            LOG.error(CANNOT_DELETE_LANGUAGE, e);
            throw new DaoException(CANNOT_DELETE_LANGUAGE, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }
}
