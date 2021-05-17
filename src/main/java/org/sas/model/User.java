package org.sas.model;

import java.util.Objects;

public class User {
    private int id;
    private String login;
    private String password;
    private String token;
    private int timeZone;

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

    @Override
    public String toString() {
        return "User{id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", token='" + token + '\'' +
                ", timeZone=" + timeZone + '}';
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
}
