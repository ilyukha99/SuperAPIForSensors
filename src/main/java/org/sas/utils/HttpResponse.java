package org.sas.utils;

import java.util.HashMap;

public class HttpResponse {
    private final HashMap<String, Object> response = new HashMap<>();

    public HttpResponse(int code, String errorMessage) {
        response.put("error", errorMessage);
        response.put("code", code);
    }

    public int getCode() {
        return (int)response.get("code");
    }

    public void setCode(int code) {
        response.put("code", code);
    }

    public String getMessage() {
        return (String)response.get("error");
    }

    public void setMessage(String errorMessage) {
        response.put("error", errorMessage);
    }

    @Override
    public String toString() {
        return response.toString();
    }
}
