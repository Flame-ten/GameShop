package epam.andrew.gameShop.entity;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class Transaction {
    private Integer id;
    private Date date;
    private Time time;
    private Boolean deleted;
    private List<GameInTransaction> gameInTransactionList = new ArrayList<>();
    private User user;
    private TransactionStatus status;

    public Transaction(Integer id, Date date, Time time) {
        this.id = id;
        this.date = date;
        this.time = time;
    }

    public Transaction() {

    }

    public void addGame(GameInTransaction gameInTransaction) {
        gameInTransactionList.add(gameInTransaction);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", date=" + date +
                ", time=" + time +
                '}';
    }

    public Money getPrice() {
        Money totalPrice = Money.zero(CurrencyUnit.of("KZT"));
        for (GameInTransaction gameInTransaction : gameInTransactionList) {
            totalPrice = totalPrice.plus(gameInTransaction.getPrice());
        }
        return totalPrice;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public List<GameInTransaction> getGameInTransactionList() {
        return gameInTransactionList;
    }

    public void setGameInTransactionList(List<GameInTransaction> gameInTransactionList) {
        this.gameInTransactionList = gameInTransactionList;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

}
