package epam.andrew.gameShop.dao.imp;

import epam.andrew.gameShop.connection.ConnectionPool;
import epam.andrew.gameShop.connection.ConnectionPoolException;
import epam.andrew.gameShop.dao.BaseDao;
import epam.andrew.gameShop.dao.Dao;
import epam.andrew.gameShop.dao.DaoException;
import epam.andrew.gameShop.entity.Image;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static epam.andrew.gameShop.util.Constant.*;

public class ImageDao extends Dao implements BaseDao<Image> {
    private static final Logger LOG = LoggerFactory.getLogger(ImageDao.class);
    private static final String FIND_BY_ID = "SELECT * FROM image WHERE id = ?";
    private static final String UPDATE_IMAGE = "UPDATE image SET name = ? WHERE id = ?";
    private static final String DELETE_IMAGE = "DELETE FROM image WHERE id = ?";
    private static final String INSERT_IMAGE = "INSERT INTO image VALUES (id,?,?,?)";
    private static final String ALL_IMAGES = "SELECT * FROM image WHERE deleted=0 LIMIT ? OFFSET ?";
    private static final String SELECT_FROM_WHERE = "SELECT * FROM image WHERE ";
    private static final String AND = "' AND ";
    private static final String CANNOT_DELETE_IMAGE = "Cannot delete image";
    private static final String CANNOT_UPDATE_IMAGE = "Cannot update image";
    private static final String CANNOT_CREATE_IMAGE = "Cannot create image";
    private static final String CANNOT_CREATE_FROM_RESULT = "Cannot create image from resultSet";
    private static final String CANNOT_GET_ALL = "Cannot get all images";
    private static final String CANNOT_FIND_BY_ID = "Cannot find by id";
    private static final String CANNOT_GET_IMAGES_BY_PARAMS = "Cannot get images by params";
    private Connection connection;
    private ConnectionPool connectionPool;

    @Override
    public Image findById(int id) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        Image image = new Image();
        try (PreparedStatement statement = connection.prepareStatement(FIND_BY_ID)) {
            statement.setInt(INDEX_1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                image = getObjectFromResultSet((resultSet));
            }
            resultSet.close();
        } catch (SQLException e) {
            LOG.error(CANNOT_FIND_BY_ID, e);
            throw new DaoException(CANNOT_FIND_BY_ID, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return image;
    }

    public List<Image> getByParams(Map<String, String> params) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        List<Image> images = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(createQuery(params))) {
            while (resultSet.next()) {
                images.add(getObjectFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            LOG.error(CANNOT_GET_IMAGES_BY_PARAMS, e);
            throw new DaoException(CANNOT_GET_IMAGES_BY_PARAMS, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return images;
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
    public List<Image> getAll(int pageNumber, int pageSize) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        List<Image> images = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(ALL_IMAGES);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                images.add(getObjectFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            LOG.error(CANNOT_GET_ALL, e);
            throw new DaoException(CANNOT_GET_ALL, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return images;
    }

    public Image getObjectFromResultSet(ResultSet resultSet) throws DaoException {
        Image image = new Image();
        try {
            image.setId(resultSet.getInt(INDEX_1));
            image.setName(resultSet.getString(INDEX_2));
            image.setPath(resultSet.getString(INDEX_3));
        } catch (SQLException e) {
            LOG.error(CANNOT_CREATE_FROM_RESULT, e);
            throw new DaoException(CANNOT_CREATE_FROM_RESULT, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return image;

    }

    @Override
    public Image create(Image image) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(INSERT_IMAGE,
                PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(INDEX_1, image.getName());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            while (resultSet.next()) {
                int id = resultSet.getInt(INDEX_1);
                image.setId(id);
            }
        } catch (SQLException e) {
            LOG.error(CANNOT_CREATE_IMAGE, e);
            throw new DaoException(CANNOT_CREATE_IMAGE, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return image;
    }

    @Override
    public void update(Image image) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_IMAGE)) {
            statement.setString(INDEX_1, image.getName());
            statement.setString(INDEX_2, image.getPath());
            statement.execute();
        } catch (SQLException e) {
            LOG.error(CANNOT_UPDATE_IMAGE, e);
            throw new DaoException(CANNOT_UPDATE_IMAGE, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public void delete(Image image) throws DaoException, ConnectionPoolException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(DELETE_IMAGE)) {
            statement.setInt(INDEX_1, image.getId());
            statement.execute();
        } catch (SQLException e) {
            LOG.error(CANNOT_DELETE_IMAGE, e);
            throw new DaoException(CANNOT_DELETE_IMAGE, e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }
}
