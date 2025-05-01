

package org.sahariardev;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author sahariar.alam
 */
public class HttpRequest {
    private String method;
    private String uri;
    private String httpVersion;
    private Map<String, String> headers;
    private String body;

    public HttpRequest() {
        this.headers = new HashMap<>();
    }

    public HttpRequest(String method, String uri, String httpVersion, Map<String, String> headers, String body) {
        this.method = method;
        this.uri = uri;
        this.httpVersion = httpVersion;
        this.headers = headers;
        this.body = body;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public void setHttpVersion(String httpVersion) {
        this.httpVersion = httpVersion;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void addHeader(String name, String value) {
        this.headers.put(name, value);
    }

    public byte[] toByteArray() {
        StringBuilder request = new StringBuilder();
        request.append(method).append(" ").append(uri).append(" ").append(httpVersion).append("\r\n");
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            request.append(entry.getKey()).append(": ").append(entry.getValue()).append("\r\n");
        }
        request.append("\r\n");

        if (body != null) {
            request.append(body);
        }

        return request.toString().getBytes();
    }

    @Override
    public String toString() {
        return "HttpRequest [method=" + method + ", uri=" + uri + ", httpVersion=" + httpVersion + ", headers="
                + headers + ", body=" + body + "]";
    }
    
    
}
