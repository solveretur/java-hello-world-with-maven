package com.stepstone.graal_lambda.service;

import java.util.HashMap;
import java.util.Map;

public class RequestResponse {
    private final int statusCode;
    private final String body;
    private final Map<String, String> headers = new HashMap<>();
    private final boolean isBase64Encoded;

    public RequestResponse(int statusCode, String body, boolean isBase64Encoded) {
        this.headers.put("Content-Type", "application/json");
        this.statusCode = statusCode;
        this.body = body;
        this.isBase64Encoded = isBase64Encoded;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public String getBody() {
        return this.body;
    }

    public Map<String, String> getHeaders() {
        return this.headers;
    }

    public boolean isBase64Encoded() {
        return isBase64Encoded;
    }
}
