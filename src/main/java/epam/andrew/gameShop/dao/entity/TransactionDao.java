package epam.andrew.gameShop.dao.entity;

import epam.andrew.gameShop.model.Transaction;

import java.util.Set;

public class TransactionDao implements EntityDao<Transaction> {
    public Transaction getId(int id) {
        return null;
    }

    public Set<Transaction> getAll() {
        return null;
    }

    public boolean create(Transaction transaction) {
        return false;
    }

    public boolean delete(int id) {
        return false;
    }

    public boolean update(Transaction transaction) {
        return false;
    }
}
