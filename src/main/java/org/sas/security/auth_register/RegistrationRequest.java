package org.sas.security.auth_register;


import javax.validation.constraints.NotEmpty;

public class RegistrationRequest {
    @NotEmpty(message = "login can't be empty")
    private String login;
    @NotEmpty(message = "password can't be empty")
    private String password;
    private int timeZone;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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

    public void setTimeZone(int timeZone) {
        this.timeZone = timeZone;
    }
}
