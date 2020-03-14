package epam.andrew.gameShop.model;

public class User {

    private int id;
    private String name;
    private String surname;
    private String phone;
    private String gender;
    private String country;

    public User(int id, String name, String surname, String phone, String gender, String country) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.gender = gender;
        this.country = country;
    }

    public User() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
