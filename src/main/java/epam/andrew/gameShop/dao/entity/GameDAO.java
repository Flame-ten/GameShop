package epam.andrew.gameShop.dao.entity;

import epam.andrew.gameShop.model.Game;

import java.util.Set;

public class GameDAO implements EntityDao<Game> {

    public Game getId(int id) {
        return null;
    }

    public Set<Game> getAll() {
        return null;
    }

    public boolean create(Game game) {
        return false;
    }

    public boolean delete(int id) {
        return false;
    }

    public boolean update(Game game) {
        return false;
    }
}
