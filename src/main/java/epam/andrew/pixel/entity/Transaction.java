package epam.andrew.pixel.entity;

import java.sql.Date;
import java.sql.Time;

public class Transaction {
    private int id;
    private double payment;
    private Date date;
    private Time time;

    public Transaction(int id, double payment, Date date, Time time) {
        this.id = id;
        this.payment = payment;
        this.date = date;
        this.time = time;
    }

    public Transaction() {

    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", payment=" + payment +
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

    public double getPayment() {
        return payment;
    }

    public void setPayment(double payment) {
        this.payment = payment;
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
