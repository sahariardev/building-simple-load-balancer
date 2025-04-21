package org.sahariardev;

public enum HttpStatusCode {
    SERVER_ERROR_500_METHOD_NOT_ALLOWED(500, "Internal Server Error"),
    SERVER_ERROR_501_METHOD_NOT_ALLOWED(501, "Not Implemented"),

    CLIENT_ERROR_400_BAD_REQUEST(400, "Bad Request"),
    CLIENT_ERROR_405_METHOD_NOT_ALLOWED(401, "Method Not Allowed"),
    CLIENT_ERROR_414_METHOD_NOT_ALLOWED(414, "URI Too Long");

    private final int statusCode;

    private final String statusMessage;

    HttpStatusCode(int statusCode, String statusMessage) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }
}
