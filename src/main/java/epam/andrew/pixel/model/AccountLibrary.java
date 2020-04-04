package epam.andrew.pixel.model;

public class AccountLibrary {
    private int id;
    private String username;
    private String gamename;

    @Override
    public String toString() {
        return "AccountLibrary{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", gamename='" + gamename + '\'' +
                '}';
    }

    public AccountLibrary(int id, String username, String gamename) {
        this.id = id;
        this.username = username;
        this.gamename = gamename;
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

    public String getGamename() {
        return gamename;
    }

    public void setGamename(String gamename) {
        this.gamename = gamename;
    }
}
