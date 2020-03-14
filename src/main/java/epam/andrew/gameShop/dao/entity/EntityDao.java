package epam.andrew.gameShop.dao.entity;

import java.util.Set;

public interface EntityDao<T> {
    T getId(int id);

    Set<T> getAll();

    boolean create(T t);

    boolean delete(int id);

    boolean update(T t);
}
