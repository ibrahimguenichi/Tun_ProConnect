package com.ProConnect.util;

import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class HttpErrorResponse {
    private String message;
    private int statusCode;
    private Map<String, String> errors;
    private List<String> generalErrors;

    public static HttpErrorResponse of(String message, int statusCode, Map<String, String> errors, List<String> generalErrors) {
        HttpErrorResponse response = new HttpErrorResponse();
        response.message = message;
        response.statusCode = statusCode;
        response.errors = errors;
        response.generalErrors = generalErrors;

        return response;
    }

    public static HttpErrorResponse of(String message, int statusCode) {
        HttpErrorResponse response = new HttpErrorResponse();
        response.message = message;
        response.statusCode = statusCode;

        return response;
    }
}
