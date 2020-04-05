package epam.andrew.pixel.entity;

import java.sql.Date;

public class TransactionDetail {
    private int id;
    private String userName;
    private String gameName;
    private double payment;
    private double amount;
    private Date date;

    @Override
    public String toString() {
        return "TransactionDetail{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", gameName='" + gameName + '\'' +
                ", payment=" + payment +
                ", amount=" + amount +
                ", date=" + date +
                '}';
    }

    public TransactionDetail() {

    }

    public TransactionDetail(int id, String userName, String gameName, double payment,
                             double amount, Date date) {
        this.id = id;
        this.userName = userName;
        this.gameName = gameName;
        this.payment = payment;
        this.amount = amount;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public double getPayment() {
        return payment;
    }

    public void setPayment(double payment) {
        this.payment = payment;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
