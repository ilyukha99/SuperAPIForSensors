package org.sas.responses;

public class AuthResponse extends HttpResponse {
    private String token;

    public AuthResponse(int code, String error, String token) {
        super(code, error);
        super.addResponseParameter("token", token);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return token;
    }
}
