package epam.andrew.pixel.dao.entity;

import epam.andrew.pixel.DaoException;

public interface EntityDao<T> {
    T findById(int id) throws DaoException;

    T create(T t) throws DaoException;

    void update(T t) throws DaoException;

    void delete(T t) throws DaoException;
}
