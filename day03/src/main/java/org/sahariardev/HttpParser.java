package org.sahariardev;

import java.io.InputStream;
import java.io.InputStreamReader;

public class HttpParser {

    public HttpRequest parseHttpRequest(InputStream inputStream) {
        InputStreamReader isr = new InputStreamReader(inputStream);

        HttpRequest httpRequest = new HttpRequest();

        parseRequestLine(isr, httpRequest);
        parseHeader(isr, httpRequest);
        parseBody(isr, httpRequest);

        return httpRequest;
    }

    private void parseRequestLine(InputStreamReader isr, HttpRequest httpRequest) {

    }

    private void parseHeader(InputStreamReader isr, HttpRequest httpRequest) {
    }

    private void parseBody(InputStreamReader isr, HttpRequest httpRequest) {
    }

}
