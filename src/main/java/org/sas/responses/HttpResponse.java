package org.sas.responses;

import java.util.LinkedHashMap;

public class HttpResponse implements Response {
    private final LinkedHashMap<String, Object> response = new LinkedHashMap<>();
    private static final String ERROR = "error";
    private static final String CODE = "code";

    public HttpResponse(int code, String errorMessage) {
        response.put(ERROR, errorMessage);
        response.put(CODE, code);
    }

    public int getCode() {
        return (int)response.get(CODE);
    }

    public void setCode(int code) {
        response.put(CODE, code);
    }

    public String getMessage() {
        return (String)response.get(ERROR);
    }

    public void setMessage(String errorMessage) {
        response.put(ERROR, errorMessage);
    }

    @Override
    public String toString() {
        return response.toString();
    }
}
