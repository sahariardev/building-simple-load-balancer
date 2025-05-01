

package org.sahariardev;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author sahariar.alam
 */
public class HttpResponse {
    
    private String httpVersion;

    private int statusCode;

    private String statusText;

    private final Map<String, String> headers;

    private byte[] body;

    public static final int STATUS_OK = 200;
    public static final int STATUS_NOT_FOUND = 404;
    public static final int STATUS_INTERNAL_SERVER_ERROR = 500;
    public static final int STATUS_BAD_REQUEST = 400;
    public static final int STATUS_UNAUTHORIZED = 401;  

    public HttpResponse() {
        this.httpVersion = "HTTP/1.1";
        this.statusCode = STATUS_OK;
        this.statusText = "OK";
        this.headers = new HashMap<>();
        this.body = new byte[0];
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public void setStatusText(int statusCode) {
        this.statusText = getStatusText(statusCode);
    }
    public String getStatusText(int statusCode) {
        switch (statusCode) {
            case STATUS_OK:
                return "OK";
            case STATUS_NOT_FOUND:
                return "Not Found";
            case STATUS_INTERNAL_SERVER_ERROR:
                return "Internal Server Error";
            case STATUS_BAD_REQUEST:
                return "Bad Request";
            case STATUS_UNAUTHORIZED:
                return "Unauthorized";
            default:
                return "Unknown Status";
        }
    }

    public void setHttpVersion(String httpVersion) {
        this.httpVersion = httpVersion;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public void addHeader(String name, String value) {
        this.headers.put(name, value);
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public byte[] toByteArray() {
        StringBuilder response = new StringBuilder();
        response.append(httpVersion).append(" ").append(statusCode).append(" ").append(statusText).append("\r\n");
        for (Map.Entry<String, String> header : headers.entrySet()) {
            response.append(header.getKey()).append(": ").append(header.getValue()).append("\r\n");
        }
        response.append("\r\n");
        byte[] headerBytes = response.toString().getBytes();
        byte[] fullResponse = new byte[headerBytes.length + body.length];
        System.arraycopy(headerBytes, 0, fullResponse, 0, headerBytes.length);
        System.arraycopy(body, 0, fullResponse, headerBytes.length, body.length);
        return fullResponse;
    }

    @Override
    public String toString() {
        return "HttpResponse [httpVersion=" + httpVersion + ", statusCode=" + statusCode + ", statusText=" + statusText
                + ", headers=" + headers + ", body=" + Arrays.toString(body) + "]";
    }

    
}
