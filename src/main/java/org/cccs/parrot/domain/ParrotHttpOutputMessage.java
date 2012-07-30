package org.cccs.parrot.domain;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpOutputMessage;

import java.io.IOException;
import java.io.OutputStream;

/**
 * User: boycook
 * Date: 30/07/2012
 * Time: 15:56
 */
public class ParrotHttpOutputMessage implements HttpOutputMessage {

    private OutputStream outputStream;
    private HttpHeaders headers;

    public ParrotHttpOutputMessage(OutputStream outputStream) {
        this(outputStream, new HttpHeaders());
    }

    public ParrotHttpOutputMessage(OutputStream outputStream, HttpHeaders headers) {
        this.outputStream = outputStream;
        this.headers = headers;
    }

    @Override
    public OutputStream getBody() throws IOException {
        return outputStream;
    }

    @Override
    public HttpHeaders getHeaders() {
        return headers;
    }
}
