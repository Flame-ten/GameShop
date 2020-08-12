package epam.andrew.gameShop.entity;

public class GameInTransaction {
    private Integer id;
    private String userName;
    private double payment;
    private int amount;
    private Game game;
    private Transaction transaction;

    public GameInTransaction() {

    }

    public GameInTransaction(int id, String userName, double payment,
                             int amount) {
        this.id = id;
        this.userName = userName;
        this.payment = payment;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "GameInTransaction{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", payment=" + payment +
                ", amount=" + amount +
                ", game=" + game +
                ", transaction=" + transaction +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

}
