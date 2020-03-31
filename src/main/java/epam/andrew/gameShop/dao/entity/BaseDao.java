package epam.andrew.gameShop.dao.entity;

import epam.andrew.gameShop.DaoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public abstract class BaseDao<T> implements EntityDao<T> {
    private static final Logger LOG = LoggerFactory.getLogger(BaseDao.class);
    private static final String SELECT_FROM = "SELECT * FROM ";
    private static final String WHERE_ID = " WHERE id =";
    private static final String ORDER_BY_ID = " ORDER BY id ";
    private static final String SELECT_COUNT_FROM = "SELECT count(*) FROM ";
    private static final String WHERE_NOT_DELETED = " WHERE deleted = 0 ";


    public abstract T getObjectFromResultSet(ResultSet resultSet) throws DaoException;

    protected abstract String getTableName();


    public List<T> getByParams(Map<String, String> params) throws DaoException {
        List<T> objects = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(createQueryForFindByParams(params))) {
            while (resultSet.next()) {
                objects.add(getObjectFromResultSet(resultSet));
            }
            LOG.debug("getting object list by parameters", params, objects);
        } catch (SQLException e) {
            LOG.error("cannot get objects list by parameters", e);
            throw new DaoException("cannot get objects list by parameters", e);
        }
        return objects;
    }

    public List<T> getAll(int pageNumber, int pageSize) throws DaoException {
        List<T> objects = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        builder.append("Select * FROM").append(getTableName()).append(" WHERE deleted=0 LIMIT ? OFFSET ?");
        try (PreparedStatement preparedStatement = connection.prepareStatement(builder.toString())) {
            preparedStatement.setInt(1, pageSize);
            preparedStatement.setInt(2, (pageNumber - 1) * pageSize);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                objects.add(getObjectFromResultSet(rs));
                LOG.debug("Get object list from page number - {} and page size - {} - {}", pageNumber, pageSize, objects);
            }
        } catch (SQLException e) {
            LOG.info("cannot get all object list", e);
            throw new DaoException("cannot get all object list", e);
        }
        return objects;
    }

    public List<T> getAll() throws DaoException {
        List<T> objects = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(SELECT_FROM).append(getTableName()).append(ORDER_BY_ID);
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(stringBuilder.toString())) {
            while (resultSet.next()) {
                objects.add(getObjectFromResultSet(resultSet));
            }
            LOG.info(stringBuilder.toString());
            LOG.debug("getting all object list", objects);
        } catch (SQLException e) {
            LOG.error("cannot get all object list", e);
            throw new DaoException("cannot get all object list", e);
        }
        return objects;
    }

    private String createQueryForFindByParams(Map<String, String> params) {
        StringBuilder query = new StringBuilder();
        query.append(SELECT_FROM).append(getTableName()).append("WHERE");
        for (Map.Entry<String, String> param : params.entrySet()) {
            if (params.size() == 1) {
                query.append(param.getKey()).append(" = '").append(param.getValue()).append("'");

                return query.toString();
            } else {
                query.append(param.getKey()).append(" = '").append(param.getValue()).append("'").append("AND");
            }
        }
        LOG.debug("result query", query);
        return query.substring(0, query.length() - "AND".length());

    }

}

