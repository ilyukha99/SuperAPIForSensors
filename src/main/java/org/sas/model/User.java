package org.sas.model;

import java.util.Objects;

public class User {
    private int id;
    private String login;
    private String password;
    private String token;
    private int timezone;

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

    public int getTimezone() {
        return timezone;
    }

    public void setTimezone(int timezone) {
        this.timezone = timezone;
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
        return id == user.id && timezone == user.timezone && Objects.equals(login, user.login)
                && Objects.equals(password, user.password) && Objects.equals(token, user.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, token, timezone);
    }
}
