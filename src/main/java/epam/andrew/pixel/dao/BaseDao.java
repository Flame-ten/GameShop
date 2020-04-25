package epam.andrew.pixel.dao;

import epam.andrew.pixel.connection.ConnectionPoolException;

import java.util.List;

public interface BaseDao<T> {
    T findById(int id) throws DaoException, ConnectionPoolException;

    List<T> getAll(int pageNumber, int pageSize) throws DaoException, ConnectionPoolException;

    T create(T t) throws DaoException, ConnectionPoolException;

    void update(T t) throws DaoException, ConnectionPoolException;

    void delete(T t) throws DaoException, ConnectionPoolException;
}
