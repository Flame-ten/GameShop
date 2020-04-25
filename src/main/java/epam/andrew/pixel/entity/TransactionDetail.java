package epam.andrew.pixel.entity;

public class TransactionDetail {
    private int id;
    private String userName;
    private double payment;
    private double amount;

    @Override
    public String toString() {
        return "TransactionDetail{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", payment=" + payment +
                ", amount=" + amount +
                '}';
    }

    public TransactionDetail() {

    }

    public TransactionDetail(int id, String userName, double payment,
                             double amount) {
        this.id = id;
        this.userName = userName;
        this.payment = payment;
        this.amount = amount;
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

}
