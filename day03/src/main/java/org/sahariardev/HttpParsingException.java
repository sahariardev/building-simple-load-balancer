package org.sahariardev;

public class HttpParsingException extends Exception {

    private final HttpStatusCode errorCode;

    public HttpParsingException(HttpStatusCode errorCode) {
        super(errorCode.getStatusMessage());
        this.errorCode = errorCode;
    }

}
