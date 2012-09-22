package org.cccs.parrot.domain;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpHeaders;

import java.io.IOException;
import java.io.OutputStream;

import static junit.framework.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

/**
 * User: boycook
 * Date: 22/09/2012
 * Time: 13:29
 */
public class TestParrotHttpOutputMessage {

    private ParrotHttpOutputMessage message;
    private OutputStream outputStream;
    private HttpHeaders headers;

    @Before
    public void setup() {
        outputStream = mock(OutputStream.class);
        headers = mock(HttpHeaders.class);
        message = new ParrotHttpOutputMessage(outputStream, headers, 200);
    }

    @Test
    public void getBodyShouldWork() throws IOException {
        assertNotNull(message.getBody());
    }

    @Test
    public void getHeadersShouldWork() {
        assertNotNull(message.getHeaders());
    }
}
