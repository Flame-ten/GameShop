package epam.andrew.gameShop.entity;

import org.joda.money.Money;

public class GameInTransaction {
    private Integer id;
    private int amount;
    private Game game;
    private Transaction transaction;
    private boolean deleted;

    public GameInTransaction() {

    }

    public GameInTransaction(int id, int amount) {
        this.id = id;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "GameInTransaction{" +
                "id=" + id +
                ", amount=" + amount +
                ", game=" + game +
                ", transaction=" + transaction +
                '}';
    }

    public Money getPrice() {
        return game.getPrice().multipliedBy(amount);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }


    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

}
