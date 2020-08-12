package epam.andrew.gameShop.entity;

import java.io.InputStream;
import java.util.Objects;

public class Image {
    private Integer id;
    private String name;
    private String path;
    private boolean deleted;
    private Game game;
    private Publisher publisher;
    private User user;
    private InputStream imageStream;

    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", game=" + game +
                ", publisher=" + publisher +
                ", user=" + user +
                ", path='" + path + '\'' +
                ", imageStream=" + imageStream +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Image image = (Image) o;
        return Objects.equals(id, image.id) &&
                Objects.equals(name, image.name) &&
                Objects.equals(game, image.game) &&
                Objects.equals(publisher, image.publisher) &&
                Objects.equals(user, image.user) &&
                Objects.equals(path, image.path) &&
                Objects.equals(imageStream, image.imageStream);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, game, publisher, user, path, imageStream);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public InputStream getImageStream() {
        return imageStream;
    }

    public void setImageStream(InputStream imageStream) {
        this.imageStream = imageStream;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
