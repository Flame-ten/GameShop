package epam.andrew.pixel.entity;

public class UserToGame {
    private int id;
    private String username;
    private String gameName;

    public UserToGame() {

    }

    @Override
    public String toString() {
        return "UserToGame{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", gamename='" + gameName + '\'' +
                '}';
    }

    public UserToGame(int id, String username, String gameName) {
        this.id = id;
        this.username = username;
        this.gameName = gameName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }
}
