package epam.andrew.pixel.entity;

import java.sql.Date;
import java.sql.Time;

public class Transaction {
    private int id;
    private Date date;
    private Time time;

    public Transaction(int id, Date date, Time time) {
        this.id = id;
        this.date = date;
        this.time = time;
    }

    public Transaction() {

    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", date=" + date +
                ", time=" + time +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

}
