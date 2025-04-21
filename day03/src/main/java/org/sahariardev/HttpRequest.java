package org.sahariardev;

public class HttpRequest extends HttpMessage {

    private HttpMethod method;


    private String requestTarget;

    private String httpVersion;

    HttpRequest() {

    }

    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }
}
