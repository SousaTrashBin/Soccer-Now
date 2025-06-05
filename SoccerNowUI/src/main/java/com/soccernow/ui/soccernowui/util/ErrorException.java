package com.soccernow.ui.soccernowui.util;

public class ErrorException extends Exception {
    private final int statusCode;

    public ErrorException(String message) {
        super(message);
        this.statusCode = -1;
    }

    public ErrorException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public ErrorException(String message, Throwable cause) {
        super(message, cause);
        this.statusCode = -1;
    }

    public ErrorException(String message, int statusCode, Throwable cause) {
        super(message, cause);
        this.statusCode = statusCode;
    }

    public String getErrorMessage() {
        return getMessage();
    }

    public int getStatusCode() {
        return statusCode;
    }
}

