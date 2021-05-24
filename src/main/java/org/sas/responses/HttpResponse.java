package org.sas.responses;

import java.util.LinkedHashMap;
import java.util.Map;

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

    public String getError() {
        return (String)response.get(ERROR);
    }

    public void setError(String errorMessage) {
        response.put(ERROR, errorMessage);
    }

    public Map<String, Object> getResponse() {
        return response;
    }

    public Map<String, Object> addResponseParameter(String name, Object value) {
        response.put(name, value);
        return response;
    }

    @Override
    public String toString() {
        return response.toString();
    }
}
