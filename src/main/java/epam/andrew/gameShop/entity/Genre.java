package epam.andrew.gameShop.entity;

import java.util.Objects;

public class Genre {
    private Integer id;
    private String info;
    private boolean deleted;

    public Genre(int id, String info) {
        this.id = id;
        this.info = info;
    }

    public Genre() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genre genre = (Genre) o;
        return id.equals(genre.id) &&
                Objects.equals(info, genre.info);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, info);
    }


    @Override
    public String toString() {
        return "Genre{" +
                "id=" + id +
                ", info='" + info + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

}
