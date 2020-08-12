package epam.andrew.gameShop.entity;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.util.Objects;

public class User {
    public static final String CURRENCY = "KZT";
    private Integer id;
    private String name;
    private String surname;
    private String login;
    private String password;
    private String email;
    private String phone;
    private String gender;
    private String country;
    private boolean deleted;
    private Money cash;
    private Role role;

    public User(Integer id, String name, String surname, String password, String login, String email, String phone,
                String gender, String country, Money cash) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.login = login;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.country = country;
        this.cash = cash;
    }

    public User() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id) &&
                Objects.equals(name, user.name) &&
                Objects.equals(surname, user.surname) &&
                Objects.equals(password, user.password) &&
                Objects.equals(login, user.login) &&
                Objects.equals(email, user.email) &&
                Objects.equals(phone, user.phone) &&
                Objects.equals(gender, user.gender) &&
                Objects.equals(country, user.country) &&
                Objects.equals(cash, user.cash);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, password, login, email, phone, gender, country, cash);
    }

    public void spendCash(Money cashAmount) {
        this.cash = this.cash.minus(cashAmount);
    }


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
                ", position='" + cash + '\'' +
                '}';
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted() {
        this.deleted = deleted;
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

    public Money getCash() {
        return cash;
    }

    public void setCash(Money cash) {
        this.setCash(Money.of(CurrencyUnit.of(User.CURRENCY), 0));
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}