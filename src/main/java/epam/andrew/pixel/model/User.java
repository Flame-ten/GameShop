package epam.andrew.pixel.model;

public class User {

    private int id;
    private String name;
    private String surname;
    private String password;
    private String login;
    private String email;
    private int phone;
    private String gender;
    private String country;
    private String position;

    /*private CashAccount personsPurse;*/

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", password='" + password + '\'' +
                ", login='" + login + '\'' +
                ", email='" + email + '\'' +
                ", phone=" + phone +
                ", gender='" + gender + '\'' +
                ", country='" + country + '\'' +
                ", position='" + position + '\'' +
                '}';
    }


    public User(int id, String name, String surname, String password, String login, String email, int phone,
                String gender, String country, String position) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.login = login;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.country = country;
        this.position = position;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

  /*  public CashAccount getPersonsPurse() {
        return personsPurse;
    }

    public void setPersonsPurse(CashAccount personsPurse) {
        this.personsPurse = personsPurse;
    }*/
}