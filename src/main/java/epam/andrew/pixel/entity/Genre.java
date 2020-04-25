package epam.andrew.pixel.entity;

public class Genre {
    private int id;
    private String info;

    @Override
    public String toString() {
        return "Genre{" +
                "id=" + id +
                ", info='" + info + '\'' +
                '}';
    }

    public Genre() {
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
