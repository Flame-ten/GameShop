package epam.andrew.pixel.entity;

public class OrderStatus {
    private int id;
    private String info;

    public OrderStatus() {

    }

    @Override
    public String toString() {
        return "Status{" +
                "id=" + id +
                ", info='" + info + '\'' +
                '}';
    }

    public OrderStatus(int id, String info) {
        this.id = id;
        this.info = info;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
