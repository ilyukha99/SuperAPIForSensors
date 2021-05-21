package org.sas.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "login", unique = true)
    private String login;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "token", nullable = false)
    private String token;
    @Column(name = "sensor_token")
    private String sensorToken;
    @Column(name = "utc_timezone")
    private int timeZone;

    private static final String UNDEFINED = "undefined";
    public User(String login, String password, int timeZone) {
        this.login = login;
        this.password = password;
        this.timeZone = timeZone;
        token = sensorToken = UNDEFINED;
    }

    public User() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getTimeZone() {
        return timeZone;
    }

    public String getSensorToken() {
        return sensorToken;
    }

    public void setSensorToken(String sensorToken) {
        this.sensorToken = sensorToken;
    }

    public void setTimeZone(int timezone) {
        this.timeZone = timezone;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return id == user.id && timeZone == user.timeZone && Objects.equals(login, user.login)
                && Objects.equals(password, user.password) && Objects.equals(token, user.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, token, timeZone);
    }

    @Override
    public String toString() {
        return "User{id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", token='" + token + '\'' +
                ", sensorToken='" + sensorToken + '\'' +
                ", timeZone=" + timeZone + '}';
    }
}
