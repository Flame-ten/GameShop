package epam.andrew.pixel.dao;

import java.util.List;

public interface EntityDao<T> {
    T findById(int id) throws DaoException;

    List<T> getAll() throws DaoException;

    T create(T t) throws DaoException;

    void update(T t) throws DaoException;

    void delete(T t) throws DaoException;
}
