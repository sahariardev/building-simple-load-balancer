
package org.sahariardev;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 *
 * @author sahariar.alam
 */
public class HttpHandler {

    public static HttpRequest parsetHttpRequest(InputStream inputStream) throws IOException {
        HttpRequest httpRequest = new HttpRequest();

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String startLine = reader.readLine();
        if (startLine == null) {
            throw new IOException("Empty request");
        }

        StringTokenizer tokenizewr = new StringTokenizer(startLine);
        if (tokenizewr.countTokens() < 3) {
            throw new IOException("Invalid request line");
        }

        httpRequest.setMethod(tokenizewr.nextToken().toUpperCase());
        httpRequest.setUri(tokenizewr.nextToken());
        httpRequest.setHttpVersion(tokenizewr.nextToken().toUpperCase());

        String headerLine;

        while ((headerLine = reader.readLine()) != null && !headerLine.isEmpty()) {
            
            int colotnIndex = headerLine.indexOf(':');
            if (colotnIndex == -1) {
                throw new IOException("Invalid header line");
            }

            String headerName = headerLine.substring(0, colotnIndex).trim();
            String headerValue = headerLine.substring(colotnIndex + 1).trim();
            
            httpRequest.addHeader(headerName, headerValue);
        }

        if (httpRequest.getHeaders().containsKey("Content-Length")) {
            int contentLength = Integer.parseInt(httpRequest.getHeaders().get("Content-Length"));
            char[] body = new char[contentLength];
            reader.read(body, 0, contentLength);
            httpRequest.setBody(new String(body));
        }

        return httpRequest;
    }

    public static HttpResponse parseHttpResponse(InputStream inputStream) throws IOException {
        HttpResponse httpResponse = new HttpResponse();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String startLine = reader.readLine();
        if (startLine == null) {
            throw new IOException("Empty response");
        }

        StringTokenizer tokenizer = new StringTokenizer(startLine);
        if (tokenizer.countTokens() < 3) {
            throw new IOException("Invalid response line");
        }

        httpResponse.setHttpVersion(tokenizer.nextToken().toUpperCase());
        httpResponse.setStatusCode(Integer.parseInt(tokenizer.nextToken()));
        httpResponse.setStatusText(tokenizer.nextToken());

        String headerLine;

        while ((headerLine = reader.readLine()) != null && !headerLine.isEmpty()) {
            int colonIndex = headerLine.indexOf(':');
            if (colonIndex == -1) {
                throw new IOException("Invalid header line");
            }

            String headerName = headerLine.substring(0, colonIndex).trim();
            String headerValue = headerLine.substring(colonIndex + 1).trim();

            httpResponse.addHeader(headerName, headerValue);
        }


        if (httpResponse.getHeaders().containsKey("content-length")) {
            int contentLength = Integer.parseInt(httpResponse.getHeaders().get("content-length"));
            char[] body = new char[contentLength];
            reader.read(body, 0, contentLength);                
            httpResponse.setBody(new String(body).getBytes("UTF-8"));
        }

        return httpResponse;
    }

}
